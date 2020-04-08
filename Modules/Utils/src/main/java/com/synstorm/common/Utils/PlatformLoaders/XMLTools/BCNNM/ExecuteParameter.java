
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
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="creationType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ligand" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="probability" type="{http://www.w3.org/2001/XMLSchema}double" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "ExecuteParameter")
public class ExecuteParameter {

    @XmlAttribute(name = "type", required = true)
    protected String type;
    @XmlAttribute(name = "creationType")
    protected String creationType;
    @XmlAttribute(name = "ligand")
    protected String ligand;
    @XmlAttribute(name = "probability")
    protected Double probability;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the creationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreationType() {
        return creationType;
    }

    /**
     * Sets the value of the creationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreationType(String value) {
        this.creationType = value;
    }

    /**
     * Gets the value of the ligand property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLigand() {
        return ligand;
    }

    /**
     * Sets the value of the ligand property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLigand(String value) {
        this.ligand = value;
    }

    /**
     * Gets the value of the probability property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getProbability() {
        return probability;
    }

    /**
     * Sets the value of the probability property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setProbability(Double value) {
        this.probability = value;
    }

}
