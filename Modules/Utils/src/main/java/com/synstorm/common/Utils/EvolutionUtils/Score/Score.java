package com.synstorm.common.Utils.EvolutionUtils.Score;

import com.synstorm.common.Utils.EnumTypes.ScoreEnum;
import com.synstorm.common.Utils.EnumTypes.Sorting;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Created by human-research on 11/01/16.
 */
public class Score implements Comparable<Score> {
    //region Fields
    private Map<ScoreEnum, Double> scoreParameters;
    private double rating; //integral rating = Math.sqrt(weight*(R^2))
    private UUID individualID;
    private int indNumInProgeny;
    private String actionsHash;
    //endregion


    /**
     * New code
     */
    public Score(UUID individualID, String hash) {
        this.individualID = individualID;
        this.scoreParameters = new TreeMap<>();
        actionsHash = hash;
    }

    //region Getters and Setters
    public UUID getIndividualID() {
        return individualID;
    }

    public String getActionsHash() {
        return actionsHash;
    }

    public static Score GetCopy(Score oldScore, UUID newUUID) {
        Score nScore = new Score(newUUID, oldScore.getActionsHash());
        for (Map.Entry<ScoreEnum, Double> entry : oldScore.scoreParameters.entrySet()) {
            if (entry.getKey() != ScoreEnum.RecombinationCounter)
                nScore.setScoreParameter(entry.getKey(), entry.getValue());
            else
                nScore.setScoreParameter(entry.getKey(), 0.0);
        }
        nScore.setRating(0.0);
        return nScore;
    }

    int getIndNumInProgeny() {
        return indNumInProgeny;
    }

    void updateIndexInProgeny(int newIndex) {
        indNumInProgeny = newIndex;
    }

    public double getRating() {
        return rating;
    }

    void setRating(double rating) {
        this.rating = rating;
    }

    public Map<ScoreEnum, Double> getScoreParameters() {
        return scoreParameters;
    }

    public Double getScoreParameter(ScoreEnum parameter) {
        return scoreParameters.get(parameter);
    }

    public void setScoreParameter(ScoreEnum parameter, double value) {
        scoreParameters.put(parameter, value);
    }

    //endregion

    //region Public Methods
    @Override
    public int compareTo(@NotNull Score score) {
        for (Map.Entry<ScoreEnum, Sorting> scoreRule : ScoreTable.scoreRules.entrySet()) {
            ScoreEnum scoreParam = scoreRule.getKey();
            int k = scoreRule.getValue() == Sorting.ASC ? 1 : -1;
            if (this.scoreParameters.get(scoreParam) > score.scoreParameters.get(scoreParam))
                return k;
            else if (this.scoreParameters.get(scoreParam) < score.scoreParameters.get(scoreParam))
                return -1 * k;
        }
        return 0;
    }
    //endregion

    //region Private Methods
    //endregion
}
