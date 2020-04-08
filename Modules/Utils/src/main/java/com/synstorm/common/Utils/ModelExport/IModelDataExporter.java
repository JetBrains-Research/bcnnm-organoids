package com.synstorm.common.Utils.ModelExport;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

/**
 * Created by dvbozhko on 16/06/16.
 */
@Model_v0
@ProductionLegacy
public interface IModelDataExporter {
    void open(String modelId);
    void write(ISimulationEvent event);
    void writeHeader(String header);
    void close();
    boolean canExport(String eventName);
}
