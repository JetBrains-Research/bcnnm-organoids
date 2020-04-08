package com.synstorm.common.Utils.EnumTypes;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

/**
 * Created by human-research on 11/01/16.
 */

@Model_v0
@NonProductionLegacy
// -> FCC

public enum ScoreEnum {
    FinalCellCount, // quantity of alive cells when model is done. More is better.
    FinalSynapseCount, // quantity of synapses when model is done. More is better.
    FinalSynapseCellRatio, // finalSynapseCount / finalCellCount. More is better. in [0; finalCellCount - 1]
    MaxCellCount, // max quantity of cell when modelling. More is better.
    MaxSynapseCount, // max quantity of synapses when modelling. More is better.
    MaxSynapseCellRatio, // maxSynapseCount / maxCellCount. More is better. in [0; maxCellCount - 1]
    FinalMaxCellRatio, // finalCellCount / maxCellCount. More is better. in [0;1]
    FinalMaxSynapseRatio, // finalSynapseCount / maxSynapseCount. More is better. in [0;1]
    Time, // time of modelling. Less is better
    LQTrain, // learning quality (LQ) on Train dataset in [0;1]
    LQPreTrain, // learning quality (LQ) on PreTrain dataset in [0;1]
    LQControl, // learning quality (LQ) on Control dataset in [0;1]
    RecombinationCounter; // how much recombinations occured for individual. Less is better

    private TeacherMode matchingMode;

    static {
        LQTrain.matchingMode = TeacherMode.Train;
        LQPreTrain.matchingMode = TeacherMode.PreTrain;
        LQControl.matchingMode = TeacherMode.Control;
    }

    public TeacherMode getMatchingMode() {
        return matchingMode;
    }
}