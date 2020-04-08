package com.synstorm.common.Utils.TraceMessageWriter;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Dmitry.Bozhko on 12/10/2014.
 */
@Model_v0
@ProductionLegacy
public class PriorityTraceWriter {
    //region Fields
    private static int maxAllowedLevel = 0;
    private static BufferedWriter bufferedWriter;
    private static StringBuilder stringBuilder;

    //endregion

    //region Constructors
    //endregion

    //region Getters and Setters
    public static void setLevel(int maximumAllowedLevel) {
        maxAllowedLevel = maximumAllowedLevel;
    }

    public static void initFile(String name) {
        long tStart = System.currentTimeMillis();
        DateFormat dateFormatter = new SimpleDateFormat("dd_MM_YYYY_HH_mm");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date startTime = new Date(tStart);
        String fileName = name + "_" + dateFormatter.format(startTime) + ".log";
        File f = new File(fileName);
        stringBuilder = new StringBuilder();
        try {
            if (!f.exists())
                f.createNewFile();
            FileWriter fw = new FileWriter(f.getAbsoluteFile());
            bufferedWriter = new BufferedWriter(fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeFile() {
        try {
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region Public Methods
    public static void println(@Nullable Object message, int level) {
        if (level <= maxAllowedLevel)
            System.out.println(message);
    }

    public static void println(String message, int level) {
        if (level <= maxAllowedLevel)
            System.out.println(message);
    }

    public static void printf(int level, String format, Object... args) {
        if (level <= maxAllowedLevel)
            System.out.printf(format, args);
    }

    public static void print(String message, int level) {
        if (level <= maxAllowedLevel)
            System.out.print(message);
    }

    public static void printlnToFile(String message, int level) {
        if (level <= maxAllowedLevel)
            stringBuilder.append(message).append("\n");
    }
    //endregion

    //region Private Methods
    //endregion
}
