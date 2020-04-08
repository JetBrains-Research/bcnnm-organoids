package com.synstorm.common.Utils.FileModelExport;

/**
 * Base abstract class for all derivative exporters which exports data into
 * files with XML structure.
 * Created by human.research on 14/03/2019.
 */
public abstract class XmlFileExporter extends FileExporter {
    //region Fields
    //endregion

    //region Constructors
    public XmlFileExporter(String format) {
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
