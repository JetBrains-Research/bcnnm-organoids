package com.synstorm.SimulationModel.Model;

import com.synstorm.SimulationModel.CellLineage.AbstractCells.Neuron;
import com.synstorm.SimulationModel.Containers.ModelContainer;
import com.synstorm.SimulationModel.Graph.SynapticGraph;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.*;
import com.synstorm.SimulationModel.LogicObject.InitialPotentialCellObject;
import com.synstorm.SimulationModel.LogicObject.LogicObjects.CellObject;
import com.synstorm.SimulationModel.LogicObject.LogicObjects.CellsFactory;
import com.synstorm.SimulationModel.LogicObject.LogicObjects.GlowingPoint;
import com.synstorm.SimulationModel.LogicObject.PotentialCellObject;
import com.synstorm.SimulationModel.ModelAction.ActionDispatcher;
import com.synstorm.SimulationModel.ModelTime.ModelTime;
import com.synstorm.SimulationModel.ModelTime.Tick;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.AxonId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.GlowingPointId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.LObjectId;
import com.synstorm.SimulationModel.SpaceShell.DimensionalSpace;
import com.synstorm.SimulationModel.Synapses.Synapse;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
import com.synstorm.common.Utils.Details.GPDetails;
import com.synstorm.common.Utils.Details.IndividualDetails;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;
import com.synstorm.common.Utils.EnumTypes.StatisticType;
import com.synstorm.common.Utils.ModelExport.IModelDataExporter;
import com.synstorm.common.Utils.ModelStatistic.IndividualStatistics;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;
import com.synstorm.common.Utils.SimulationEvents.IndividualEvents.*;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

import java.util.*;

/**
 * Class for individual model
 * Created by dvbozhko on 10/06/16.
 */

@Model_v0
@ProductionLegacy
public class Individual {
    //region Fields
    protected Tick currentTick;
    protected Tick nextTick;
    private final UUID uuid;
    protected final Map<CellId, CellObject> cellObjects;
    private final DimensionalSpace dimensionalSpace;
    private final ModelTime modelTime;
    private final EnumMap<ProceedResponseMethods, IProceedMethodResponse> proceedMethods;
    private final EnumMap<StatisticType, IProceedCondition> checkConditionsMethods;
    private final Set<ObjectMovedResponse> potentialMovedObjects;
    private final IndividualStatistics statistics;
    private final List<IModelDataExporter> exporters;
    private final IModelDataExporter statisticsExporter;
    protected final int exportCheckpointPercentageQuantum;
    private final long maxTicks;
    protected int previousPercentage;
    protected int percentage;
    protected final Map<Integer, CellId> initialNeurons;
    protected final ActionDispatcher actionDispatcher;

    protected final SynapticGraph network;
    //protected final NetworkActivity networkActivity;
    //endregion

    //region Constructors
    private Individual(UUID id, String hash, List<IModelDataExporter> iExporters, IModelDataExporter statExporter,
                       byte[] individualSeed) throws Exception {
        modelTime = new ModelTime();
        currentTick = modelTime.getCurrentTick();
        uuid = id;
        cellObjects = new LinkedHashMap<>();
        proceedMethods = new EnumMap<>(ProceedResponseMethods.class);
        checkConditionsMethods = new EnumMap<>(StatisticType.class);
        potentialMovedObjects = new LinkedHashSet<>();
        dimensionalSpace = new DimensionalSpace(id);
        actionDispatcher = new ActionDispatcher(uuid);
        network = new SynapticGraph();
        //networkActivity = new NetworkActivity();

        statistics = new IndividualStatistics(uuid, hash);
        exporters = iExporters;
        statisticsExporter = statExporter;
        initialNeurons = new HashMap<>();
        exportCheckpointPercentageQuantum = ModelLoader.getIndividualCheckpointQuantum();
        maxTicks = Long.valueOf(ModelLoader.getCalculationConditionValue(StatisticType.MaxTick));
        previousPercentage = 0;
        percentage = 0;

        ModelContainer.INSTANCE.addAdditionalElement(id, modelTime, dimensionalSpace, individualSeed);
        initializeProceedMethods();
        initializeCheckConditionsMethods();
        initializeGP();
        ModelLoader.getCellTypes().entrySet().stream()
                .forEach(cellTypeEntry -> exporters.stream()
                        .forEach(exporter -> exporter.write(
                                new CellTypeExportEvent(cellTypeEntry.getKey(), cellTypeEntry.getValue()))));
        for (StatisticType statisticType : StatisticType.values())
            exporters.stream().forEach(exporter -> exporter.write(new StatisticTypeExportEvent(statisticType)));
    }

