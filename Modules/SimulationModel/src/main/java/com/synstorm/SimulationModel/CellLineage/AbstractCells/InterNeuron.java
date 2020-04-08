package com.synstorm.SimulationModel.CellLineage.AbstractCells;

import com.synstorm.SimulationModel.LogicObject.InitialPotentialCellObject;
import com.synstorm.SimulationModel.LogicObject.PotentialCellObject;
import com.synstorm.SimulationModel.ModelAction.ActionDispatcher;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.ConfigLoader.ModelLoader;

import java.util.LinkedList;

/**
 * Abstract class for interneuron type of neurons
 * Created by dvbozhko on 09/06/16.
 */

@Model_v0
@ProductionLegacy
public abstract class InterNeuron extends Neuron {
    //region Fields
    //endregion

    //region Constructors
    public InterNeuron(CellId id, PotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
        super(id, potentialCellObject, dispatcher);
        signalMovingDuration = ModelLoader.getActionDetails(calcSignalMovingSP).getDurationByGenes(individualId);
    }

    public InterNeuron(CellId id, InitialPotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
        super(id, potentialCellObject, dispatcher);
        signalMovingDuration = ModelLoader.getActionDetails(calcSignalMovingSP).getDurationByGenes(individualId);
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public boolean isActive() {
        return activity.isReleasing() || dendriteTree.isAboveThreshold();
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    @Override
    protected void initializeMethodsForExecution() {
        super.initializeMethodsForExecution();
//        methodMap.put("CheckStopGrowingAxon", this::CheckStopGrowingAxon);
//        methodMap.put("NeurotransmitterRelease", this::NeurotransmitterRelease);
//        methodMap.put("NeurotransmitterUptake", this::NeurotransmitterUptake);
//        methodMap.put("NeurotransmitterDegrade", this::NeurotransmitterDegrade);
//        methodMap.put("NeurotransmitterExpress", this::NeurotransmitterExpress);
//        methodMap.put("StartNeurotransmitterRelease", this::StartNeurotransmitterRelease);
//        methodMap.put("BindNeurotransmitterToReceptors", this::BindNeurotransmitterToReceptors);
//        methodMap.put("IncreasePotential", this::IncreasePotential);
//        methodMap.put("DecreasePotential", this::DecreasePotential);
//        methodMap.put("MakeSynapseConstant", this::MakeSynapseConstant);
//        methodMap.put("BreakConnections", this::BreakConnections);
//        methodMap.put("CheckNominalNeurotransmitter", this::CheckNominalNeurotransmitter);
//        methodMap.put("CheckStimuliReceptors", this::CheckStimuliReceptors);
//        methodMap.put("ActivateSynapse", this::ActivateSynapse);
    }

    @Override
    protected void initializeAdditionalComponents() {
        super.initializeAdditionalComponents();
        canGenerateSpike = false;
        isAxonDone = false;
        synapsesToActivate = new LinkedList<>();

        gafName = diffType + "GrowAxonFactor";
        gaName = diffType + "GrowAxon";
        cafName = diffType + "ConnectAxonFactor";
        caName = diffType + "ConnectAxon";

        dendriteConnectionsProteinConcentration = 0;
        dendriteConnectionsProteinConcentrationThreshold = 1000;

        axonConnectionsProteinConcentration = 0;
        axonConnectionsProteinConcentrationThreshold = 1000;
    }

    protected boolean canBeDeleted() {
        return !dendriteTree.hasConnection() && !dmAxon.hasConnection() && !hasSynapseToActivate();
    }
    //endregion

    //region Private Methods
    //endregion
}
