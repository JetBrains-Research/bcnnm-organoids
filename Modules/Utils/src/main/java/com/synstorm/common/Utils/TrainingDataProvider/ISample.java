package com.synstorm.common.Utils.TrainingDataProvider;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

import java.util.List;

/**
 * Created by Dmitry.Bozhko on 10/13/2015.
 */
@Model_v0
@NonProductionLegacy
public interface ISample {
    List<Double> getData();
    short getLabel();
}
