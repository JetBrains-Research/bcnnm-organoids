package com.synstorm.common.Utils.FileModelExport;

import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ModelExport.IModelDataExporter;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;
import com.synstorm.common.Utils.SimulationEvents.TeacherEvents.AnswerGetEvent;

/**
 * Class for exporting answer data from individual training into json format.
 * Created by dvbozhko on 23/06/16.
 */
public class JsonAnswersExporter extends FileExporter implements IModelDataExporter {
    //region Fields
    //endregion

    //region Constructors
    public JsonAnswersExporter(String prefix) {
        super(".Answers." + prefix + ".json");
        stringBuilder.append("{\n\"activity\": [");
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public void write(ISimulationEvent event) {
        if (canExport(event)) {
            update(",");
            AnswerGetEvent answerGetEvent = (AnswerGetEvent) event;
            stringBuilder
                    .append("\n")
                    .append(answerGetEvent.getJsonAnswer())
                    .append(",");
        }
    }

    @Override
    public void close() {
        deleteLastSeparator(stringBuilder, ",");
        stringBuilder.append("\n]\n}");
        super.close();
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
