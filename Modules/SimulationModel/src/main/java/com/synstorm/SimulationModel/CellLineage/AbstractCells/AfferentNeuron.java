package com.synstorm.SimulationModel.CellLineage.AbstractCells;

import com.synstorm.SimulationModel.Annotations.SignalingPathway;
import com.synstorm.SimulationModel.Containers.ModelContainer;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.IMethodResponse;
import com.synstorm.SimulationModel.LogicObject.IActionParameters;
import com.synstorm.SimulationModel.LogicObject.InitialPotentialCellObject;
import com.synstorm.SimulationModel.LogicObject.PotentialCellObject;
import com.synstorm.SimulationModel.ModelAction.ActionDispatcher;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.ConfigLoader.ModelLoader;


/**
 * Abstract class for afferent (sensor) types of neurons
 * Created by dvbozhko on 09/06/16.
 */

@Model_v0
@ProductionLegacy
public abstract class AfferentNeuron extends Neuron {
    //region Fields
    private double dummySpikeProbability;
    //endregion

    //region Constructors
    public AfferentNeuron(CellId id, PotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
        super(id, potentialCellObject, dispatcher);
        signalMovingDuration = ModelLoader.getActionDetails(calcSignalMovingSP).getDurationByGenes(individualId);
    }

    public AfferentNeuron(CellId id, InitialPotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
        super(id, potentialCellObject, dispatcher);
        signalMovingDuration = ModelLoader.getActionDetails(calcSignalMovingSP).getDurationByGenes(individualId);
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public boolean isActive() {
        return activity.isReleasing();
    }

    @SignalingPathway
    public IMethodResponse GenerateDummySpike(IActionParameters parameters) {
        if (!canGenerateSpike)
            return ModelContainer.INSTANCE.returnEmptyResponse();

        double samplingDummyProbability = ModelContainer.INSTANCE.nextDouble(individualId);
        if (samplingDummyProbability <= dummySpikeProbability)
            startSignalTransmitting();
        return ModelContainer.INSTANCE.returnEmptyResponse();
    }

    public boolean externalStimuli(double input) {
        if (input > 0.1) {
            if (!isActive())
                networkActivityListener.incrementActiveNeuronCount();
            startSignalTransmitting();
            return true;
        }

        return false;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    @Override
    protected void initializeMethodsForExecution() {
        super.initializeMethodsForExecution();
        methodMap.put("GenerateDummySpike", this::GenerateDummySpike);
//        methodMap.put("CheckStopGrowingAxon", this::CheckStopGrowingAxon);
//        methodMap.put("CheckNominalNeurotransmitter", this::CheckNominalNeurotransmitter);
//        methodMap.put("NeurotransmitterRelease", this::NeurotransmitterRelease);
//        methodMap.put("NeurotransmitterUptake", this::NeurotransmitterUptake);
//        methodMap.put("NeurotransmitterDegrade", this::NeurotransmitterDegrade);
//        methodMap.put("NeurotransmitterExpress", this::NeurotransmitterExpress);
//        methodMap.put("StartNeurotransmitterRelease", this::StartNeurotransmitterRelease);
    }

    @Override
    protected void initializeAdditionalComponents() {
        super.initializeAdditionalComponents();
        canGenerateSpike = true;
        isAxonDone = false;
        gaName = diffType + "GrowAxon";
        caName = diffType + "ConnectAxon";

        axonConnectionsProteinConcentration = 0;
        axonConnectionsProteinConcentrationThreshold = 1000;
        dummySpikeProbability = 0.5;
    }

    @Override
    protected boolean canBeDeleted() {
        return !dmAxon.hasConnection();
    }
    //endregion

    //region Private Methods
    //endregion
}
