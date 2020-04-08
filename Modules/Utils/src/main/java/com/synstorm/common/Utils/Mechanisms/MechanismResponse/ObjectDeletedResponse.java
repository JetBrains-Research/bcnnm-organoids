package com.synstorm.common.Utils.Mechanisms.MechanismResponse;

import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;

/**
 * Created by dvbozhko on 07/06/16.
 */

@Model_v1
public class ObjectDeletedResponse extends EmptyResponse implements IEventExecutionResult {
    //region Fields
    private final int id;
    //endregion

    //region Constructors
    public ObjectDeletedResponse(int cellId) {
        id = cellId;
    }
    //endregion

    //region Getters and Setters
    public int getId() {
        return id;
    }
    //endregion

    //region Public Methods
    @Override
    public ProceedResponseMethods getProceedMethod() {
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
