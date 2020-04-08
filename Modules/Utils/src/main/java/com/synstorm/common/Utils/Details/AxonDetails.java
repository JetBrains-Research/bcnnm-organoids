package com.synstorm.common.Utils.Details;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

/**
 * Created by Dmitry.Bozhko on 3/25/2015.
 */

@Model_v0
@NonProductionLegacy

public class AxonDetails implements IDetails {
    //region Fields
    private String neurotransmitter;
    private double expressingNTPercentage;
    private double releasingNTPercentage;
    private double uptakeNTPercentage;

    private boolean hasAxon;
    //endregion

    //region Constructors
    public AxonDetails(String neurotransmitter, String expressingNTPercentage,
                       String releasingNTPercentage, String uptakeNTPercentage) {
        this.neurotransmitter = neurotransmitter;
        this.expressingNTPercentage = Double.parseDouble(expressingNTPercentage);
        this.releasingNTPercentage = Double.parseDouble(releasingNTPercentage);
        this.uptakeNTPercentage = Double.parseDouble(uptakeNTPercentage);
        this.hasAxon = true;
    }

    public AxonDetails() {
        this.neurotransmitter = "";
        this.expressingNTPercentage = 0.0;
        this.releasingNTPercentage = 0.0;
        this.uptakeNTPercentage = 0.0;
        this.hasAxon = false;
    }
    //endregion

    //region Getters and Setters
    public double getExpressingNTPercentage() {
        return expressingNTPercentage;
    }

    public double getReleasingNTPercentage() {
        return releasingNTPercentage;
    }

    public double getUptakeNTPercentage() {
        return uptakeNTPercentage;
    }

    public String getNeurotransmitter() {
        return neurotransmitter;
    }

    public boolean hasAxon() {
        return hasAxon;
    }

    public void setNeurotransmitter(String neurotransmitter) {
        this.neurotransmitter = neurotransmitter;
    }

    public void setExpressingNTPercentage(double expressingNTPercentage) {
        this.expressingNTPercentage = expressingNTPercentage;
    }

    public void setReleasingNTPercentage(double releasingNTPercentage) {
        this.releasingNTPercentage = releasingNTPercentage;
    }

    public void setUptakeNTPercentage(double uptakeNTPercentage) {
        this.uptakeNTPercentage = uptakeNTPercentage;
    }

    public void setHasAxon(boolean hasAxon) {
        this.hasAxon = hasAxon;
    }

    //endregion

    //region Public Methods
    //endregion

    //region Private Methods
    //endregion
}
