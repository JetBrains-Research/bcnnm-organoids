package com.synstorm.common.Utils.Details;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

/**
 * Created by Dmitry.Bozhko on 5/28/2015.
 */

@Model_v0
@NonProductionLegacy

public class SeedDetails implements IDetails {
    //region Fields
    private byte[] seed;
    //endregion

    //region Constructors
    public SeedDetails(String seedValue) {
        seed = seedValue.getBytes();
    }
    //endregion

    //region Getters and Setters
    public byte[] getSeed() {
        return seed;
    }
    //endregion

    //region Public Methods
    //endregion

    //region Private Methods
    //endregion
}
