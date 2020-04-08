
package com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="selectionLigand" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="selectionType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "ConditionParameter")
public class ConditionParameter {

    @XmlAttribute(name = "selectionLigand")
    protected String selectionLigand;
    @XmlAttribute(name = "selectionType")
    protected String selectionType;

    /**
     * Gets the value of the selectionLigand property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelectionLigand() {
        return selectionLigand;
    }

    /**
     * Sets the value of the selectionLigand property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelectionLigand(String value) {
        this.selectionLigand = value;
    }

    /**
     * Gets the value of the selectionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelectionType() {
        return selectionType;
    }

    /**
     * Sets the value of the selectionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelectionType(String value) {
        this.selectionType = value;
    }

}
