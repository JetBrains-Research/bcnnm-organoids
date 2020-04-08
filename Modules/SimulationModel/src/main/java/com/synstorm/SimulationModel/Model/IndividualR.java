package com.synstorm.SimulationModel.Model;

import com.synstorm.DES.AllowedEvent;
import com.synstorm.DES.EventsDispatcher;
import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.DES.IProceedResponse;
import com.synstorm.SimulationModel.CellLineage.R.Neuron;
import com.synstorm.SimulationModel.Compartments.DendriteTree;
import com.synstorm.SimulationModel.Connections.Synapse;
import com.synstorm.SimulationModel.LogicObjectR.AbstractCompartment;
import com.synstorm.SimulationModel.LogicObjectR.Cell;
import com.synstorm.SimulationModel.LogicObjectR.Compartment;
import com.synstorm.SimulationModel.LogicObjectR.LogicObject;
import com.synstorm.SimulationModel.SpaceShell.Shell;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ICompartmentDescription;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathway;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.Mechanisms.MechanismResponse.*;
import com.synstorm.common.Utils.ModelConfiguration.ModelConfig;
import com.synstorm.common.Utils.ModelStatistic.IndividualStatistics;
import com.synstorm.common.Utils.SimulationEvents.IndividualEvents.ObjectAddEventR;
import com.synstorm.common.Utils.SimulationEvents.IndividualEvents.ObjectDeleteEventR;
import com.synstorm.common.Utils.SimulationEvents.IndividualEvents.ObjectDifferentiatedEventR;
import com.synstorm.common.Utils.SimulationEvents.SpaceEvents.BaseSignalEvent;
import com.synstorm.common.Utils.TraceMessageWriter.PriorityTraceWriter;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Model_v1
public class IndividualR {
    //region Fields
    private final UUID uuid;
    private final Shell spaceShell;
    private final Set<ObjectMovedResponse> potentialMovedObjects;
    protected final TIntObjectMap<LogicObject> indLObjects;
    protected final ModelConfig modelConfig;
    protected final EventsDispatcher eventsDispatcher;
    protected long nextTick;

    private final Map<IProceedResponse, IProceedMethodResponse> proceedMethods;
    private final IndividualStatistics statistics;
    //endregion

    //region Constructors
    public IndividualR(UUID id) {
        uuid = id;
        potentialMovedObjects = new LinkedHashSet<>();
        spaceShell = Model.INSTANCE.getSpaceShell();
        eventsDispatcher = Model.INSTANCE.getEventsDispatcher();
        modelConfig = ModelConfig.INSTANCE;
        indLObjects = new TIntObjectHashMap<>();
        statistics = new IndividualStatistics(uuid, "");
        nextTick = 0;

        proceedMethods = new HashMap<>();
        initializeProceedMethods();

        IndividualInitializerR.INSTANCE.addInitialObjects().forEach(this::executeProceedMethod);
        updateIndividualState();
    }
    //endregion

    //region Getters and Setters
    public UUID getId() {
        return uuid;
    }

    public TIntObjectMap<LogicObject> getIndLObjects() {
        return indLObjects;
    }

    public IndividualStatistics getStatistics() {
        return statistics;
    }
    //endregion

    //region Public Methods
    public void calculateState() {
        Model.INSTANCE.updateCurrentToNext();
        final List<IEventExecutionResult> eventExecutionResults = eventsDispatcher.calculateNextTick();
        eventExecutionResults.forEach(this::executeProceedMethod);

        if (potentialMovedObjects.size() > 0) {
            spaceShell.moveLObjects(potentialMovedObjects);
            potentialMovedObjects.clear();
        }

        updateIndividualState();
    }

    public TIntSet getObjectIdsByTypes(Set<String> types) {
        final TIntSet result = new TIntHashSet();

        indLObjects.forEachEntry((k, v) -> {
            if (types.contains(v.getType()))
                return result.add(k);
            return true;
        });

        return result;
    }

    public void instantTraumatizing(ArrayList<Integer> cellIds, ISignalingPathway customPathway) {
        cellIds.forEach(id -> {
            final LogicObject cell = indLObjects.get(id);
            cell.startSignalingPathway(customPathway);
            cell.disruptAllSignalingPathwaysExcept(new HashSet<AllowedEvent>() {{
                add(customPathway.getPathway());
            }});
        });

        updateIndividualState();
    }

    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    private void initializeProceedMethods() {
        proceedMethods.put(ProceedResponseMethods.CoordinateObjectAddedResponse, this::proceedCoordinateObjectAddedResponse);
        proceedMethods.put(ProceedResponseMethods.CellAddedResponse, this::proceedCellAddedResponse);
        proceedMethods.put(ProceedResponseMethods.ObjectDeletedResponse, this::proceedObjectDeletedResponse);
        proceedMethods.put(ProceedResponseMethods.CellNecrotizeResponse, this::proceedCellNecrotizeResponse);
        proceedMethods.put(ProceedResponseMethods.ObjectDifferentiatedResponse, this::proceedObjectDifferentiatedResponse);
        proceedMethods.put(ProceedResponseMethods.ObjectMovedResponse, this::proceedObjectMovedResponse);
        proceedMethods.put(ProceedResponseMethods.CompartmentAddedResponse, this::proceedCompartmentAddedResponse);
        proceedMethods.put(ProceedResponseMethods.SynapseAddedResponse, this::proceedSynapseFormAddedResponse);
        proceedMethods.put(ProceedResponseMethods.SpreadChemicalSignalResponse, this::proceedSpreadChemicalSignalResponse);
        proceedMethods.put(ProceedResponseMethods.GatherChemicalSignalResponse, this::proceedGatherChemicalSignalResponse);
    }

