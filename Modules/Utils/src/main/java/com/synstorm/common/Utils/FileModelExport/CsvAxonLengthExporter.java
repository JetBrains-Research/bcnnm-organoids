package com.synstorm.common.Utils.FileModelExport;

/**
 * Created by human-research on 2018-11-30.
 */
public class CsvAxonLengthExporter extends CsvFileExporter {

    //region Fields

    //endregion


    //region Constructors

    public CsvAxonLengthExporter() {
        super(".AxonLength.csv");
    }

    //endregion


    //region Getters and Setters

    //endregion


    //region Public Methods
    public void exportObject(int id, int length) {
        update("\n");
        stringBuilder
                .append(id).append(",")
                .append(length).append("\n");
    }

    @Override
    protected void initializeAllowedEvents() {
    }

    @Override
    public void writeHeader(String header) {
        stringBuilder.append("NeuronId,")
                .append("AxonLength\n");
    }
    //endregion


    //region Private Methods

    //endregion

}
