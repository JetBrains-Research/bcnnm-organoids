package com.synstorm.common.Utils.PlatformLoaders.Objects.MechanismsObjects;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import org.jetbrains.annotations.Contract;

@Model_v1
public enum ConditionType {
    Default(0),
    OnConditionTrue(1),
    OnConditionFalse(2);

    private final int id;

    ConditionType(int id) {
        this.id = id;
    }

    @Contract(pure = true)
    public int getId() {
        return id;
    }
}
