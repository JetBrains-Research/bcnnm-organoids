package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

/**
 * Created by dvbozhko on 16/06/16.
 */
@Model_v1
@ProductionLegacy
public class TickEvent implements ISimulationEvent {
    //region Fields
    private long tick;
    //endregion

    //region Constructors
    public TickEvent(long tick) {
        this.tick = tick;
    }
    //endregion

    //region Getters and Setters
    public long getTick() {
        return tick;
    }
    //endregion

    //region Public Methods
    @Override
    public SimulationEvents getEventMethod() {
        return SimulationEvents.TickEvent;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
