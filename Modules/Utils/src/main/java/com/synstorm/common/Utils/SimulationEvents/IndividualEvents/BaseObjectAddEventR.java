package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

/**
 * Base abstract class for adding new object to model event.
 * Created by dvbozhko on 23/06/16.
 */
@Model_v1
public abstract class BaseObjectAddEventR implements ISimulationEvent {
    //region Fields
    private int id;
    private int parentId;
    private String type;
    private int[] coordinate;
    //endregion

    //region Constructors
    public BaseObjectAddEventR(int id, int parentId, String type, int[] coordinate) {
        this.id = id;
        this.parentId = parentId;
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

    public int[] getCoordinate() {
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
