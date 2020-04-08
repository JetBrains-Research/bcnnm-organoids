
package com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CompartmentType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CompartmentType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Axon"/>
 *     &lt;enumeration value="AxonalTerminal"/>
 *     &lt;enumeration value="DendriteTree"/>
 *     &lt;enumeration value="DendriticSpine"/>
 *     &lt;enumeration value="Synapse"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CompartmentType")
@XmlEnum
public enum CompartmentType {

    @XmlEnumValue("Axon")
    AXON("Axon"),
    @XmlEnumValue("AxonalTerminal")
    AXONAL_TERMINAL("AxonalTerminal"),
    @XmlEnumValue("DendriteTree")
    DENDRITE_TREE("DendriteTree"),
    @XmlEnumValue("DendriticSpine")
    DENDRITIC_SPINE("DendriticSpine"),
    @XmlEnumValue("Synapse")
    SYNAPSE("Synapse");
    private final String value;

    CompartmentType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CompartmentType fromValue(String v) {
        for (CompartmentType c: CompartmentType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
