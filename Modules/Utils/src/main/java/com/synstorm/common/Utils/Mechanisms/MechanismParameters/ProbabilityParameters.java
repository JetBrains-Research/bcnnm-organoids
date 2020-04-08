package com.synstorm.common.Utils.Mechanisms.MechanismParameters;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathwayParameters;

@Model_v1
public class ProbabilityParameters implements ISignalingPathwayParameters {
    //region Fields
    private final double probability;
    //endregion

    //region Constructors
    public ProbabilityParameters(double p) {
        probability = p;
    }
    //endregion

    //region Getters and Setters
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
