package com.synstorm.common.Utils.ConfigWorker;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

/**
 * Created by Dmitry.Bozhko on 7/1/2014.
 */

@Model_v0
@NonProductionLegacy

public class TemplateAttribute implements IAttribute {
    private String attributeName;

    public String getAttributeName() {
        return attributeName;
    }

    @Override
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    private TemplateAttribute() { }

    public TemplateAttribute(String attributeName) {
        this();
        this.attributeName = attributeName;
    }
}
