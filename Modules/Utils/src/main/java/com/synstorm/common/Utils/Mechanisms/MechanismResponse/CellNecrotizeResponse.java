package com.synstorm.common.Utils.Mechanisms.MechanismResponse;

import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.DES.IProceedResponse;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;

@Model_v1
public class CellNecrotizeResponse implements IEventExecutionResult {
    //region Fields
    private final int parentId;
    private final String objectType;
    //endregion

    //region Constructors
    public CellNecrotizeResponse(int parentId, String objectType) {
        this.parentId = parentId;
        this.objectType = objectType;
    }
    //endregion

    //region Getters and Setters
    public int getParentId() {
        return parentId;
    }

    public String getObjectType() {
        return objectType;
    }
    //endregion

    //region Public Methods
    @Override
    public IProceedResponse getProceedMethod() {
        return ProceedResponseMethods.CellNecrotizeResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
