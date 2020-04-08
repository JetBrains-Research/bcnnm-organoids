package com.synstorm.SimulationModel.LogicObject;

import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.LObjectId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

import java.util.UUID;

/**
 * Created by dvbozhko on 16/06/16.
 */

@Model_v0
@NonProductionLegacy
public abstract class AbstractPotentialCell implements IPotentialCellObject {
    //region Fields
    private final LObjectId parentId;
    private final UUID individualId;
    private final ICoordinate coordinate;
    private final String cellType;
    //endregion

    //region Constructors
    public AbstractPotentialCell(UUID individualId, LObjectId parentId, ICoordinate coordinate, String cellType) {
        this.individualId = individualId;
        this.parentId = parentId;
        this.coordinate = coordinate;
        this.cellType = cellType;
    }
    //endregion

    //region Getters and Setters
    public UUID getIndividualId() {
        return individualId;
    }

    public LObjectId getParentId() {
        return parentId;
    }

    public ICoordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public String getCellType() {
        return cellType;
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
