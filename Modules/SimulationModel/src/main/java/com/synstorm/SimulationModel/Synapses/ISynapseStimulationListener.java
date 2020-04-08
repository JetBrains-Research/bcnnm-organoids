package com.synstorm.SimulationModel.Synapses;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;

/**
 * Created by dvbozhko on 2/15/16.
 */

@Model_v0
@ProductionLegacy
public interface ISynapseStimulationListener {
    void updateActiveReceptors();
}
