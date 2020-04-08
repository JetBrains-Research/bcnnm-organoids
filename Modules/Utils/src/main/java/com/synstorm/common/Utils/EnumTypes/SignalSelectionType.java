package com.synstorm.common.Utils.EnumTypes;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import org.jetbrains.annotations.Contract;

/**
 * Created by dvbozhko on 13/04/2017.
 */

@Model_v1

public enum SignalSelectionType {
//    Antigradient(SignalSelectionType::calcAntigradientP),
//    Gradient(SignalSelectionType::calcGradientP);

    Antigradient(SignalSelectionType::calcAntigradient),
    Gradient(SignalSelectionType::calcGradient);

//    private final IntensityToProbabilityCalculator calculator;
    private final IntensityGradientCalculator calculator;

//    SignalSelectionType(IntensityToProbabilityCalculator probabilityCalculator) {
//        calculator = probabilityCalculator;
//    }

    SignalSelectionType(IntensityGradientCalculator gradientCalculator) {
        calculator = gradientCalculator;
    }

//    public double calcProbability(double intensity) {
//        return calculator.execute(intensity);
//    }

    public double calcProbability(double curIntensity, double destIntensity) {
        return calculator.execute(curIntensity, destIntensity);
    }

//    @Contract(pure = true)
//    private static double calcGradientP(double intensity) {
//        return intensity;
//    }
//
//    @Contract(pure = true)
//    private static double calcAntigradientP(double intensity) {
//        return 1 / intensity;
//    }

    @Contract(pure = true)
    private static double calcGradient(double curIntensity, double destIntensity) {
        final double res = destIntensity - curIntensity;
        return res > 0 ? res : 0;// destIntensity - curIntensity;
    }

    @Contract(pure = true)
    private static double calcAntigradient(double curIntensity, double destIntensity) {
        final double res =  curIntensity - destIntensity;
        return res > 0 ? res : 0;
    }

//    @FunctionalInterface
//    private interface IntensityToProbabilityCalculator {
//        double execute(double intensity);
//    }

    @FunctionalInterface
    private interface IntensityGradientCalculator {
        double execute(double curIntensity, double destIntensity);
    }
}
