package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ObjectIds.IObjectId;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

/**
 * Class for adding synapse during modeling.
 * Created by dvbozhko on 15/06/16.
 */
@Model_v0
@ProductionLegacy
public class SynapseAddEvent extends BaseSynapseAddEvent implements ISimulationEvent {
    //region Fields
    private long tick;
    //endregion

    //region Constructors
    public SynapseAddEvent(long tick, IObjectId id, IObjectId idConnect) {
        super(id, idConnect);
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
        return SimulationEvents.SynapseAddEvent;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
