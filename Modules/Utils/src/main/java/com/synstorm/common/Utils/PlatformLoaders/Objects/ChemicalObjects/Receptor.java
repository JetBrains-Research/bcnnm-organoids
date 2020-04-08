/*
 * Copyright (c) 2018. This file was made as a part of BCNNM@JetBrains_Research work.
 */

package com.synstorm.common.Utils.PlatformLoaders.Objects.ChemicalObjects;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import org.jetbrains.annotations.Contract;

import java.util.Objects;

@Model_v1
public final class Receptor {
    //region Fields
    private final String id;
    private final Ligand ligand;
    //endregion


    //region Constructors

    public Receptor(String id, Ligand ligand) {
        this.id = id;
        this.ligand = ligand;
    }

    //endregion


    //region Getters and Setters

    @Contract(pure = true)
    public String getId() {
        return id;
    }

    @Contract(pure = true)
    public Ligand getLigand() {
        return ligand;
    }

    //endregion


    //region Public Methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receptor receptor = (Receptor) o;
        return Objects.equals(id, receptor.id) &&
                Objects.equals(ligand, receptor.ligand);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, ligand);
    }

    //endregion


    //region Private Methods

    //endregion

}
