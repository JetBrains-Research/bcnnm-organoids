package com.synstorm.SimulationModel.LogicObject.ActionMethodResponse;

import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.AxonId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

/**
 * Created by dvbozhko on 07/06/16.
 */
public class AxonGrownResponse extends BaseAxonGrownResponse implements IMethodResponse {
    //region Fields
    //endregion

    //region Constructors
    public AxonGrownResponse(AxonId axonId, CellId cellId, ICoordinate currentCoordinate, ICoordinate coordinateToGrow) {
        super(axonId, cellId, currentCoordinate, coordinateToGrow);
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public ProceedResponseMethods getMethodType() {
        return ProceedResponseMethods.AxonGrownResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
