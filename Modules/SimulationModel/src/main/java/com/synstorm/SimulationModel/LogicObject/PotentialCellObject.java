package com.synstorm.SimulationModel.LogicObject;

import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.LObjectId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

import java.util.UUID;

/**
 * Created by dvbozhko on 06/06/16.
 */

@Model_v0
@NonProductionLegacy
public class PotentialCellObject extends AbstractPotentialCell implements IPotentialCellObject {
    //region Fields
    //endregion

    //region Constructors
    public PotentialCellObject(UUID individualId, LObjectId parentId, ICoordinate coordinate, String cellType) {
        super(individualId, parentId, coordinate, cellType);
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
