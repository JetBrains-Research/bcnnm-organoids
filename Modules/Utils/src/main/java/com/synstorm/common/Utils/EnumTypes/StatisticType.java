package com.synstorm.common.Utils.EnumTypes;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

/**
 * Created by Dmitry.Bozhko on 9/25/2015.
 */

@Model_v0
@NonProductionLegacy

public enum StatisticType {
    CellType(0),
    FactorType(1),
    NtType(2),
    Capacity(3),
    MinTick(4),
    MaxTick(5),
    MinCoordinate(6),
    MaxCoordinate(7),
    FirstSpikeTickNum(8),
    LastSpikeTickNum(9),
    //IndividualsCountInProgeny(10),
    SpaceAggression(11),
    SeedValue(12),
//    TopScoreValueFstProgeny(13),
//    TopScoreValueLstProgeny(14),
//    TopFinalCellCountFstProgeny(15),
//    TopFinalCellCountLstProgeny(16),
//    TopFinalSynapseCountFstProgeny(17),
//    TopFinalSynapseCountLstProgeny(18),
//    TopFinalSynapseCellRatioFstProgeny(19),
//    TopFinalSynapseCellRatioLstProgeny(20),
//    TopMaxCellCountFstProgeny(21),
//    TopMaxCellCountLstProgeny(22),
//    TopMaxSynapseCountFstProgeny(23),
//    TopMaxSynapseCountLstProgeny(24),
//    TopMaxSynapseCellRatioFstProgeny(25),
//    TopMaxSynapseCellRatioLstProgeny(26),
//    TopFinalMaxCellRatioFstProgeny(27),
//    TopFinalMaxCellRatioLstProgeny(28),
//    TopFinalMaxSynapseRatioFstProgeny(29),
//    TopFinalMaxSynapseRatioLstProgeny(30),
//    TopTimeFstProgeny(31),
//    TopTimeLstProgeny(32),
//    TopTrainAccuracyFstProgeny(33),
//    TopTrainAccuracyLstProgeny(34),
//    GenerationCount(35),
//    TopIndividualsPercent(36),
//    TopIndividualsRecombinationPercent(37),
    TrainingQuality(38),
    ExperimentName(39),
    TeacherSeedValue(40),
    ModelingTime(41),
    ModelId(42),
    TotalCachedCoordinates(43),
    TotalGetsFromCachedCoordinates(44),
    SignificantTicksCount(45),
    AnswerLengths(46),
    ModelingStartTime(47),
    IndividualDuplicatesCount(48),

    //add
    FinalCellCount(49),
    SynapseCount(50);

    private final int id;

    StatisticType(int i) {
        this.id = i;
    }

    public int getId() {
        return id;
    }
}
