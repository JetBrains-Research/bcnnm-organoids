package com.synstorm.SimulationModel.LogicObject.ActionMethodResponse;

import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.AxonId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

/**
 * Created by dvbozhko on 14/06/16.
 */
public class AxonFormResponse implements IMethodResponse  {
    //region Fields
    private CellId cellId;
    private AxonId axonId;
    private ICoordinate coordinate;
    //endregion

    //region Constructors
    public AxonFormResponse(CellId cellId, AxonId axonId, ICoordinate axonCoordinate) {
        this.cellId = cellId;
        this.axonId = axonId;
        coordinate = axonCoordinate;
    }
    //endregion

    //region Getters and Setters
    public CellId getCellId() {
        return cellId;
    }

    public AxonId getAxonId() {
        return axonId;
    }

    public ICoordinate getCoordinate() {
        return coordinate;
    }
    //endregion

    //region Public Methods
    @Override
    public ProceedResponseMethods getMethodType() {
        return ProceedResponseMethods.AxonFormResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
