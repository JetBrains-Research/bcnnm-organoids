package com.synstorm.SimulationModel.LogicObject.LogicObjects;

import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.LObjectId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

import java.util.UUID;

@Model_v0
@NonProductionLegacy
public abstract class SpaceObject extends LogicObject {
    //region Fields
    protected ICoordinate coordinate;
    //endregion

    //region Constructors
    public SpaceObject(LObjectId id, UUID individualId, ICoordinate coordinate) {
        super(id, individualId);
        this.coordinate = coordinate;
    }
    //endregion

    //region Getters and Setters
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