package com.synstorm.SimulationModel.CellLineage.AbstractCells;

import com.synstorm.SimulationModel.Annotations.SignalingPathway;
import com.synstorm.SimulationModel.CellFunctionalComponents.Axon;
import com.synstorm.SimulationModel.CellFunctionalComponents.DendriteTree;
import com.synstorm.SimulationModel.Containers.ModelContainer;
import com.synstorm.SimulationModel.LogicObject.ActionMethodParameters.NeurotransmitterParameters;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.*;
import com.synstorm.SimulationModel.LogicObject.IActionParameters;
import com.synstorm.SimulationModel.LogicObject.InitialPotentialCellObject;
import com.synstorm.SimulationModel.LogicObject.PotentialCellObject;
import com.synstorm.SimulationModel.Model.INetworkActivityListener;
import com.synstorm.SimulationModel.ModelAction.ActionDispatcher;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.SimulationModel.Synapses.Synapse;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
import com.synstorm.common.Utils.Details.AxonDetails;
import com.synstorm.common.Utils.Details.DendriteDetails;
import com.synstorm.common.Utils.EnumTypes.ActionFunctionalType;
import com.synstorm.common.Utils.EnumTypes.SignalSelectionType;
import com.synstorm.common.Utils.SignalCoordinateProbability.CoordinateProbability;
import com.synstorm.common.Utils.SignalCoordinateProbability.SignalProbability;
import com.synstorm.common.Utils.SignalCoordinateProbability.SignalProbabilityMap;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Base abstract class for all types of neurons
 * Created by dvbozhko on 07/06/16.
 */

@Model_v0
@ProductionLegacy
public abstract class Neuron extends Cell {
    //region Fields
    Queue<Synapse> synapsesToActivate;
    String gafName;     //grow axon factor name
    String gaName;      //grow axon name
    String cafName;     //connect axon factor name
    String caName;      //connect axon name

    Axon dmAxon;
    DendriteTree dendriteTree;
    boolean canGenerateSpike;
    boolean isAxonDone;

    short dendriteConnectionsProteinConcentration;
    short dendriteConnectionsProteinConcentrationThreshold;
    short axonConnectionsProteinConcentration;
    short axonConnectionsProteinConcentrationThreshold;
    long signalMovingDuration;

    protected NeuronActivity activity;

    protected String calcSignalMovingSP;
    protected String checkStopGrowingAxonSP;
    protected String startNtReleaseSP;
    protected String ntReleaseSP;
    protected String ntUptakeSP;
    protected String ntDegradeSP;
    protected String ntExpressSP;
    protected String bindNtToReceptorsSP;
    protected String increasePotentialSP;
    protected String decreasePotentialSP;
    protected String makeSynapseConstantSP;
    protected String breakConnectionsSP;
    protected String checkNominalNtSP;
    protected String checkStimuliReceptorsSP;
    protected String activateSynapseSP;

    protected INetworkActivityListener networkActivityListener;
    //endregion

    //region Constructors
    public Neuron(CellId id, PotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
        super(id, potentialCellObject, dispatcher);
        activity = new NeuronActivity();
//        signalMovingDuration = ModelLoader.getActionDetails(calcSignalMovingSP).getDurationByGenes(individualId);
    }

    public Neuron(CellId id, InitialPotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
        super(id, potentialCellObject, dispatcher);
        activity = new NeuronActivity();
//        signalMovingDuration = ModelLoader.getActionDetails(calcSignalMovingSP).getDurationByGenes(individualId);
    }
    //endregion

    //region Getters and Setters
    public String getGafName() {
        return gafName;
    }

    public String getCafName() {
        return cafName;
    }

    public Set<String> getGrowFactors() {
        if (receivedFactorsByAction.containsKey(gaName))
            return receivedFactorsByAction.get(gaName);
        else
            return new HashSet<>();
    }

    public List<ICoordinate> getAxonCoordinates() {
        if (dmAxon != null)
            return dmAxon.getCoordinateList();
        else
            return new ArrayList<>();
    }

    public void setNetworkActivityListener(INetworkActivityListener listener) {
        networkActivityListener = listener;
    }
    //endregion

    //region Public Methods
    public abstract boolean isActive();

