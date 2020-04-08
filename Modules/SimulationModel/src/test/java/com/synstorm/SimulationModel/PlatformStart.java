/*
 * Copyright (c) 2018. This file was made as a part of BCNNM@JetBrains_Research work.
 */

package com.synstorm.SimulationModel;

import com.synstorm.common.Utils.PlatformLoaders.Configuration.PlatformConfiguration;
import com.synstorm.common.Utils.SimArgs.SimulationArguments;

/**
 * Created by human-research on 10/05/2018.
 */
public final class PlatformStart {
    //region Fields

    //endregion


    //region Constructors

    //endregion


    //region Getters and Setters

    //endregion


    //region Public Methods
    public static void main(String[] args) throws Exception {
        SimulationArguments.INSTANCE.proceedArguments(args);
        Loader.INSTANCE.load(SimulationArguments.INSTANCE.getConfigPath());
        PlatformConfiguration platformConfiguration = Loader.INSTANCE.getPlatformConfiguration();
        System.out.println("Platform loaded.");
    }
    //endregion


    //region Private Methods

    //endregion

}
