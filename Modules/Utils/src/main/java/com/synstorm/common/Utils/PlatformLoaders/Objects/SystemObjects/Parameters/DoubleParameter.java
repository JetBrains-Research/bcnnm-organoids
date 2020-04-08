package com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathway;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

/**
 * Created by human-research on 15/05/2018.
 */
@Model_v1
public final class DoubleParameter implements IStageParameter {
    //region Fields
    private final Double parameter;
    //endregion


    //region Constructors

    DoubleParameter(Object parameter) {
        this.parameter = (Double) parameter;
    }

    @Contract(pure = true)
    @Override
    public double getDoubleValue() {
        return parameter;
    }

    @Contract(pure = true)
    @Override
    public String getStringValue() {
        return parameter.toString();
    }

    @Contract(pure = true)
    @Override
    public long getLongValue() {
        return parameter.longValue();
    }

    @Contract(pure = true)
    @Override
    public int getIntValue() {
        return parameter.intValue();
    }

    @Nullable
    @Contract(pure = true)
    @Override
    public ISignalingPathway getSignalingPathway() {
        return null;
    }

    @Nullable
    @Contract(pure = true)
    @Override
    public ILogicObjectDescription getLogicObjectDescription() {
        return null;
    }

    @Nullable
    @Contract(pure = true)
    @Override
    public DatasetType getDatasetType() {
        return null;
    }

    @Nullable
    @Contract(pure = true)
    @Override
    public ModelStageAction getModelStageAction() {
        return null;
    }

    //endregion


    //region Getters and Setters

    //endregion


    //region Public Methods

    //endregion


    //region Private Methods

    //endregion

}
