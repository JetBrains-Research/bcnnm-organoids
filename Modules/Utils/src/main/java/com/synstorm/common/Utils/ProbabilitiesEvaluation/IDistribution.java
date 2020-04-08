package com.synstorm.common.Utils.ProbabilitiesEvaluation;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;

/**
 * Author: Vlad Myrov
 * Date: 04/10/2018
 */

@Model_v1
public interface IDistribution {
    double getProbabilityDensity(double[] args);
    double getCumulativeProbability(double[] args);
    double getEventProbability(double[] args);
}
