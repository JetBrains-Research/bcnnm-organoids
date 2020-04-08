
package com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Distribution.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Distribution">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Binomial"/>
 *     &lt;enumeration value="Pascal"/>
 *     &lt;enumeration value="Poisson"/>
 *     &lt;enumeration value="Exponential"/>
 *     &lt;enumeration value="Gaussian"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Distribution")
@XmlEnum
public enum Distribution {

    @XmlEnumValue("Binomial")
    BINOMIAL("Binomial"),
    @XmlEnumValue("Pascal")
    PASCAL("Pascal"),
    @XmlEnumValue("Poisson")
    POISSON("Poisson"),
    @XmlEnumValue("Exponential")
    EXPONENTIAL("Exponential"),
    @XmlEnumValue("Gaussian")
    GAUSSIAN("Gaussian");
    private final String value;

    Distribution(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Distribution fromValue(String v) {
        for (Distribution c: Distribution.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
