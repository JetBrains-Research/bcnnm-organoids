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
public final class StringParameter implements IStageParameter {
    //region Fields
    private String parameter;
    //endregion


    //region Constructors
    StringParameter(Object parameter) {
        this.parameter = (String) parameter;
    }
    //endregion


    //region Getters and Setters

    //endregion


    //region Public Methods

    @Contract(pure = true)
    @Override
    public double getDoubleValue() {
        return 0.0;
    }

    @Contract(pure = true)
    @Override
    public String getStringValue() {
        return parameter;
    }

    @Contract(pure = true)
    @Override
    public long getLongValue() {
        return 0L;
    }

    @Contract(pure = true)
    @Override
    public int getIntValue() {
        return 0;
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


    //region Private Methods

    //endregion

}
