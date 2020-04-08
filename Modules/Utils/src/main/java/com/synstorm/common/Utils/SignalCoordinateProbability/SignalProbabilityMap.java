package com.synstorm.common.Utils.SignalCoordinateProbability;

import com.google.common.util.concurrent.AtomicDouble;
import com.synstorm.common.Utils.EnumTypes.SignalSelectionType;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Class for storing set of signal intensities for given coordinates of interest
 * Created by dvbozhko on 11/04/2017.
 */
public class SignalProbabilityMap {
    //region Fields
    private int capacity;
    private final ICoordinate[] coordinates;
    private final Map<ICoordinate, SignalProbability> signalProbabilityMap;
    private final Set<String> checkingSignals;
    private final Map<String, AtomicDouble> signalsAmount;
    //endregion

    //region Constructors
    public SignalProbabilityMap(Set<String> signals) {
        capacity = 0;                                       //actual coordinates and probabilities amount
        coordinates = new ICoordinate[27];                  //27 - max neighbors count

        signalProbabilityMap = new HashMap<>();
        checkingSignals = signals;
        signalsAmount = signals.stream().collect(Collectors.toMap(
                signal -> signal,
                signal -> new AtomicDouble(0.)
        ));
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    /**
     * Adds list of coordinates {@param coordinatesToAdd} of interest for further probability calculation
     *
     * @param coordinatesToAdd  list of coordinates of interest
     */
    public void addCoordinates(List<ICoordinate> coordinatesToAdd) {
        coordinatesToAdd.forEach(this::addCoordinate);
    }

    /**
     * Adds coordinate {@param coordinateToAdd} of interest for further probability calculation
     *
     * @param coordinateToAdd   coordinate of interest
     */
    public void addCoordinate(ICoordinate coordinateToAdd) {
        coordinates[capacity++] = coordinateToAdd;
        signalProbabilityMap.put(coordinateToAdd, new SignalProbability(checkingSignals));
    }

    /**
     * Adds intensity {@param intensity} of given signal {@param signal}
     * calculated in space by coordinate {@param coordinate}. Addition drops if signal
     * in given coordinate is more than we are trying to add
     *
     * @param coordinate    coordinate of interest
     * @param signal        signal of interest
     * @param intensity     value of signal intensity
     */
    public void addSignalIntensity(ICoordinate coordinate, String signal, double intensity) {
        signalProbabilityMap.get(coordinate).replaceIfMore(signal, intensity);
    }

    /**
     * Calculates the most probable coordinate from possible based on signal selection {@param signalSelection},
     * given predicate {@param predicate} and uniformly distributed check value {@param checkProbability}
     * @param checkProbability  check value, must be obtained from uniform distribution
     * @param predicate         given predicate based on signal intensity values and their combination
     * @param signalSelection   map with 'signal-type of selection' pair
     * @return                  'coordinate-probability' pair
     */
    @Nullable
    public CoordinateProbability getCoordinateCandidate(double checkProbability, Predicate<SignalProbability> predicate,
                                                        Map<String, SignalSelectionType> signalSelection) {
        int candidatesCapacity = 0;
        final int signalsCountToCheck = signalSelection.size();
        final CoordinateSignalProbability[] candidates = new CoordinateSignalProbability[capacity];

        // calculating coordinate candidates based on predicate
        // and calculating sum of similar signal intensities for further normalisation
        for (int i = 0; i < capacity; i++) {
            final ICoordinate coordinate = coordinates[i];
            final SignalProbability signalProbability = signalProbabilityMap.get(coordinate);
            if (predicate.test(signalProbability)) {
                candidates[candidatesCapacity++] = new CoordinateSignalProbability(coordinate, signalProbability);
                signalProbability.getSignalProbabilities().forEach((signal, value) -> {
                    if (signalSelection.containsKey(signal)) {
                        final double intensity = value.get();
                        final SignalSelectionType selectionType = signalSelection.get(signal);
                        if (selectionType == SignalSelectionType.Gradient)
                            signalsAmount.get(signal).addAndGet(intensity);
                        else
                            signalsAmount.get(signal).addAndGet(1 - intensity);
                    }
                });
            }
        }

        // calculating the most suitable coordinate candidate using uniform distribution
        // and normalized intensities as probabilities
        double totalProbability = 0.;
        for (int i = 0; i < candidatesCapacity; i++) {
            final CoordinateSignalProbability coordinateSignalProbability = candidates[i];
            final AtomicDouble fullProbability = new AtomicDouble(0.);

            signalSelection.forEach((key, value) -> {
                final double intensity = coordinateSignalProbability.getSignalProbability(key);
                final double maxIntensity = signalsAmount.get(key).get();
                fullProbability.addAndGet(intensity / maxIntensity);
            });

            final double candidateProbability = fullProbability.get() / signalsCountToCheck;
            totalProbability += candidateProbability;
            if (checkProbability < totalProbability) {
                ICoordinate candidate = coordinateSignalProbability.getCoordinate();
                return new CoordinateProbability(candidate, candidateProbability);
            }
        }

        return null;
    }

    /**
     * Calculates the most probable signal with value of intensity based on {@param signalSelection}
     * check value {@param checkProbability} in given coordinate {@param coordinate} and passed
     * predicate {@param predicate}
     *
     * @param checkProbability  check value, must be obtained from uniform distribution
     * @param coordinate        coordinate of interest
     * @param predicate         given predicate based on signal intensity values and their combination
     * @param signalSelection   map with signal-type of selection pair
     * @return                  name of the most probable signal
     */
    public String getSignalCandidateByCoordinate(double checkProbability, ICoordinate coordinate,
                                                 Predicate<SignalProbability> predicate,
                                                 Map<String, SignalSelectionType> signalSelection) {
        final String[] candidateSignal = {""};
        CoordinateSignalProbability potentialCandidate = null;

        // checking coordinate for being a potential candidate
        // based on given predicate
        final SignalProbability signalProbability = signalProbabilityMap.get(coordinate);
        if (predicate.test(signalProbability))
            potentialCandidate = new CoordinateSignalProbability(coordinate, signalProbability);

        final AtomicDouble maxProbability = new AtomicDouble(0.);
        if (potentialCandidate != null) {
            final CoordinateSignalProbability candidate = potentialCandidate;
            signalSelection.forEach((key, value) -> {
                final double intensity = candidate.getSignalProbability(key);
                final AtomicDouble normalizedProbability = new AtomicDouble(0.);

                if (value == SignalSelectionType.Gradient)
                    normalizedProbability.addAndGet(intensity);
                else
                    normalizedProbability.addAndGet(1 - intensity);

                if (maxProbability.get() < normalizedProbability.get() && normalizedProbability.get() > checkProbability) {
                    candidateSignal[0] = key;
                    maxProbability.set(normalizedProbability.get());
                }
            });
        }

        return candidateSignal[0];
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
