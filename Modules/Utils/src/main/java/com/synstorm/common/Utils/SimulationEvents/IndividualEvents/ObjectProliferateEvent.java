package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ObjectIds.IObjectId;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

/**
 * Created by dvbozhko on 15/06/16.
 */
@Model_v0
public class ObjectProliferateEvent implements ISimulationEvent {
    //region Fields
    private long tick;
    private int id;
    private String type;
    private String previousType;
    //endregion

    //region Constructors
    public ObjectProliferateEvent(long tick, IObjectId id, String type, String previousType) {
        this.tick = tick;
        this.id = id.getId();
        this.type = type;
        this.previousType = previousType;
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

    public String getPreviousType() {
        return previousType;
    }
    //endregion

    //region Public Methods
    @Override
    public SimulationEvents getEventMethod() {
        return SimulationEvents.ObjectProliferateEvent;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
