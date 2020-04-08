package com.synstorm.common.Utils.PlatformLoaders.Configuration;

import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathway;
import com.synstorm.common.Utils.Mechanisms.ModelingMechanisms;
import com.synstorm.common.Utils.PlatformLoaders.Objects.MechanismsObjects.Mechanism;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MechanismsConfiguration {
    //region Fields
    private final Map<ModelingMechanisms, Mechanism> mechanisms;
    //endregion


    //region Constructors

    public MechanismsConfiguration() {
        this.mechanisms = new HashMap<>();
    }

    //endregion


    //region Getters and Setters
    public Set<ModelingMechanisms> getMechanismsIndex() {
        return mechanisms.keySet();
    }

    public Mechanism getMechanism(String id) {
        return mechanisms.get(ModelingMechanisms.valueOf(id));
    }

    @Nullable
    public ISignalingPathway getSignalingPathway(String spName) {
        for (Mechanism mechanism : mechanisms.values()) {
            if (mechanism.mechanismContainsSP(spName))
                return mechanism.getSignalingPathway(spName);
        }
        return null;
    }

    @Nullable
    public Mechanism getMechanismBySignalingPathway(String spName) {
        for (Mechanism mechanism : mechanisms.values()) {
            if (mechanism.mechanismContainsSP(spName))
                return mechanism;
        }
        return null;
    }
    //endregion


    //region Public Methods
    public void addMechanism(Mechanism mechanism) {
        mechanisms.put(mechanism.getId(), mechanism);
    }
    //endregion


    //region Private Methods

    //endregion

}
