package com.synstorm.common.Utils.Mechanisms.MechanismResponse;

import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;

/**
 * Created by dvbozhko on 22/11/2016.
 */

@Model_v1
public class NeurotransmitterReleaseResponse implements IEventExecutionResult {
    //region Fields
    private final int presynapticCellId;
    private final int postsynapticCellId;
    //endregion

    //region Constructors
    public NeurotransmitterReleaseResponse(int preId, int postId) {
        presynapticCellId = preId;
        postsynapticCellId = postId;
    }
    //endregion

    //region Getters and Setters
    public int getPostsynapticCellId() {
        return postsynapticCellId;
    }

    public int getPresynapticCellId() {
        return presynapticCellId;
    }
    //endregion

    //region Public Methods
    @Override
    public ProceedResponseMethods getProceedMethod() {
        return ProceedResponseMethods.NeurotransmitterReleaseResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
