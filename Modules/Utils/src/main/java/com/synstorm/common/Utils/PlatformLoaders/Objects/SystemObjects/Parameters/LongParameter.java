package com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathway;

/**
 * Created by human-research on 15/05/2018.
 */
@Model_v1
public class LongParameter implements IStageParameter {
    //region Fields
    private final Long parameter;
    //endregion


    //region Constructors

    LongParameter(Object parameter) {
        this.parameter = (Long) parameter;
    }

    //endregion


    //region Getters and Setters

    //endregion


    //region Public Methods

    @Override
    public double getDoubleValue() {
        return parameter.doubleValue();
    }

    @Override
    public String getStringValue() {
        return null;
    }

    @Override
    public long getLongValue() {
        return parameter;
    }

    @Override
    public int getIntValue() {
        return parameter.intValue();
    }

    @Override
    public ISignalingPathway getSignalingPathway() {
        return null;
    }

    @Override
    public ILogicObjectDescription getLogicObjectDescription() {
        return null;
    }

    @Override
    public DatasetType getDatasetType() {
        return null;
    }

    @Override
    public ModelStageAction getModelStageAction() {
        return null;
    }

    //endregion


    //region Private Methods

    //endregion

}
