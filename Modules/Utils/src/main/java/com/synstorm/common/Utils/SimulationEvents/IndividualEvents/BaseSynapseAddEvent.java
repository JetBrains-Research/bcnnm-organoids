package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.ObjectIds.IObjectId;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

/**
 * Base abstract class for adding synapse event.
 * Created by dvbozhko on 23/06/16.
 */

public abstract class BaseSynapseAddEvent implements ISimulationEvent {
    //region Fields
    private int id;
    private int idConnect;
    //endregion

    //region Constructors
    public BaseSynapseAddEvent(IObjectId id, IObjectId idConnect) {
        this.id = id.getId();
        this.idConnect = idConnect.getId();
    }
    //endregion

    //region Getters and Setters
    public int getId() {
        return id;
    }

    public int getIdConnect() {
        return idConnect;
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
