package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ObjectIds.IObjectId;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

/**
 * Class for adding new object event during modeling.
 * Created by dvbozhko on 15/06/16.
 */
@Model_v0

public class ObjectAddEvent extends BaseObjectAddEvent implements ISimulationEvent {
    //region Fields
    private long tick;
    private ICoordinate parentCoordinate;
    //endregion

    //region Constructors
    public ObjectAddEvent(long tick, IObjectId id, IObjectId parentId, String type, ICoordinate coordinate, ICoordinate parentCoordinate) {
        super(id, parentId, type, coordinate);
        this.tick = tick;
        this.parentCoordinate = parentCoordinate;
    }
    //endregion

    //region Getters and Setters
    public long getTick() {
        return tick;
    }

    public ICoordinate getParentCoordinate() {
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
