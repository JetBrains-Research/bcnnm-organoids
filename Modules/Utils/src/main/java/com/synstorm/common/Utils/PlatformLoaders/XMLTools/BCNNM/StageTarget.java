
package com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StageTarget.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StageTarget">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Random"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StageTarget")
@XmlEnum
public enum StageTarget {

    @XmlEnumValue("Random")
    RANDOM("Random");
    private final String value;

    StageTarget(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StageTarget fromValue(String v) {
        for (StageTarget c: StageTarget.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
