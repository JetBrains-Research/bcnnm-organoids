package com.synstorm.common.Utils.ConfigurationStrings;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;

import java.util.UUID;

/**
 * Created by human-research on 14/09/16.
 */

@Model_v0
@ProductionLegacy
public class ConstantValues {
    public static final String UUID_DEFAULT_SEED = "0000000000000000";
    public static final UUID DEFAULT_START_UUID = new UUID(00000000L, 00000000L);
    public static final int INT_DURATION_LIMIT = 1000;

    public static final String AWS_S3_BUCKET = "s3://papersexperiments2019/";

}
