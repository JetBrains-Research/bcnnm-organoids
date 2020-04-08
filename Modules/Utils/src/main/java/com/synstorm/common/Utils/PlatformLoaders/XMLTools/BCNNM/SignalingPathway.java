
package com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Condition" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}Predicate" minOccurs="0"/>
 *                   &lt;element ref="{}ConditionParameter" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{}ExecuteParameter" minOccurs="0"/>
 *         &lt;element name="Cascades" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}Cascade" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="duration" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="initial" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "condition",
    "executeParameter",
    "cascades"
})
@XmlRootElement(name = "SignalingPathway")
public class SignalingPathway {

    @XmlElement(name = "Condition")
    protected SignalingPathway.Condition condition;
    @XmlElement(name = "ExecuteParameter")
    protected ExecuteParameter executeParameter;
    @XmlElement(name = "Cascades")
    protected SignalingPathway.Cascades cascades;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "duration")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger duration;
    @XmlAttribute(name = "initial")
    protected Boolean initial;

    /**
     * Gets the value of the condition property.
     * 
     * @return
     *     possible object is
     *     {@link SignalingPathway.Condition }
     *     
     */
    public SignalingPathway.Condition getCondition() {
        return condition;
    }

    /**
     * Sets the value of the condition property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignalingPathway.Condition }
     *     
     */
    public void setCondition(SignalingPathway.Condition value) {
        this.condition = value;
    }

    /**
     * Gets the value of the executeParameter property.
     * 
     * @return
     *     possible object is
     *     {@link ExecuteParameter }
     *     
     */
    public ExecuteParameter getExecuteParameter() {
        return executeParameter;
    }

    /**
     * Sets the value of the executeParameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExecuteParameter }
     *     
     */
    public void setExecuteParameter(ExecuteParameter value) {
        this.executeParameter = value;
    }

    /**
     * Gets the value of the cascades property.
     * 
     * @return
     *     possible object is
     *     {@link SignalingPathway.Cascades }
     *     
     */
    public SignalingPathway.Cascades getCascades() {
        return cascades;
    }

    /**
     * Sets the value of the cascades property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignalingPathway.Cascades }
     *     
     */
    public void setCascades(SignalingPathway.Cascades value) {
        this.cascades = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDuration(BigInteger value) {
        this.duration = value;
    }

    /**
     * Gets the value of the initial property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInitial() {
        return initial;
    }

    /**
     * Sets the value of the initial property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInitial(Boolean value) {
        this.initial = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{}Cascade" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "cascade"
    })
    public static class Cascades {

        @XmlElement(name = "Cascade", required = true)
        protected List<Cascade> cascade;

        /**
         * Gets the value of the cascade property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the cascade property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCascade().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Cascade }
         * 
         * 
         */
        public List<Cascade> getCascade() {
            if (cascade == null) {
                cascade = new ArrayList<Cascade>();
            }
            return this.cascade;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{}Predicate" minOccurs="0"/>
     *         &lt;element ref="{}ConditionParameter" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "predicate",
        "conditionParameter"
    })
    public static class Condition {

        @XmlElement(name = "Predicate")
        protected Predicate predicate;
        @XmlElement(name = "ConditionParameter")
        protected ConditionParameter conditionParameter;

        /**
         * Gets the value of the predicate property.
         * 
         * @return
         *     possible object is
         *     {@link Predicate }
         *     
         */
        public Predicate getPredicate() {
            return predicate;
        }

        /**
         * Sets the value of the predicate property.
         * 
         * @param value
         *     allowed object is
         *     {@link Predicate }
         *     
         */
        public void setPredicate(Predicate value) {
            this.predicate = value;
        }

        /**
         * Gets the value of the conditionParameter property.
         * 
         * @return
         *     possible object is
         *     {@link ConditionParameter }
         *     
         */
        public ConditionParameter getConditionParameter() {
            return conditionParameter;
        }

        /**
         * Sets the value of the conditionParameter property.
         * 
         * @param value
         *     allowed object is
         *     {@link ConditionParameter }
         *     
         */
        public void setConditionParameter(ConditionParameter value) {
            this.conditionParameter = value;
        }

    }

}
