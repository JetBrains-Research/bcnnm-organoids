package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ObjectIds.IObjectId;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

/**
 * Base abstract class for growing axon event.
 * Created by dvbozhko on 23/06/16.
 */
@Model_v1
public abstract class BaseAxonGrowEvent implements ISimulationEvent {
    //region Fields
    private int id;
    private ICoordinate coordinateToGrow;
    private ICoordinate currentCoordinate;
    //endregion

    //region Constructors
    public BaseAxonGrowEvent(IObjectId id, ICoordinate currentCoordinate, ICoordinate coordinateToGrow) {
        this.id = id.getId();
        this.currentCoordinate = currentCoordinate;
        this.coordinateToGrow = coordinateToGrow;
    }
    //endregion

    //region Getters and Setters
    public int getId() {
        return id;
    }

    public ICoordinate getCurrentCoordinate() {
        return currentCoordinate;
    }

    public ICoordinate getCoordinateToGrow() {
        return coordinateToGrow;
    }
    //endregion

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
