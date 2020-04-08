package com.synstorm.common.Utils.Details;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by human-research on 16/02/16.
 */

@Model_v0
@NonProductionLegacy

public class EvolutionDetails implements IDetails {
    //region Fields
    private Map<String, String> attributeValueMap;
    //endregion


    //region Constructors

    public EvolutionDetails() {
        this.attributeValueMap = new HashMap<>();
    }

    //endregion


    //region Getters and Setters

    //endregion


    //region Public Methods
    public void addAttribute(String name, String value) {
        attributeValueMap.put(name, value);
    }

    public String getValue(String attributeName) {
        return attributeValueMap.get(attributeName);
    }
    //endregion


    //region Private Methods

    //endregion

}
