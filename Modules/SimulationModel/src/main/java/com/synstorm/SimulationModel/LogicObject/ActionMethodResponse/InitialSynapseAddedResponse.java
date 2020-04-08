package com.synstorm.SimulationModel.LogicObject.ActionMethodResponse;

import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.AxonId;
import com.synstorm.SimulationModel.Synapses.Synapse;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;

/**
 * Created by dvbozhko on 17/06/16.
 */
public class InitialSynapseAddedResponse extends BaseSynapseAddedResponse implements IMethodResponse {
    //region Fields
    private int connectOrder;
    //endregion

    //region Constructors
    public InitialSynapseAddedResponse(AxonId axonId, Synapse addedSynapse) {
        super(axonId, addedSynapse);
    }
    //endregion

    //region Getters and Setters
    public int getConnectOrder() {
        return connectOrder;
    }

    public void setConnectOrder(int connectOrder) {
        this.connectOrder = connectOrder;
    }
    //endregion

    //region Public Methods
    @Override
    public ProceedResponseMethods getMethodType() {
        return ProceedResponseMethods.InitialSynapseAddedResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
