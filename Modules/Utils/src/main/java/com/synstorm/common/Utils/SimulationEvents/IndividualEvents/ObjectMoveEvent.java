package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ObjectIds.IObjectId;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

/**
 * Created by dvbozhko on 15/06/16.
 */
@Model_v0
public class ObjectMoveEvent implements ISimulationEvent {
    //region Fields
    private long tick;
    private int id;
    private ICoordinate coordinate;
    private ICoordinate previousCoordinate;
    //endregion

    //region Constructors
    public ObjectMoveEvent(long tick, IObjectId id, ICoordinate coordinate, ICoordinate previousCoordinate) {
        this.tick = tick;
        this.id = id.getId();
        this.coordinate = coordinate;
        this.previousCoordinate = previousCoordinate;
    }
    //endregion

    //region Getters and Setters
    public long getTick() {
        return tick;
    }

    public int getId() {
        return id;
    }

    public ICoordinate getCoordinate() {
        return coordinate;
    }

    public ICoordinate getPreviousCoordinate() {
        return previousCoordinate;
    }
    //endregion

    //region Public Methods
    @Override
    public SimulationEvents getEventMethod() {
        return SimulationEvents.ObjectMoveEvent;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
