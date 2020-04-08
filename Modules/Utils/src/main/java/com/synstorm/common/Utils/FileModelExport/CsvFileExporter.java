package com.synstorm.common.Utils.FileModelExport;

import com.synstorm.common.Utils.ModelExport.IModelDataExporter;

/**
 * Base abstract class for all derivative exporters which exports data into
 * files with csv structure.
 * Created by dvbozhko on 19/01/2017.
 */
public abstract class CsvFileExporter extends FileExporter implements IModelDataExporter {
    //region Fields
    //endregion

    //region Constructors
    public CsvFileExporter(String format) {
        super(format);
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public void close() {
        deleteLastSeparator(stringBuilder, "\n");
        super.close();
    }

    public abstract void writeHeader(String header);
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
