package com.synstorm.common.Utils.ConfigWorker;

/**
 * Created by Dmitry.Bozhko on 7/2/2014.
 */
public class ConfigAttribute extends TemplateAttribute {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ConfigAttribute(String attributeName, String attributeValue) {
        super(attributeName);
        this.value = attributeValue;
    }
}
