package com.synstorm.SimulationModel.LogicObject.ActionMethodResponse;

import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.AxonId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

/**
 * Created by dvbozhko on 17/06/16.
 */
public class InitialAxonGrownResponse extends BaseAxonGrownResponse implements IMethodResponse {
    //region Fields
    private int growOrder;
    //endregion

    //region Constructors
    public InitialAxonGrownResponse(AxonId axonId, CellId cellId, ICoordinate currentCoordinate, ICoordinate coordinateToGrow) {
        super(axonId, cellId, currentCoordinate, coordinateToGrow);
    }
    //endregion

    //region Getters and Setters
    public int getGrowOrder() {
        return growOrder;
    }

    public void setGrowOrder(int growOrder) {
        this.growOrder = growOrder;
    }
    //endregion

    //region Public Methods
    @Override
    public ProceedResponseMethods getMethodType() {
        return ProceedResponseMethods.InitialAxonGrownResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
