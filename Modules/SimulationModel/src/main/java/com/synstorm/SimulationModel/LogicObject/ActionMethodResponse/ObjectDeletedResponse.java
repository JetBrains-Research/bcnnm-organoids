package com.synstorm.SimulationModel.LogicObject.ActionMethodResponse;

import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;

/**
 * Created by dvbozhko on 07/06/16.
 */
public class ObjectDeletedResponse implements IMethodResponse {
    //region Fields
    private final CellId id;
    //endregion

    //region Constructors
    public ObjectDeletedResponse(CellId cellId) {
        id = cellId;
    }
    //endregion

    //region Getters and Setters
    public CellId getId() {
        return id;
    }
    //endregion

    //region Public Methods
    @Override
    public ProceedResponseMethods getMethodType() {
        return ProceedResponseMethods.ObjectDeletedResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
