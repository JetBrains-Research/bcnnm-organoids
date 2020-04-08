package com.synstorm.common.Utils.Mechanisms.MechanismResponse;

import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;

/**
 * Created by dvbozhko on 07/06/16.
 */
@Model_v1
public class SynapseAddedResponse extends ObjectAddedResponse implements IEventExecutionResult {
    //region Fields
    private final int dstCellId;
    //endregion

    //region Constructors
    public SynapseAddedResponse(int parentId, int dstCellId, String objectType) {
        super(parentId, objectType);
        this.dstCellId = dstCellId;
    }
    //endregion

    //region Getters and Setters
    public int getDstCellId() {
        return dstCellId;
    }
    //endregion

    //region Public Methods
    @Override
    public ProceedResponseMethods getProceedMethod() {
        return ProceedResponseMethods.SynapseAddedResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
