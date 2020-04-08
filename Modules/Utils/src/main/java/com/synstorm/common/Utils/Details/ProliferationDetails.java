package com.synstorm.common.Utils.Details;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

/**
 * Created by Dmitry.Bozhko on 7/8/2015.
 */

@Model_v0
@NonProductionLegacy

public class ProliferationDetails implements IDetails {
    //region Fields
    private String factorName;
    private String cellType;
    //endregion

    //region Constructors
    public ProliferationDetails(String factorName, String cellType) {
        this.factorName = factorName;
        this.cellType = cellType;
    }
    //endregion

    //region Getters and Setters
    public String getFactorName() {
        return factorName;
    }

    public String getCellType() {
        return cellType;
    }
    //endregion

    //region Public Methods
    //endregion

    //region Private Methods
    //endregion
}
