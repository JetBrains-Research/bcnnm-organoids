package com.synstorm.common.Utils.Details;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.CellFunctionalType;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by Dmitry.Bozhko on 3/25/2015.
 */

@Model_v0
@NonProductionLegacy

public class CellDetails implements IDetails {
    //region Fields
    private String type;
    private CellFunctionalType cellFunctionalType;
    private Map<String, String> emittedFactors;
    private Map<String, Set<String>> receivedFactors; //key is action, value is a set of factors
    private Set<ProliferationDetails> proliferationInfoSet;
    private Set<String> actions;

    private AxonDetails axonDetails;
    private DendriteDetails dendriteDetails;
    //endregion

    //region Constructors
    public CellDetails(String type, CellFunctionalType cellFunctionalType) {
        this.type = type;
        this.cellFunctionalType = cellFunctionalType;
        this.receivedFactors = new HashMap<>();
        this.emittedFactors = new HashMap<>();
        this.proliferationInfoSet = new HashSet<>();
        this.actions = new TreeSet<>();
        this.axonDetails = new AxonDetails();
        this.dendriteDetails = new DendriteDetails();
    }
    //endregion

    //region Getters and Setters
    public String getType() {
        return type;
    }

    public CellFunctionalType getCellFunctionalType() {
        return cellFunctionalType;
    }

    public Map<String, Set<String>> getReceivedFactors() { return Collections.unmodifiableMap(receivedFactors); }

    public void setCellFunctionalType(CellFunctionalType cellFunctionalType) {
        this.cellFunctionalType = cellFunctionalType;
    }

    public AxonDetails getAxonDetails() {
        return axonDetails;
    }

    public DendriteDetails getDendriteDetails() {
        return dendriteDetails;
    }

    public Set<String> getActions() {
        return actions;
    }

    public Set<ProliferationDetails> getProliferationInfoSet() {
        return proliferationInfoSet;
    }

    //endregion

    //region Public Methods
    public void addReceivedFactor(String actionName, List<String> factorsList) {
        if (receivedFactors.containsKey(actionName))
            receivedFactors.get(actionName).addAll(factorsList);
        else
            receivedFactors.put(actionName, new TreeSet<>(factorsList));
    }

    public void addEmittedFactor(String actionName) {
        emittedFactors.put(actionName, actionName);
    }

    public void addProliferationInfo(ProliferationDetails proliferationDetails) {
        proliferationInfoSet.add(proliferationDetails);
    }

    public void addAction(String actionName) {
        actions.add(actionName);
    }

    public boolean checkActionExist(String actionName) {
        return actions.contains(actionName);
    }

    public void addAxon(AxonDetails axonDetails) {
        this.axonDetails = axonDetails;
    }

    public void addDendrite(DendriteDetails dendriteDetails) {
        this.dendriteDetails = dendriteDetails;
    }

    public void removeActionWithReceivedFactor(String actionName) {
        receivedFactors.remove(actionName);
    }

    public void removeEmittedFactor(String factorName) {
        emittedFactors.remove(factorName);
    }

    @Nullable
    public Set<String> getReceivedFactorsByAction(String actionName) {
        if (receivedFactors.containsKey(actionName))
            return receivedFactors.get(actionName);

        return null;
    }

    public Set<String> getActionsByReceivedFactor(String factorName) {
        Set<String> result = new TreeSet<>();
        receivedFactors.entrySet().stream().forEach((entry) -> {
            if (entry.getValue().contains(factorName))
                result.add(entry.getKey());
        });
        return result;
    }

    public Set<String> getActionsWithReceivedFactors() {
        return receivedFactors.keySet();
    }

    public Set<String> getEmittedFactors() {
        return emittedFactors.keySet();
    }

    public boolean isFactorUsedByCell(String factorName) {
        return emittedFactors.keySet().contains(factorName) || getActionsByReceivedFactor(factorName).size() != 0;
    }
    //endregion

    //region Private Methods
    //endregion
}
