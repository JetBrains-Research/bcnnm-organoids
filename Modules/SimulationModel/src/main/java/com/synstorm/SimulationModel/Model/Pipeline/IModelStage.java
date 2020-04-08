package com.synstorm.SimulationModel.Model.Pipeline;

import com.synstorm.common.Utils.ConsoleProgressBar.IProgressBar;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.IStageParameter;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.ModelStageAction;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.StageAttribute;

import java.util.Map;

public interface IModelStage {
    int getId();
    ModelStageAction getStage();
    long calculateStage(long startTick, IProgressBar progressBar);
    Map<StageAttribute, IStageParameter> getParameters();
    boolean checkStageConditions();
    int getStagePercentage();
}
