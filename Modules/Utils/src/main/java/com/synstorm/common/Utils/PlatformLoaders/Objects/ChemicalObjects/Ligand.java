/*
 * Copyright (c) 2018. This file was made as a part of BCNNM@JetBrains_Research work.
 */

package com.synstorm.common.Utils.PlatformLoaders.Objects.ChemicalObjects;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import org.jetbrains.annotations.Contract;

import java.util.Objects;

@Model_v1
public final class Ligand {
    //region Fields
    private final int num;
    private final String type;
    private final int radius;
    //endregion


    //region Constructors

    public Ligand(int num, String type, int radius) {
        this.num = num;
        this.type = type;
        this.radius = radius;
    }

    //endregion


    //region Getters and Setters

    @Contract(pure = true)
    public String getType() {
        return type;
    }

    @Contract(pure = true)
    public int getNum() {
        return num;
    }

    @Contract(pure = true)
    public int getRadius() {
        return radius;
    }

    //endregion


    //region Public Methods


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ligand ligand = (Ligand) o;
        return Objects.equals(type, ligand.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    //endregion


    //region Private Methods

    //endregion

}
