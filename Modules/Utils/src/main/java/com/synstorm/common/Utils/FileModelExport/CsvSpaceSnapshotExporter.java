package com.synstorm.common.Utils.FileModelExport;

/**
 * Created by human-research on 2018-11-30.
 */
public class CsvSpaceSnapshotExporter extends CsvFileExporter {

    //region Fields

    //endregion


    //region Constructors

    public CsvSpaceSnapshotExporter() {
        super(".SpaceSnapshot.csv");
    }

    //endregion


    //region Getters and Setters

    //endregion


    //region Public Methods
    public void exportObject(int id, String type, int[] coordinate) {
        update("\n");
        stringBuilder
                .append(id).append(";")
                .append(type).append(";")
                .append(coordinate[0]).append(",")
                .append(coordinate[1]).append(",")
                .append(coordinate[2]).append("\n");
    }

    @Override
    protected void initializeAllowedEvents() {
    }

    @Override
    public void writeHeader(String header) {
        stringBuilder.append("id;")
                .append("ObjectType;")
                .append("x,")
                .append("y,")
                .append("z\n");
    }
    //endregion


    //region Private Methods

    //endregion

}
