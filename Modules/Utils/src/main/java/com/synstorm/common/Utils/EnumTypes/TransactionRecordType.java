package com.synstorm.common.Utils.EnumTypes;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

/**
 * Created by Dmitry.Bozhko on 9/3/2014.
 */

@Model_v0
@NonProductionLegacy

public enum TransactionRecordType {
    Insert,
    Delete,
    NotChanged
}
