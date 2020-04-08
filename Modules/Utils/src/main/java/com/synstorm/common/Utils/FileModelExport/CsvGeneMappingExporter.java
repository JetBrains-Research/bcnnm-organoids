package com.synstorm.common.Utils.FileModelExport;

import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ModelExport.IModelDataExporter;
import com.synstorm.common.Utils.SimulationEvents.CommonEvents.ActionToGenesMapEvent;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

/**
 * Created by dvbozhko on 13/03/2017.
 */
public class CsvGeneMappingExporter extends CsvFileExporter implements IModelDataExporter {
    //region Fields
    //endregion

    //region Constructors
    public CsvGeneMappingExporter() {
        super(".GeneMap.csv");
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public void writeHeader(String header) {
        stringBuilder.append("GeneName,")
                .append(header)
                .append("\n");
    }

    @Override
    public void write(ISimulationEvent event) {
        if (canExport(event)) {
            update("\n");
            ActionToGenesMapEvent actionToGenesMapEvent = (ActionToGenesMapEvent) event;
            stringBuilder.append(actionToGenesMapEvent.getGeneName()).append(",");
            String genesPresentation = actionToGenesMapEvent.getActions().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(",","",""));
            stringBuilder.append(genesPresentation).append("\n");
        }
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    @Override
    protected void initializeAllowedEvents() {
        addAllowedEvent(SimulationEvents.ActionToGenesMapEvent);
    }
    //endregion

    //region Private Methods
    @NotNull
    @Contract(pure = true)
    private String boolToInt(boolean val) {
        if (val)
            return "1";
        else
            return "0";
    }
    //endregion
}
