
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
 *         &lt;element name="System">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}Space"/>
 *                   &lt;element name="Pipeline">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element ref="{}Stage" maxOccurs="unbounded"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Training" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Datasets" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element ref="{}Dataset" maxOccurs="unbounded" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="lqWindowSize" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                           &lt;attribute name="clusteringSeedCount" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Mechanisms">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}Mechanism" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ReceptorsDescription" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}ReceptorDescription" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Ligands" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}Ligand" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ModelCells">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}ModelCell" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="SignalPoints">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}SignalPoint" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "system",
    "mechanisms",
    "receptorsDescription",
    "ligands",
    "modelCells",
    "signalPoints"
})
@XmlRootElement(name = "BCNNM")
public class BCNNM {

    @XmlElement(name = "System", required = true)
    protected BCNNM.System system;
    @XmlElement(name = "Mechanisms", required = true)
    protected BCNNM.Mechanisms mechanisms;
    @XmlElement(name = "ReceptorsDescription")
    protected BCNNM.ReceptorsDescription receptorsDescription;
    @XmlElement(name = "Ligands")
    protected BCNNM.Ligands ligands;
    @XmlElement(name = "ModelCells", required = true)
    protected BCNNM.ModelCells modelCells;
    @XmlElement(name = "SignalPoints", required = true)
    protected BCNNM.SignalPoints signalPoints;

    /**
     * Gets the value of the system property.
     * 
     * @return
     *     possible object is
     *     {@link BCNNM.System }
     *     
     */
    public BCNNM.System getSystem() {
        return system;
    }

    /**
     * Sets the value of the system property.
     * 
     * @param value
     *     allowed object is
     *     {@link BCNNM.System }
     *     
     */
    public void setSystem(BCNNM.System value) {
        this.system = value;
    }

    /**
     * Gets the value of the mechanisms property.
     * 
     * @return
     *     possible object is
     *     {@link BCNNM.Mechanisms }
     *     
     */
    public BCNNM.Mechanisms getMechanisms() {
        return mechanisms;
    }

    /**
     * Sets the value of the mechanisms property.
     * 
     * @param value
     *     allowed object is
     *     {@link BCNNM.Mechanisms }
     *     
     */
    public void setMechanisms(BCNNM.Mechanisms value) {
        this.mechanisms = value;
    }

    /**
     * Gets the value of the receptorsDescription property.
     * 
     * @return
     *     possible object is
     *     {@link BCNNM.ReceptorsDescription }
     *     
     */
    public BCNNM.ReceptorsDescription getReceptorsDescription() {
        return receptorsDescription;
    }

    /**
     * Sets the value of the receptorsDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link BCNNM.ReceptorsDescription }
     *     
     */
    public void setReceptorsDescription(BCNNM.ReceptorsDescription value) {
        this.receptorsDescription = value;
    }

    /**
     * Gets the value of the ligands property.
     * 
     * @return
     *     possible object is
     *     {@link BCNNM.Ligands }
     *     
     */
    public BCNNM.Ligands getLigands() {
        return ligands;
    }

    /**
     * Sets the value of the ligands property.
     * 
     * @param value
     *     allowed object is
     *     {@link BCNNM.Ligands }
     *     
     */
    public void setLigands(BCNNM.Ligands value) {
        this.ligands = value;
    }

    /**
     * Gets the value of the modelCells property.
     * 
     * @return
     *     possible object is
     *     {@link BCNNM.ModelCells }
     *     
     */
    public BCNNM.ModelCells getModelCells() {
        return modelCells;
    }

    /**
     * Sets the value of the modelCells property.
     * 
     * @param value
     *     allowed object is
     *     {@link BCNNM.ModelCells }
     *     
     */
    public void setModelCells(BCNNM.ModelCells value) {
        this.modelCells = value;
    }

    /**
     * Gets the value of the signalPoints property.
     * 
     * @return
     *     possible object is
     *     {@link BCNNM.SignalPoints }
     *     
     */
    public BCNNM.SignalPoints getSignalPoints() {
        return signalPoints;
    }