    public IMethodResponse formInitialAxon() {
        return formAxon();
    }

    public IMethodResponse formInitialDendriteTree() {
        return formDendriteTree();
    }

    public IMethodResponse growInitialAxonByCoordinate(ICoordinate axonCoordinate, ICoordinate coordinateToGrow) {
        dmAxon.grow(coordinateToGrow);
        return new InitialAxonGrownResponse(dmAxon.getAxonId(), (CellId) objectId, axonCoordinate, coordinateToGrow);
    }

    public IMethodResponse connectInitialAxonByCoordinate(CellId potentialConnectionObject) {
        Synapse synapse = dmAxon.connect(potentialConnectionObject, diffType);
        startAction(ntExpressSP, ActionFunctionalType.Do,
                new NeurotransmitterParameters(getCurrentTick().getTick(), synapse.getPostsynapticNeuron()));
        return new InitialSynapseAddedResponse(dmAxon.getAxonId(), synapse);
    }

    private IMethodResponse growAxonByCoordinate(ICoordinate axonCoordinate, ICoordinate coordinateToGrow) {
        dmAxon.grow(coordinateToGrow);
        return new AxonGrownResponse(dmAxon.getAxonId(), (CellId) objectId, axonCoordinate, coordinateToGrow);
    }

    private IMethodResponse connectAxonByCoordinate(CellId potentialConnectionObject) {
        Synapse synapse = dmAxon.connect(potentialConnectionObject, diffType);
        startAction(ntExpressSP, ActionFunctionalType.Do,
                new NeurotransmitterParameters(getCurrentTick().getTick(), synapse.getPostsynapticNeuron()));
        return new SynapseAddedResponse(dmAxon.getAxonId(), synapse);
    }

    public void activateDendriteSynapse(Synapse synapse) {
        synapsesToActivate.add(synapse);
        startAction(activateSynapseSP, ActionFunctionalType.Do);
    }

    public void activateDendriteInitialSynapse(Synapse synapse) {
        addDendriteSynapse(synapse);
    }

    public void removeDendriteSynapse(CellId neuronId) {
        dendriteTree.removeConnection(neuronId);
    }

    public void removeAxonalSynapse(CellId neuronId) {
        dmAxon.removeConnection(neuronId);
    }

    public void stimulate(CellId presynapticId) {
        dendriteTree.addToStimulated(presynapticId);
        startAction(bindNtToReceptorsSP, ActionFunctionalType.Do);
    }
    //endregion

    //region Package-local Methods
    void startSignalTransmitting() {
        if (activity.isReleasing())
            return;

        final long currentTick = getCurrentTick().getTick();
        dmAxon.getAxonalConnectionLength().forEach((key, value) -> {
            final long startTick = currentTick + value * signalMovingDuration;
            startAction(startNtReleaseSP, ActionFunctionalType.Do,
                    new NeurotransmitterParameters(startTick, key));
            activity.incrementRelease();
        });
    }

    boolean hasSynapseToActivate() {
        return synapsesToActivate.size() > 0;
    }
    //endregion

    //region Protected Methods

    @Override
    protected void initializeAdditionalComponents() {
        calcSignalMovingSP = diffType + "CalculateSignalMoving";
        checkStopGrowingAxonSP = diffType + "CheckStopGrowingAxon";
        startNtReleaseSP = diffType + "StartNeurotransmitterRelease";
        ntReleaseSP = diffType + "NeurotransmitterRelease";
        ntUptakeSP = diffType + "NeurotransmitterUptake";
        ntDegradeSP = diffType + "NeurotransmitterDegrade";
        ntExpressSP = diffType + "NeurotransmitterExpress";
        bindNtToReceptorsSP = diffType + "BindNeurotransmitterToReceptors";
        increasePotentialSP = diffType + "IncreasePotential";
        decreasePotentialSP = diffType + "DecreasePotential";
        makeSynapseConstantSP = diffType + "MakeSynapseConstant";
        breakConnectionsSP = diffType + "BreakConnections";
        checkNominalNtSP = diffType + "CheckNominalNeurotransmitter";
        checkStimuliReceptorsSP = diffType + "CheckStimuliReceptors";
        activateSynapseSP = diffType + "ActivateSynapse";
    }

