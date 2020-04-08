/*
 * Copyright (c) 2018. This file was made as a part of BCNNM@JetBrains_Research work.
 */

package com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.DatasetType;
import org.jetbrains.annotations.Contract;

/**
 * Created by human-research on 15/05/2018.
 */

@Model_v1
public final class Dataset {
    //region Fields
    private final int datasetId;
    private final DatasetType datasetType;
    private final int classCount;
    private final String setFileName;
    private final int setSize;
    //endregion


    //region Constructors

    public Dataset(int datasetId, DatasetType datasetType, int classCount, String setFileName, int setSize) {
        this.datasetId = datasetId;
        this.datasetType = datasetType;
        this.classCount = classCount;
        this.setFileName = setFileName;
        this.setSize = setSize;
    }

    //endregion


    //region Getters and Setters
    @Contract(pure = true)
    public int getDatasetId() {
        return datasetId;
    }

    @Contract(pure = true)
    public DatasetType getDatasetType() {
        return datasetType;
    }

    @Contract(pure = true)
    public int getClassCount() {
        return classCount;
    }

    @Contract(pure = true)
    public String getSetFileName() {
        return setFileName;
    }

    @Contract(pure = true)
    public int getSetSize() {
        return setSize;
    }

    //endregion


    //region Public Methods

    //endregion


    //region Private Methods

    //endregion

}
