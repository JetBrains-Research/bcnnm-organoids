package com.synstorm.SimulationModel.LogicObject;

import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.LObjectId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

import java.util.UUID;

/**
 * Created by dvbozhko on 17/06/16.
 */

@Model_v0
@NonProductionLegacy
public interface IPotentialCellObject {

    UUID getIndividualId();

    LObjectId getParentId();

    ICoordinate getCoordinate();

    String getCellType();
}
