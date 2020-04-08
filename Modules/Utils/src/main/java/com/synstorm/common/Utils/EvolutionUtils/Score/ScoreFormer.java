package com.synstorm.common.Utils.EvolutionUtils.Score;

import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
import com.synstorm.common.Utils.EnumTypes.ScoreEnum;
import com.synstorm.common.Utils.ModelStatistic.IndividualStatistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class for managing scores for each individual
 * Created by dvbozhko on 3/5/16.
 */
public class ScoreFormer {
    //region Fields
    private ScoreTable scoreTable;
    private Set<ScoreEnum> scoreParameters;
    private Map<ScoreEnum, IScoreFormingMethod> scoreFormingMethods;
    //endregion

    //region Constructors
    public ScoreFormer() {
        scoreTable = new ScoreTable();
        scoreParameters = ModelLoader.getScoreRulesSet();
        scoreFormingMethods = new HashMap<>();
        scoreFormingMethodsInit();
    }
    //endregion

    //region Getters/Setters
    public ScoreTable getScoreTable() {
        return scoreTable;
    }
    //endregion

    //region Public methods
    public Score formScore(IndividualStatistics statistics) {
        Score score = new Score(statistics.getIndividualId(), statistics.getActionsHash());
        scoreParameters.stream().forEach(scoreEnum -> scoreFormingMethods.get(scoreEnum).execute(statistics, score));
        return score;
    }

    public void addScores(List<Score> scores) {
        scores.stream().forEach(this::addScore);
    }

    public void addScore(Score score) {
        scoreTable.addIndividualScore(score);
    }

    public void printScoreTable(boolean withRating, int topN) {
        scoreTable.printScoreTable(withRating, topN);
    }

    //endregion

    //region Private methods
    private void scoreFormingMethodsInit() {
        scoreFormingMethods.put(ScoreEnum.FinalCellCount, this::formFinalCellCountScore);
        scoreFormingMethods.put(ScoreEnum.FinalSynapseCount, this::formFinalSynapsesCountScore);
        scoreFormingMethods.put(ScoreEnum.FinalSynapseCellRatio, this::formFinalSynapseCellRatioScore);
        scoreFormingMethods.put(ScoreEnum.MaxCellCount, this::formMaxCellCountScore);
        scoreFormingMethods.put(ScoreEnum.MaxSynapseCount, this::formMaxSynapseCountScore);
        scoreFormingMethods.put(ScoreEnum.MaxSynapseCellRatio, this::formMaxSynapseCellRatioScore);
        scoreFormingMethods.put(ScoreEnum.FinalMaxCellRatio, this::formFinalMaxCellRatioScore);
        scoreFormingMethods.put(ScoreEnum.FinalMaxSynapseRatio, this::formFinalMaxSynapseRatioScore);
        scoreFormingMethods.put(ScoreEnum.Time, this::formTimeScore);
        scoreFormingMethods.put(ScoreEnum.LQTrain, this::formLQTrainScore);
        scoreFormingMethods.put(ScoreEnum.LQPreTrain, this::formLQPreTrainScore);
        scoreFormingMethods.put(ScoreEnum.LQControl, this::formLQControlScore);
        scoreFormingMethods.put(ScoreEnum.RecombinationCounter, this::formRecombinationCounter);

    }

    private void formFinalCellCountScore(IndividualStatistics statistics, Score score) {
        score.setScoreParameter(ScoreEnum.FinalCellCount, statistics.getCellCount());
    }

    private void formFinalSynapsesCountScore(IndividualStatistics statistics, Score score) {
        score.setScoreParameter(ScoreEnum.FinalSynapseCount, statistics.getSynapseCount());
    }

    private void formFinalSynapseCellRatioScore(IndividualStatistics statistics, Score score) {
        double synapseCellRatio = statistics.getSynapseCount() / (double) statistics.getCellCount();
        score.setScoreParameter(ScoreEnum.FinalSynapseCellRatio, synapseCellRatio);
    }

    private void formMaxCellCountScore(IndividualStatistics statistics, Score score) {
        score.setScoreParameter(ScoreEnum.MaxCellCount, statistics.getMaxCellCount());
    }

    private void formMaxSynapseCountScore(IndividualStatistics statistics, Score score) {
        score.setScoreParameter(ScoreEnum.MaxSynapseCount, statistics.getMaxSynapseCount());
    }

    private void formMaxSynapseCellRatioScore(IndividualStatistics statistics, Score score) {
        double maxSynapseCellRatio = statistics.getMaxSynapseCount() / (double) statistics.getMaxCellCount();
        score.setScoreParameter(ScoreEnum.MaxSynapseCellRatio, maxSynapseCellRatio);
    }

    private void formFinalMaxCellRatioScore(IndividualStatistics statistics, Score score) {
        double finalMaxCellRatio = statistics.getCellCount() / (double) statistics.getMaxCellCount();
        score.setScoreParameter(ScoreEnum.FinalMaxCellRatio, finalMaxCellRatio);
    }

    private void formFinalMaxSynapseRatioScore(IndividualStatistics statistics, Score score) {
        double finalMaxSynapseRatio = statistics.getSynapseCount() / (double) statistics.getMaxSynapseCount();
        score.setScoreParameter(ScoreEnum.FinalMaxSynapseRatio, finalMaxSynapseRatio);
    }

    private void formTimeScore(IndividualStatistics statistics, Score score) {
        score.setScoreParameter(ScoreEnum.Time, statistics.getSignificantTick());
    }

    private void formLQTrainScore(IndividualStatistics statistics, Score score) {
        score.setScoreParameter(ScoreEnum.LQTrain, statistics.getLqValue(ScoreEnum.LQTrain.getMatchingMode()));
    }

    private void formLQPreTrainScore(IndividualStatistics statistics, Score score) {
        score.setScoreParameter(ScoreEnum.LQPreTrain, statistics.getLqValue(ScoreEnum.LQPreTrain.getMatchingMode()));
    }

    private void formLQControlScore(IndividualStatistics statistics, Score score) {
        score.setScoreParameter(ScoreEnum.LQControl, statistics.getLqValue(ScoreEnum.LQControl.getMatchingMode()));
    }

    private void formRecombinationCounter(IndividualStatistics statistics, Score score) {
        score.setScoreParameter(ScoreEnum.RecombinationCounter, 0.0);
    }
    //endregion

    private interface IScoreFormingMethod {
        void execute(IndividualStatistics statistics, Score score);
    }
}
