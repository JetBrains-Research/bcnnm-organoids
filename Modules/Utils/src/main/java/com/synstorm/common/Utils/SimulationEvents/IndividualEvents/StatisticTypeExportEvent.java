package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.EnumTypes.StatisticType;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

/**
 * Class for exporting statistic name for logging.
 * Created by dvbozhko on 22/06/16.
 */
@Model_v0
@ProductionLegacy
public class StatisticTypeExportEvent implements ISimulationEvent {
    //region Fields
    private int id;
    private String name;
    //endregion

    //region Constructors
    public StatisticTypeExportEvent(StatisticType type) {
        this.id = type.getId();
        this.name = type.name();
    }
    //endregion

    //region Getters and Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    //endregion

    //region Public Methods
    @Override
    public SimulationEvents getEventMethod() {
        return SimulationEvents.StatisticTypeExportEvent;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
