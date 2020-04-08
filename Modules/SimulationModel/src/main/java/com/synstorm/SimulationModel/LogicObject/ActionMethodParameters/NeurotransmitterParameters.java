package com.synstorm.SimulationModel.LogicObject.ActionMethodParameters;

import com.synstorm.SimulationModel.LogicObject.IActionParameters;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;

/**
 * Created by dvbozhko on 09/03/2017.
 */
public class NeurotransmitterParameters extends EmptyActionParameters implements IActionParameters {
    //region Fields
    private final CellId postSynapticNeuronId;
    //endregion

    //region Constructors
    public NeurotransmitterParameters(long startTick, CellId postSynapticNeuronId) {
        super(startTick);
        this.postSynapticNeuronId = postSynapticNeuronId;
    }
    //endregion

    //region Getters and Setters
    public CellId getPostSynapticNeuronId() {
        return postSynapticNeuronId;
    }
    //endregion

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
