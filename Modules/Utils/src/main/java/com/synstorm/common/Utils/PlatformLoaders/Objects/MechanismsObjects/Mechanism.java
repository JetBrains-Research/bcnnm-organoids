/*
 * Copyright (c) 2018. This file was made as a part of BCNNM@JetBrains_Research work.
 */

package com.synstorm.common.Utils.PlatformLoaders.Objects.MechanismsObjects;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.Mechanisms.ModelingMechanisms;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Model_v1
public final class Mechanism {
    //region Fields
    private final ModelingMechanisms id;
    private final Map<String, SignalingPathway> signalingPathways;
    //endregion


    //region Constructors

    public Mechanism(String id) {
        this.id = ModelingMechanisms.valueOf(id);
        this.signalingPathways = new HashMap<>();
    }

    //endregion


    //region Getters and Setters

    @Contract(pure = true)
    public ModelingMechanisms getId() {
        return id;
    }

    public SignalingPathway getSignalingPathway(String id) {
        return signalingPathways.get(id);
    }
    //endregion

    @NotNull
    @Contract(pure = true)
    public Set<String> getSignalingPathwaysIndex() {
        return signalingPathways.keySet();
    }

    //region Public Methods
    public void addSignalingPathway(SignalingPathway signalingPathway) {
        signalingPathways.put(signalingPathway.getPathway().getName(), signalingPathway);
    }

    public boolean mechanismContainsSP(String spName) {
        return signalingPathways.keySet().contains(spName);
    }
    //endregion


    //region Private Methods

    //endregion

}
