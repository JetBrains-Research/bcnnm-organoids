package com.synstorm.SimulationModel;


import com.synstorm.SimulationModel.ModelProcessors.BaseModelProcessor;
import com.synstorm.SimulationModel.ModelProcessors.ModelProcessorFactory;
import com.synstorm.SimulationModel.Utils.ModelStrings;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.SimArgs.SimulationArguments;

/**
 * Entry point class for simulation
 * Created by dvbozhko on 13/09/16.
 */
@Model_v0
@ProductionLegacy
public class SimulationModelMain {
    public static void main(String[] args) throws Exception {
        suppressLogging();
        System.out.println("#### Modeling started ####");
        ModelProcessorFactory modelProcessorFactory = new ModelProcessorFactory();
        SimulationArguments.INSTANCE.proceedArguments(args);
        Loader.INSTANCE.load(ModelStrings.CONFIG_PATH + SimulationArguments.INSTANCE.getConfigPath());
        System.out.println("Platform loaded.");
        BaseModelProcessor modelProcessor = modelProcessorFactory.returnModelProcessor();
        modelProcessor.calculateModel();
    }

    private static void suppressLogging() {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(ch.qos.logback.classic.Level.OFF);
        java.util.logging.Logger global = java.util.logging.Logger.getLogger("");
        global.setLevel(java.util.logging.Level.OFF);
    }
}