    //region Signaling Pathways
    @SignalingPathway
    protected IMethodResponse CheckStopGrowingAxon(IActionParameters parameters) {
        axonConnectionsProteinConcentration++;
        if (axonConnectionsProteinConcentration >= axonConnectionsProteinConcentrationThreshold) {
            if (!dmAxon.hasGrown() && dmAxon.hasConnection()) {
                endCurrentAction();
                startAction(checkNominalNtSP, ActionFunctionalType.Do);
            } else {
                dmAxon.resetHasGrown();
                axonConnectionsProteinConcentration = 0;
            }
        }

        return ModelContainer.INSTANCE.returnEmptyResponse();
    }

    @SignalingPathway
    protected IMethodResponse StartNeurotransmitterRelease(IActionParameters parameters) {
        final CellId cellId = ((NeurotransmitterParameters) parameters).getPostSynapticNeuronId();
        startAction(ntReleaseSP, ActionFunctionalType.Do,
                new NeurotransmitterParameters(getCurrentTick().getTick(), cellId));
        return ModelContainer.INSTANCE.returnEmptyResponse();
    }

    @SignalingPathway
    protected IMethodResponse NeurotransmitterRelease(IActionParameters parameters) {
        final CellId cellId = ((NeurotransmitterParameters) parameters).getPostSynapticNeuronId();
        final Synapse synapse = dmAxon.releaseNeurotransmitter(cellId);

        startAction(ntUptakeSP, ActionFunctionalType.Do,
                new NeurotransmitterParameters(getCurrentTick().getTick(), cellId));
        startAction(ntDegradeSP, ActionFunctionalType.Do,
                new NeurotransmitterParameters(getCurrentTick().getTick(), cellId));

        if (!dmAxon.isNTAboveThreshold(cellId))
            startAction(ntExpressSP, ActionFunctionalType.Do,
                    new NeurotransmitterParameters(getCurrentTick().getTick(), synapse.getPostsynapticNeuron()));

        activity.decrementRelease();
        if (!isActive())
            networkActivityListener.decrementActiveNeuronCount();

        if (synapse.isCurrentlyStimulated()) {
            if (!synapse.isPreviouslyStimulated())
                networkActivityListener.incrementActiveSynapseCount();
            return new NeurotransmitterReleaseResponse(synapse.getPresynapticNeuron(), synapse.getPostsynapticNeuron());
        } else
            return ModelContainer.INSTANCE.returnEmptyResponse();
    }

    @SignalingPathway
    protected IMethodResponse NeurotransmitterUptake(IActionParameters parameters) {
        final CellId cellId = ((NeurotransmitterParameters) parameters).getPostSynapticNeuronId();
        final Synapse synapse = dmAxon.uptakeNeurotransmitter(cellId);

        if (!synapse.isCurrentlyStimulated()) {
            actionDispatcher.endCurrentAction();
            if (synapse.isPreviouslyStimulated())
                networkActivityListener.decrementActiveSynapseCount();
        }

        return ModelContainer.INSTANCE.returnEmptyResponse();
    }

    @SignalingPathway
    protected IMethodResponse NeurotransmitterDegrade(IActionParameters parameters) {
        final CellId cellId = ((NeurotransmitterParameters) parameters).getPostSynapticNeuronId();
        final Synapse synapse = dmAxon.degradeNeurotransmitter(cellId);

        if (!synapse.isCurrentlyStimulated()) {
            actionDispatcher.endCurrentAction();
            if (synapse.isPreviouslyStimulated())
                networkActivityListener.decrementActiveSynapseCount();
        }

        return ModelContainer.INSTANCE.returnEmptyResponse();
    }

    @SignalingPathway
    protected IMethodResponse NeurotransmitterExpress(IActionParameters parameters) {
        final CellId cellId = ((NeurotransmitterParameters) parameters).getPostSynapticNeuronId();
        boolean isTerminalFull = dmAxon.expressNeurotransmitter(cellId);
        if (isTerminalFull)
            actionDispatcher.endCurrentAction();
        return ModelContainer.INSTANCE.returnEmptyResponse();
    }

