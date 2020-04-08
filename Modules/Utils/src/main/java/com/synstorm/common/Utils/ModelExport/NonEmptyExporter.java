package com.synstorm.common.Utils.ModelExport;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * Base abstract class for all derivative exporters which can exports data.
 * Created by dvbozhko on 23/06/16.
 */
@Model_v0
@ProductionLegacy
public abstract class NonEmptyExporter extends EmptyExporter implements IModelDataExporter {
    //region Fields
    protected Set<SimulationEvents> allowedEvents;
    //endregion

    //region Constructors
    public NonEmptyExporter() {
        allowedEvents = new HashSet<>();
        initializeAllowedEvents();
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    public boolean canExportEvent(SimulationEvents eventType) {
        return allowedEvents.contains(eventType);
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    protected boolean canExport(ISimulationEvent event) {
        return canExportEvent(event.getEventMethod());
    }

    protected void addAllowedEvent(SimulationEvents eventName) {
        allowedEvents.add(eventName);
    }

    protected abstract void initializeAllowedEvents();
    //endregion

    //region Private Methods
    //endregion
}
