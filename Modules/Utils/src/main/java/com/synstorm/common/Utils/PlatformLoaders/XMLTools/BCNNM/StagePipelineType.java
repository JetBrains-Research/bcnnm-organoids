
package com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StagePipelineType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StagePipelineType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Trauma"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StagePipelineType")
@XmlEnum
public enum StagePipelineType {

    @XmlEnumValue("Trauma")
    TRAUMA("Trauma");
    private final String value;

    StagePipelineType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StagePipelineType fromValue(String v) {
        for (StagePipelineType c: StagePipelineType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
