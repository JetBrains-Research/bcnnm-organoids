/*
 * Copyright (c) 2018. This file was made as a part of BCNNM@JetBrains_Research work.
 */

package com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by human-research on 15/05/2018.
 */

@Model_v1
public final class Training {
    //region Fields
    private final int lqWindowSize;
    private final int clusteringSeedCount;
    private Map<Integer, Dataset> datasetMap;
    //endregion


    //region Constructors

    public Training(int lqWindowSize, int clusteringSeedCount) {
        this.lqWindowSize = lqWindowSize;
        this.clusteringSeedCount = clusteringSeedCount;
        this.datasetMap = new HashMap<>();
    }

    //endregion


    //region Getters and Setters
    @Contract(pure = true)
    public int getLqWindowSize() {
        return lqWindowSize;
    }

    @Contract(pure = true)
    public int getClusteringSeedCount() {
        return clusteringSeedCount;
    }

    @Contract(pure = true)
    public Map<Integer, Dataset> getDatasetMap() {
        return datasetMap;
    }

    //endregion


    //region Public Methods
    public void addDataset(Dataset dataset) {
        datasetMap.put(dataset.getDatasetId(), dataset);
    }
    //endregion


    //region Private Methods

    //endregion

}
