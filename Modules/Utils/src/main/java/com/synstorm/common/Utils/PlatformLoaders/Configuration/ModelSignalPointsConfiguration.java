/*
 * Copyright (c) 2018. This file was made as a part of BCNNM@JetBrains_Research work.
 */

package com.synstorm.common.Utils.PlatformLoaders.Configuration;

import com.synstorm.common.Utils.PlatformLoaders.Objects.SignalPointsObjects.ModelSignalPoint;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by human-research on 10/05/2018.
 */
public final class ModelSignalPointsConfiguration {
    //region Fields
    private final Map<String, ModelSignalPoint> modelSignalPointMap;
    //endregion


    //region Constructors

    public ModelSignalPointsConfiguration() {
        this.modelSignalPointMap = new HashMap<>();
    }

    //endregion


    //region Getters and Setters
    @NotNull
    @Contract(pure = true)
    public Collection<ModelSignalPoint> getAvailableModelSignalPoints() {
        return modelSignalPointMap.values();
    }

    public ModelSignalPoint getModelSignalPoint(String type) {
        return modelSignalPointMap.get(type);
    }
    //endregion


    //region Public Methods
    public void addModelSignalPoint(ModelSignalPoint modelSignalPoint) {
        modelSignalPointMap.put(modelSignalPoint.getId(), modelSignalPoint);
    }
    //endregion


    //region Private Methods

    //endregion

}
