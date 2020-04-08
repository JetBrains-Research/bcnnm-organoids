package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ObjectIds.IObjectId;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

/**
 * Class for adding new object event before modeling.
 * Created by dvbozhko on 23/06/16.
 */
@Model_v0
@ProductionLegacy
public class InitialObjectAddEvent extends BaseObjectAddEvent implements ISimulationEvent {
    //region Fields
    //endregion

    //region Constructors
    public InitialObjectAddEvent(IObjectId id, IObjectId parentId, String type, ICoordinate coordinate) {
        super(id, parentId, type, coordinate);
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public SimulationEvents getEventMethod() {
        return SimulationEvents.InitialObjectAddEvent;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
