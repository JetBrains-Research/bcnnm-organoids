package com.synstorm.SimulationModel.CellLineage.ConcreteCells;

import com.synstorm.SimulationModel.Annotations.SignalingPathway;
import com.synstorm.SimulationModel.CellLineage.AbstractCells.EfferentNeuron;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.IMethodResponse;
import com.synstorm.SimulationModel.LogicObject.IActionParameters;
import com.synstorm.SimulationModel.LogicObject.InitialPotentialCellObject;
import com.synstorm.SimulationModel.LogicObject.PotentialCellObject;
import com.synstorm.SimulationModel.ModelAction.ActionDispatcher;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

/**
 * Class for decision neuron
 * Created by dvbozhko on 09/06/16.
 */

@Model_v0
@NonProductionLegacy
public class DecisionNeuron extends EfferentNeuron {
    //region Fields
    //endregion

    //region Constructors
    public DecisionNeuron(CellId id, PotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
        super(id, potentialCellObject, dispatcher);
    }

    public DecisionNeuron(CellId id, InitialPotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
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
        methodMap.put("DecisionNeuronFormDendriteTree", this::DecisionNeuronFormDendriteTree);
        methodMap.put("DecisionNeuronGrowAxonFactor", this::DecisionNeuronGrowAxonFactor);
        methodMap.put("DecisionNeuronConnectAxonFactor", this::DecisionNeuronConnectAxonFactor);

        methodMap.put("DecisionNeuronBindNeurotransmitterToReceptors", this::DecisionNeuronBindNeurotransmitterToReceptors);
        methodMap.put("DecisionNeuronIncreasePotential", this::DecisionNeuronIncreasePotential);
        methodMap.put("DecisionNeuronDecreasePotential", this::DecisionNeuronDecreasePotential);
        methodMap.put("DecisionNeuronMakeSynapseConstant", this::DecisionNeuronMakeSynapseConstant);
        methodMap.put("DecisionNeuronBreakConnections", this::DecisionNeuronBreakConnections);
        methodMap.put("DecisionNeuronCheckStimuliReceptors", this::DecisionNeuronCheckStimuliReceptors);
        methodMap.put("DecisionNeuronActivateSynapse", this::DecisionNeuronActivateSynapse);
    }
    //endregion

    //region Private Methods
    @SignalingPathway
    private IMethodResponse DecisionNeuronFormDendriteTree(IActionParameters parameters) {
        return formDendriteTree();
    }

    @SignalingPathway
    private IMethodResponse DecisionNeuronGrowAxonFactor(IActionParameters parameters) {
        return growAxonFactor();
    }

    @SignalingPathway
    private IMethodResponse DecisionNeuronConnectAxonFactor(IActionParameters parameters) {
        return connectAxonFactor();
    }

    @SignalingPathway
    protected IMethodResponse DecisionNeuronBindNeurotransmitterToReceptors(IActionParameters parameters) {
        return BindNeurotransmitterToReceptors(parameters);
    }

    @SignalingPathway
    protected IMethodResponse DecisionNeuronIncreasePotential(IActionParameters parameters) {
        return IncreasePotential(parameters);
    }

    @SignalingPathway
    protected IMethodResponse DecisionNeuronDecreasePotential(IActionParameters parameters) {
        return DecreasePotential(parameters);
    }

    @SignalingPathway
    protected IMethodResponse DecisionNeuronMakeSynapseConstant(IActionParameters parameters) {
        return MakeSynapseConstant(parameters);
    }

    @SignalingPathway
    protected IMethodResponse DecisionNeuronBreakConnections(IActionParameters parameters) {
        return BreakConnections(parameters);
    }

    @SignalingPathway
    protected IMethodResponse DecisionNeuronCheckStimuliReceptors(IActionParameters parameters) {
        return CheckStimuliReceptors(parameters);
    }

    @SignalingPathway
    protected IMethodResponse DecisionNeuronActivateSynapse(IActionParameters parameters) {
        return ActivateSynapse(parameters);
    }
    //endregion
}
