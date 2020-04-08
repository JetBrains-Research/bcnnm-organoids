package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ObjectIds.IObjectId;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

/**
 * Class for growing initial axon event before modeling.
 * Created by dvbozhko on 23/06/16.
 */
@Model_v0
@NonProductionLegacy
public class InitialAxonGrowEvent extends BaseAxonGrowEvent implements ISimulationEvent {
    //region Fields
    private int order;
    //endregion

    //region Constructors
    public InitialAxonGrowEvent(int order, IObjectId id, ICoordinate currentCoordinate, ICoordinate coordinateToGrow) {
        super(id, currentCoordinate, coordinateToGrow);
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
        return SimulationEvents.InitialAxonGrowEvent;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
