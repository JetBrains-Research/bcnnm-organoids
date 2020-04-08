package com.synstorm.common.Utils.ConfigLoader;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.Details.IDetails;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Dmitry.Bozhko on 3/25/2015.
 */

@Model_v0
@ProductionLegacy

public interface ILoader {
    void load(String configFileName, String templateFileName);
    void save(String newConfigFilename) throws Exception;

    @Nullable
    IDetails getDetails(String key);
}
