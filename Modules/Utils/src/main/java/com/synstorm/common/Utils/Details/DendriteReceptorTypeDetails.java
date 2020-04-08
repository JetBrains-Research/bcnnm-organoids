package com.synstorm.common.Utils.Details;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by human-research on 01/10/15.
 */

@Model_v0
@NonProductionLegacy

public class DendriteReceptorTypeDetails {
    //region Fields
    private String receptorType;
    private String neurotransmitter;
    private Set<String> sourceCellTypes;
    //endregion

    //region Constructors
    public DendriteReceptorTypeDetails(String receptorType, String neurotransmitter, Set<String> sourceCellTypes) {
        this.receptorType = receptorType;
        this.neurotransmitter = neurotransmitter;
        this.sourceCellTypes = sourceCellTypes;
    }

    public DendriteReceptorTypeDetails(String receptorType, String neurotransmitter) {
        this.receptorType = receptorType;
        this.neurotransmitter = neurotransmitter;
        this.sourceCellTypes = new TreeSet<>();
    }
    //endregion

    //region Getters and Setters
    public String getReceptorType() {
        return receptorType;
    }

    public String getNeurotransmitter() {
        return neurotransmitter;
    }

    public Set<String> getSourceCellTypes() {
        return sourceCellTypes;
    }
    //endregion

    //region Public Methods
    public void addSourceCell(String cellType) {
        sourceCellTypes.add(cellType);
    }

    public void removeSourceCell(String cellType) {
        sourceCellTypes.remove(cellType);
    }
    //endregion

    //region Private Methods
    //endregion
}