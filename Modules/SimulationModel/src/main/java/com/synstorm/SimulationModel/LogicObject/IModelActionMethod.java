package com.synstorm.SimulationModel.LogicObject;

import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.IMethodResponse;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

/**
 * Interface for executing action on signaling pathway
 * Created by dvbozhko on 06/06/16.
 */

@Model_v0
@NonProductionLegacy
@FunctionalInterface
public interface IModelActionMethod {
    IMethodResponse execute(IActionParameters params);
}