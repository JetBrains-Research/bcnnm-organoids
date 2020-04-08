package com.synstorm.SimulationModel.LogicObject;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

/**
 * Interface for parameters to execution methods
 * Created by dvbozhko on 09/03/2017.
 */

@Model_v0
@NonProductionLegacy
public interface IActionParameters {
    long getStartTick();
}
