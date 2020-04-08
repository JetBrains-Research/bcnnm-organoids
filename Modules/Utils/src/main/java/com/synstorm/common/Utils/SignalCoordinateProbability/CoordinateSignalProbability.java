package com.synstorm.common.Utils.SignalCoordinateProbability;

import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

/**
 * Created by dvbozhko on 12/04/2017.
 */
public class CoordinateSignalProbability extends SignalProbability {
    //region Fields
    private final ICoordinate coordinate;
    //endregion

    //region Constructors
    public CoordinateSignalProbability(ICoordinate coordinate, SignalProbability signalProbability) {
        super(signalProbability);
        this.coordinate = coordinate;
    }
    //endregion

    //region Getters and Setters
    public ICoordinate getCoordinate() {
        return coordinate;
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
