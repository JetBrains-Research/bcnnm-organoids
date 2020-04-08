package com.synstorm.common.Utils.FileModelExport;

import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ModelExport.IModelDataExporter;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;
import com.synstorm.common.Utils.SimulationEvents.IndividualEvents.StatisticArrayFormEvent;
import com.synstorm.common.Utils.SimulationEvents.IndividualEvents.StatisticMapFormEvent;
import com.synstorm.common.Utils.SimulationEvents.IndividualEvents.StatisticSingleFormEvent;

import java.util.Map;

/**
 * Class for exporting final statistic data from individual modeling into json format.
 * Created by dvbozhko on 23/06/16.
 */
public class JsonStatisticsExporter extends FileExporter implements IModelDataExporter {
    //region Fields
    //endregion

    //region Constructors
    public JsonStatisticsExporter() {
        super(".Statistic.json");
        stringBuilder.append("{");
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public void write(ISimulationEvent event) {
        if (canExport(event)) {
            if (event instanceof StatisticSingleFormEvent)
                writeStat((StatisticSingleFormEvent) event);
            else if (event instanceof StatisticArrayFormEvent)
                writeStat((StatisticArrayFormEvent) event);
            else if (event instanceof StatisticMapFormEvent)
                writeStat((StatisticMapFormEvent) event);
        }
    }

    @Override
    public void close() {
        deleteLastSeparator(stringBuilder, ",");
        stringBuilder.append("\n}");
        super.close();
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    @Override
    protected void initializeAllowedEvents() {
        addAllowedEvent(SimulationEvents.StatisticSingleFormEvent);
        addAllowedEvent(SimulationEvents.StatisticArrayFormEvent);
        addAllowedEvent(SimulationEvents.StatisticMapFormEvent);
    }
    //endregion

    //region Private Methods
    private void writeStat(StatisticSingleFormEvent event) {
        update(",");
        stringBuilder
                .append("\n\"")
                .append(event.getType().toString())
                .append("\": ")
                .append(event.getValue())
                .append(",");
    }

    private void writeStat(StatisticArrayFormEvent event) {
        update(",");
        stringBuilder
                .append("\n\"")
                .append(event.getType().toString())
                .append("\": [");
        event.getValue().stream()
                .forEach(item -> stringBuilder
                        .append(item)
                        .append(","));
        if (event.getValue().size() > 0)
            deleteLastSeparator(stringBuilder, ",");
        stringBuilder.append("],");
    }

    private void writeStat(StatisticMapFormEvent event) {
        update(",");
        stringBuilder
                .append("\n\"")
                .append(event.getType().toString())
                .append("\": [");
        ((Map<Object, Object>) event.getValue()).entrySet().stream()
                .forEach(item -> stringBuilder
                        .append("{\"")
                        .append(item.getKey())
                        .append("\":")
                        .append(item.getValue())
                        .append("},"));
        if (event.getValue().size() > 0)
            deleteLastSeparator(stringBuilder, ",");
        stringBuilder.append("],");
    }
    //endregion
}
