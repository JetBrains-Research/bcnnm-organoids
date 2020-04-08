package com.synstorm.SimulationModel.LogicObject.ActionMethodResponse;

import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.AxonId;
import com.synstorm.SimulationModel.Synapses.Synapse;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;

/**
 * Created by dvbozhko on 07/06/16.
 */
public class SynapseAddedResponse extends BaseSynapseAddedResponse implements IMethodResponse {
    //region Fields
    //endregion

    //region Constructors
    public SynapseAddedResponse(AxonId axonId, Synapse addedSynapse) {
        super(axonId, addedSynapse);
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public ProceedResponseMethods getMethodType() {
        return ProceedResponseMethods.SynapseAddedResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
