package com.synstorm.common.Utils.ConsoleProgressBar;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;

/**
 * Progress bar class for multiple simulations. Can be used in evolution,
 * multi-configuration and multi-seeds simulations.
 * Performance calculating in IpH: individuals per hour.
 * Created by dvbozhko on 19/08/16.
 */

@Model_v0
@ProductionLegacy

public class MultiIndividualProgressBar extends BaseConsoleProgressBar implements IProgressBar {
    //region Fields
    //endregion

    //region Constructors
    public MultiIndividualProgressBar(String simulationName, int stageCnt, long simulationStartTime) {
        super("Multi agent simulation", simulationName, stageCnt, simulationStartTime);
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    @Override
    protected String calculateAdditionalInfo(Object... args) {
        final int individualsDone = (int) args[1];
        final int currentIndividuals = (int) args[2];
        final int cBatches = (int) args[3];
        final int wBatches = (int) args[4];
        double performance = calculatePerformance(individualsDone) * 3600;
        return "IpH: " + String.format("%1$11s", performanceFormatter.format(performance)) + separator + "Done: " +
                String.format("%1$4s", individualsDone) + separator + "Simulating: " +
                String.format("%1$2s", currentIndividuals) + separator + "[C] batches: " +
                String.format("%1$2s", cBatches) + separator + "[W] batches: " +
                String.format("%1$2s", wBatches);
    }
    //endregion

    //region Private Methods
    //endregion
}
