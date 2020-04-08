
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
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="mode" type="{}StageMode" />
 *       &lt;attribute name="type" type="{}StagePipelineType" />
 *       &lt;attribute name="datasetType" type="{}DatasetType" />
 *       &lt;attribute name="dataset" type="{}DatasetSource" />
 *       &lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="ratio" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="cellType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="target" type="{}StageTarget" />
 *       &lt;attribute name="pharma" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="time" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="scoring" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="customSP" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "Stage")
public class Stage {

    @XmlAttribute(name = "id")
    protected Long id;
    @XmlAttribute(name = "mode")
    protected StageMode mode;
    @XmlAttribute(name = "type")
    protected StagePipelineType type;
    @XmlAttribute(name = "datasetType")
    protected DatasetType datasetType;
    @XmlAttribute(name = "dataset")
    protected DatasetSource dataset;
    @XmlAttribute(name = "size")
    protected Integer size;
    @XmlAttribute(name = "ratio")
    protected Double ratio;
    @XmlAttribute(name = "cellType")
    protected String cellType;
    @XmlAttribute(name = "target")
    protected StageTarget target;
    @XmlAttribute(name = "pharma")
    protected String pharma;
    @XmlAttribute(name = "time")
    protected Long time;
    @XmlAttribute(name = "scoring")
    protected String scoring;
    @XmlAttribute(name = "customSP")
    protected String customSP;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the mode property.
     * 
     * @return
     *     possible object is
     *     {@link StageMode }
     *     
     */
    public StageMode getMode() {
        return mode;
    }

    /**
     * Sets the value of the mode property.
     * 
     * @param value
     *     allowed object is
     *     {@link StageMode }
     *     
     */
    public void setMode(StageMode value) {
        this.mode = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link StagePipelineType }
     *     
     */
    public StagePipelineType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link StagePipelineType }
     *     
     */
    public void setType(StagePipelineType value) {
        this.type = value;
    }

    /**
     * Gets the value of the datasetType property.
     * 
     * @return
     *     possible object is
     *     {@link DatasetType }
     *     
     */
    public DatasetType getDatasetType() {
        return datasetType;
    }

    /**
     * Sets the value of the datasetType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatasetType }
     *     
     */
    public void setDatasetType(DatasetType value) {
        this.datasetType = value;
    }

    /**
     * Gets the value of the dataset property.
     * 
     * @return
     *     possible object is
     *     {@link DatasetSource }
     *     
     */
    public DatasetSource getDataset() {
        return dataset;
    }

    /**
     * Sets the value of the dataset property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatasetSource }
     *     
     */
    public void setDataset(DatasetSource value) {
        this.dataset = value;
    }

    /**
     * Gets the value of the size property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSize(Integer value) {
        this.size = value;
    }

    /**
     * Gets the value of the ratio property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRatio() {
        return ratio;
    }

    /**
     * Sets the value of the ratio property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRatio(Double value) {
        this.ratio = value;
    }

    /**
     * Gets the value of the cellType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCellType() {
        return cellType;
    }

    /**
     * Sets the value of the cellType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCellType(String value) {
        this.cellType = value;
    }

    /**
     * Gets the value of the target property.
     * 
     * @return
     *     possible object is
     *     {@link StageTarget }
     *     
     */
    public StageTarget getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     * 
     * @param value
     *     allowed object is
     *     {@link StageTarget }
     *     
     */
    public void setTarget(StageTarget value) {
        this.target = value;
    }

    /**
     * Gets the value of the pharma property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPharma() {
        return pharma;
    }

    /**
     * Sets the value of the pharma property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPharma(String value) {
        this.pharma = value;
    }

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTime(Long value) {
        this.time = value;
    }

    /**
     * Gets the value of the scoring property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScoring() {
        return scoring;
    }

    /**
     * Sets the value of the scoring property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScoring(String value) {
        this.scoring = value;
    }

    /**
     * Gets the value of the customSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomSP() {
        return customSP;
    }

    /**
     * Sets the value of the customSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomSP(String value) {
        this.customSP = value;
    }

}
