package com.synstorm.common.Utils.Mechanisms.MechanismResponse;

import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.DES.IProceedResponse;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;

/**
 * Empty response class for signaling pathways.
 * Created by dvbozhko on 06/06/16.
 */

@Model_v1
public class EmptyResponse implements IEventExecutionResult {
    @Override
    public IProceedResponse getProceedMethod() {
        return ProceedResponseMethods.EmptyResponse;
    }
}
