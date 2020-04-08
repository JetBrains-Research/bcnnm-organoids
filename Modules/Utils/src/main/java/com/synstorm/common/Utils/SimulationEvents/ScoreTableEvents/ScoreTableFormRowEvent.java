package com.synstorm.common.Utils.SimulationEvents.ScoreTableEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.Details.IndividualDetails;
import com.synstorm.common.Utils.EnumTypes.ScoreEnum;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Class for getting information about adding new score to the evolution score table.
 * Created by dvbozhko on 09/08/16.
 */
@Model_v0
@NonProductionLegacy
public class ScoreTableFormRowEvent implements ISimulationEvent {
    //region Fields
    private final UUID individualId;
    private final UUID firstParentId;
    private final UUID secondParentId;
    private final byte[] individualSeed;
    private final double finalRating;
    private final Map<ScoreEnum, Double> scores;
    private final List<Double> geneDurations;
    //endregion

    //region Constructors
    public ScoreTableFormRowEvent(IndividualDetails individualDetails, byte[] individualSeed,
                                  Map<ScoreEnum, Double> scores, double finalRating) {
        this.individualId = individualDetails.getUuid();
        this.firstParentId = individualDetails.getParent1UUID();
        this.secondParentId = individualDetails.getParent2UUID();
        this.individualSeed = individualSeed;
        this.scores = scores;
        this.finalRating = finalRating;
        this.geneDurations = individualDetails.getIndividualGeneDurations();
    }
    //endregion

    //region Getters and Setters
    public UUID getIndividualId() {
        return individualId;
    }

    public UUID getFirstParentId() {
        return firstParentId;
    }

    public UUID getSecondParentId() {
        return secondParentId;
    }

    public byte[] getIndividualSeed() {
        return individualSeed;
    }

    public Map<ScoreEnum, Double> getScores() {
        return scores;
    }

    public List<Double> getGeneDurations() {
        return geneDurations;
    }

    public double getFinalRating() {
        return finalRating;
    }
    //endregion

    //region Public Methods
    @Override
    public SimulationEvents getEventMethod() {
        return SimulationEvents.ScoreTableFormRowEvent;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
