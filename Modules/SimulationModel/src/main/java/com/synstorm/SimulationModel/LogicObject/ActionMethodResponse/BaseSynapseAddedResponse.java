package com.synstorm.SimulationModel.LogicObject.ActionMethodResponse;

import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.AxonId;
import com.synstorm.SimulationModel.Synapses.Synapse;

/**
 * Created by dvbozhko on 17/06/16.
 */
public abstract class BaseSynapseAddedResponse {
    //region Fields
    private final Synapse synapse;
    private final AxonId axonId;
    //endregion

    //region Constructors
    public BaseSynapseAddedResponse(AxonId axonId, Synapse addedSynapse) {
        this.axonId = axonId;
        synapse = addedSynapse;
    }
    //endregion

    //region Getters and Setters
    public Synapse getSynapse() {
        return synapse;
    }

    public AxonId getAxonId() {
        return axonId;
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
