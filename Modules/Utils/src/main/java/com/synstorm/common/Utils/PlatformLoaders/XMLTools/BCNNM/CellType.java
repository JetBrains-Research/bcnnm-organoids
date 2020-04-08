
package com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CellType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CellType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="StemCell"/>
 *     &lt;enumeration value="NeuronCell"/>
 *     &lt;enumeration value="GlialCell"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CellType")
@XmlEnum
public enum CellType {

    @XmlEnumValue("StemCell")
    STEM_CELL("StemCell"),
    @XmlEnumValue("NeuronCell")
    NEURON_CELL("NeuronCell"),
    @XmlEnumValue("GlialCell")
    GLIAL_CELL("GlialCell");
    private final String value;

    CellType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CellType fromValue(String v) {
        for (CellType c: CellType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
