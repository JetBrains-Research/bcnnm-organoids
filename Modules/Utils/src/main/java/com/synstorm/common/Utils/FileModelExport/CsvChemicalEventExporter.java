package com.synstorm.common.Utils.FileModelExport;

import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ModelExport.IModelDataExporter;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;
import com.synstorm.common.Utils.SimulationEvents.SpaceEvents.BaseSignalEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Created by human-research on 24/01/2019.
 */
public class CsvChemicalEventExporter extends CsvFileExporter implements IModelDataExporter {
    //region Fields
    private final int[] filter;
    private boolean filterEnabled;
    //endregion

    //region Constructors
    public CsvChemicalEventExporter(@NotNull String[] args) {
        super(".ChemicalEvents.csv");
        this.filterEnabled = true;
        this.filter = new int[args.length];
        initializeFilter(args);

    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public void writeHeader(String header) {
        stringBuilder.append("tick,objectId,eventType,signalId")
                .append("\n");
    }

    @Override
    public void write(ISimulationEvent event) {
        if (canExport(event)) {
            update("\n");
            BaseSignalEvent baseSignalEvent = (BaseSignalEvent) event;
            if (filterEnabled && Arrays.stream(filter).noneMatch(e -> e == baseSignalEvent.getSignalId()))
                return;

            writeEvent(baseSignalEvent);
        }
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    @Override
    protected void initializeAllowedEvents() {
        addAllowedEvent(SimulationEvents.SpreadChemicalSignal);
        addAllowedEvent(SimulationEvents.GatherChemicalSignal);
    }
    //endregion

    //region Private Methods
    private void initializeFilter(@NotNull String[] args) {
        if (args[0].equals("_")) {
            this.filterEnabled = false;
            return;
        }

        for (int i = 0; i < filter.length; i++) {
            filter[i] = Integer.parseInt(args[i]);
        }
    }

    private void writeEvent(@NotNull BaseSignalEvent baseSignalEvent) {
        stringBuilder.append(baseSignalEvent.getTick())
                .append(",")
                .append(baseSignalEvent.getObjectId())
                .append(",")
                .append(baseSignalEvent.getEventMethod())
                .append(",")
                .append(baseSignalEvent.getSignalId()).append("\n");
    }

    //endregion
}
