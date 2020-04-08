package com.synstorm.SimulationModel.Utils;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

import java.io.File;

/**
 * Created by dvbozhko on 11/20/15.
 */
@Model_v0
@NonProductionLegacy
public class ModelStrings {
    public static final String CONFIG_PATH = System.getProperty("user.dir") + File.separator + "data" + File.separator;
    public static final String SAMPLES_PATH = System.getProperty("user.dir") + File.separator + "data" + File.separator
            + "TrainSamples" + File.separator;
    public static final String RESULTS_PATH = System.getProperty("user.dir").equals("/") ?
            System.getProperty("user.dir") + "results" + File.separator :
            System.getProperty("user.dir") + File.separator + "results" + File.separator;
}