    @SignalingPathway
    protected IMethodResponse BindNeurotransmitterToReceptors(IActionParameters parameters) {
        dendriteTree.bindNeurotransmitterToReceptors();
        startAction(increasePotentialSP, ActionFunctionalType.Do);
        return ModelContainer.INSTANCE.returnEmptyResponse();
    }

    @SignalingPathway
    protected IMethodResponse IncreasePotential(IActionParameters parameters) {
        final boolean isNotActive = !isActive();
        if (dendriteTree.calculateActiveReceptorsOverTime()) {
            startSignalTransmitting();
            if (isNotActive)
                networkActivityListener.incrementActiveNeuronCount();
        }
        startAction(decreasePotentialSP, ActionFunctionalType.Do);
        return ModelContainer.INSTANCE.returnEmptyResponse();
    }

    @SignalingPathway
    protected IMethodResponse DecreasePotential(IActionParameters parameters) {
        final boolean isActive = isActive();
        if (dendriteTree.decreaseActiveReceptorsOverTime() && isActive && !isActive())
            networkActivityListener.decrementActiveNeuronCount();
        return ModelContainer.INSTANCE.returnEmptyResponse();
    }

    @SignalingPathway
    protected IMethodResponse MakeSynapseConstant(IActionParameters parameters) {
        dendriteTree.makeSynapsesConstant();
        return ModelContainer.INSTANCE.returnEmptyResponse();
    }

    @SignalingPathway
    protected IMethodResponse BreakConnections(IActionParameters parameters) {
        Set<Synapse> deletedSynapses = dendriteTree.breakConnections();
//        if (canBeDeleted())
//            startAction(deleteActionName, ActionFunctionalType.Delete);

        return new SynapsesDeletedResponse(deletedSynapses);
    }

    @SignalingPathway
    protected IMethodResponse CheckNominalNeurotransmitter(IActionParameters parameters) {
        int condition = dmAxon.compareReleaseFrequency(ModelContainer.INSTANCE.nextDouble(individualId));
        if (condition == 1)
            dmAxon.increaseNominalNTCount(ModelContainer.INSTANCE.nextDouble(individualId));
        else if (condition == -1)
            dmAxon.decreaseNominalNTCount(ModelContainer.INSTANCE.nextDouble(individualId));
        return ModelContainer.INSTANCE.returnEmptyResponse();
    }

    @SignalingPathway
    protected IMethodResponse CheckStimuliReceptors(IActionParameters parameters) {
        Set<Synapse> changedSynapses = dendriteTree.checkStimuliReceptors();
        if (changedSynapses.size() == 0)
            return ModelContainer.INSTANCE.returnEmptyResponse();
        else
            return new SynapticPowerChangedResponse(changedSynapses);
    }

    @SignalingPathway
    protected IMethodResponse ActivateSynapse(IActionParameters parameters) {
        Synapse synapse = synapsesToActivate.remove();
        addDendriteSynapse(synapse);
        return ModelContainer.INSTANCE.returnEmptyResponse();
    }
    //endregion

    protected IMethodResponse growAxonFactor() {
        dendriteConnectionsProteinConcentration++;
        if (dendriteConnectionsProteinConcentration >= dendriteConnectionsProteinConcentrationThreshold)
            disruptAction(gafName);

        return ModelContainer.INSTANCE.returnEmptyResponse();
    }

    protected IMethodResponse connectAxonFactor() {
        if (dendriteConnectionsProteinConcentration >= dendriteConnectionsProteinConcentrationThreshold)
            disruptAction(cafName);

        return ModelContainer.INSTANCE.returnEmptyResponse();
    }

