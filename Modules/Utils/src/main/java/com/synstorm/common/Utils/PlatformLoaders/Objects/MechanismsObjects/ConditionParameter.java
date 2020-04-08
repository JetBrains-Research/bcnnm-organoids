package com.synstorm.common.Utils.PlatformLoaders.Objects.MechanismsObjects;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.SignalSelectionType;
import org.jetbrains.annotations.Contract;

@Model_v1
public final class ConditionParameter {
    private final int selectionLigand;
    private final SignalSelectionType selectionType;


    public ConditionParameter(int selectionLigand, String selectionType) {
        this.selectionLigand = selectionLigand;
        this.selectionType = SignalSelectionType.valueOf(selectionType);
    }

    @Contract(pure = true)
    public int getSelectionLigand() {
        return selectionLigand;
    }

    @Contract(pure = true)
    public SignalSelectionType getSelectionType() {
        return selectionType;
    }
}
