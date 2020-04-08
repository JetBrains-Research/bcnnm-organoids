package com.synstorm.SimulationModel.ModelAgent;

import com.synstorm.SimulationModel.Model.IndividualR;
import com.synstorm.SimulationModel.Model.Pipeline.IModelStage;
import com.synstorm.SimulationModel.Model.Pipeline.ModelPipeline;
import com.synstorm.common.Utils.ConsoleProgressBar.IProgressBar;
import com.synstorm.common.Utils.EnumTypes.ConsoleColors;
import com.synstorm.common.Utils.ModelStatistic.IndividualStatistics;
import org.jetbrains.annotations.NotNull;

/**
 * Callable class for calculating non-learning individuals in separate thread.
 * Uses for single individual calculation simulation.
 * Created by dvbozhko on 20/08/16.
 */
public class SingleIndividualAgent extends IndividualAgent {
    //region Fields
    //endregion

    //region Constructors

    public SingleIndividualAgent(IndividualR individual, IProgressBar progressBar, @NotNull IIndividualAgentListener listener) {
        super(individual, progressBar, listener);
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public void startCalculating() {
        long startTick = 0;
        for (int i = 0; i < ModelPipeline.INSTANCE.getStagesLength(); i++) {
            IModelStage modelStage = ModelPipeline.INSTANCE.getStage(i);
            int id = modelStage.getId();
            progressBar.startStage(id, modelStage.getStage());
            startTick = ModelPipeline.INSTANCE.getStage(i).calculateStage(startTick, progressBar);
            progressBar.endStage();
        }

    }

    @Override
    public void endCalculating() {
//        individual.exportStatistic();
        statisticsInProgressBar();
    }
    //endregion

    //region Package-local Methods
    //endregion

    @Override
    protected void statisticsInProgressBar() {
        IndividualStatistics statistics = individual.getStatistics();
        String yellow = ConsoleColors.ANSI_YELLOW.value();
        String reset = ConsoleColors.ANSI_RESET.value();

        String maxTickCount = yellow + String.valueOf(statistics.getSignificantTick()) + reset;
        String significantTickCount = yellow + String.valueOf(statistics.getSignificantTickCount()) + reset;
        String cellCount = yellow + String.valueOf(statistics.getCellCount()) + reset;
        String maxCellCount = yellow + String.valueOf(statistics.getMaxCellCount()) + reset;
        String synapseCount = yellow + String.valueOf(statistics.getSynapseCount()) + reset;
        String maxSynapseCount = yellow + String.valueOf(statistics.getMaxSynapseCount()) + reset;

//        Map<String, Integer> cellTypeCount = statistics.getCellTypeCounts();
//        int longestLength = cellTypeCount.keySet().stream()
//                .sorted((e1, e2) -> e1.length() > e2.length() ? -1 : 1)
//                .findAny().get().length();
//        StringBuilder types = new StringBuilder();
//        cellTypeCount.entrySet().stream()
//                .forEach(pair -> types.append("\n\t").append(String.format("%1$-" + longestLength + "s", pair.getKey()))
//                        .append(" := ").append(yellow).append(pair.getValue()).append(reset));
//
//        progressBar.printModelStatus(statistics.getIndividualId(), statistics.getSignificantTick());
//        progressBar.addAdditionalInfoList(new ArrayList<AdditionalInfo>() {{
//            add(new AdditionalInfo("Maximum tick", maxTickCount, 1));
//            add(new AdditionalInfo("Actual processed tick count", significantTickCount, 1));
//            add(new AdditionalInfo("Cell count by differential types", types.toString(), 2));
//            add(new AdditionalInfo("Final cell count", cellCount, 1));
//            add(new AdditionalInfo("Maximum cell count", maxCellCount, 1));
//            add(new AdditionalInfo("Final synapse count", synapseCount, 1));
//            add(new AdditionalInfo("Maximum synapse count", maxSynapseCount, 1));
//        }});
    }
    //endregion

    //region Private Methods
    //endregion
}