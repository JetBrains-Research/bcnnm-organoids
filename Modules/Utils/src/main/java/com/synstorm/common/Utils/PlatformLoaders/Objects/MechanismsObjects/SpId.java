package com.synstorm.common.Utils.PlatformLoaders.Objects.MechanismsObjects;

import com.synstorm.DES.AllowedEvent;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.Mechanisms.ModelingMechanisms;

import java.util.Objects;

@Model_v1
public final class SpId implements AllowedEvent {
    //region Fields
    private final ModelingMechanisms mechanism;
    private final String name;
    private final int hash;
    //endregion


    //region Constructors

    public SpId(ModelingMechanisms mechanism, String name) {
        this.mechanism = mechanism;
        this.name = name;
        hash = Objects.hash(mechanism, name);
    }

    @Override
    public ModelingMechanisms getMechanism() {
        return mechanism;
    }

    @Override
    public String getName() {
        return name;
    }

    //endregion


    //region Getters and Setters

    //endregion


    //region Public Methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpId spId = (SpId) o;
        return mechanism == spId.mechanism &&
                Objects.equals(name, spId.name);
    }

    @Override
    public int hashCode() {
        return hash;
    }

    //endregion


    //region Private Methods

    //endregion

}
