package com.synstorm.common.Utils.ConsoleProgressBar;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;

/**
 * Progress bar class for single learning simulation.
 * Performance calculating in ApS: answers per second.
 * Created by dvbozhko on 3/1/16.
 */

@Model_v0
@ProductionLegacy

public class TrainingProgressBar extends BaseConsoleProgressBar implements IProgressBar {
    //region Fields
    //endregion

    //region Constructors
    public TrainingProgressBar(String simulationName, int stageCnt, long simulationStartTime) {
        super("Individual simulation with learning", simulationName, stageCnt, simulationStartTime);
    }
    //endregion

    //region Getters/Setters
    //endregion

    //region Public methods
    //endregion

    //region Protected Methods
    @Override
    protected String calculateAdditionalInfo(Object... args) {
        final int conditionCounter = (int) args[1];
        double performance = calculatePerformance(conditionCounter);
        return "ApS: " + performanceFormatter.format(performance) + separator + "Answers done: " + conditionCounter;
    }
}
