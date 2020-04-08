/*
 * Copyright (c) 2018. This file was made as a part of BCNNM@JetBrains_Research work.
 */

package com.synstorm.common.Utils.PlatformLoaders.Objects.ChemicalObjects;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import org.jetbrains.annotations.Contract;

import java.util.Objects;

@Model_v1
public final class ReceptorDescription {
    //region Fields
    private final String id;
    private final Ligand ligand;
    //endregion


    //region Constructors

    public ReceptorDescription(String id, Ligand ligand) {
        this.id = id;
        this.ligand = ligand;
    }

    //endregion


    //region Getters and Setters

    @Contract(pure = true)
    public String getType() {
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
        ReceptorDescription receptorDescription = (ReceptorDescription) o;
        return Objects.equals(id, receptorDescription.id) &&
                Objects.equals(ligand, receptorDescription.ligand);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, ligand);
    }

    //endregion


    //region Private Methods

    //endregion

}
