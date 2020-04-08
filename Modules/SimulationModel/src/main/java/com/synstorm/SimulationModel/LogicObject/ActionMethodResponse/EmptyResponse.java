package com.synstorm.SimulationModel.LogicObject.ActionMethodResponse;

import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;

/**
 * Empty response class for signaling pathways.
 * Created by dvbozhko on 06/06/16.
 */
public class EmptyResponse implements IMethodResponse {

    @Override
    public ProceedResponseMethods getMethodType() {
        return ProceedResponseMethods.EmptyResponse;
    }
}
