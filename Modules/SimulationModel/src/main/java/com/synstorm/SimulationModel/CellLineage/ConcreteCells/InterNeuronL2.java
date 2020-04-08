package com.synstorm.SimulationModel.CellLineage.ConcreteCells;

import com.synstorm.SimulationModel.Annotations.SignalingPathway;
import com.synstorm.SimulationModel.CellLineage.AbstractCells.InterNeuron;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.IMethodResponse;
import com.synstorm.SimulationModel.LogicObject.IActionParameters;
import com.synstorm.SimulationModel.LogicObject.InitialPotentialCellObject;
import com.synstorm.SimulationModel.LogicObject.PotentialCellObject;
import com.synstorm.SimulationModel.ModelAction.ActionDispatcher;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

/**
 * Class for interneuron layer 2
 * Created by dvbozhko on 09/06/16.
 */

@Model_v0
@NonProductionLegacy
public class InterNeuronL2 extends InterNeuron {
    //region Fields
    //endregion

    //region Constructors
    public InterNeuronL2(CellId id, PotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
        super(id, potentialCellObject, dispatcher);
    }

    public InterNeuronL2(CellId id, InitialPotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
        super(id, potentialCellObject, dispatcher);
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    @Override
    protected void initializeMethodsForExecution() {
        super.initializeMethodsForExecution();
        methodMap.put("InterNeuronL2FormDendriteTree", this::InterNeuronL2FormDendriteTree);
        methodMap.put("InterNeuronL2FormAxon", this::InterNeuronL2FormAxon);
        methodMap.put("InterNeuronL2GrowAxonFactor", this::InterNeuronL2GrowAxonFactor);
        methodMap.put("InterNeuronL2GrowAxon", this::InterNeuronL2GrowAxon);
        methodMap.put("InterNeuronL2ConnectAxonFactor", this::InterNeuronL2ConnectAxonFactor);
        methodMap.put("InterNeuronL2ConnectAxon", this::InterNeuronL2ConnectAxon);

        methodMap.put("InterNeuronL2CheckStopGrowingAxon", this::InterNeuronL2CheckStopGrowingAxon);
        methodMap.put("InterNeuronL2NeurotransmitterRelease", this::InterNeuronL2NeurotransmitterRelease);
        methodMap.put("InterNeuronL2NeurotransmitterUptake", this::InterNeuronL2NeurotransmitterUptake);
        methodMap.put("InterNeuronL2NeurotransmitterDegrade", this::InterNeuronL2NeurotransmitterDegrade);
        methodMap.put("InterNeuronL2NeurotransmitterExpress", this::InterNeuronL2NeurotransmitterExpress);
        methodMap.put("InterNeuronL2StartNeurotransmitterRelease", this::InterNeuronL2StartNeurotransmitterRelease);
        methodMap.put("InterNeuronL2BindNeurotransmitterToReceptors", this::InterNeuronL2BindNeurotransmitterToReceptors);
        methodMap.put("InterNeuronL2IncreasePotential", this::InterNeuronL2IncreasePotential);
        methodMap.put("InterNeuronL2DecreasePotential", this::InterNeuronL2DecreasePotential);
        methodMap.put("InterNeuronL2MakeSynapseConstant", this::InterNeuronL2MakeSynapseConstant);
        methodMap.put("InterNeuronL2BreakConnections", this::InterNeuronL2BreakConnections);
        methodMap.put("InterNeuronL2CheckNominalNeurotransmitter", this::InterNeuronL2CheckNominalNeurotransmitter);
        methodMap.put("InterNeuronL2CheckStimuliReceptors", this::InterNeuronL2CheckStimuliReceptors);
        methodMap.put("InterNeuronL2ActivateSynapse", this::InterNeuronL2ActivateSynapse);
    }
    //endregion

    //region Private Methods

    //region Signaling Pathways
    @SignalingPathway
    private IMethodResponse InterNeuronL2FormDendriteTree(IActionParameters parameters) {
        return formDendriteTree();
    }

    @SignalingPathway
    private IMethodResponse InterNeuronL2FormAxon(IActionParameters parameters) {
        return formAxon();
    }

    @SignalingPathway
    private IMethodResponse InterNeuronL2GrowAxonFactor(IActionParameters parameters) {
        return growAxonFactor();
    }

    @SignalingPathway
    private IMethodResponse InterNeuronL2GrowAxon(IActionParameters parameters) {
        return growAxon();
    }

    @SignalingPathway
    private IMethodResponse InterNeuronL2ConnectAxonFactor(IActionParameters parameters) {
        return connectAxonFactor();
    }

    @SignalingPathway
    private IMethodResponse InterNeuronL2ConnectAxon(IActionParameters parameters) {
        return connectAxon();
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL2CheckStopGrowingAxon(IActionParameters parameters) {
        return CheckStopGrowingAxon(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL2StartNeurotransmitterRelease(IActionParameters parameters) {
        return StartNeurotransmitterRelease(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL2NeurotransmitterRelease(IActionParameters parameters) {
        return NeurotransmitterRelease(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL2NeurotransmitterUptake(IActionParameters parameters) {
        return NeurotransmitterUptake(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL2NeurotransmitterDegrade(IActionParameters parameters) {
        return NeurotransmitterDegrade(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL2NeurotransmitterExpress(IActionParameters parameters) {
        return NeurotransmitterExpress(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL2BindNeurotransmitterToReceptors(IActionParameters parameters) {
        return BindNeurotransmitterToReceptors(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL2IncreasePotential(IActionParameters parameters) {
        return IncreasePotential(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL2DecreasePotential(IActionParameters parameters) {
        return DecreasePotential(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL2MakeSynapseConstant(IActionParameters parameters) {
        return MakeSynapseConstant(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL2BreakConnections(IActionParameters parameters) {
        return BreakConnections(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL2CheckNominalNeurotransmitter(IActionParameters parameters) {
        return CheckNominalNeurotransmitter(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL2CheckStimuliReceptors(IActionParameters parameters) {
        return CheckStimuliReceptors(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL2ActivateSynapse(IActionParameters parameters) {
        return ActivateSynapse(parameters);
    }
    //endregion

    //endregion
}
