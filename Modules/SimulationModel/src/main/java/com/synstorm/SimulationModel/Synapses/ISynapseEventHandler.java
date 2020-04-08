package com.synstorm.SimulationModel.Synapses;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;

/**
 * Created by Dmitry.Bozhko on 6/19/2015.
 */

@Model_v0
@ProductionLegacy
public interface ISynapseEventHandler {
    void stimulateDendrite(Synapse synapse);
    void removeSynapseFromStimulated(Synapse synapse);
    void addSynapseToNetwork(Synapse synapse);
    void addSynapseToNetworkWithActivation(Synapse synapse);
    void removeSynapseFromNetwork(Synapse synapse);
}
