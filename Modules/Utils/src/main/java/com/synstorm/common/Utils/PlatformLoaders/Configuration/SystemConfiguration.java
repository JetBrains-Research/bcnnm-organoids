package com.synstorm.common.Utils.PlatformLoaders.Configuration;

import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Pipeline;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Training;

public class SystemConfiguration {
    //region Fields
    private final int limit;

    private Pipeline pipeline;
    private Training training;

    //endregion


    //region Constructors

    public SystemConfiguration(int limit) {
        this.limit = limit;
        this.pipeline = new Pipeline();
    }

    //endregion


    //region Getters and Setters
    public int getLimit() {
        return limit;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public Training getTraining() {
        return training;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    //endregion


    //region Public Methods

    //endregion


    //region Private Methods

    //endregion
}
