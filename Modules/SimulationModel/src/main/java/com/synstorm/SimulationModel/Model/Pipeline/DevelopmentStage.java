package com.synstorm.SimulationModel.Model.Pipeline;

import com.synstorm.SimulationModel.Model.Model;
import com.synstorm.common.Utils.ConsoleProgressBar.IProgressBar;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.IStageParameter;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.ModelStageAction;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.StageAttribute;

import java.util.Map;

/**
 * Created by human-research on 17/05/2018.
 */
public final class DevelopmentStage extends ModelStage {
    //region Fields
    private long startTick;
    private long endTick;
    private final long stageTicks;
    //endregion


    //region Constructors

    public DevelopmentStage(int id, Map<StageAttribute, IStageParameter> parameters) {
        super(id, ModelStageAction.Development, parameters);
        stageTicks = parameters.get(StageAttribute.time).getLongValue();
        endTick = 0;
    }

    //endregion


    //region Getters and Setters
    //endregion


    //region Public Methods

    @Override
    public boolean checkStageConditions() {
        return Model.INSTANCE.getCurrentTick() >= endTick;
    }

    @Override
    public int getStagePercentage() {
        return (int) (((Model.INSTANCE.getCurrentTick() - startTick) / (double) stageTicks) * 100);
    }

    @Override
    public long calculateStage(long startTick, IProgressBar progressBar) {
        this.startTick = startTick;
        endTick = parameters.get(StageAttribute.time).getLongValue() + startTick;
        while(!checkStageConditions()) {
            Model.INSTANCE.getIndividual().calculateState();
            progressBar.updatePercentage(getStagePercentage(), Model.INSTANCE.getCurrentTick());
        }
        return startTick + endTick;
    }

    //endregion


    //region Private Methods

    //endregion

}
