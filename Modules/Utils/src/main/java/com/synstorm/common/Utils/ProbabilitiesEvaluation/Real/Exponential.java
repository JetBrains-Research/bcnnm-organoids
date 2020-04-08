package com.synstorm.common.Utils.ProbabilitiesEvaluation.Real;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.jetbrains.annotations.NotNull;

/**
 * Author: Vlad Myrov
 * Date: 04/10/2018
 */

@Model_v1
public class Exponential extends RealDistribution {
    //region Fields
    //endregion


    //region Constructors
    public Exponential(@NotNull double[] args) {
        distribution = new ExponentialDistribution(args[0]);
    }
    //endregion


    //region Getters and Setters
    //endregion


    //region Public Methods
    public double getProbabilityDensity(double[] args) {
        return distribution.density(args[0]);
    }

    public double getCumulativeProbability(double[] args) {
        return distribution.cumulativeProbability(args[0]);
    }

    public double getEventProbability(double[] args) {
        return getProbabilityDensity(args);
    }
    //endregion


    //region Private Methods
    //endregion
}