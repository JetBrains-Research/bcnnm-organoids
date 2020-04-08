/*
 * Copyright (c) 2018. This file was made as a part of BCNNM@JetBrains_Research work.
 */

package com.synstorm.common.Utils.PlatformLoaders.Objects.SignalPointsObjects;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathway;
import com.synstorm.common.Utils.ConfigInterfaces.LogicObjectTypes;
import com.synstorm.common.Utils.Coordinates.ObjectState;
import org.jetbrains.annotations.Contract;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by human-research on 10/05/2018.
 */
@Model_v1
public final class ModelSignalPoint implements ILogicObjectDescription {
    //region Fields
    private final LogicObjectTypes baseType;
    private final String type;
    private final Set<ISignalingPathway> SPs;
    //endregion


    //region Constructors

    public ModelSignalPoint(String type) {
        this.baseType = LogicObjectTypes.SignalPoint;
        this.type = type;
        this.SPs = new HashSet<>();
    }

    //endregion


    //region Getters and Setters

    @Contract(pure = true)
    public LogicObjectTypes getBaseType() {
        return baseType;
    }

    @Contract(pure = true)
    @Override
    public ObjectState getState() {
        return ObjectState.NoVolume;
    }

    @Contract(pure = true)
    @Override
    public Set<ISignalingPathway> getSignalingPathways() {
        return SPs;
    }

    @Contract(pure = true)
    public String getId() {
        return type;
    }


    //endregion


    //region Public Methods
    public void addSP(ISignalingPathway sp) {
        SPs.add(sp);
    }
    //endregion


    //region Private Methods

    //endregion

}
