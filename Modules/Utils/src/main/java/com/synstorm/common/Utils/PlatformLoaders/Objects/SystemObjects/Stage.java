/*
 * Copyright (c) 2018. This file was made as a part of BCNNM@JetBrains_Research work.
 */

package com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.IStageParameter;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.StageAttribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by human-research on 15/05/2018.
 */

@Model_v1
public class Stage {
    //region Fields
    private Map<StageAttribute, IStageParameter> stageAttributes;
    private List<Stage> stageList;
    //endregion


    //region Constructors

    public Stage() {
        this.stageAttributes = new HashMap<>();
        this.stageList = new ArrayList<>();
    }

    //endregion


    //region Getters and Setters

    public List<Stage> getStageList() {
        return stageList;
    }

    public Map<StageAttribute, IStageParameter> getStageAttributes() {
        return stageAttributes;
    }

    public IStageParameter getStageAttribute(StageAttribute stageAttribute) {
        return stageAttributes.get(stageAttribute);
    }

    //endregion


    //region Public Methods

    public void addStage(int id, Stage stage) {
        stageList.add(id, stage);
    }

    public void setStageAttribute(StageAttribute stageAttribute, IStageParameter value) {
        stageAttributes.put(stageAttribute, value);
    }

    //endregion


    //region Private Methods

    //endregion

}
