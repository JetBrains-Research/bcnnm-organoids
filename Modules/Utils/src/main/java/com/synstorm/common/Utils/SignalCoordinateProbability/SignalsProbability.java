package com.synstorm.common.Utils.SignalCoordinateProbability;

import gnu.trove.list.array.TDoubleArrayList;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Class for storing set of signal intensities for given coordinates of interest
 * Created by dvbozhko on 11/04/2017.
 */
public class SignalsProbability {
    //region Fields
    private final ArrayList<int[]> coordinates;
    private final TDoubleArrayList selectionSignalProbabilities;
    private double selectionSignalTotalP;
    //endregion

    //region Constructors
    public SignalsProbability() {
        selectionSignalTotalP = 0.;
        coordinates = new ArrayList<>();
        selectionSignalProbabilities = new TDoubleArrayList();
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    public void addCoordinateProbability(int[] coordinateToAdd) {
        coordinates.add(coordinateToAdd);
    }

    public void addSignalProbability(double selectionSignalP) {
        selectionSignalProbabilities.add(selectionSignalP);
        selectionSignalTotalP += selectionSignalP;
    }

    @Nullable
    public CoordinateProbabilityR getCoordinateCandidate(double checkProbability) {
        // calculating the most suitable coordinate candidate using uniform distribution
        // and normalized intensities as probabilities
        double totalProbability = 0.;
        for (int i = 0; i < coordinates.size(); ++i) {
            final double candidateProbability = selectionSignalProbabilities.get(i) / selectionSignalTotalP;
            totalProbability += candidateProbability;
            if (checkProbability < totalProbability) {
                final int[] candidate = coordinates.get(i);
                return new CoordinateProbabilityR(candidate, candidateProbability);
            }
        }

        return null;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
