package com.synstorm.SimulationModel.LogicObject.ActionMethodResponse;

import com.synstorm.SimulationModel.Synapses.Synapse;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;

import java.util.Set;

/**
 * Created by dvbozhko on 07/06/16.
 */
public class SynapsesDeletedResponse implements IMethodResponse {
    //region Fields
    private Set<Synapse> synapses;
    //endregion

    //region Constructors
    public SynapsesDeletedResponse(Set<Synapse> deletedSynapses) {
        synapses = deletedSynapses;
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
        return ProceedResponseMethods.SynapsesDeletedResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
