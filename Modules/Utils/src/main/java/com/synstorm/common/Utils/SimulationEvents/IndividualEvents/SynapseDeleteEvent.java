package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ObjectIds.IObjectId;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

/**
 * Created by dvbozhko on 15/06/16.
 */
@Model_v0
@ProductionLegacy
public class SynapseDeleteEvent implements ISimulationEvent {
    //region Fields
    private long tick;
    private int idOut;
    private int idIn;
    //endregion

    //region Constructors
    public SynapseDeleteEvent(long tick, IObjectId idOut, IObjectId idIn) {
        this.tick = tick;
        this.idOut = idOut.getId();
        this.idIn = idIn.getId();
    }
    //endregion

    //region Getters and Setters
    public long getTick() {
        return tick;
    }

    public int getIdOut() {
        return idOut;
    }

    public int getIdIn() {
        return idIn;
    }
    //endregion

    //region Public Methods
    @Override
    public SimulationEvents getEventMethod() {
        return SimulationEvents.SynapseDeleteEvent;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
