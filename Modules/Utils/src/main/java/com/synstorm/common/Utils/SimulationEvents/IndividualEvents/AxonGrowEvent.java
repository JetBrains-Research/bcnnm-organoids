package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ObjectIds.IObjectId;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

/**
 * Class for growing axon event during modeling.
 * Created by dvbozhko on 15/06/16.
 */
@Model_v1
public class AxonGrowEvent extends BaseAxonGrowEvent implements ISimulationEvent {
    //region Fields
    private long tick;
    //endregion

    //region Constructors
    public AxonGrowEvent(long tick, IObjectId id, ICoordinate currentCoordinate, ICoordinate coordinateToGrow) {
        super(id, currentCoordinate, coordinateToGrow);
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
        return SimulationEvents.AxonGrowEvent;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
