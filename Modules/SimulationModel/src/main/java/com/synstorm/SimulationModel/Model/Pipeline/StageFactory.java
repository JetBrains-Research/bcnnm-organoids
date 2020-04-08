package com.synstorm.SimulationModel.Model.Pipeline;

import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.ModelStageAction;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.StageAttribute;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by human-research on 17/05/2018.
 */
public enum StageFactory {
    INSTANCE;
    private final Map<ModelStageAction, Function<Stage, ModelStage>> stageAttributeFunctionMap = new HashMap<>();

    StageFactory() {
        initializeMap();
    }

    public ModelStage createStage(ModelStageAction modelStageAction, Stage stage) {
        return stageAttributeFunctionMap.get(modelStageAction).apply(stage);
    }

    private void initializeMap() {
        stageAttributeFunctionMap.put(ModelStageAction.Development, StageFactory::createDevelopmentStage);
        stageAttributeFunctionMap.put(ModelStageAction.Damage, StageFactory::createDamageStage);
    }

    private static ModelStage createDevelopmentStage(Stage stage) {
        return new DevelopmentStage(
                stage.getStageAttribute(StageAttribute.id).getIntValue(),
                stage.getStageAttributes());
    }

    private static ModelStage createDamageStage(Stage stage) {
        return new DamageStage(
                stage.getStageAttribute(StageAttribute.id).getIntValue(),
                stage.getStageAttributes());
    }
}
