package com.synstorm.common.Utils.Mechanisms.MechanismResponse;

import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;

@Model_v1
public class CompartmentAddedResponse extends ObjectAddedResponse implements IEventExecutionResult {
    //region Fields
    //endregion

    //region Constructors
    public CompartmentAddedResponse(int parentId, String objectType) {
        super(parentId, objectType);
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public ProceedResponseMethods getProceedMethod() {
        return ProceedResponseMethods.CompartmentAddedResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
