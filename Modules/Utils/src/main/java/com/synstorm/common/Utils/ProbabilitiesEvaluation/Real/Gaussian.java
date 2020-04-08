package com.synstorm.common.Utils.ProbabilitiesEvaluation.Real;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.jetbrains.annotations.NotNull;

/**
 * Author: Vlad Myrov
 * Date: 04/10/2018
 */

@Model_v1
public class Gaussian extends RealDistribution {
    //region Fields
    //endregion

    //region Constructors
    public Gaussian(@NotNull double[] args) {
        distribution = new NormalDistribution(args[0], args[1]);
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
        return getProbabilityDensity(args) / distribution.density(((NormalDistribution)distribution).getMean());
    }
    //endregion


    //region Private Methods
    //endregion
}