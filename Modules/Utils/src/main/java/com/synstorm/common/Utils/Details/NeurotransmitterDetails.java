package com.synstorm.common.Utils.Details;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.NeurotransmitterType;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Dmitry.Bozhko on 5/27/2015.
 */

@Model_v0
@NonProductionLegacy

public class NeurotransmitterDetails implements IDetails {
    //region Fields
    private String name;
    private NeurotransmitterType type;
    private double releaseThreshold;
    private Map<String, DendriteReceptorTypeDetails> receptorTypeDetailsMap;
    //endregion

    //region Constructors
    public NeurotransmitterDetails(String name, String type, String threshold) {
        this.name = name;
        this.type = NeurotransmitterType.valueOf(type);
        this.releaseThreshold = Double.parseDouble(threshold);
        this.receptorTypeDetailsMap = new HashMap<>();
    }
    //endregion

    //region Getters and Setters
    public String getName() {
        return name;
    }

    public NeurotransmitterType getType() {
        return type;
    }

    public double getReleaseThreshold() {
        return releaseThreshold;
    }

    public void setReleaseThreshold(double releaseThreshold) {
        this.releaseThreshold = releaseThreshold;
    }

    public Map<String, DendriteReceptorTypeDetails> getReceptorTypeDetailsMap() {
        return receptorTypeDetailsMap;
    }

    public DendriteReceptorTypeDetails GetReceptor(String receptorType) {
        return receptorTypeDetailsMap.get(receptorType);
    }

    @Nullable
    public String getReceptorByCellType(Set<String> possibleReceptors, String sourceCellType) {
        Optional<String> result = possibleReceptors.stream()
                .filter(item -> receptorTypeDetailsMap.containsKey(item) &&
                                receptorTypeDetailsMap.get(item).getSourceCellTypes().contains(sourceCellType))
                .findFirst();
        if (result.isPresent())
            return result.get();
        else
            return null;
    }
    //endregion

    //region Public Methods
    public boolean addReceptor(String receptorType) {
        if (!receptorTypeDetailsMap.containsKey(receptorType)) {
            receptorTypeDetailsMap.put(receptorType, new DendriteReceptorTypeDetails(receptorType, name));
            return true;
        }
        return false;
    }

    public boolean addReceptor(DendriteReceptorTypeDetails dendriteReceptorTypeDetails) {
        if (!receptorTypeDetailsMap.containsKey(dendriteReceptorTypeDetails.getReceptorType())) {
            receptorTypeDetailsMap.put(dendriteReceptorTypeDetails.getReceptorType(), dendriteReceptorTypeDetails);
            return true;
        }
        return false;
    }

    public void removeReceptor(String receptorType) {
        receptorTypeDetailsMap.remove(receptorType);
    }
    //endregion

    //region Private Methods
    //endregion
}
