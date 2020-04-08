package com.synstorm.common.Utils.SignalCoordinateProbability;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;

/**
 * Created by dvbozhko on 13/04/2017.
 */
@Model_v1
public class CoordinateProbabilityR {
    //region Fields
    private final int[] coordinate;
    private final double probability;
    //endregion

    //region Constructors
    public CoordinateProbabilityR(int[] coordinate, double probability) {
        this.coordinate = coordinate;
        this.probability = probability;
    }
    //endregion

    //region Getters and Setters
    public int[] getCoordinate() {
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
