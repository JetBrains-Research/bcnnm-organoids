package com.synstorm.common.Utils.PlatformLoaders.Configuration;

import com.synstorm.common.Utils.PlatformLoaders.Objects.ChemicalObjects.Ligand;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class LigandsConfiguration {
    //region Fields
    private final Map<String, Ligand> ligands;
    //endregion


    //region Constructors

    public LigandsConfiguration() {
        this.ligands = new HashMap<>();
    }

    //endregion


    //region Getters and Setters

    public Set<String> getLigandsIndex() {
        return ligands.keySet();
    }

    public List<Ligand> getAllLigands() {
        return new ArrayList<>(ligands.values());
    }

    @Nullable
    public Ligand getLigand(String id) {
        return ligands.get(id);
    }
    //endregion


    //region Public Methods
    public void addLigand(Ligand ligand) {
        ligands.put(ligand.getType(), ligand);
    }

    public int[] getLigandsRadius() {
        final int[] result = new int[ligands.size()];
        ligands.values().forEach(ligand -> result[ligand.getNum()] = ligand.getRadius());

        return result;
    }
    //endregion


    //region Private Methods

    //endregion

}
