
package com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM;

import javax.xml.bind.annotation.*;
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
 *         &lt;element name="SPs" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded">
 *                   &lt;element ref="{}SP" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Compartments" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}Compartment" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="baseType" use="required" type="{}CellType" />
 *       &lt;attribute name="immovable" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sPs",
    "compartments"
})
@XmlRootElement(name = "ModelCell")
public class ModelCell {

    @XmlElement(name = "SPs")
    protected ModelCell.SPs sPs;
    @XmlElement(name = "Compartments")
    protected ModelCell.Compartments compartments;
    @XmlAttribute(name = "id", required = true)
    protected String id;
    @XmlAttribute(name = "baseType", required = true)
    protected CellType baseType;
    @XmlAttribute(name = "immovable")
    protected Boolean immovable;

    /**
     * Gets the value of the sPs property.
     * 
     * @return
     *     possible object is
     *     {@link ModelCell.SPs }
     *     
     */
    public ModelCell.SPs getSPs() {
        return sPs;
    }

    /**
     * Sets the value of the sPs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModelCell.SPs }
     *     
     */
    public void setSPs(ModelCell.SPs value) {
        this.sPs = value;
    }

    /**
     * Gets the value of the compartments property.
     * 
     * @return
     *     possible object is
     *     {@link ModelCell.Compartments }
     *     
     */
    public ModelCell.Compartments getCompartments() {
        return compartments;
    }

    /**
     * Sets the value of the compartments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModelCell.Compartments }
     *     
     */
    public void setCompartments(ModelCell.Compartments value) {
        this.compartments = value;
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
     * Gets the value of the baseType property.
     * 
     * @return
     *     possible object is
     *     {@link CellType }
     *     
     */
    public CellType getBaseType() {
        return baseType;
    }

    /**
     * Sets the value of the baseType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CellType }
     *     
     */
    public void setBaseType(CellType value) {
        this.baseType = value;
    }

    /**
     * Gets the value of the immovable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isImmovable() {
        return immovable;
    }

    /**
     * Sets the value of the immovable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setImmovable(Boolean value) {
        this.immovable = value;
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
     *         &lt;element ref="{}Compartment" maxOccurs="unbounded" minOccurs="0"/>
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
        "compartment"
    })
    public static class Compartments {

        @XmlElement(name = "Compartment")
        protected List<Compartment> compartment;

        /**
         * Gets the value of the compartment property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the compartment property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCompartment().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Compartment }
         * 
         * 
         */
        public List<Compartment> getCompartment() {
            if (compartment == null) {
                compartment = new ArrayList<Compartment>();
            }
            return this.compartment;
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
     *       &lt;sequence maxOccurs="unbounded">
     *         &lt;element ref="{}SP" maxOccurs="unbounded" minOccurs="0"/>
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
        "sp"
    })
    public static class SPs {

        @XmlElement(name = "SP")
        protected List<SP> sp;

        /**
         * Gets the value of the sp property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the sp property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSP().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SP }
         * 
         * 
         */
        public List<SP> getSP() {
            if (sp == null) {
                sp = new ArrayList<SP>();
            }
            return this.sp;
        }

    }

}
