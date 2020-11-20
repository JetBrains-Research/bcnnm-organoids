package com.synstorm.common.Utils.FileModelExport;

import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ModelExport.IModelDataExporter;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;
import com.synstorm.common.Utils.SimulationEvents.IndividualEvents.ConcentrationChangeEvent;

import java.util.Arrays;

/**
 * Class for exporting concentration changes
 * Created by vmyrov on 13/10/2018.
 */
public class ConcentrationsExporter extends CsvFileExporter implements IModelDataExporter {
    //region Constructors
    public ConcentrationsExporter() {
        super("Concentrations.csv");
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public void writeHeader(String header) {
        stringBuilder.append("Tick;Type;Factor;Coordinate;Change_value;Radius\n");
    }

    @Override
    public void write(ISimulationEvent event) {
        if (canExport(event)) {
            ConcentrationChangeEvent concentrationEvent = (ConcentrationChangeEvent)event;

            String factor = Integer.toString(concentrationEvent.getFactor());
            String coordinate = Arrays.toString(concentrationEvent.getCoord());
            String changeValue = Double.toString(concentrationEvent.getChangeValue());
            String radius = Integer.toString(concentrationEvent.getRadius());

            stringBuilder.append(concentrationEvent.getTick()).append(';')
                    .append(concentrationEvent.getType()).append(';')
                    .append(factor).append(';')
                    .append(coordinate).append(';')
                    .append(changeValue).append(';')
                    .append(radius).append('\n');
        }
    }
    //endregion

    //region Protected Methods
    @Override
    protected void initializeAllowedEvents() {
        addAllowedEvent(SimulationEvents.ConcentrationChangeEvent);
    }
}