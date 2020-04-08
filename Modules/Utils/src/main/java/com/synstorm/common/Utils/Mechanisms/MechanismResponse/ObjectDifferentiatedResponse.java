package com.synstorm.common.Utils.Mechanisms.MechanismResponse;

import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;

/**
 * Created by dvbozhko on 07/06/16.
 */

@Model_v1
public class ObjectDifferentiatedResponse extends EmptyResponse implements IEventExecutionResult {
    //region Fields
    private final int id;
    private final String cellType;
    //endregion

    //region Constructors
    public ObjectDifferentiatedResponse(int id, String cellType) {
        this.id = id;
        this.cellType = cellType;
    }
    //endregion

    //region Getters and Setters
    public int getId() {
        return id;
    }

    public String getCellType() {
        return cellType;
    }
    //endregion

    //region Public Methods
    @Override
    public ProceedResponseMethods getProceedMethod() {
        return ProceedResponseMethods.ObjectDifferentiatedResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
