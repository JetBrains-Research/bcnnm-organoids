package com.synstorm.common.Utils.Mechanisms.MechanismResponse;

import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;

/**
 * Created by dvbozhko on 07/06/16.
 */
@Model_v1
public class ObjectMovedResponse implements IEventExecutionResult {
    //region Fields
    private final int id;
    private final int[] coordinate;
    private final double probability;
    //endregion

    //region Constructors
    public ObjectMovedResponse(int objectId, int[] moveCoordinate, double movingProbability) {
        id = objectId;
        coordinate = moveCoordinate;
        probability = movingProbability;
    }
    //endregion

    //region Getters and Setters
    public int getId() {
        return id;
    }

    public int[] getCoordinate() {
        return coordinate;
    }

    public double getProbability() {
        return probability;
    }
    //endregion

    //region Public Methods
    @Override
    public ProceedResponseMethods getProceedMethod() {
        return ProceedResponseMethods.ObjectMovedResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
