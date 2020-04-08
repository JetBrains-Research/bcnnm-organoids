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
 * Class for interneuron layer 1
 * Created by dvbozhko on 09/06/16.
 */

@Model_v0
@NonProductionLegacy
public class InterNeuronL1 extends InterNeuron {
    //region Fields
    //endregion

    //region Constructors
    public InterNeuronL1(CellId id, PotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
        super(id, potentialCellObject, dispatcher);
    }

    public InterNeuronL1(CellId id, InitialPotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
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
        methodMap.put("InterNeuronL1FormDendriteTree", this::InterNeuronL1FormDendriteTree);
        methodMap.put("InterNeuronL1FormAxon", this::InterNeuronL1FormAxon);
        methodMap.put("InterNeuronL1GrowAxonFactor", this::InterNeuronL1GrowAxonFactor);
        methodMap.put("InterNeuronL1GrowAxon", this::InterNeuronL1GrowAxon);
        methodMap.put("InterNeuronL1ConnectAxonFactor", this::InterNeuronL1ConnectAxonFactor);
        methodMap.put("InterNeuronL1ConnectAxon", this::InterNeuronL1ConnectAxon);

        methodMap.put("InterNeuronL1CheckStopGrowingAxon", this::InterNeuronL1CheckStopGrowingAxon);
        methodMap.put("InterNeuronL1NeurotransmitterRelease", this::InterNeuronL1NeurotransmitterRelease);
        methodMap.put("InterNeuronL1NeurotransmitterUptake", this::InterNeuronL1NeurotransmitterUptake);
        methodMap.put("InterNeuronL1NeurotransmitterDegrade", this::InterNeuronL1NeurotransmitterDegrade);
        methodMap.put("InterNeuronL1NeurotransmitterExpress", this::InterNeuronL1NeurotransmitterExpress);
        methodMap.put("InterNeuronL1StartNeurotransmitterRelease", this::InterNeuronL1StartNeurotransmitterRelease);
        methodMap.put("InterNeuronL1BindNeurotransmitterToReceptors", this::InterNeuronL1BindNeurotransmitterToReceptors);
        methodMap.put("InterNeuronL1IncreasePotential", this::InterNeuronL1IncreasePotential);
        methodMap.put("InterNeuronL1DecreasePotential", this::InterNeuronL1DecreasePotential);
        methodMap.put("InterNeuronL1MakeSynapseConstant", this::InterNeuronL1MakeSynapseConstant);
        methodMap.put("InterNeuronL1BreakConnections", this::InterNeuronL1BreakConnections);
        methodMap.put("InterNeuronL1CheckNominalNeurotransmitter", this::InterNeuronL1CheckNominalNeurotransmitter);
        methodMap.put("InterNeuronL1CheckStimuliReceptors", this::InterNeuronL1CheckStimuliReceptors);
        methodMap.put("InterNeuronL1ActivateSynapse", this::InterNeuronL1ActivateSynapse);
    }
    //endregion

    //region Private Methods

    //region Signaling Pathways
    @SignalingPathway
    private IMethodResponse InterNeuronL1FormDendriteTree(IActionParameters parameters) {
        return formDendriteTree();
    }

    @SignalingPathway
    private IMethodResponse InterNeuronL1FormAxon(IActionParameters parameters) {
        return formAxon();
    }

    @SignalingPathway
    private IMethodResponse InterNeuronL1GrowAxonFactor(IActionParameters parameters) {
        return growAxonFactor();
    }

    @SignalingPathway
    private IMethodResponse InterNeuronL1GrowAxon(IActionParameters parameters) {
        return growAxon();
    }

    @SignalingPathway
    private IMethodResponse InterNeuronL1ConnectAxonFactor(IActionParameters parameters) {
        return connectAxonFactor();
    }

    @SignalingPathway
    private IMethodResponse InterNeuronL1ConnectAxon(IActionParameters parameters) {
        return connectAxon();
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL1CheckStopGrowingAxon(IActionParameters parameters) {
        return CheckStopGrowingAxon(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL1StartNeurotransmitterRelease(IActionParameters parameters) {
        return StartNeurotransmitterRelease(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL1NeurotransmitterRelease(IActionParameters parameters) {
        return NeurotransmitterRelease(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL1NeurotransmitterUptake(IActionParameters parameters) {
        return NeurotransmitterUptake(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL1NeurotransmitterDegrade(IActionParameters parameters) {
        return NeurotransmitterDegrade(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL1NeurotransmitterExpress(IActionParameters parameters) {
        return NeurotransmitterExpress(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL1BindNeurotransmitterToReceptors(IActionParameters parameters) {
        return BindNeurotransmitterToReceptors(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL1IncreasePotential(IActionParameters parameters) {
        return IncreasePotential(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL1DecreasePotential(IActionParameters parameters) {
        return DecreasePotential(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL1MakeSynapseConstant(IActionParameters parameters) {
        return MakeSynapseConstant(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL1BreakConnections(IActionParameters parameters) {
        return BreakConnections(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL1CheckNominalNeurotransmitter(IActionParameters parameters) {
        return CheckNominalNeurotransmitter(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL1CheckStimuliReceptors(IActionParameters parameters) {
        return CheckStimuliReceptors(parameters);
    }

    @SignalingPathway
    protected IMethodResponse InterNeuronL1ActivateSynapse(IActionParameters parameters) {
        return ActivateSynapse(parameters);
    }
    //endregion

    //endregion
}
