package com.synstorm.common.Utils.PlatformLoaders.Configuration;

import com.synstorm.common.Utils.PlatformLoaders.Objects.ChemicalObjects.ReceptorDescription;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ReceptorsDescriptionConfiguration {
    //region Fields
    private final Map<String, ReceptorDescription> receptors;
    //endregion


    //region Constructors

    public ReceptorsDescriptionConfiguration() {
        this.receptors = new HashMap<>();
    }

    //endregion


    //region Getters and Setters
    public ReceptorDescription getReceptor(String id) {
        return receptors.get(id);
    }

    public Set<String> getReceptorsIndex() {
        return receptors.keySet();
    }
    //endregion


    //region Public Methods
    public void addReceptor(ReceptorDescription receptorDescription) {
        receptors.put(receptorDescription.getType(), receptorDescription);
    }
    //endregion


    //region Private Methods

    //endregion

}
