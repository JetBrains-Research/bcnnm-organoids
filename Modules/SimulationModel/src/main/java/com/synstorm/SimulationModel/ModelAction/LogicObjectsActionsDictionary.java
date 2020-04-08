package com.synstorm.SimulationModel.ModelAction;

import com.synstorm.SimulationModel.SimulationIdentifiers.ModelActionIds.ModelActionId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.SignalingTopologicalType;
import com.synstorm.common.Utils.EnumTypes.TransactionRecordType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class for storing signaling pathway descriptions for factors and glowing points
 * Created by dvbozhko on 09/06/16.
 */

@Model_v0
@NonProductionLegacy
public class LogicObjectsActionsDictionary {
    //region Fields
    private final Map<ModelActionId, LogicObjectActionDescription> outerSignals;
    private final Map<ModelActionId, GlowingPointActionDescription> gpWithKpSignals;
    //endregion

    //region Constructors
    public LogicObjectsActionsDictionary() {
        outerSignals = new LinkedHashMap<>();
        gpWithKpSignals = new LinkedHashMap<>();
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    public Set<LogicObjectActionDescription> actionDescriptionsForSignals(Set<String> signals) {
        return outerSignals.entrySet().stream()
                .filter(signal -> signals.contains(signal.getValue().getActionName()))
                .map(Entry::getValue)
                .collect(Collectors.toSet());
    }

    public void updateActionDescriptionsInfo(List<LogicObjectActionDescription> adList) {
        adList.forEach(this::updateActionDescription);
    }

    public Map<ModelActionId, GlowingPointActionDescription> getGpWithKpSignals(Set<String> receivedFactors) {
        return gpWithKpSignals.entrySet().stream()
                .filter(e -> receivedFactors.contains(e.getValue().getActionName()))
                .collect(Collectors.toMap(
                        Entry::getKey,
                        Entry::getValue
                ));
    }
    //endregion

    //region Private Methods
    private void updateActionDescription(LogicObjectActionDescription actionDescription) {
        TransactionRecordType recordType = actionDescription.getTransactionRecordType();
        ModelActionId actionId = actionDescription.getId();

        switch (recordType) {
            case Insert:
                actionDescription.resetActionState();
                if (actionDescription.signalingTopologicalType == SignalingTopologicalType.OuterCell) {
                    outerSignals.put(actionId, actionDescription);
                    if (actionDescription instanceof GlowingPointActionDescription && actionDescription.isKpCalculated())
                        gpWithKpSignals.put(actionId, (GlowingPointActionDescription) actionDescription);
                }
                break;
            case Delete:
                if (outerSignals.containsKey(actionId)) {
                    outerSignals.remove(actionId);
                    if (gpWithKpSignals.containsKey(actionId))
                        gpWithKpSignals.remove(actionId);
                }
                break;
            default:
                break;
        }
    }
    //endregion
}
