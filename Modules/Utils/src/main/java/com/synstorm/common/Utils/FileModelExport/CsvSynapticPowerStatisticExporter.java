package com.synstorm.common.Utils.FileModelExport;

import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ModelExport.IModelDataExporter;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;
import com.synstorm.common.Utils.SimulationEvents.IndividualEvents.SynapticPowerChangedEvent;

/**
 * Class for exporting synaptic power of simulation synapses from individual into csv format.
 * Created by dvbozhko on 06/12/2016.
 */
public class CsvSynapticPowerStatisticExporter extends CsvFileExporter implements IModelDataExporter {
    //region Fields
    //endregion

    //region Constructors
    public CsvSynapticPowerStatisticExporter() {
        super("SynapticPower.csv");
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public void writeHeader(String header) {
        stringBuilder.append("Tick,PostsynapticNeuron,PresynapticNeuron,Power\n");
    }

    @Override
    public void write(ISimulationEvent event) {
        if (canExport(event)) {
            update("\n");
            SynapticPowerChangedEvent synapticPowerChangedEvent = (SynapticPowerChangedEvent) event;
            stringBuilder
                    .append(synapticPowerChangedEvent.getTick())
                    .append(",")
                    .append(synapticPowerChangedEvent.getPostId())
                    .append(",")
                    .append(synapticPowerChangedEvent.getPreId())
                    .append(",")
                    .append(synapticPowerChangedEvent.getSynapticPower());
            stringBuilder.append("\n");
        }
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    @Override
    protected void initializeAllowedEvents() {
        addAllowedEvent(SimulationEvents.SynapticPowerChangedEvent);
    }
    //endregion

    //region Private Methods
    //endregion
}
