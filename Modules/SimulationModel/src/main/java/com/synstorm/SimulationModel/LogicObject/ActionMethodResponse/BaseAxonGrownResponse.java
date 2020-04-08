package com.synstorm.SimulationModel.LogicObject.ActionMethodResponse;

import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.AxonId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

/**
 * Created by dvbozhko on 17/06/16.
 */
public abstract class BaseAxonGrownResponse {
    //region Fields
    private final AxonId axonId;
    private final CellId cellId;
    private final ICoordinate coordinateToGrow;
    private final ICoordinate currentCoordinate;
    //endregion

    //region Constructors
    public BaseAxonGrownResponse(AxonId axonId, CellId cellId, ICoordinate currentCoordinate, ICoordinate coordinateToGrow) {
        this.axonId = axonId;
        this.cellId = cellId;
        this.currentCoordinate = currentCoordinate;
        this.coordinateToGrow = coordinateToGrow;
    }
    //endregion

    //region Getters and Setters
    public AxonId getAxonId() {
        return axonId;
    }

    public CellId getCellId() {
        return cellId;
    }

    public ICoordinate getCoordinateToGrow() {
        return coordinateToGrow;
    }

    public ICoordinate getCurrentCoordinate() {
        return currentCoordinate;
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
