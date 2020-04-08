/*
 * Copyright (c) 2018. This file was made as a part of BCNNM@JetBrains_Research work.
 */

package com.synstorm.common.Utils.PlatformLoaders.Objects.SpaceObjects;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;

/**
 * Created by human-research on 12/05/2018.
 */
@Model_v1
public interface IObjectInstance {
    int[] getCoordinate();
    ILogicObjectDescription getILogicObjectDescription();
}