    public Individual(IndividualDetails iDetails, List<IModelDataExporter> iExporters, IModelDataExporter statExporter,
                      byte[] individualSeed) throws Exception {
        this(iDetails.getUuid(), iDetails.getActionsHash(), iExporters, statExporter, individualSeed);
        IndividualInitializer.INSTANCE.addInitialCells(uuid, iDetails).stream()
                .forEach(response -> {
                    try { executeProceedMethod(response); }
                    catch (Exception e) { throw new RuntimeException(e); }
                });

        IndividualInitializer.INSTANCE.connectInitialSynapses(uuid, initialNeurons, cellObjects, iDetails).stream()
                .forEach(response -> {
                    try { executeProceedMethod(response); }
                    catch (Exception e) { throw new RuntimeException(e); }
                });

        refreshActionStates();
    }
    //endregion

    //region Getters and Setters
    public UUID getId() {
        return uuid;
    }

    public int getPercentage() {
        return percentage;
    }

    public boolean checkConditions() {
        List<Boolean> result = new ArrayList<>();
        ModelLoader.getAllCalculationConditions().forEach(sType -> result.add(executeCheckConditionMethod(sType)));

        return result.stream().anyMatch(e -> e);
    }

    public IndividualStatistics getStatistics() {
        return statistics;
    }
    //endregion

    //region Public Methods
    public void calculateState() throws Exception {
        modelTime.tick(nextTick.getTick());
        currentTick = modelTime.getCurrentTick();
        proceedExportAndStatistic(new TickEvent(currentTick.getTick()));
        CalculationResult calcResult = actionDispatcher.executeActions(currentTick);
        final boolean isSpaceUpdated = calcResult.hasUpdatingSpaceResponse();
        final List<IMethodResponse> responses = calcResult.getResponses();
        for (IMethodResponse response : responses)
            executeProceedMethod(response);

        if (isSpaceUpdated) {
            dimensionalSpace.moveLObjects(potentialMovedObjects);
            potentialMovedObjects.clear();
            updateSpaceInfo();
        }

        refreshActionStates();
    }

