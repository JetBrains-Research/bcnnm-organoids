package com.synstorm.SimulationModel.LogicObject.LogicObjects;

import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.LObjectId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

import java.util.UUID;

@Model_v0
@NonProductionLegacy
public class GatheringPoint extends SignalPoint {
    //region Fields
    //endregion

    //region Constructors
    public GatheringPoint(LObjectId id, UUID individualId, ICoordinate coordinate) {
        super(id, individualId, coordinate);
    }
    //endregion

    //region Getters and Setters
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
