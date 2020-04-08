package com.synstorm.common.Utils.SignalCoordinateProbability;

import com.google.common.math.DoubleMath;
import gnu.trove.map.hash.TIntDoubleHashMap;

import java.util.function.Predicate;

/**
 * Created by dvbozhko on 11/04/2017.
 */
public class SignalsIntensity {
    //region Fields
    private final double epsilon;
    private final TIntDoubleHashMap signalIntensities;
    private int[] signals;
    //endregion

    //region Constructors
    public SignalsIntensity() {
        epsilon = 1e-10;
        signalIntensities = new TIntDoubleHashMap();
    }
    //endregion

    //region Public Methods
    public int[] getSignals() {
        if (signals == null)
            signals = signalIntensities.keys();

        return signals;
    }

    public void addSignal(int signal) {
        signalIntensities.put(signal, 0.);
    }

    public void addIntensity(int signal, double intensity) {
        signalIntensities.put(signal, intensity);
    }

    public Predicate<SignalsIntensity> isIntensityEqual(int signal, double intensity) {
        return p -> p.hasSignal(signal) && p.isEqual(signal, intensity);
    }

    public Predicate<SignalsIntensity> isIntensityMore(int signal, double intensity) {
        return p -> p.hasSignal(signal) && p.isMore(signal, intensity);
    }

    public Predicate<SignalsIntensity> isIntensityMoreOrEqual(int signal, double intensity) {
        return p -> p.hasSignal(signal) && p.isMoreOrEqual(signal, intensity);
    }

    public Predicate<SignalsIntensity> isIntensityLess(int signal, double intensity) {
        return p -> p.hasSignal(signal) && p.isLess(signal, intensity);
    }

    public Predicate<SignalsIntensity> isIntensityLessOrEqual(int signal, double intensity) {
        return p -> p.hasSignal(signal) && p.isLessOrEqual(signal, intensity);
    }
    //endregion

    //region Private Methods
    private boolean hasSignal(int signal) {
        return signalIntensities.containsKey(signal);
    }

    private boolean isEqual(int signal, double intensity) {
        return DoubleMath.fuzzyEquals(signalIntensities.get(signal), intensity, epsilon);
    }

    private boolean isMore(int signal, double intensity) {
        return DoubleMath.fuzzyCompare(signalIntensities.get(signal), intensity, epsilon) == 1;
    }

    private boolean isLess(int signal, double intensity) {
        return DoubleMath.fuzzyCompare(signalIntensities.get(signal), intensity, epsilon) == -1;
    }

    private boolean isMoreOrEqual(int signal, double intensity) {
        final int compare = DoubleMath.fuzzyCompare(signalIntensities.get(signal), intensity, epsilon);
        return compare == 1 || compare == 0;
    }

    private boolean isLessOrEqual(int signal, double intensity) {
        final int compare = DoubleMath.fuzzyCompare(signalIntensities.get(signal), intensity, epsilon);
        return compare == -1 || compare == 0;
    }
    //endregion
}
