package com.synstorm.SimulationModel.CellFunctionalComponents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;

/**
 * Created by dvbozhko on 2/10/16.
 */

@Model_v0
@ProductionLegacy
public interface IDendriticSpineListener {
    void updateActiveReceptorCount(int delta);
}
