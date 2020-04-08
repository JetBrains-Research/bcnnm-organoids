/*
 * Copyright (c) 2018. This file was made as a part of BCNNM@JetBrains_Research work.
 */

package com.synstorm.common.Utils.PlatformLoaders.Objects.CellObjects;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ICellObjectDescription;
import com.synstorm.common.Utils.ConfigInterfaces.LogicObjectTypes;
import com.synstorm.common.Utils.Coordinates.ObjectState;
import org.jetbrains.annotations.Contract;

@Model_v1
public final class Cell extends BaseCellObject implements ICellObjectDescription {
    //region Fields
    private boolean immovable;
    //endregion

    //region Constructors
    public Cell(String id, LogicObjectTypes baseType, boolean immovable) {
        super(id, baseType);
        this.immovable = immovable;
    }
    //endregion

    //region Getters and Setters
    @Override
    @Contract(pure = true)
    public ObjectState getState() {
        return !immovable ? ObjectState.VolumeMovable : ObjectState.VolumeImmovable;
    }
    //endregion

    //region Public Methods
    //endregion

    //region Private Methods
    //endregion
}
