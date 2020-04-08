
package com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatasetType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DatasetType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Train"/>
 *     &lt;enumeration value="Control"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DatasetType")
@XmlEnum
public enum DatasetType {

    @XmlEnumValue("Train")
    TRAIN("Train"),
    @XmlEnumValue("Control")
    CONTROL("Control");
    private final String value;

    DatasetType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DatasetType fromValue(String v) {
        for (DatasetType c: DatasetType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
