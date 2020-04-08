package com.synstorm.common.Utils.ConfigLoader;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.ConfigWorker.ConfigReader;
import com.synstorm.common.Utils.ConfigurationStrings.XmlQueryStrings;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Dmitry.Bozhko on 3/25/2015.
 */
@Model_v0
@NonProductionLegacy
public class BaseLoader {
    //region Fields
    protected ConfigReader configReader;
    protected NumberFormat numberFormat = new DecimalFormat(XmlQueryStrings.TECH_DECI_FORMAT);
    //endregion

    //region Constructors

    /**
     * Default constructor
     */
    public BaseLoader() {
        configReader = new ConfigReader();
    }
    //endregion

    //region Getters and Setters
    public ConfigReader getConfigReader() {
        return configReader;
    }

    //endregion

    //region Public Methods
    protected void initialize(String configName, String templateName) {
        configReader.loadTemplate(templateName);
        configReader.readConfig(configName);
    }

    //endregion

    //region Private Methods
    //endregion
}
