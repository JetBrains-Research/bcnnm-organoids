package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

@Model_v1
public class ObjectDeleteEventR implements ISimulationEvent {
    //region Fields
    private long tick;
    private int id;
    private String type;
    //endregion

    //region Constructors
    public ObjectDeleteEventR(long tick, int id, String type) {
        this.tick = tick;
        this.id = id;
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
