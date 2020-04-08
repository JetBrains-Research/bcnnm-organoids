package com.synstorm.common.Utils.ModelExport;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

/**
 * Empty exporter class. Uses when export mode is set do <Disabled>
 * Created by dvbozhko on 16/06/16.
 */
@Model_v0
@ProductionLegacy
public class EmptyExporter implements IModelDataExporter {
    //region Fields
    //endregion

    //region Constructors
    public EmptyExporter() {

    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public void open(String modelId) {

    }

    @Override
    public void write(ISimulationEvent event) {

    }

    @Override
    public void writeHeader(String header) {

    }

    @Override
    public void close() {

    }

    public boolean canExport(String eventName) {
        return false;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
