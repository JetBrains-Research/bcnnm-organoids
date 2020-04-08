package com.synstorm.common.Utils.Details;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.ActionFunctionalType;
import com.synstorm.common.Utils.EnumTypes.ActionSignalType;
import com.synstorm.common.Utils.EnumTypes.SignalingTopologicalType;
import com.synstorm.common.Utils.EvolutionUtils.Gene.Gene;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dmitry.Bozhko on 3/25/2015.
 */

@Model_v0
@NonProductionLegacy

public class ActionDetails implements IDetails {
    //region Fields
    private String name;
    private int neighborhood;
    private ActionSignalType signalType;
    private SignalingTopologicalType signalingTopologicalType;
    private ActionFunctionalType actionFunctionalType;
    private int duration;
    private boolean initial;
    private boolean repeatable;
    private Map<UUID, Integer> durationByGenes;

    //endregion

    //region Constructors
    public ActionDetails(String name, String neighborhood, String signalType, String signalingTopologicalType,
                         String functionalType, String duration, String initial, String repeatable) {
        this.name = name;
        this.neighborhood = Integer.parseInt(neighborhood);
        this.signalType = ActionSignalType.valueOf(signalType);
        this.signalingTopologicalType = SignalingTopologicalType.valueOf(signalingTopologicalType);
        this.actionFunctionalType = ActionFunctionalType.valueOf(functionalType);
        this.duration = (int) Double.parseDouble(duration);
        this.durationByGenes = new HashMap<>();
        this.initial = initial.equals("1");
        this.repeatable = repeatable.equals("1");
        this.durationByGenes = new ConcurrentHashMap<>();
    }
    //endregion

    //region Getters and Setters
    public String getName() {
        return name;
    }

    public int getNeighborhood() {
        return neighborhood;
    }

    public ActionSignalType getSignalType() {
        return signalType;
    }

    public SignalingTopologicalType getSignalingTopologicalType() {
        return signalingTopologicalType;
    }

    public ActionFunctionalType getActionFunctionalType() {
        return actionFunctionalType;
    }

    public int getDuration() {
        return duration;
    }

    public int getDurationByGenes(UUID individualId) {
        if (isActionPresentedByGenes(individualId))
            return durationByGenes.get(individualId);
        else
            return duration;
    }

    public boolean isActionPresentedByGenes(UUID individualId) {
        return durationByGenes.containsKey(individualId);
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public boolean isInitial() {
        return initial;
    }

    public boolean isFactor() {
         return signalingTopologicalType.equals(SignalingTopologicalType.OuterCell)
                && actionFunctionalType.equals(ActionFunctionalType.Do);
    }

    public void setNeighborhood(int neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void setSignalType(ActionSignalType signalType) {
        this.signalType = signalType;
    }

    public void setSignalingTopologicalType(SignalingTopologicalType signalingTopologicalType) {
        this.signalingTopologicalType = signalingTopologicalType;
    }

    public void setActionFunctionalType(ActionFunctionalType triggerType) {
        this.actionFunctionalType = triggerType;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }

    //endregion

    //region Public Methods
    public void clearDurationByGenes() {
        durationByGenes.clear();
    }

    public void calculateDurationByGenes(UUID individualId, List<Gene> genes) {
        final double gaDuration = genes.stream()
                .filter(g -> g.getActionPresentationMap().get(name) != 0)
                .map(g -> g.getDuration() * g.getActionPresentationMap().get(name))
                .reduce(.0, (d1, d2) -> d1 + d2);
        durationByGenes.put(individualId, roundValue(gaDuration));
    }

    //endregion

    //region Private Methods
    @Contract(pure = true)
    private int roundValue(double value) {
        return (int) Math.round(value);
    }
    //endregion
}
