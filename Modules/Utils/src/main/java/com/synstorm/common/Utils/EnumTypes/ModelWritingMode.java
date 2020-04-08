package com.synstorm.common.Utils.EnumTypes;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

/**
 * Created by dvbozhko on 12/11/15.
 */

@Model_v0
@NonProductionLegacy

public enum ModelWritingMode {
    Empty,
    SQLFull,
    SQLCheckpoints,
    SQLStatistic,
    FileAnswers,
    FileStatistic
}
