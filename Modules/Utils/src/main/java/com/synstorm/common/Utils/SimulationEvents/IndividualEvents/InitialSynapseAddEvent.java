package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ObjectIds.IObjectId;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

/**
 * Class for adding initial synapse before modeling.
 * Created by dvbozhko on 23/06/16.
 */
@Model_v0
@ProductionLegacy
public class InitialSynapseAddEvent extends BaseSynapseAddEvent implements ISimulationEvent {
    //region Fields
    private int order;
    //endregion

    //region Constructors
    public InitialSynapseAddEvent(int order, IObjectId id, IObjectId idConnect) {
        super(id, idConnect);
        this.order = order;
    }
    //endregion

    //region Getters and Setters
    public int getOrder() {
        return order;
    }
    //endregion

    //region Public Methods
    @Override
    public SimulationEvents getEventMethod() {
        return SimulationEvents.InitialSynapseAddEvent;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
