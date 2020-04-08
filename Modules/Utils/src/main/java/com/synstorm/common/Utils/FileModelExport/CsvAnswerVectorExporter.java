package com.synstorm.common.Utils.FileModelExport;

import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ModelExport.IModelDataExporter;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;
import com.synstorm.common.Utils.SimulationEvents.TeacherEvents.AnswerGetEvent;

/**
 * Class for exporting answer vectors data from individual training into csv format.
 * Created by dvbozhko on 23/06/16.
 */
public class CsvAnswerVectorExporter extends CsvFileExporter implements IModelDataExporter {
    //region Fields
    //endregion

    //region Constructors
    public CsvAnswerVectorExporter(String prefix) {
        super(".Answers." + prefix + ".csv");
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public void writeHeader(String header) {
        stringBuilder.append("Label,")
                .append(header)
                .append(",AnswerLength,Tick\n");
    }

    @Override
    public void write(ISimulationEvent event) {
        if (canExport(event)) {
            update("\n");
            AnswerGetEvent answerGetEvent = (AnswerGetEvent) event;
            stringBuilder.append(answerGetEvent.getLabel()).append(",");
            answerGetEvent.getVector().values().stream().forEach(val -> stringBuilder.append(val).append(","));
            stringBuilder.append(answerGetEvent.getLength()).append(",").append(answerGetEvent.getTick()).append("\n");
        }
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    @Override
    protected void initializeAllowedEvents() {
        addAllowedEvent(SimulationEvents.AnswerGetEvent);
    }
    //endregion

    //region Private Methods
    //endregion
}
