package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

/**
 * Class for adding new object event during modeling.
 * Created by dvbozhko on 15/06/16.
 */
@Model_v1
public class ObjectAddEventR extends BaseObjectAddEventR implements ISimulationEvent {
    //region Fields
    private long tick;
    private int[] parentCoordinate;
    //endregion

    //region Constructors
    public ObjectAddEventR(long tick, int id, int parentId, String type, int[] coordinate, int[] parentCoordinate) {
        super(id, parentId, type, coordinate);
        this.tick = tick;
        this.parentCoordinate = parentCoordinate;
    }
    //endregion

    //region Getters and Setters
    public long getTick() {
        return tick;
    }

    public int[] getParentCoordinate() {
        return parentCoordinate;
    }
    //endregion

    //region Public Methods
    @Override
    public SimulationEvents getEventMethod() {
        return SimulationEvents.ObjectAddEvent;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
