package com.synstorm.common.Utils.Randomizer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by human-research on 06/06/16.
 */
public class ResultsWriter {
    double[] results;


    public ResultsWriter(double[] results) {
        this.results = results;
    }

    public void writeResults(String filename) throws IOException {
        NumberFormat numberFormat = new DecimalFormat("0.00000000000000000000");
        FileWriter fw = new FileWriter(filename);
        PrintWriter pw = new PrintWriter(fw);
        for (int j = 0; j < results.length; j++) {
            pw.print(numberFormat.format(results[j]));
            if (j != results.length - 1)
                pw.print(",");
        }
        pw.close();
        fw.close();
    }
    //region Fields

    //endregion


    //region Constructors

    //endregion


    //region Getters and Setters

    //endregion


    //region Public Methods

    //endregion


    //region Private Methods

    //endregion

}
