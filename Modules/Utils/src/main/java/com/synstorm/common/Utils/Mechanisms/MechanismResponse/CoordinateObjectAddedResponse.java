package com.synstorm.common.Utils.Mechanisms.MechanismResponse;

import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;

@Model_v1
public class CoordinateObjectAddedResponse extends ObjectAddedResponse implements IEventExecutionResult {
    //region Fields
    private final int[] coordinate;
    //endregion

    //region Constructors
    public CoordinateObjectAddedResponse(int parentId, String objectType, int[] coordinate) {
        super(parentId, objectType);
        this.coordinate = coordinate;
    }
    //endregion

    //region Getters and Setters
    public int[] getCoordinate() {
        return coordinate;
    }
    //endregion

    //region Public Methods
    @Override
    public ProceedResponseMethods getProceedMethod() {
        return ProceedResponseMethods.CoordinateObjectAddedResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
