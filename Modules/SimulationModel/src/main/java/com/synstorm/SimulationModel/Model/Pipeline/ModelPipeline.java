package com.synstorm.SimulationModel.Model.Pipeline;

import java.util.HashMap;
import java.util.Map;

public enum ModelPipeline {
    INSTANCE;
    //region Fields
    private final Map<Integer, IModelStage> stages;
    //endregion


    //region Constructors

    ModelPipeline() {
        this.stages = new HashMap<>();
    }

    //endregion


    //region Getters and Setters
    public int getStagesLength() {
        return stages.keySet().size();
    }

    public IModelStage getStage(int id) {
        return stages.get(id);
    }
    //endregion


    //region Public Methods
    public void addStage(int id, IModelStage modelStage) {
        stages.put(id, modelStage);
    }

    //endregion


    //region Private Methods

    //endregion

}
