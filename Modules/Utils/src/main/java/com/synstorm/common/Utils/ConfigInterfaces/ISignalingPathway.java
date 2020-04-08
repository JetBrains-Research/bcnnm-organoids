package com.synstorm.common.Utils.ConfigInterfaces;

import com.synstorm.DES.AllowedEvent;
import com.synstorm.DES.IEventParameters;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;

import java.util.Set;

@Model_v1
public interface ISignalingPathway extends IEventParameters {
    AllowedEvent getPathway();

    int getPriority();
    int getDuration();
    boolean isInitial();

    ISignalingPathwayParameters getExecutingParameters();
    ICondition getCondition();

    Set<AllowedEvent> getExecutingOnConditionTrueSignalingPathways();
    Set<AllowedEvent> getExecutingOnConditionFalseSignalingPathways();
    Set<AllowedEvent> getExecutingDefaultSignalingPathways();

    Set<AllowedEvent> getDisruptingOnConditionTrueSignalingPathways();
    Set<AllowedEvent> getDisruptingOnConditionFalseSignalingPathways();
    Set<AllowedEvent> getDisruptingDefaultSignalingPathways();
}
