package com.synstorm.SimulationModel.LogicObject.ActionMethodResponse;

import com.synstorm.SimulationModel.Synapses.Synapse;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;

import java.util.Set;

/**
 * Created by dvbozhko on 06/12/2016.
 */
public class SynapticPowerChangedResponse implements IMethodResponse {
    //region Fields
    private final Set<Synapse> synapses;
    //endregion

    //region Constructors
    public SynapticPowerChangedResponse(Set<Synapse> synapses) {
        this.synapses = synapses;
    }
    //endregion

    //region Getters and Setters
    public Set<Synapse> getSynapses() {
        return synapses;
    }
    //endregion

    //region Public Methods
    @Override
    public ProceedResponseMethods getMethodType() {
        return ProceedResponseMethods.SynapticPowerChangedResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
