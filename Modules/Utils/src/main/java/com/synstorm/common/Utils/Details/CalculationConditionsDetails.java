package com.synstorm.common.Utils.Details;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.StatisticType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by human-research on 27/04/2017.
 */

@Model_v0
@NonProductionLegacy

public class CalculationConditionsDetails {
    private Map<StatisticType, String> conditionsMap;
    //region Fields

    //endregion


    //region Constructors

    public CalculationConditionsDetails() {
        this.conditionsMap = new HashMap<>();
    }

    //endregion


    //region Getters and Setters
    public String getValue(StatisticType condition) {
        return conditionsMap.get(condition);
    }
    //endregion


    //region Public Methods
    public Set<StatisticType> getAllConditions() {
        return conditionsMap.keySet();
    }

    public boolean addCondition(StatisticType condition, String value) {
        if (!conditionsMap.containsKey(condition)) {
            conditionsMap.put(condition, value);
            return true;
        } else
            return false;
    }
    //endregion


    //region Private Methods

    //endregion

}
