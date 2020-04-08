package com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by human-research on 15/05/2018.
 */
@Model_v1
public enum StageParametersFactory {
    INSTANCE;
    //region Fields
    private final Map<StageAttribute, Function<Object, IStageParameter>> map;
    //endregion


    //region Constructors

    StageParametersFactory() {
        map = new HashMap<>();
        initializeMap();
    }



    //endregion


    //region Getters and Setters

    //endregion


    //region Public Methods
    public IStageParameter createStageParameter(StageAttribute stageAttribute, Object parameterValue) {
        return map.get(stageAttribute).apply(parameterValue);
    }
    //endregion


    //region Private Methods
    private void initializeMap() {
        map.put(StageAttribute.id, StageParametersFactory::createLongParameter);
        map.put(StageAttribute.mode, StageParametersFactory::createObjectParameter);
        map.put(StageAttribute.type, StageParametersFactory::createObjectParameter);
        map.put(StageAttribute.dataset, StageParametersFactory::createObjectParameter);
        map.put(StageAttribute.datasetType, StageParametersFactory::createObjectParameter);
        map.put(StageAttribute.size, StageParametersFactory::createLongParameter);
        map.put(StageAttribute.target, StageParametersFactory::createObjectParameter);
        map.put(StageAttribute.scoring, StageParametersFactory::createStringParameter);
        map.put(StageAttribute.cellType, StageParametersFactory::createObjectParameter);
        map.put(StageAttribute.ratio, StageParametersFactory::createDoubleParameter);
        map.put(StageAttribute.pharma, StageParametersFactory::createStringParameter);
        map.put(StageAttribute.time, StageParametersFactory::createLongParameter);
        map.put(StageAttribute.customSp, StageParametersFactory::createObjectParameter);
    }

    @NotNull
    @Contract("_ -> new")
    static private StringParameter createStringParameter(Object parameterValue) {
        return new StringParameter(parameterValue);
    }

    @NotNull
    @Contract("_ -> new")
    static private DoubleParameter createDoubleParameter(Object parameterValue) {
        return new DoubleParameter(parameterValue);
    }

    @NotNull
    @Contract("_ -> new")
    static private ObjectParameter createObjectParameter(Object parameterValue) {
        return new ObjectParameter(parameterValue);
    }

    @NotNull
    @Contract("_ -> new")
    static private LongParameter createLongParameter(Object parameterValue) {
        return new LongParameter(parameterValue);
    }
    //endregion

}
