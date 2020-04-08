package com.synstorm.SimulationModel.Model.Pipeline;

import com.synstorm.SimulationModel.Model.Model;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathway;
import com.synstorm.common.Utils.ConsoleProgressBar.IProgressBar;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.IStageParameter;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.ModelStageAction;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.StageAttribute;

import java.util.HashSet;
import java.util.Map;

/**
 * Created by human-research on 17/05/2018.
 */
public final class DamageStage extends ModelStage {
    //region Fields

    //endregion


    //region Constructors

    public DamageStage(int id, Map<StageAttribute, IStageParameter> parameters) {
        super(id, ModelStageAction.Damage, parameters);
    }

    //endregion


    //region Getters and Setters

    //endregion


    //region Public Methods

    @Override
    public long calculateStage(long startTick, IProgressBar progressBar) {
        final double ratio = parameters.get(StageAttribute.ratio).getDoubleValue();
        final String cellType = parameters.get(StageAttribute.cellType).getLogicObjectDescription().getId();
        final ISignalingPathway customSp = parameters.get(StageAttribute.customSp).getSignalingPathway();
        Model.INSTANCE.traumatizeIndividual(ratio, new HashSet<String>() {{
            add(cellType);
        }}, customSp);
        progressBar.updatePercentage(getStagePercentage(), Model.INSTANCE.getCurrentTick());
        return startTick;
    }

    @Override
    public boolean checkStageConditions() {
        return true;
    }

    @Override
    public int getStagePercentage() {
        return 100;
    }

    //endregion


    //region Private Methods

    //endregion

}
