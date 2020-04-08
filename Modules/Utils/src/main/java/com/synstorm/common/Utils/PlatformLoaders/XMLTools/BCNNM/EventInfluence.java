
package com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EventInfluence.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EventInfluence">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Execute"/>
 *     &lt;enumeration value="Disrupt"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EventInfluence")
@XmlEnum
public enum EventInfluence {

    @XmlEnumValue("Execute")
    EXECUTE("Execute"),
    @XmlEnumValue("Disrupt")
    DISRUPT("Disrupt");
    private final String value;

    EventInfluence(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EventInfluence fromValue(String v) {
        for (EventInfluence c: EventInfluence.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
