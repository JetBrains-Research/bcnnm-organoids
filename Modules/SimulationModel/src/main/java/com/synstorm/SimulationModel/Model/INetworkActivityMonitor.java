package com.synstorm.SimulationModel.Model;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;

/**
 * Interface for monitoring network activity state.
 * Created by dvbozhko on 10/03/2017.
 */

@Model_v0
@ProductionLegacy
public interface INetworkActivityMonitor {
    boolean isNetworkActive();
}
