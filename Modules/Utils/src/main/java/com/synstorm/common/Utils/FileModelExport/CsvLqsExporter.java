package com.synstorm.common.Utils.FileModelExport;

import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ModelExport.IModelDataExporter;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;
import com.synstorm.common.Utils.SimulationEvents.TeacherEvents.LQCalculatedEvent;

import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * Class for exporting LQ values calculated on several seeds during simulation using csv format.
 * Created by dvbozhko on 18/01/2017.
 */
public class CsvLqsExporter extends CsvFileExporter implements IModelDataExporter {
    //region Fields
    //endregion

    //region Constructors
    public CsvLqsExporter(String prefix) {
        super(".Lqs." + prefix + ".csv");
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public void writeHeader(String header) {
        stringBuilder.append(header).append("\n");
    }

    @Override
    public void write(ISimulationEvent event) {
        if (canExport(event)) {
            update("\n");
            LQCalculatedEvent lqCalculatedEvent = (LQCalculatedEvent) event;
            String lqArray = DoubleStream.of(lqCalculatedEvent.getLqs())
                    .mapToObj(Double::toString)
                    .collect(Collectors.joining(",","",""));
            stringBuilder.append(lqArray).append("\n");
        }
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    @Override
    protected void initializeAllowedEvents() {
        addAllowedEvent(SimulationEvents.LQCalculatedEvent);
    }
    //endregion

    //region Private Methods
    //endregion
}
