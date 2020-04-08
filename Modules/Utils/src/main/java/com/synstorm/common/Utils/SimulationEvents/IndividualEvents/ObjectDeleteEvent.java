package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ObjectIds.IObjectId;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

/**
 * Created by dvbozhko on 15/06/16.
 */
@Model_v0
public class ObjectDeleteEvent implements ISimulationEvent {
    //region Fields
    private long tick;
    private int id;
    private String type;
    //endregion

    //region Constructors
    public ObjectDeleteEvent(long tick, IObjectId id, String type) {
        this.tick = tick;
        this.id = id.getId();
        this.type = type;
    }
    //endregion

    //region Getters and Setters
    public long getTick() {
        return tick;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }
    //endregion

    //region Public Methods
    @Override
    public SimulationEvents getEventMethod() {
        return SimulationEvents.ObjectDeleteEvent;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
