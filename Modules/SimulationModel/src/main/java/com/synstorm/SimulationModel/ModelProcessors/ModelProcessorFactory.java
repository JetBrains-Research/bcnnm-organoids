package com.synstorm.SimulationModel.ModelProcessors;

import com.synstorm.common.Utils.SimArgs.SimulationArguments;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory class for constructing and returning correct type of model processor.
 * Created by dvbozhko on 13/09/16.
 */
public class ModelProcessorFactory {
    //region Fields
    private Map<String, IGetModelProcessor> stringIGetModelProcessorMap;
    //endregion

    //region Constructors
    public ModelProcessorFactory() {
        stringIGetModelProcessorMap = new HashMap<>();
        initializeMethodReferences();
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    public BaseModelProcessor returnModelProcessor() {
        return stringIGetModelProcessorMap.get(SimulationArguments.INSTANCE.getProcessor()).execute();
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    private void initializeMethodReferences() {
        stringIGetModelProcessorMap.put("SingleIndividualProcessor", this::returnSimpleIndividualProcessor);
//        stringIGetModelProcessorMap.put("SingleLearningProcessor", this::returnLearningIndividualProcessor);
//        stringIGetModelProcessorMap.put("EvolutionIndividualProcessor", this::returnSimpleEvolutionProcessor);
//        stringIGetModelProcessorMap.put("EvolutionLearningProcessor", this::returnLearningEvolutionProcessor);
//        stringIGetModelProcessorMap.put("MultiSeedIndividualProcessor", this::returnMultiSeedIndividualProcessor);
//        stringIGetModelProcessorMap.put("MultiSeedLearningProcessor", this::returnMultiSeedLearningProcessor);
//        stringIGetModelProcessorMap.put("MultiConfigurationIndividualProcessor", this::returnMultiConfigurationIndividualProcessor);
//        stringIGetModelProcessorMap.put("MultiConfigurationLearningProcessor", this::returnMultiConfigurationLearningProcessor);
    }

    @Contract(" -> !null")
    private BaseModelProcessor returnSimpleIndividualProcessor() {
        return new SingleIndividualProcessor();
    }

//    @Contract(" -> !null")
//    private BaseModelProcessor returnLearningIndividualProcessor() {
//        return new SingleLearningProcessor();
//    }
//
//    @Contract(" -> !null")
//    private BaseModelProcessor returnSimpleEvolutionProcessor() {
//        return new EvolutionIndividualProcessor();
//    }
//
//    @Contract(" -> !null")
//    private BaseModelProcessor returnLearningEvolutionProcessor() {
//        return new EvolutionLearningProcessor();
//    }
//
//    @Contract(" -> !null")
//    private BaseModelProcessor returnMultiSeedLearningProcessor() {
//        return new MultiSeedLearningProcessor();
//    }
//
//    @Contract(" -> !null")
//    private BaseModelProcessor returnMultiSeedIndividualProcessor() {
//        return new MultiSeedIndividualProcessor();
//    }
//
//    @Contract(" -> !null")
//    private BaseModelProcessor returnMultiConfigurationIndividualProcessor() {
//        return new MultiConfigurationIndividualProcessor();
//    }
//
//    @Contract(" -> !null")
//    private BaseModelProcessor returnMultiConfigurationLearningProcessor() {
//        return new MultiConfigurationLearningProcessor();
//    }
    //endregion

    @FunctionalInterface
    private interface IGetModelProcessor {
        BaseModelProcessor execute();
    }
}
