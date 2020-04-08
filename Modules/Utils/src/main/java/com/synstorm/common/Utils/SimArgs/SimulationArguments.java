package com.synstorm.common.Utils.SimArgs;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * Class container for defined console arguments for simulation.
 * Created by dvbozhko on 23/01/2017.
 */
@Model_v1
public enum SimulationArguments {
    INSTANCE;

    //region Fields
    @Option(name="-processor", usage = "Processor defines simulation mode")
    private String processor = "SingleIndividualProcessor";

    @Option(name="-configPath", usage = "Path to a directory with configuration")
    private String configPath = "Experiments/Configuration-I/A";

    @Option(name="-logLevel", usage = "Simulation output messages level")
    private int logLevel = 0;

    @Option(name="-indSeedNum", usage = "Seed number for individual from corresponding section")
    private int individualSeedNum = 0;

    @Option(name = "-randomIndividualSeeds", usage = "Use random seeds for INDIVIDUAL calculations")
    private boolean randomIndividualSeeds = false;

    @Option(name = "-updateFrequency", usage = "Percentage value of progress bar and slack updates")
    private int updateFrequency = 5;
    @Option(name = "-statisticExporters", usage = "Name of the exporters used in this session")
    private String statisticExporters = "default";

    //endregion

    //region Constructors
    public void proceedArguments(String[] args) throws Exception {
        CmdLineParser parser = new CmdLineParser(this);
        parser.parseArgument(args);
    }
    //endregion

    //region Getters and Setters
    public String getProcessor() {
        return processor;
    }

    public String getConfigPath() {
        return configPath;
    }

    public int getLogLevel() {
        return logLevel;
    }

    public int getIndividualSeedNum() {
        return individualSeedNum;
    }

    public boolean isRandomIndividualSeeds() {
        return randomIndividualSeeds;
    }

    public String getStatisticExporters() { return statisticExporters; }

    public int getUpdateFrequency() {
        return updateFrequency;
    }
    //endregion

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
