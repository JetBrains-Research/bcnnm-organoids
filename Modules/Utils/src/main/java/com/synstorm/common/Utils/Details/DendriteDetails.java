package com.synstorm.common.Utils.Details;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitry.Bozhko on 3/25/2015.
 */

@Model_v0
@NonProductionLegacy

public class DendriteDetails implements IDetails {
    //region Fields
    private double stimuliThreshold;
    private double constantThreshold;
    private boolean hasDendrite;
    private Set<String> dendriteReceptors;
    //endregion

    //region Constructors
    public DendriteDetails(String stimuliThreshold, String constantThreshold) {
        this.stimuliThreshold = Double.parseDouble(stimuliThreshold);
        this.constantThreshold = Double.parseDouble(constantThreshold);
        this.hasDendrite = true;
        this.dendriteReceptors = new HashSet<>();
    }


    public DendriteDetails() {
        this.stimuliThreshold = 0.0;
        this.constantThreshold = 0.0;
        this.hasDendrite = false;
        this.dendriteReceptors = new HashSet<>();
    }
    //endregion

    //region Getters and Setters

    public double getStimuliThreshold() {
        return stimuliThreshold;
    }

    public double getConstantThreshold() {
        return constantThreshold;
    }

    public boolean hasDendrite() {
        return hasDendrite;
    }

    public Set<String> getDendriteReceptors() {
        return dendriteReceptors;
    }

    public void setStimuliThreshold(double stimuliThreshold) {
        this.stimuliThreshold = stimuliThreshold;
    }

    public void setConstantThreshold(double constantThreshold) {
        this.constantThreshold = constantThreshold;
    }

    public void setHasDendrite(boolean hasDendrite) {
        this.hasDendrite = hasDendrite;
    }

    //endregion

    //region Public Methods
    public void addDendriteReceptor(String receptorType) {
        dendriteReceptors.add(receptorType);
    }

    public void removeDendriteReceptor(String receptorType) {
        dendriteReceptors.remove(receptorType);
    }
    //endregion

    //region Private Methods
    //endregion
}
