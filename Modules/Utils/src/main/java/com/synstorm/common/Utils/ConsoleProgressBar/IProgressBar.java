package com.synstorm.common.Utils.ConsoleProgressBar;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.ModelStageAction;

import java.util.List;
import java.util.UUID;

/**
 * Progress bar interface
 * Created by dvbozhko on 3/5/16.
 */

@Model_v1

public interface IProgressBar {
    void showEmptyBar();
    void printModelStatus(UUID modelId, long conditionCounter);
    void addAdditionalInfoList(List<AdditionalInfo> additionalInfoList);
    void addAdditionalInfo(AdditionalInfo additionalInfo);
    void printAllAdditionalInfo();
    void updatePercentage(Object... args);// percentage, long conditionCounter);
    String getStartTime();
    String getModelingTime();

    void startStage(int stageNum, ModelStageAction modelStageAction);
//    void updateStagePercentage(Object... args);
    void endStage();
}
