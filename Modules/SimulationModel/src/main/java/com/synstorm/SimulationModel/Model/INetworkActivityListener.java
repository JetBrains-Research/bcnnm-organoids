package com.synstorm.SimulationModel.Model;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;

/**
 * Listener interface for updating active neuron and synapse count.
 * Created by dvbozhko on 10/03/2017.
 */

@Model_v0
@ProductionLegacy
public interface INetworkActivityListener {
    void incrementActiveNeuronCount();
    void decrementActiveNeuronCount();
    void incrementActiveSynapseCount();
    void decrementActiveSynapseCount();
}
