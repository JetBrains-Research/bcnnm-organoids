package com.synstorm.common.Utils.TrainingDataProvider;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by dvbozhko on 11/9/15.
 */
@Model_v0
@NonProductionLegacy
public class DigitalImageSample implements ISample {
    //region Fields
    private final List<Double> data;
    private final short label;
    private int classIndex;
    //endregion

    //region Constructors
    public DigitalImageSample(String sample) {
        String[] splitted = sample.split(",");
        data = new ArrayList<>(splitted.length - 1);
        label = Short.valueOf(splitted[0]);

        for (int i = 1; i < splitted.length; i++)
            data.add(Double.valueOf(splitted[i]));
    }
    //endregion

    //region Public Methods
    @Override
    public List<Double> getData() {
        return data;
    }

    public short getLabel() {
        return label;
    }

    public int getClassIndex() {
        return classIndex;
    }

    public void setClassIndex(int classIndex) {
        this.classIndex = classIndex;
    }
    //endregion

    //region Private Methods
    //endregion
}