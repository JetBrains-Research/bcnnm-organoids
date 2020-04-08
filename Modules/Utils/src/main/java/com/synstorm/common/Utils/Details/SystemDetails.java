package com.synstorm.common.Utils.Details;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dmitry.Bozhko on 5/27/2015.
 */

@Model_v0
@NonProductionLegacy

public class SystemDetails implements IDetails {
    //region Fields
    private Map<String, String> attributeValueMap;
    //endregion

    //region Constructors
    public SystemDetails() {
        attributeValueMap = new HashMap<>();
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
