package com.synstorm.common.Utils.PlatformLoaders.Configuration;

import com.synstorm.common.Utils.PlatformLoaders.Objects.ChemicalObjects.Receptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ReceptorsConfiguration {
    //region Fields
    private final Map<String, Receptor> receptors;
    //endregion


    //region Constructors

    public ReceptorsConfiguration() {
        this.receptors = new HashMap<>();
    }

    //endregion


    //region Getters and Setters
    public Receptor getReceptor(String id) {
        return receptors.get(id);
    }

    public Set<String> getReceptorsIndex() {
        return receptors.keySet();
    }
    //endregion


    //region Public Methods
    public void addReceptor(Receptor receptor) {
        receptors.put(receptor.getId(), receptor);
    }
    //endregion


    //region Private Methods

    //endregion

}
