package com.synstorm.common.Utils.Mechanisms.MechanismResponse;

import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;

/**
 * Created by dvbozhko on 07/06/16.
 */

@Model_v1
public abstract class ObjectAddedResponse extends EmptyResponse implements IEventExecutionResult {
    //region Fields
    private final int parentId;
    private final String objectType;
    //endregion

    //region Constructors
    public ObjectAddedResponse(int parentId, String objectType) {
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
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
