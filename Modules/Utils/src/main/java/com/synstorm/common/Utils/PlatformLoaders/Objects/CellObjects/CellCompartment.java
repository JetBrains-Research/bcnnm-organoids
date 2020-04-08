/*
 * Copyright (c) 2018. This file was made as a part of BCNNM@JetBrains_Research work.
 */

package com.synstorm.common.Utils.PlatformLoaders.Objects.CellObjects;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ICompartmentDescription;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathway;
import com.synstorm.common.Utils.ConfigInterfaces.LogicObjectTypes;
import com.synstorm.common.Utils.Coordinates.ObjectState;
import com.synstorm.common.Utils.PlatformLoaders.Objects.ChemicalObjects.ReceptorDescription;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

@Model_v1
public final class CellCompartment extends BaseCellObject implements ICompartmentDescription {
    //region Fields
    private Map<ReceptorDescription, ISignalingPathway> compartmentReceptors;
    private final boolean unique;
    //endregion

    //region Constructors
    public CellCompartment(String id, LogicObjectTypes baseType, boolean unique) {
        super(id, baseType);
        this.compartmentReceptors = new HashMap<>();
        this.unique = unique;
    }
    //endregion

    //region Getters and Setters
    @Override
    @Contract(pure = true)
    public ObjectState getState() {
        return ObjectState.NoVolume;
    }

    @Contract(pure = true)
    public boolean isUnique() {
        return unique;
    }
    //endregion

    //region Public Methods
    public void addReceptor(ReceptorDescription receptorDescription, ISignalingPathway signalingPathway) {
        compartmentReceptors.put(receptorDescription, signalingPathway);
    }
    //endregion


    //region Private Methods

    //endregion

}
