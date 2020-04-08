package com.synstorm.common.Utils.SignalCoordinateProbability;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

/**
 * Created by dvbozhko on 13/04/2017.
 */
@Model_v0
@NonProductionLegacy
public class CoordinateProbability {
    //region Fields
    private final ICoordinate coordinate;
    private final double probability;
    //endregion

    //region Constructors
    public CoordinateProbability(ICoordinate coordinate, double probability) {
        this.coordinate = coordinate;
        this.probability = probability;
    }
    //endregion

    //region Getters and Setters
    public ICoordinate getCoordinate() {
        return coordinate;
    }

    public double getProbability() {
        return probability;
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
