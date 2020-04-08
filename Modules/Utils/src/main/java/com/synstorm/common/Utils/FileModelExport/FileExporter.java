package com.synstorm.common.Utils.FileModelExport;

import com.synstorm.common.Utils.ModelExport.IModelDataExporter;
import com.synstorm.common.Utils.ModelExport.NonEmptyExporter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Base abstract class for all derivative exporters which exports data into
 * files with custom structures.
 * Created by dvbozhko on 23/06/16.
 */
public abstract class FileExporter extends NonEmptyExporter implements IModelDataExporter {
    //region Fields
    protected BufferedWriter bufferedWriter;
    protected StringBuilder stringBuilder;
    protected String format;
    //endregion

    //region Constructors
    protected FileExporter(String format) {
        super();
        stringBuilder = new StringBuilder();
        this.format = format;
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public void open(String modelId) {
        String fileName = modelId + format;
        File f = new File(fileName);
        f.getParentFile().mkdirs();
        try {
            boolean newFile = false;
            if (!f.exists())
                newFile = f.createNewFile();

            if (newFile) {
                FileWriter fw = new FileWriter(f.getAbsoluteFile());
                bufferedWriter = new BufferedWriter(fw);
            } else
                throw new IllegalStateException("Cannot create file for FileExporter");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        writeToFile();
        closeWriter();
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    protected void deleteLastSeparator(StringBuilder sb, String separator) {
        int lastSeparator = sb.lastIndexOf(separator);
        if (lastSeparator >= 0)
            sb.deleteCharAt(lastSeparator);
    }

    protected void update(String separator) {
        if (bufferSizeReached()) {
            deleteLastSeparator(stringBuilder, separator);
            writeToFile();
            cleanStringBuilder();
            stringBuilder.append(separator);
        }
    }
    //endregion

    //region Private Methods
    private boolean bufferSizeReached() {
        return stringBuilder.length() >= 1000;
    }

    private void cleanStringBuilder() {
        stringBuilder.delete(0, stringBuilder.length());
    }

    private void writeToFile() {
        try {
            bufferedWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeWriter() {
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //endregion
}
