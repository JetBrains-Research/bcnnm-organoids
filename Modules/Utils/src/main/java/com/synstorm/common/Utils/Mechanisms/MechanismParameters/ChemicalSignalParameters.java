package com.synstorm.common.Utils.Mechanisms.MechanismParameters;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathwayParameters;

@Model_v1
public class ChemicalSignalParameters implements ISignalingPathwayParameters {
    //region Fields
    private final int chemicalSignal;
    private final int maxRadius;
    //endregion

    //region Constructors
    public ChemicalSignalParameters(int chemSignal, int radius) {
        chemicalSignal = chemSignal;
        maxRadius = radius;
    }
    //endregion

    //region Getters and Setters
    public int getChemicalSignal() {
        return chemicalSignal;
    }

    public int getMaxRadius() {
        return maxRadius;
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
