package com.synstorm.common.Utils.PlatformLoaders.Objects.MechanismsObjects;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import org.jetbrains.annotations.Contract;

/**
 * Created by human-research on 15/11/2018.
 */
@Model_v1
public enum CascadeAction {
    Execute(0),
    Disrupt(1);

    private final int id;

    CascadeAction(int id) {
        this.id = id;
    }

    @Contract(pure = true)
    public int getId() {
        return id;
    }
}