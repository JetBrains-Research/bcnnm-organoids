package com.synstorm.common.Utils.ConfigWorker;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

/**
 * Created by Dmitry.Bozhko on 7/2/2014.
 */

@Model_v0
@NonProductionLegacy

public interface IAttribute {
    String getAttributeName();
    void setAttributeName(String attributeName);
}
