package com.synstorm.common.Utils.SimulationEvents.SpaceEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

/**
 * Created by human-research on 2019-01-24.
 */
@Model_v1
public class BaseSignalEvent implements ISimulationEvent {
    //region Fields
    private final long tick;
    private final int objectId;
    private final SimulationEvents eventType;
    private final int signalId;
    //endregion


    //region Constructors

    public BaseSignalEvent(long tick, int objectId, SimulationEvents eventType, int signalId) {
        this.tick = tick;
        this.objectId = objectId;
        this.eventType = eventType;
        this.signalId = signalId;
    }

    @Override
    public SimulationEvents getEventMethod() {
        return eventType;
    }

    //endregion


    //region Getters and Setters

    public long getTick() {
        return tick;
    }

    public int getObjectId() {
        return objectId;
    }

    public int getSignalId() {
        return signalId;
    }

    //endregion


    //region Public Methods

    //endregion


    //region Private Methods

    //endregion

}
