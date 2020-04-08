package com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathway;

/**
 * Created by human-research on 15/05/2018.
 */
@Model_v1
public class ObjectParameter implements IStageParameter {
    //region Fields
    private final Object parameter;
    //endregion


    //region Constructors

    ObjectParameter(Object parameter) {
        this.parameter = parameter;
    }

    //endregion


    //region Getters and Setters

    //endregion


    //region Public Methods
    @Override
    public double getDoubleValue() {
        return 0.0;
    }

    @Override
    public String getStringValue() {
        return null;
    }

    @Override
    public long getLongValue() {
        return 0L;
    }

    @Override
    public ISignalingPathway getSignalingPathway() {
        if (parameter instanceof ISignalingPathway)
            return (ISignalingPathway) parameter;
        else return null;
    }

    @Override
    public int getIntValue() {
        return 0;
    }

    @Override
    public ILogicObjectDescription getLogicObjectDescription() {
        if (parameter instanceof ILogicObjectDescription)
            return (ILogicObjectDescription) parameter;
        else return null;
    }

    @Override
    public DatasetType getDatasetType() {
        if (parameter instanceof DatasetType)
            return DatasetType.valueOf(String.valueOf(parameter));
        else return null;
    }

    @Override
    public ModelStageAction getModelStageAction() {
        if (parameter instanceof ModelStageAction)
            return ModelStageAction.valueOf(String.valueOf(parameter));
        else return null;
    }
    //endregion


    //region Private Methods

    //endregion

}
