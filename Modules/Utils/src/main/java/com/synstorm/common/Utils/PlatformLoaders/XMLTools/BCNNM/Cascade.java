
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
 *       &lt;attribute name="pathway" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="action" use="required" type="{}EventInfluence" />
 *       &lt;attribute name="condition" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "Cascade")
public class Cascade {

    @XmlAttribute(name = "pathway", required = true)
    protected String pathway;
    @XmlAttribute(name = "action", required = true)
    protected EventInfluence action;
    @XmlAttribute(name = "condition", required = true)
    protected String condition;

    /**
     * Gets the value of the pathway property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPathway() {
        return pathway;
    }

    /**
     * Sets the value of the pathway property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPathway(String value) {
        this.pathway = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link EventInfluence }
     *     
     */
    public EventInfluence getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventInfluence }
     *     
     */
    public void setAction(EventInfluence value) {
        this.action = value;
    }

    /**
     * Gets the value of the condition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Sets the value of the condition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCondition(String value) {
        this.condition = value;
    }

}
