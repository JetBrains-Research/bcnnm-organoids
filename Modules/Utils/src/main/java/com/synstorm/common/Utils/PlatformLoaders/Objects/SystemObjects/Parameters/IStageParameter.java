package com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathway;

/**
 * Created by human-research on 15/05/2018.
 */
@Model_v1
public interface IStageParameter {
    double getDoubleValue();
    long getLongValue();
    int getIntValue();

    String getStringValue();
    ISignalingPathway getSignalingPathway();
    ILogicObjectDescription getLogicObjectDescription();
    DatasetType getDatasetType();
    ModelStageAction getModelStageAction();
}
