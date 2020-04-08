
package com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StageMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StageMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Development"/>
 *     &lt;enumeration value="Damage"/>
 *     &lt;enumeration value="Learning"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StageMode")
@XmlEnum
public enum StageMode {

    @XmlEnumValue("Development")
    DEVELOPMENT("Development"),
    @XmlEnumValue("Damage")
    DAMAGE("Damage"),
    @XmlEnumValue("Learning")
    LEARNING("Learning");
    private final String value;

    StageMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StageMode fromValue(String v) {
        for (StageMode c: StageMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
