package com.synstorm.common.Utils.SimulationEvents.CommonEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

import java.util.List;

/**
 * Created by dvbozhko on 13/03/2017.
 */
@Model_v1
public class ActionToGenesMapEvent implements ISimulationEvent {
    //region Fields
    private final String geneName;
    private final List<Integer> actions;
    //endregion

    //region Constructors
    public ActionToGenesMapEvent(String geneName, List<Integer> actions) {
        this.geneName = geneName;
        this.actions = actions;
    }
    //endregion

    //region Getters and Setters
    public String getGeneName() {
        return geneName;
    }

    public List<Integer> getActions() {
        return actions;
    }
    //endregion

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    @Override
    public SimulationEvents getEventMethod() {
        return SimulationEvents.ActionToGenesMapEvent;
    }
    //endregion

    //region Private Methods
    //endregion
}