    protected IMethodResponse growAxon() {
        startAction(caName, ActionFunctionalType.Do);
        double checkProbability = ModelContainer.INSTANCE.nextDouble(individualId);
        Set<String> signals = receivedFactorsByAction.get(gaName);
        SignalProbability signalProbability = new SignalProbability(signals);
        Map<String, SignalSelectionType> signalSelection = signals.stream()
                .collect(Collectors.toMap(
                        signal -> signal,
                        signal -> SignalSelectionType.Gradient
                ));
        Predicate<SignalProbability> predicate = signals.stream()
                .map(signal -> signalProbability.isIntensityMore(signal, 0.))
                .reduce(Predicate::and)
                .get();
        ICoordinate axonCoordinate = dmAxon.getEndCoordinate();
        SignalProbabilityMap spMap =
                ModelContainer.INSTANCE.affectingAllNeighborProbabilities(individualId, dmAxon.getAxonId(), signals);

        CoordinateProbability coordinateProbability = spMap.getCoordinateCandidate(checkProbability, predicate, signalSelection);
        if (coordinateProbability != null) {
            ICoordinate coordinateToGrow = coordinateProbability.getCoordinate();
            return growAxonByCoordinate(axonCoordinate, coordinateToGrow);
        }

        return ModelContainer.INSTANCE.returnEmptyResponse();
    }

    protected IMethodResponse connectAxon() {
        startAction(gaName, ActionFunctionalType.Do);
        double checkProbability = ModelContainer.INSTANCE.nextDouble(individualId);
        Set<String> signals = receivedFactorsByAction.get(caName);
        SignalProbability signalProbability = new SignalProbability(signals);
        Map<String, SignalSelectionType> signalSelection = signals.stream()
                .collect(Collectors.toMap(
                        signal -> signal,
                        signal -> SignalSelectionType.Gradient
                ));
        Predicate<SignalProbability> predicate = signals.stream()
                .map(signal -> signalProbability.isIntensityMore(signal, 0.))
                .reduce(Predicate::and)
                .get();

        SignalProbabilityMap spMap =
                ModelContainer.INSTANCE.affectingAllNeighborProbabilities(individualId, dmAxon.getAxonId(), signals);

        CoordinateProbability coordinateProbability = spMap.getCoordinateCandidate(checkProbability, predicate, signalSelection);
        if (coordinateProbability != null) {
            ICoordinate coordinateToGrow = coordinateProbability.getCoordinate();
            CellId potentialConnectionObject = ModelContainer.INSTANCE.getCellIdByCoordinate(individualId, coordinateToGrow);
            if (potentialConnectionObject != null)
                return connectAxonByCoordinate(potentialConnectionObject);
        }

        return ModelContainer.INSTANCE.returnEmptyResponse();
    }

    protected IMethodResponse formAxon() {
        AxonDetails axonDetails = ModelLoader.getCellDetails(diffType).getAxonDetails();
        if (axonDetails.hasAxon()) {
            dmAxon = new Axon(individualId, (CellId) objectId, coordinate, ModelLoader.getCellDetails(diffType).getAxonDetails());
            startAction(gaName, ActionFunctionalType.Do);
            return new AxonFormResponse((CellId) objectId, dmAxon.getAxonId(), coordinate);
        } else
            return ModelContainer.INSTANCE.returnEmptyResponse();
    }

    protected IMethodResponse formDendriteTree() {
        DendriteDetails dendriteDetails = ModelLoader.getCellDetails(diffType).getDendriteDetails();
        if (dendriteDetails.hasDendrite()) {
            dendriteTree = new DendriteTree(individualId, (CellId) objectId, ModelLoader.getCellDetails(diffType).getDendriteDetails());
            startAction(gafName, ActionFunctionalType.Do);
            startAction(cafName, ActionFunctionalType.Do);
//            startAction(breakConnectionsSP, ActionFunctionalType.Do);
            startAction(checkStimuliReceptorsSP, ActionFunctionalType.Do);
        }

        return ModelContainer.INSTANCE.returnEmptyResponse();
    }

    protected abstract boolean canBeDeleted();
    //endregion

    //region Private Methods
    private void addDendriteSynapse(Synapse synapse) {
        String receptorType = ModelLoader.getReceptorType(diffType, synapse.getPresynapticNeuronType());
        dendriteTree.addConnection(receptorType, synapse);
//        synapse.activate();
    }
    //endregion

    protected class NeuronActivity {
        private int releaseCounter;

        public NeuronActivity() {
            releaseCounter = 0;
        }

        public void incrementRelease() {
            releaseCounter++;
        }

        public void decrementRelease() {
            releaseCounter--;
        }

        public boolean isReleasing() {
            return releaseCounter > 0;
        }
    }
}