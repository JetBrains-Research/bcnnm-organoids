package com.synstorm.common.Utils.Mechanisms.MechanismParameters;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathwayParameters;

@Model_v1
public class ObjectCreateParameters implements ISignalingPathwayParameters {
    //region Fields
    private final String diffType;
    //endregion

    //region Constructors
    public ObjectCreateParameters(String cellType) {
        diffType = cellType;
    }
    //endregion

    //region Getters and Setters
    public String getDiffType() {
        return diffType;
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