    public void exportStatistic() {
        statistics.getStatisticFormEvents().forEach(statisticsExporter::write);
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    protected boolean timeForCheckpoint() {
        percentage = (int)((currentTick.getTick() / (double) maxTicks) * 100);
        if (percentage - previousPercentage >= exportCheckpointPercentageQuantum) {
            previousPercentage = percentage;
            return true;
        } else
            return false;
    }

    protected void refreshActionStates() {
        timeForCheckpoint();
        actionDispatcher.updateActionsState(currentTick);
        updateLogicObjectSignificantTick();
        ModelContainer.INSTANCE.updateActionDescriptionsInfo(uuid);
    }

    protected void proceedExportAndStatistic(ISimulationEvent event) {
        exporters.forEach(exporter -> exporter.write(event));
        statistics.proceedEvent(event);
    }
    //endregion

    //region Private Methods
    private void initializeCheckConditionsMethods() {
        checkConditionsMethods.put(StatisticType.MaxTick, this::checkMaxTicks);
        checkConditionsMethods.put(StatisticType.FinalCellCount, this::checkCellCount);
    }

    private boolean executeCheckConditionMethod(StatisticType statisticType) {
        return checkConditionsMethods.get(statisticType).execute();
    }

    private boolean checkMaxTicks() {
        return nextTick.getTick() >= maxTicks;
    }

    private boolean checkCellCount() {
        return cellObjects.size() >= Integer.valueOf(ModelLoader.getCalculationConditionValue(StatisticType.FinalCellCount));
    }

    private void initializeProceedMethods() throws Exception {
        proceedMethods.put(ProceedResponseMethods.ObjectAddedResponse, this::proceedObjectAddedResponse);
        proceedMethods.put(ProceedResponseMethods.InitialObjectAddedResponse, this::proceedInitialObjectAddedResponse);
        proceedMethods.put(ProceedResponseMethods.ObjectDeletedResponse, this::proceedObjectDeletedResponse);
        proceedMethods.put(ProceedResponseMethods.ObjectProliferatedResponse, this::proceedObjectProliferatedResponse);
        proceedMethods.put(ProceedResponseMethods.ObjectMovedResponse, this::proceedObjectMovedResponse);
        proceedMethods.put(ProceedResponseMethods.AxonFormResponse, this::proceedAxonFormResponse);
        proceedMethods.put(ProceedResponseMethods.AxonGrownResponse, this::proceedAxonGrownResponse);
        proceedMethods.put(ProceedResponseMethods.InitialAxonGrownResponse, this::proceedInitialAxonGrownResponse);
        proceedMethods.put(ProceedResponseMethods.SynapseAddedResponse, this::proceedSynapseAddedResponse);
        proceedMethods.put(ProceedResponseMethods.InitialSynapseAddedResponse, this::proceedInitialSynapseAddedResponse);
        proceedMethods.put(ProceedResponseMethods.SynapsesDeletedResponse, this::proceedSynapsesDeletedResponse);
        proceedMethods.put(ProceedResponseMethods.SynapticPowerChangedResponse, this::proceedSynapticPowerChangedResponse);
        proceedMethods.put(ProceedResponseMethods.NeurotransmitterReleaseResponse, this::proceedNeurotransmitterReleaseResponse);
    }

    private void executeProceedMethod(IMethodResponse response) throws Exception {
        proceedMethods.get(response.getMethodType()).execute(response);
    }

    private void initializeGP() {
        List<GPDetails> gpDetails = ModelLoader.getGPsList();
        gpDetails
                .forEach(details ->
                        dimensionalSpace.addGlowingPoint(new GlowingPoint(new GlowingPointId(uuid), uuid, details)));
    }

    private void proceedObjectAddedResponse(IMethodResponse response) throws Exception {
        PotentialCellObject added = ((ObjectAddedResponse) response).getPotentialCellObject();
        ICoordinate coordinate = added.getCoordinate();
        boolean canAdd = dimensionalSpace.tryAddObject(coordinate);
        if (!canAdd)
            return;

        ICoordinate nearCoordinate = dimensionalSpace.getNearRandomCoordinateForCreation(coordinate);
        CellObject toAdd = CellsFactory.INSTANCE.createCell(added, actionDispatcher);
        CellId cellId = (CellId) toAdd.getObjectId();
        CellId parentId = (CellId) toAdd.getParentId();
        String cellType = toAdd.getCellType();
        ICoordinate previousCoordinate = cellObjects.get(parentId).getCoordinate();

        ObjectAddEvent objectAddEvent = new ObjectAddEvent(currentTick.getTick(), cellId, parentId, cellType, coordinate, previousCoordinate);
        proceedExportAndStatistic(objectAddEvent);

//        if (toAdd instanceof Neuron)
//            ((Neuron) toAdd).setNetworkActivityListener(networkActivity);

        toAdd.setNearCoordinateForCreation(nearCoordinate);
        dimensionalSpace.addLObject(cellId, coordinate, !toAdd.getMovingAbility());
        dimensionalSpace.updateCellsForGpWithKp(cellId, toAdd.getReceivedFactors());
        cellObjects.put(cellId, toAdd);
    }

    private void proceedInitialObjectAddedResponse(IMethodResponse response) throws Exception {
        InitialPotentialCellObject added = ((InitialObjectAddedResponse) response).getPotentialCellObject();
        ICoordinate coordinate = added.getCoordinate();
        boolean canAdd = dimensionalSpace.tryAddObject(coordinate);
        if (!canAdd)
            return;

        ICoordinate nearCoordinate = dimensionalSpace.getNearRandomCoordinateForCreation(coordinate);
        CellObject toAdd = CellsFactory.INSTANCE.createCell(added, actionDispatcher);
        CellId cellId = (CellId) toAdd.getObjectId();
        LObjectId parentId = toAdd.getParentId();
        String cellType = toAdd.getCellType();

        InitialObjectAddEvent objectAddEvent = new InitialObjectAddEvent(cellId, parentId, cellType, coordinate);
        proceedExportAndStatistic(objectAddEvent);

        if (toAdd instanceof Neuron) {
            //((Neuron) toAdd).setNetworkActivityListener(networkActivity);
            initialNeurons.put(added.getConfigId(), cellId);
        }

        toAdd.setNearCoordinateForCreation(nearCoordinate);
        dimensionalSpace.addLObject(cellId, coordinate, !toAdd.getMovingAbility());
        dimensionalSpace.updateCellsForGpWithKp(cellId, toAdd.getReceivedFactors());
        cellObjects.put(cellId, toAdd);
    }

    private void proceedObjectDeletedResponse(IMethodResponse response) throws Exception {
        CellId deletedId = ((ObjectDeletedResponse) response).getId();
        CellObject toDelete = cellObjects.get(deletedId);
        String cellType = cellObjects.get(deletedId).getCellType();
        ObjectDeleteEvent objectDeleteEvent = new ObjectDeleteEvent(currentTick.getTick(), deletedId, cellType);
        proceedExportAndStatistic(objectDeleteEvent);
        dimensionalSpace.removeLObject(deletedId);
        dimensionalSpace.updateCellsForGpWithKp(deletedId, toDelete.getReceivedFactors());
        actionDispatcher.removeMethods(deletedId);
        cellObjects.remove(deletedId);
    }

    private void proceedObjectProliferatedResponse(IMethodResponse response) throws Exception {
        PotentialCellObject proliferated = ((ObjectProliferatedResponse) response).getPotentialCellObject();
        CellId proliferatedId = ((ObjectProliferatedResponse) response).getId();
        ICoordinate nearCoordinate = cellObjects.get(proliferatedId).getNearCoordinateForCreation();
        CellObject toProliferate = CellsFactory.INSTANCE.createCell(proliferatedId, proliferated, actionDispatcher);
        String cellType = toProliferate.getCellType();
        String previousCellType = cellObjects.get(proliferatedId).getCellType();
        ObjectProliferateEvent objectProliferateEvent = new ObjectProliferateEvent(currentTick.getTick(), proliferatedId, cellType, previousCellType);
        proceedExportAndStatistic(objectProliferateEvent);
        cellObjects.remove(proliferatedId);

//        if (toProliferate instanceof Neuron)
//            ((Neuron) toProliferate).setNetworkActivityListener(networkActivity);

        toProliferate.setNearCoordinateForCreation(nearCoordinate);
        dimensionalSpace.updatePermanencyAfterProliferation(proliferatedId, !toProliferate.getMovingAbility());
        dimensionalSpace.updateCellsForGpWithKp(proliferatedId, toProliferate.getReceivedFactors());
        cellObjects.put(proliferatedId, toProliferate);
    }

    private void proceedObjectMovedResponse(IMethodResponse response) throws Exception {
        potentialMovedObjects.add((ObjectMovedResponse) response);
    }

    private void proceedAxonFormResponse(IMethodResponse response) throws Exception {
        AxonFormResponse axonFormResponse = (AxonFormResponse) response;
        dimensionalSpace.addAxon(axonFormResponse.getAxonId(), axonFormResponse.getCoordinate());
        ((Neuron) cellObjects.get(axonFormResponse.getCellId())).getGrowFactors()
                .forEach(signal -> dimensionalSpace.setSignalIgnore(
                        axonFormResponse.getAxonId(),
                        axonFormResponse.getCellId(),
                        signal));
    }

    private void proceedAxonGrownResponse(IMethodResponse response) throws Exception {
        AxonGrownResponse axonGrowResponse = (AxonGrownResponse) response;
        AxonId axonId = axonGrowResponse.getAxonId();
        CellId cellId = axonGrowResponse.getCellId();
        ICoordinate coordinateToGrow = axonGrowResponse.getCoordinateToGrow();
        ICoordinate currentCoordinate = axonGrowResponse.getCurrentCoordinate();
        AxonGrowEvent axonGrowEvent = new AxonGrowEvent(currentTick.getTick(), cellId, currentCoordinate, coordinateToGrow);
        proceedExportAndStatistic(axonGrowEvent);
        dimensionalSpace.moveAxonEnding(axonId, coordinateToGrow);
    }

    private void proceedInitialAxonGrownResponse(IMethodResponse response) throws Exception {
        InitialAxonGrownResponse axonGrowResponse = (InitialAxonGrownResponse) response;
        AxonId axonId = axonGrowResponse.getAxonId();
        CellId cellId = axonGrowResponse.getCellId();
        ICoordinate coordinateToGrow = axonGrowResponse.getCoordinateToGrow();
        ICoordinate currentCoordinate = axonGrowResponse.getCurrentCoordinate();
        int order = axonGrowResponse.getGrowOrder();
        AxonGrowEvent axonGrowEvent = new AxonGrowEvent(order, cellId, currentCoordinate, coordinateToGrow);
        proceedExportAndStatistic(axonGrowEvent);
        dimensionalSpace.moveAxonEnding(axonId, coordinateToGrow);
    }

    private void proceedSynapseAddedResponse(IMethodResponse response) throws Exception {
        SynapseAddedResponse synapseAddedResponse = (SynapseAddedResponse) response;
        Synapse addedSynapse = synapseAddedResponse.getSynapse();
        AxonId axonId = synapseAddedResponse.getAxonId();
        CellId preId = addedSynapse.getPresynapticNeuron();
        CellId postId = addedSynapse.getPostsynapticNeuron();
        SynapseAddEvent synapseAddEvent = new SynapseAddEvent(currentTick.getTick(), preId, postId);
        proceedExportAndStatistic(synapseAddEvent);
        Neuron postSynNeuron = (Neuron) cellObjects.get(postId);
        postSynNeuron.activateDendriteSynapse(addedSynapse);
        network.createSynapse(addedSynapse);
        dimensionalSpace.setSignalIgnore(axonId, postId, postSynNeuron.getGafName());
    }

    private void proceedInitialSynapseAddedResponse(IMethodResponse response) throws Exception {
        InitialSynapseAddedResponse synapseAddedResponse = (InitialSynapseAddedResponse) response;
        Synapse addedSynapse = synapseAddedResponse.getSynapse();
        AxonId axonId = synapseAddedResponse.getAxonId();
        CellId preId = addedSynapse.getPresynapticNeuron();
        CellId postId = addedSynapse.getPostsynapticNeuron();
        int order = synapseAddedResponse.getConnectOrder();
        InitialSynapseAddEvent synapseAddEvent = new InitialSynapseAddEvent(order, preId, postId);
        proceedExportAndStatistic(synapseAddEvent);
        Neuron postSynNeuron = (Neuron) cellObjects.get(postId);
        postSynNeuron.activateDendriteSynapse(addedSynapse);
        network.createSynapse(addedSynapse);
        dimensionalSpace.setSignalIgnore(axonId, postId, postSynNeuron.getGafName());
    }

    private void proceedSynapsesDeletedResponse(IMethodResponse response) throws Exception {
        Set<Synapse> deletedSynapses = ((SynapsesDeletedResponse) response).getSynapses();
        deletedSynapses.stream()
                .filter(synapse -> !synapse.isCurrentlyStimulated())
                .forEach(synapse -> {
                    CellId preId = synapse.getPresynapticNeuron();
                    CellId postId = synapse.getPostsynapticNeuron();
                    Neuron preSynNeuron = ((Neuron) cellObjects.get(preId));
                    Neuron postSynNeuron = ((Neuron) cellObjects.get(postId));
                    SynapseDeleteEvent synapseDeleteEvent = new SynapseDeleteEvent(currentTick.getTick(), preId, postId);
                    proceedExportAndStatistic(synapseDeleteEvent);
                    preSynNeuron.removeAxonalSynapse(postId);
                    postSynNeuron.removeDendriteSynapse(preId);
                    network.deleteSynapse(synapse);
                    dimensionalSpace.setSignalReceive(postId, preId, postSynNeuron.getGafName());
                });
    }

    private void proceedSynapticPowerChangedResponse(IMethodResponse response) throws Exception {
        SynapticPowerChangedResponse synapticPowerChangedResponse = (SynapticPowerChangedResponse) response;
        Set<Synapse> changedSynapses = synapticPowerChangedResponse.getSynapses();
        changedSynapses
                .forEach(synapse -> {
                    CellId preId = synapse.getPresynapticNeuron();
                    CellId postId = synapse.getPostsynapticNeuron();
                    SynapticPowerChangedEvent synapticPowerChangedEvent =
                            new SynapticPowerChangedEvent(currentTick.getTick(), postId, preId, synapse.getSynapticPower());
                    proceedExportAndStatistic(synapticPowerChangedEvent);
                });
    }

    private void proceedNeurotransmitterReleaseResponse(IMethodResponse response) throws Exception {
        NeurotransmitterReleaseResponse neurotransmitterReleaseResponse = (NeurotransmitterReleaseResponse) response;
        CellId preId = neurotransmitterReleaseResponse.getPresynapticCellId();
        CellId postId = neurotransmitterReleaseResponse.getPostsynapticCellId();
        Neuron postSynNeuron = (Neuron) cellObjects.get(postId);
        postSynNeuron.stimulate(preId);
    }

    private void updateLogicObjectSignificantTick() {
        nextTick = actionDispatcher.getNextSignificantTick();
    }

    private void updateSpaceInfo() {
        cellObjects.forEach((cellId, cellObject) -> {
            ICoordinate coordinate = cellObject.getCoordinate();
            ICoordinate updateCoordinate = dimensionalSpace.getCoordinateById(cellId);
            if (!cellObject.getCoordinate().equals(updateCoordinate)) {
                ObjectMoveEvent objectMoveEvent = new ObjectMoveEvent(currentTick.getTick(), cellId, updateCoordinate, coordinate);
                proceedExportAndStatistic(objectMoveEvent);
                cellObject.setCoordinate(updateCoordinate);
                dimensionalSpace.updateCellsForGpWithKp(cellId, cellObject.getReceivedFactors());
                ICoordinate nearCoordinate = dimensionalSpace.getNearRandomCoordinateForCreation(updateCoordinate);
                cellObject.setNearCoordinateForCreation(nearCoordinate);
            }

            if (dimensionalSpace.isCoordinateBusy(cellObject.getNearCoordinateForCreation())) {
                ICoordinate nearCoordinate = dimensionalSpace.getNearRandomCoordinateForCreation(updateCoordinate);
                cellObject.setNearCoordinateForCreation(nearCoordinate);
            }
        });
    }
    //endregion

    @FunctionalInterface
    private interface IProceedCondition {
        boolean execute();
    }

    @FunctionalInterface
    private interface IProceedMethodResponse {
        void execute(IMethodResponse response) throws Exception;
    }
}
