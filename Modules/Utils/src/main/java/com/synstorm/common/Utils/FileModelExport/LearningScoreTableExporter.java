package com.synstorm.common.Utils.FileModelExport;

import com.synstorm.common.Utils.ConfigurationStrings.XmlQueryStrings;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ModelExport.IModelDataExporter;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;
import com.synstorm.common.Utils.SimulationEvents.ScoreTableEvents.LearningScoreTableFormRowEvent;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Class for exporting score table in evolution mode for individual with learning.
 * Created by dvbozhko on 10/02/2017.
 */
public class LearningScoreTableExporter extends CsvFileExporter implements IModelDataExporter {
    //region Fields
    private NumberFormat numberFormat = new DecimalFormat(XmlQueryStrings.TECH_DECI_FORMAT);
    //endregion

    //region Constructors
    public LearningScoreTableExporter() {
        super(".ScoreTable.csv");
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public void writeHeader(String header) {
        stringBuilder.append("IndividualId,Parent1Id,Parent2Id,IndividualSeed,TeacherSeed,")
                .append(header)
                .append("Rating\n");
    }

    @Override
    public void write(ISimulationEvent event) {
        if (canExport(event)) {
            update("\n");
            LearningScoreTableFormRowEvent scoreTableFormRowEvent = (LearningScoreTableFormRowEvent) event;
            String parent1String = scoreTableFormRowEvent.getFirstParentId() == null ? "" : scoreTableFormRowEvent.getFirstParentId().toString();
            String parent2String = scoreTableFormRowEvent.getSecondParentId() == null ? "" : scoreTableFormRowEvent.getSecondParentId().toString();
            stringBuilder.append("\"").append(scoreTableFormRowEvent.getIndividualId().toString()).append("\",")
                    .append("\"").append(parent1String).append("\",")
                    .append("\"").append(parent2String).append("\",")
                    .append(new String(scoreTableFormRowEvent.getIndividualSeed())).append(",")
                    .append(new String(scoreTableFormRowEvent.getTeacherSeed())).append(",");

            scoreTableFormRowEvent.getScores().values().stream()
                    .forEach(score -> stringBuilder
                            .append(numberFormat.format(score))
                            .append(","));
            scoreTableFormRowEvent.getMedianLQValues().stream()
                    .forEach(medianLQ -> stringBuilder
                            .append(numberFormat.format(medianLQ))
                            .append(","));
            scoreTableFormRowEvent.getGeneDurations().stream()
                    .forEach(geneDuration -> stringBuilder
                            .append(numberFormat.format(geneDuration))
                            .append(","));
            stringBuilder.append(numberFormat.format(scoreTableFormRowEvent.getFinalRating())).append("\n");
        }
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    @Override
    protected void initializeAllowedEvents() {
        addAllowedEvent(SimulationEvents.LearningScoreTableFormRowEvent);
    }
    //endregion

    //region Private Methods
    //endregion
}
