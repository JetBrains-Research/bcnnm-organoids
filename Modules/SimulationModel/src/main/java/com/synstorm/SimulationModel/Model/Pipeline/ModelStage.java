package com.synstorm.SimulationModel.Model.Pipeline;

import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.IStageParameter;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.ModelStageAction;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.StageAttribute;
import org.jetbrains.annotations.Contract;

import java.util.Map;

public abstract class ModelStage implements IModelStage {
    //region Fields
    protected final int id;
    protected final ModelStageAction stage;
    protected final Map<StageAttribute, IStageParameter> parameters;
    //endregion


    //region Constructors

    public ModelStage(int id, ModelStageAction stage, Map<StageAttribute, IStageParameter> parameters) {
        this.id = id;
        this.stage = stage;
        this.parameters = parameters;
    }


    //endregion


    //region Getters and Setters
    @Contract(pure = true)
    @Override
    public int getId() {
        return id;
    }

    @Contract(pure = true)
    @Override
    public Map<StageAttribute, IStageParameter> getParameters() {
        return parameters;
    }

    public ModelStageAction getStage() {
        return stage;
    }

    //endregion


    //region Public Methods
    //endregion


    //region Private Methods
    //endregion

}
