package com.synstorm.common.Utils.ProbabilitiesEvaluation;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ProbabilitiesEvaluation.Integer.Binomial;
import com.synstorm.common.Utils.ProbabilitiesEvaluation.Integer.Pascal;
import com.synstorm.common.Utils.ProbabilitiesEvaluation.Integer.Poisson;
import com.synstorm.common.Utils.ProbabilitiesEvaluation.Real.Exponential;
import com.synstorm.common.Utils.ProbabilitiesEvaluation.Real.Gaussian;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Author: Vlad Myrov
 * Date: 04/10/2018
 */

@Model_v1
public enum DistributionFactory {
    INSTANCE;
    //region Fields
    private Map<String, Function<double[], IDistribution>> classMap;
    //endregion


    //region Constructors
    DistributionFactory() {
        classMap = createDistributionMap();
    }
    //endregion


    //region Getters and Setters
    //endregion


    //region Public Methods
    public IDistribution generateDistribution(String type, double[] args) {
        Function<double[], IDistribution> func = classMap.get(type);
        return func.apply(args);
    }
    //endregion


    //region Private Methods
    private Map<String, Function<double[], IDistribution>> createDistributionMap() {
        Map<String, Function<double[], IDistribution>> distributionMap = new HashMap<>();

        distributionMap.put("Gaussian", Gaussian::new);
        distributionMap.put("Exponential", Exponential::new);
        distributionMap.put("Poisson", Poisson::new);
        distributionMap.put("Binomial", Binomial::new);
        distributionMap.put("Pascal", Pascal::new);

        return distributionMap;
    }
    //endregion
}