    /**
     * Sets the value of the signalPoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link BCNNM.SignalPoints }
     *     
     */
    public void setSignalPoints(BCNNM.SignalPoints value) {
        this.signalPoints = value;
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
     *         &lt;element ref="{}Ligand" maxOccurs="unbounded" minOccurs="0"/>
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
        "ligand"
    })
    public static class Ligands {

        @XmlElement(name = "Ligand")
        protected List<Ligand> ligand;

        /**
         * Gets the value of the ligand property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ligand property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getLigand().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Ligand }
         * 
         * 
         */
        public List<Ligand> getLigand() {
            if (ligand == null) {
                ligand = new ArrayList<Ligand>();
            }
            return this.ligand;
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
     *         &lt;element ref="{}Mechanism" maxOccurs="unbounded" minOccurs="0"/>
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
        "mechanism"
    })
    public static class Mechanisms {

        @XmlElement(name = "Mechanism")
        protected List<Mechanism> mechanism;

        /**
         * Gets the value of the mechanism property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the mechanism property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMechanism().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Mechanism }
         * 
         * 
         */
        public List<Mechanism> getMechanism() {
            if (mechanism == null) {
                mechanism = new ArrayList<Mechanism>();
            }
            return this.mechanism;
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
     *         &lt;element ref="{}ModelCell" maxOccurs="unbounded"/>
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
        "modelCell"
    })
    public static class ModelCells {

        @XmlElement(name = "ModelCell", required = true)
        protected List<ModelCell> modelCell;

        /**
         * Gets the value of the modelCell property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the modelCell property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getModelCell().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ModelCell }
         * 
         * 
         */
        public List<ModelCell> getModelCell() {
            if (modelCell == null) {
                modelCell = new ArrayList<ModelCell>();
            }
            return this.modelCell;
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
     *         &lt;element ref="{}ReceptorDescription" maxOccurs="unbounded" minOccurs="0"/>
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
        "receptorDescription"
    })
    public static class ReceptorsDescription {

        @XmlElement(name = "ReceptorDescription")
        protected List<ReceptorDescription> receptorDescription;

        /**
         * Gets the value of the receptorDescription property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the receptorDescription property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getReceptorDescription().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ReceptorDescription }
         * 
         * 
         */
        public List<ReceptorDescription> getReceptorDescription() {
            if (receptorDescription == null) {
                receptorDescription = new ArrayList<ReceptorDescription>();
            }
            return this.receptorDescription;
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
     *         &lt;element ref="{}SignalPoint" maxOccurs="unbounded" minOccurs="0"/>
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
        "signalPoint"
    })
    public static class SignalPoints {

        @XmlElement(name = "SignalPoint")
        protected List<SignalPoint> signalPoint;

        /**
         * Gets the value of the signalPoint property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the signalPoint property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSignalPoint().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SignalPoint }
         * 
         * 
         */
        public List<SignalPoint> getSignalPoint() {
            if (signalPoint == null) {
                signalPoint = new ArrayList<SignalPoint>();
            }
            return this.signalPoint;
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
     *         &lt;element ref="{}Space"/>
     *         &lt;element name="Pipeline">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element ref="{}Stage" maxOccurs="unbounded"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Training" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Datasets" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element ref="{}Dataset" maxOccurs="unbounded" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="lqWindowSize" type="{http://www.w3.org/2001/XMLSchema}int" />
     *                 &lt;attribute name="clusteringSeedCount" type="{http://www.w3.org/2001/XMLSchema}int" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
        "space",
        "pipeline",
        "training"
    })
    public static class System {

        @XmlElement(name = "Space", required = true)
        protected Space space;
        @XmlElement(name = "Pipeline", required = true)
        protected BCNNM.System.Pipeline pipeline;
        @XmlElement(name = "Training")
        protected BCNNM.System.Training training;

        /**
         * Gets the value of the space property.
         * 
         * @return
         *     possible object is
         *     {@link Space }
         *     
         */
        public Space getSpace() {
            return space;
        }

        /**
         * Sets the value of the space property.
         * 
         * @param value
         *     allowed object is
         *     {@link Space }
         *     
         */
        public void setSpace(Space value) {
            this.space = value;
        }

        /**
         * Gets the value of the pipeline property.
         * 
         * @return
         *     possible object is
         *     {@link BCNNM.System.Pipeline }
         *     
         */
        public BCNNM.System.Pipeline getPipeline() {
            return pipeline;
        }

        /**
         * Sets the value of the pipeline property.
         * 
         * @param value
         *     allowed object is
         *     {@link BCNNM.System.Pipeline }
         *     
         */
        public void setPipeline(BCNNM.System.Pipeline value) {
            this.pipeline = value;
        }

        /**
         * Gets the value of the training property.
         * 
         * @return
         *     possible object is
         *     {@link BCNNM.System.Training }
         *     
         */
        public BCNNM.System.Training getTraining() {
            return training;
        }

        /**
         * Sets the value of the training property.
         * 
         * @param value
         *     allowed object is
         *     {@link BCNNM.System.Training }
         *     
         */
        public void setTraining(BCNNM.System.Training value) {
            this.training = value;
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
         *         &lt;element ref="{}Stage" maxOccurs="unbounded"/>
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
            "stage"
        })
        public static class Pipeline {

            @XmlElement(name = "Stage", required = true)
            protected List<Stage> stage;

            /**
             * Gets the value of the stage property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the stage property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getStage().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Stage }
             * 
             * 
             */
            public List<Stage> getStage() {
                if (stage == null) {
                    stage = new ArrayList<Stage>();
                }
                return this.stage;
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
         *         &lt;element name="Datasets" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element ref="{}Dataset" maxOccurs="unbounded" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="lqWindowSize" type="{http://www.w3.org/2001/XMLSchema}int" />
         *       &lt;attribute name="clusteringSeedCount" type="{http://www.w3.org/2001/XMLSchema}int" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "datasets"
        })
        public static class Training {

            @XmlElement(name = "Datasets")
            protected BCNNM.System.Training.Datasets datasets;
            @XmlAttribute(name = "lqWindowSize")
            protected Integer lqWindowSize;
            @XmlAttribute(name = "clusteringSeedCount")
            protected Integer clusteringSeedCount;

            /**
             * Gets the value of the datasets property.
             * 
             * @return
             *     possible object is
             *     {@link BCNNM.System.Training.Datasets }
             *     
             */
            public BCNNM.System.Training.Datasets getDatasets() {
                return datasets;
            }

            /**
             * Sets the value of the datasets property.
             * 
             * @param value
             *     allowed object is
             *     {@link BCNNM.System.Training.Datasets }
             *     
             */
            public void setDatasets(BCNNM.System.Training.Datasets value) {
                this.datasets = value;
            }

            /**
             * Gets the value of the lqWindowSize property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getLqWindowSize() {
                return lqWindowSize;
            }

            /**
             * Sets the value of the lqWindowSize property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setLqWindowSize(Integer value) {
                this.lqWindowSize = value;
            }

            /**
             * Gets the value of the clusteringSeedCount property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getClusteringSeedCount() {
                return clusteringSeedCount;
            }

            /**
             * Sets the value of the clusteringSeedCount property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setClusteringSeedCount(Integer value) {
                this.clusteringSeedCount = value;
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
             *         &lt;element ref="{}Dataset" maxOccurs="unbounded" minOccurs="0"/>
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
                "dataset"
            })
            public static class Datasets {

                @XmlElement(name = "Dataset")
                protected List<Dataset> dataset;

                /**
                 * Gets the value of the dataset property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the dataset property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getDataset().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Dataset }
                 * 
                 * 
                 */
                public List<Dataset> getDataset() {
                    if (dataset == null) {
                        dataset = new ArrayList<Dataset>();
                    }
                    return this.dataset;
                }

            }

        }

    }

}