    private void executeProceedMethod(@NotNull IEventExecutionResult response) {
        proceedMethods.get(response.getProceedMethod()).execute(response);
    }

    private void proceedSpreadChemicalSignalResponse(IEventExecutionResult response) {
        final ChemicalSignalResponse chemicalSignalResponse = (ChemicalSignalResponse) response;
        final int objectId = chemicalSignalResponse.getObjectId();
        final int signalId = chemicalSignalResponse.getSignalId();
        BaseSignalEvent event = new BaseSignalEvent(nextTick, objectId, SimulationEvents.SpreadChemicalSignal, signalId);
        Model.INSTANCE.proceedExportAndStatistic(event);
    }

    private void proceedGatherChemicalSignalResponse(IEventExecutionResult response) {
        final ChemicalSignalResponse chemicalSignalResponse = (ChemicalSignalResponse) response;
        final int objectId = chemicalSignalResponse.getObjectId();
        final int signalId = chemicalSignalResponse.getSignalId();
        BaseSignalEvent event = new BaseSignalEvent(nextTick, objectId, SimulationEvents.GatherChemicalSignal, signalId);
        Model.INSTANCE.proceedExportAndStatistic(event);
    }

    private void proceedCoordinateObjectAddedResponse(IEventExecutionResult response) {
        final CoordinateObjectAddedResponse cResponse = (CoordinateObjectAddedResponse) response;
        final int parentId = cResponse.getParentId();
        final String objectType = cResponse.getObjectType();
        final int[] coordinate = cResponse.getCoordinate();
        final ILogicObjectDescription description = modelConfig.getLDescription(objectType);
        final LogicObject signalPoint = (LogicObject) description.getBaseType().create(parentId, description);
        final int signalPointId = signalPoint.getObjectId();
        spaceShell.addObject(signalPointId, coordinate, description.getState());
        indLObjects.put(signalPointId, signalPoint);

        ObjectAddEventR event = new ObjectAddEventR(nextTick, signalPointId, parentId, objectType, coordinate, spaceShell.getNegativeCoordinate());
        Model.INSTANCE.proceedExportAndStatistic(event);
    }

    private void proceedCellAddedResponse(IEventExecutionResult response) {
        final CellAddedResponse cResponse = (CellAddedResponse) response;
        final int parentId = cResponse.getParentId();
        final String objectType = cResponse.getObjectType();
        final ILogicObjectDescription description = modelConfig.getLDescription(objectType);

        if (spaceShell.addNeighbor(parentId, -1, description.getState())) {
            final Cell cell = (Cell) description.getBaseType().create(parentId, description);
            final int cellId = cell.getObjectId();
            final int[] cellCoordinate = spaceShell.getCoordinate(-1);
            final int[] previousCoordinate = spaceShell.getCoordinate(parentId);
            spaceShell.replaceObject(-1, cellId, description.getState());
            indLObjects.put(cellId, cell);
            ObjectAddEventR event = new ObjectAddEventR(nextTick, cellId, parentId, objectType, cellCoordinate, previousCoordinate);
            Model.INSTANCE.proceedExportAndStatistic(event);
        }
    }

    private void proceedCellNecrotizeResponse(IEventExecutionResult response) {
        final CellNecrotizeResponse cResponse = (CellNecrotizeResponse) response;
        final int parentId = cResponse.getParentId();
        final LogicObject deletedObject = indLObjects.remove(parentId);
        final String cellType = deletedObject.getType();
        final int[] coordinate = spaceShell.getCoordinate(parentId);
        deletedObject.markAsDeleted();
        spaceShell.removeObject(parentId);

        final String objectType = cResponse.getObjectType();
        final ILogicObjectDescription description = modelConfig.getLDescription(objectType);
        final LogicObject signalPoint = (LogicObject) description.getBaseType().create(parentId, description);
        final int signalPointId = signalPoint.getObjectId();
        spaceShell.addObject(signalPointId, coordinate, description.getState());
        indLObjects.put(signalPointId, signalPoint);

        ObjectDeleteEventR addEvent = new ObjectDeleteEventR(nextTick, parentId, cellType);
        Model.INSTANCE.proceedExportAndStatistic(addEvent);

        ObjectAddEventR deleteEvent = new ObjectAddEventR(nextTick, signalPointId, parentId, objectType, coordinate, coordinate);
        Model.INSTANCE.proceedExportAndStatistic(deleteEvent);
    }

