package com.synstorm.common.Utils.ConfigInterfaces;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.Coordinates.ObjectState;

import java.util.Set;

@Model_v1
public interface ILogicObjectDescription {
    String getId();
    LogicObjectTypes getBaseType();
    ObjectState getState();
    Set<ISignalingPathway> getSignalingPathways();
}
