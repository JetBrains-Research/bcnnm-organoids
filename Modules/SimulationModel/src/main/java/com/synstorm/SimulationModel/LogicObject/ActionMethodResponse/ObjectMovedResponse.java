package com.synstorm.SimulationModel.LogicObject.ActionMethodResponse;

import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.LObjectId;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

/**
 * Created by dvbozhko on 07/06/16.
 */
public class ObjectMovedResponse implements IMethodResponse {
    //region Fields
    private final LObjectId id;
    private final ICoordinate coordinate;
    private final double probability;
    //endregion

    //region Constructors
    public ObjectMovedResponse(LObjectId objectId, ICoordinate moveCoordinate, double movingProbability) {
        id = objectId;
        coordinate = moveCoordinate;
        probability = movingProbability;
    }
    //endregion

    //region Getters and Setters
    public LObjectId getId() {
        return id;
    }

    public ICoordinate getCoordinate() {
        return coordinate;
    }

    public int[] getCoordinateAsArray() {
        return new int[]{coordinate.getX(), coordinate.getY(), coordinate.getZ()};
    }

    public double getProbability() {
        return probability;
    }
    //endregion

    //region Public Methods
    @Override
    public ProceedResponseMethods getMethodType() {
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
