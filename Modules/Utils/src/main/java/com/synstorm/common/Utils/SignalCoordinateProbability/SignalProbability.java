package com.synstorm.common.Utils.SignalCoordinateProbability;

import com.google.common.util.concurrent.AtomicDouble;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by dvbozhko on 11/04/2017.
 */
public class SignalProbability {
    //region Fields
    protected final Map<String, AtomicDouble> signalProbabilities;
    //endregion

    //region Constructors
    protected SignalProbability(SignalProbability signalProbabilities) {
        this.signalProbabilities = signalProbabilities.signalProbabilities;
    }

    public SignalProbability(Set<String> signals) {
        signalProbabilities = signals.stream().collect(Collectors.toMap(
                signal -> signal,
                signal -> new AtomicDouble(0.)
        ));
    }
    //endregion

    //region Getters and Setters
    public Map<String, AtomicDouble> getSignalProbabilities() {
        return signalProbabilities;
    }

    public double getSignalProbability(String signal) {
        return signalProbabilities.get(signal).get();
    }
    //endregion

    //region Public Methods
    public void replaceIfMore(String signal, double intensity) {
        final AtomicDouble signalValue = signalProbabilities.get(signal);
        if (signalValue.get() < intensity)
            signalValue.set(intensity);
    }

    public Predicate<SignalProbability> isIntensityEqual(String signal, double intensity) {
        return p -> p.signalProbabilities.get(signal).get() == intensity;
    }

    public Predicate<SignalProbability> isIntensityMore(String signal, double intensity) {
        return p -> p.signalProbabilities.get(signal).get() > intensity;
    }

    public Predicate<SignalProbability> isIntensityMoreOrEqual(String signal, double intensity) {
        return p -> p.signalProbabilities.get(signal).get() >= intensity;
    }

    public Predicate<SignalProbability> isIntensityLess(String signal, double intensity) {
        return p -> p.signalProbabilities.get(signal).get() < intensity;
    }

    public Predicate<SignalProbability> isIntensityLessOrEqual(String signal, double intensity) {
        return p -> p.signalProbabilities.get(signal).get() <= intensity;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
