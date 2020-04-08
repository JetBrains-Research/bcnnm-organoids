package com.synstorm.SimulationModel.CellLineage.ConcreteCells;

import com.synstorm.SimulationModel.Annotations.SignalingPathway;
import com.synstorm.SimulationModel.CellLineage.AbstractCells.AfferentNeuron;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.IMethodResponse;
import com.synstorm.SimulationModel.LogicObject.IActionParameters;
import com.synstorm.SimulationModel.LogicObject.InitialPotentialCellObject;
import com.synstorm.SimulationModel.LogicObject.PotentialCellObject;
import com.synstorm.SimulationModel.ModelAction.ActionDispatcher;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

/**
 * Class for sensor neuron
 * Created by dvbozhko on 09/06/16.
 */

@Model_v0
@NonProductionLegacy
public class Sensor extends AfferentNeuron {
    //region Fields
    //endregion

    //region Constructors
    public Sensor(CellId id, PotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
        super(id, potentialCellObject, dispatcher);
    }

    public Sensor(CellId id, InitialPotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
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
        methodMap.put("SensorFormAxon", this::SensorFormAxon);
        methodMap.put("SensorGrowingAxon", this::SensorGrowingAxon);
        methodMap.put("SensorConnectAxon", this::SensorConnectAxon);

        methodMap.put("SensorCheckStopGrowingAxon", this::SensorCheckStopGrowingAxon);
        methodMap.put("SensorCheckNominalNeurotransmitter", this::SensorCheckNominalNeurotransmitter);
        methodMap.put("SensorNeurotransmitterRelease", this::SensorNeurotransmitterRelease);
        methodMap.put("SensorNeurotransmitterUptake", this::SensorNeurotransmitterUptake);
        methodMap.put("SensorNeurotransmitterDegrade", this::SensorNeurotransmitterDegrade);
        methodMap.put("SensorNeurotransmitterExpress", this::SensorNeurotransmitterExpress);
        methodMap.put("SensorStartNeurotransmitterRelease", this::SensorStartNeurotransmitterRelease);
    }
    //endregion

    //region Private Methods

    //region Signaling Pathways
    @SignalingPathway
    private IMethodResponse SensorFormAxon(IActionParameters parameters) {
        return formAxon();
    }

    @SignalingPathway
    private IMethodResponse SensorGrowingAxon(IActionParameters parameters) {
        return growAxon();
    }

    @SignalingPathway
    private IMethodResponse SensorConnectAxon(IActionParameters parameters) {
        return connectAxon();
    }

    @SignalingPathway
    protected IMethodResponse SensorCheckStopGrowingAxon(IActionParameters parameters) {
        return CheckStopGrowingAxon(parameters);
    }

    @SignalingPathway
    protected IMethodResponse SensorStartNeurotransmitterRelease(IActionParameters parameters) {
        return StartNeurotransmitterRelease(parameters);
    }

    @SignalingPathway
    protected IMethodResponse SensorNeurotransmitterRelease(IActionParameters parameters) {
        return NeurotransmitterRelease(parameters);
    }

    @SignalingPathway
    protected IMethodResponse SensorNeurotransmitterUptake(IActionParameters parameters) {
        return NeurotransmitterUptake(parameters);
    }

    @SignalingPathway
    protected IMethodResponse SensorNeurotransmitterDegrade(IActionParameters parameters) {
        return NeurotransmitterDegrade(parameters);
    }

    @SignalingPathway
    protected IMethodResponse SensorNeurotransmitterExpress(IActionParameters parameters) {
        return NeurotransmitterExpress(parameters);
    }

    @SignalingPathway
    protected IMethodResponse SensorCheckNominalNeurotransmitter(IActionParameters parameters) {
        return CheckNominalNeurotransmitter(parameters);
    }
    //endregion

    //endregion
}
