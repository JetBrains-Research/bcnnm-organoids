package com.synstorm.common.Utils.ProbabilitiesEvaluation.Integer;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import org.apache.commons.math3.distribution.PascalDistribution;
import org.jetbrains.annotations.NotNull;

/**
 * Author: Vlad Myrov
 * Date: 04/10/2018
 */

@Model_v1
public class Pascal extends IntegerDistribution {
    //region Fields
    //endregion


    //region Constructors
    public Pascal(@NotNull double[] args) {
        distribution = new PascalDistribution((int)args[0], args[1]);
    }
    //endregion


    //region Getters and Setters

    //endregion


    //region Public Methods
    public double getProbabilityDensity(double[] args) {
        return distribution.probability((int)args[0]);
    }

    public double getCumulativeProbability(double[] args) {
        return distribution.cumulativeProbability((int)args[0]);
    }

    public double getEventProbability(double[] args) {
        return getProbabilityDensity(args);
    }
    //endregion


    //region Private Methods
    //endregion
}