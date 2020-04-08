package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.ObjectIds.IObjectId;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

/**
 * Base abstract class for adding new object to model event.
 * Created by dvbozhko on 23/06/16.
 */
@Model_v0
public abstract class BaseObjectAddEvent implements ISimulationEvent {
    //region Fields
    private int id;
    private int parentId;
    private String type;
    private ICoordinate coordinate;
    //endregion

    //region Constructors
    public BaseObjectAddEvent(IObjectId id, IObjectId parentId, String type, ICoordinate coordinate) {
        this.id = id.getId();
        this.parentId = parentId.getId();
        this.type = type;
        this.coordinate = coordinate;

    }
    //endregion

    //region Getters and Setters
    public int getId() {
        return id;
    }

    public int getParentId() {
        return parentId;
    }

    public String getType() {
        return type;
    }

    public ICoordinate getCoordinate() {
        return coordinate;
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
