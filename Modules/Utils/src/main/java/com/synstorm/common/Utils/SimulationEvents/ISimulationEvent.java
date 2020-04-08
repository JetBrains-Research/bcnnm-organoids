package com.synstorm.common.Utils.SimulationEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;

/**
 * Common interface for events which arise during simulation.
 * Created by dvbozhko on 15/06/16.
 */
@Model_v1
public interface ISimulationEvent {
    SimulationEvents getEventMethod();
}
