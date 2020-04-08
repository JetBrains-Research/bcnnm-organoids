package com.synstorm.SimulationModel.CellLineage.AbstractCells;

import com.synstorm.SimulationModel.LogicObject.InitialPotentialCellObject;
import com.synstorm.SimulationModel.LogicObject.PotentialCellObject;
import com.synstorm.SimulationModel.ModelAction.ActionDispatcher;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;

import java.util.LinkedList;

/**
 * Abstract class for efferent (motor, decision) types of neurons
 * Created by dvbozhko on 09/06/16.
 */

@Model_v0
@ProductionLegacy
public abstract class EfferentNeuron extends Neuron {
    //region Fields
    //endregion

    //region Constructors
    public EfferentNeuron(CellId id, PotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
        super(id, potentialCellObject, dispatcher);
    }

    public EfferentNeuron(CellId id, InitialPotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
        super(id, potentialCellObject, dispatcher);
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public boolean isActive() {
        return dendriteTree.isAboveThreshold();
    }
    //endregion

    //region Package-local Methods
    @Override
    void startSignalTransmitting() {

    }
    //endregion

    //region Protected Methods
    @Override
    protected void initializeMethodsForExecution() {
        super.initializeMethodsForExecution();
//        methodMap.put("BindNeurotransmitterToReceptors", this::BindNeurotransmitterToReceptors);
//        methodMap.put("IncreasePotential", this::IncreasePotential);
//        methodMap.put("DecreasePotential", this::DecreasePotential);
//        methodMap.put("MakeSynapseConstant", this::MakeSynapseConstant);
//        methodMap.put("BreakConnections", this::BreakConnections);
//        methodMap.put("CheckStimuliReceptors", this::CheckStimuliReceptors);
//        methodMap.put("ActivateSynapse", this::ActivateSynapse);
    }

    @Override
    protected void initializeAdditionalComponents() {
        super.initializeAdditionalComponents();
        synapsesToActivate = new LinkedList<>();
        gafName = diffType + "GrowAxonFactor";
        cafName = diffType + "ConnectAxonFactor";

        dendriteConnectionsProteinConcentration = 0;
        dendriteConnectionsProteinConcentrationThreshold = 1000;
    }

    @Override
    protected boolean canBeDeleted() {
        return !dendriteTree.hasConnection() && !hasSynapseToActivate();
    }
    //endregion

    //region Private Methods
    //endregion
}
