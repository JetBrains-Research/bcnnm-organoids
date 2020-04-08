package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

/**
 * Created by dvbozhko on 15/06/16.
 */
@Model_v1
public class ObjectDifferentiatedEventR implements ISimulationEvent {
    //region Fields
    private long tick;
    private int id;
    private int parentId;
    private String type;
    private String previousType;
    //endregion

    //region Constructors
    public ObjectDifferentiatedEventR(long tick, int id, int parentId, String type, String previousType) {
        this.tick = tick;
        this.id = id;
        this.parentId = parentId;
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

    public int getParentId() { return parentId; }

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
        return SimulationEvents.ObjectDifferentiatedEvent;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
