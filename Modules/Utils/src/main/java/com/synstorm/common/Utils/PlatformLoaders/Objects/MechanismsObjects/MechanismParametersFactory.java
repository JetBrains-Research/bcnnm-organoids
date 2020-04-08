/*
 * Copyright (c) 2018. This file was made as a part of BCNNM@JetBrains_Research work.
 */

package com.synstorm.common.Utils.PlatformLoaders.Objects.MechanismsObjects;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathwayParameters;
import com.synstorm.common.Utils.Mechanisms.MechanismParameters.ChemicalSignalParameters;
import com.synstorm.common.Utils.Mechanisms.MechanismParameters.ObjectCreateParameters;
import com.synstorm.common.Utils.Mechanisms.MechanismParameters.ProbabilityParameters;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Model_v1
public enum MechanismParametersFactory {
    INSTANCE;
    //region Fields
    private final Map<String, Function<Object[], ISignalingPathwayParameters>> nameToParameter;
    //endregion


    //region Constructors
    MechanismParametersFactory() {
        nameToParameter = new HashMap<>();

        initializeMap();
    }
    //endregion


    //region Getters and Setters
    //endregion


    //region Public Methods
    public ISignalingPathwayParameters createExecuteParameter(String type, Object[] parameter) {
        return nameToParameter.get(type).apply(parameter);
    }
    //endregion


    //region Private Methods
    private void initializeMap() {
        nameToParameter.put("Chemical", MechanismParametersFactory::createChemicalSignalParameter);
        nameToParameter.put("Object", MechanismParametersFactory::createObjectCreateParameter);
        nameToParameter.put("Probability", MechanismParametersFactory::createProbabilityParameter);
    }

    static private ISignalingPathwayParameters createChemicalSignalParameter(Object[] parameter) {
        return new ChemicalSignalParameters((Integer) parameter[0], (Integer) parameter[1]);
    }

    static private ISignalingPathwayParameters createObjectCreateParameter(Object[] parameter) {
        return new ObjectCreateParameters((String) parameter[0]);
    }

    static private ISignalingPathwayParameters createProbabilityParameter(Object[] parameter) {
        return new ProbabilityParameters((Double) parameter[0]);
    }
    //endregion

}
