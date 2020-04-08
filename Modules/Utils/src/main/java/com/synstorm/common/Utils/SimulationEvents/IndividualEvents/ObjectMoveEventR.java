package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

/**
 * Created by dvbozhko on 15/06/16.
 */
@Model_v1
public class ObjectMoveEventR implements ISimulationEvent {
    //region Fields
    private long tick;
    private int id;
    private int[] coordinate;
    private int[] previousCoordinate;
    //endregion

    //region Constructors
    public ObjectMoveEventR(long tick, int id, int[] coordinate, int[] previousCoordinate) {
        this.tick = tick;
        this.id = id;
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

    public int[] getCoordinate() {
        return coordinate;
    }

    public int[] getPreviousCoordinate() {
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