    private void proceedObjectDeletedResponse(IEventExecutionResult response) {
        final ObjectDeletedResponse cResponse = (ObjectDeletedResponse) response;
        final int id = cResponse.getId();
        final LogicObject deletedObject = indLObjects.remove(id);
        final String objType = deletedObject.getType();
        deletedObject.markAsDeleted();
        spaceShell.removeObject(id);

        ObjectDeleteEventR event = new ObjectDeleteEventR(nextTick, id, objType);
        Model.INSTANCE.proceedExportAndStatistic(event);
    }

    private void proceedObjectDifferentiatedResponse(IEventExecutionResult response) {
        final ObjectDifferentiatedResponse cResponse = (ObjectDifferentiatedResponse) response;
        final int id = cResponse.getId();
        final String cellType = cResponse.getCellType();
        final ILogicObjectDescription description = modelConfig.getLDescription(cellType);
        final Cell cell = (Cell) description.getBaseType().create(-1, description);
        final int cellId = cell.getObjectId();
        final LogicObject deletedObject = indLObjects.remove(id);
        final String previousType = deletedObject.getType();
        deletedObject.markAsDeleted();
        spaceShell.replaceObject(id, cellId, description.getState());
        indLObjects.put(cellId, cell);

        ObjectDifferentiatedEventR event = new ObjectDifferentiatedEventR(nextTick, cellId, id, cell.getType(), previousType);
        Model.INSTANCE.proceedExportAndStatistic(event);
    }

    private void proceedObjectMovedResponse(IEventExecutionResult response) {
        potentialMovedObjects.add((ObjectMovedResponse) response);
    }

    private void proceedCompartmentAddedResponse(IEventExecutionResult response) {
        final CompartmentAddedResponse cResponse = (CompartmentAddedResponse) response;
        final int parentId = cResponse.getParentId();
        final String objectType = cResponse.getObjectType();
        final ICompartmentDescription description = (ICompartmentDescription) modelConfig.getLDescription(objectType);
        final int[] coordinate = spaceShell.getCoordinate(parentId);
        final Compartment compartment = (Compartment) description.getBaseType().create(parentId, description);
        final int compartmentId = compartment.getObjectId();
        spaceShell.addObject(compartmentId, coordinate, description.getState());
        indLObjects.put(compartmentId, compartment);

        ((AbstractCompartment) indLObjects.get(parentId)).addCompartment(compartmentId, compartment);

        ObjectAddEventR event = new ObjectAddEventR(nextTick, compartmentId, parentId, objectType, coordinate, spaceShell.getNegativeCoordinate());
        Model.INSTANCE.proceedExportAndStatistic(event);
    }

    private void proceedSynapseFormAddedResponse(IEventExecutionResult response) {
        final SynapseAddedResponse cResponse = (SynapseAddedResponse) response;
        final int srcAxonTerminalId = cResponse.getParentId();
        final int destCellId = cResponse.getDstCellId();
        final String objectType = cResponse.getObjectType();
        final ICompartmentDescription description = (ICompartmentDescription) modelConfig.getLDescription(objectType);
        final int[] coordinate = spaceShell.getCoordinate(srcAxonTerminalId);
        final Optional<DendriteTree> dendriteTreeOptional = ((Neuron) indLObjects.get(destCellId)).getDendriteTree();
        if (dendriteTreeOptional.isPresent()) {
            final DendriteTree dendriteTree = dendriteTreeOptional.get();
            final Synapse synapse = (Synapse) description.getBaseType().create(srcAxonTerminalId, description);
            final int synapseId = synapse.getObjectId();
            spaceShell.addObject(synapseId, coordinate, description.getState());
            indLObjects.put(synapseId, synapse);

            final int srcCellId = ((AbstractCompartment) indLObjects.get(srcAxonTerminalId)).getRootId();
            final int dstSpineId = dendriteTree.getSpineCandidate();
            synapse.connect(srcAxonTerminalId, dstSpineId, srcCellId, destCellId);

            ((Neuron) indLObjects.get(srcCellId)).addOutboundSynapse(synapse);
            ((Neuron) indLObjects.get(destCellId)).addInboundSynapse(synapse);

            ObjectAddEventR event = new ObjectAddEventR(nextTick, synapseId, srcAxonTerminalId, objectType, coordinate, spaceShell.getNegativeCoordinate());
            Model.INSTANCE.proceedExportAndStatistic(event);
        } else {
            PriorityTraceWriter.printf(0, "error: id=%d %s\n", destCellId, "doesn't have a dendrite tree");
        }
    }

    private void updateIndividualState() {
        spaceShell.recalculateSignalSpreading();
        nextTick = eventsDispatcher.updateState();
        Model.INSTANCE.updateNextTick(nextTick);
    }
    //endregion

    @FunctionalInterface
    private interface IProceedMethodResponse {
        void execute(IEventExecutionResult response);
    }
}
