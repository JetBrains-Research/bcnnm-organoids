
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
 *       &lt;attribute name="rule" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="args" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="distribution" type="{}Distribution" />
 *       &lt;attribute name="distributionVariable" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="distributionParameters" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "Predicate")
public class Predicate {

    @XmlAttribute(name = "rule")
    protected String rule;
    @XmlAttribute(name = "args")
    protected String args;
    @XmlAttribute(name = "distribution")
    protected Distribution distribution;
    @XmlAttribute(name = "distributionVariable")
    protected String distributionVariable;
    @XmlAttribute(name = "distributionParameters")
    protected String distributionParameters;

    /**
     * Gets the value of the rule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRule() {
        return rule;
    }

    /**
     * Sets the value of the rule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRule(String value) {
        this.rule = value;
    }

    /**
     * Gets the value of the args property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArgs() {
        return args;
    }

    /**
     * Sets the value of the args property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArgs(String value) {
        this.args = value;
    }

    /**
     * Gets the value of the distribution property.
     * 
     * @return
     *     possible object is
     *     {@link Distribution }
     *     
     */
    public Distribution getDistribution() {
        return distribution;
    }

    /**
     * Sets the value of the distribution property.
     * 
     * @param value
     *     allowed object is
     *     {@link Distribution }
     *     
     */
    public void setDistribution(Distribution value) {
        this.distribution = value;
    }

    /**
     * Gets the value of the distributionVariable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistributionVariable() {
        return distributionVariable;
    }

    /**
     * Sets the value of the distributionVariable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistributionVariable(String value) {
        this.distributionVariable = value;
    }

    /**
     * Gets the value of the distributionParameters property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistributionParameters() {
        return distributionParameters;
    }

    /**
     * Sets the value of the distributionParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistributionParameters(String value) {
        this.distributionParameters = value;
    }

}
