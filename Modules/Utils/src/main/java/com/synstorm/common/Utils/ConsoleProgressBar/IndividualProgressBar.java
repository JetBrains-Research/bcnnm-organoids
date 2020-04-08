package com.synstorm.common.Utils.ConsoleProgressBar;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;

/**
 * Progress bar class for single non-learning simulation.
 * Performance calculating in TpS: tick per second.
 * Created by Dmitry.Bozhko on 3/12/2015.
 */

@Model_v1

public class IndividualProgressBar extends BaseConsoleProgressBar implements IProgressBar {
    //region Fields
    //endregion

    //region Constructors
    public IndividualProgressBar(String simulationName, int stageCnt, long simulationStartTime) {
        super("Individual simulation", simulationName, stageCnt, simulationStartTime);
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    //endregion

    //region Protected Methods
    @Override
    protected String calculateAdditionalInfo(Object... args) {
        final long conditionCounter = (long) args[1];
        double performance = calculatePerformance(conditionCounter);
        return "TpS: " + performanceFormatter.format(performance) + separator + "Ticks done: " + conditionCounter;
    }
    //endregion
}