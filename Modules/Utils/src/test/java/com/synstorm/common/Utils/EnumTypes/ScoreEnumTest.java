package com.synstorm.common.Utils.EnumTypes;

import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
import com.synstorm.common.Utils.EvolutionUtils.Score.Score;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertTrue;

/**
 * Created by human-research on 07/06/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ModelLoader.class})
public class ScoreEnumTest {
    Score score1;
    Score score2;

    @PrepareForTest({ModelLoader.class})
    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(ModelLoader.class);
        PowerMockito.when(ModelLoader.getScoreRuleWeight(ScoreEnum.FinalCellCount)).thenReturn(1.0);
        PowerMockito.when(ModelLoader.getScoreRuleWeight(ScoreEnum.FinalSynapseCount)).thenReturn(0.9);
        PowerMockito.when(ModelLoader.getScoreRuleWeight(ScoreEnum.FinalSynapseCellRatio)).thenReturn(0.8);
        PowerMockito.when(ModelLoader.getScoreRuleWeight(ScoreEnum.MaxCellCount)).thenReturn(0.7);
        PowerMockito.when(ModelLoader.getScoreRuleWeight(ScoreEnum.MaxSynapseCount)).thenReturn(0.6);
        PowerMockito.when(ModelLoader.getScoreRuleWeight(ScoreEnum.MaxSynapseCellRatio)).thenReturn(0.5);
        PowerMockito.when(ModelLoader.getScoreRuleWeight(ScoreEnum.FinalMaxCellRatio)).thenReturn(0.4);
        PowerMockito.when(ModelLoader.getScoreRuleWeight(ScoreEnum.FinalMaxSynapseRatio)).thenReturn(0.3);
        PowerMockito.when(ModelLoader.getScoreRuleWeight(ScoreEnum.Time)).thenReturn(0.2);
        PowerMockito.when(ModelLoader.getScoreRuleWeight(ScoreEnum.LQTrain)).thenReturn(0.1);
        PowerMockito.when(ModelLoader.getScoreRuleWeight(ScoreEnum.RecombinationCounter)).thenReturn(1.0);

        score1 = new Score(null, "");
        score1.setScoreParameter(ScoreEnum.FinalCellCount, 100.0);
        score1.setScoreParameter(ScoreEnum.FinalSynapseCount, 1000.0);
        score1.setScoreParameter(ScoreEnum.FinalSynapseCellRatio, 10.0);
        score1.setScoreParameter(ScoreEnum.MaxCellCount, 200.0);
        score1.setScoreParameter(ScoreEnum.MaxSynapseCount, 5000.0);
        score1.setScoreParameter(ScoreEnum.MaxSynapseCellRatio, 25.0);
        score1.setScoreParameter(ScoreEnum.FinalMaxCellRatio, 0.5);
        score1.setScoreParameter(ScoreEnum.FinalMaxSynapseRatio, 0.2);
        score1.setScoreParameter(ScoreEnum.Time, 555.0);
        score1.setScoreParameter(ScoreEnum.LQTrain, 0.8);
        score1.setScoreParameter(ScoreEnum.RecombinationCounter, 0);

        score2 = new Score(null, "");
        score2.setScoreParameter(ScoreEnum.FinalCellCount, 25);
        score2.setScoreParameter(ScoreEnum.FinalSynapseCount, 600.0);
        score2.setScoreParameter(ScoreEnum.FinalSynapseCellRatio, 24.0);
        score2.setScoreParameter(ScoreEnum.MaxCellCount, 50.0);
        score2.setScoreParameter(ScoreEnum.MaxSynapseCount, 2450.0);
        score2.setScoreParameter(ScoreEnum.MaxSynapseCellRatio, 49.0);
        score2.setScoreParameter(ScoreEnum.FinalMaxCellRatio, 0.5);
        score2.setScoreParameter(ScoreEnum.FinalMaxSynapseRatio, 0.02449);
        score2.setScoreParameter(ScoreEnum.Time, 442.0);
        score2.setScoreParameter(ScoreEnum.LQTrain, 0.7);
        score2.setScoreParameter(ScoreEnum.RecombinationCounter, 2);

    }

    @PrepareForTest({ModelLoader.class})
    @Test
    public void getScoreResult() throws Exception {
        double e = 0.0000001d;
        assertTrue(score1.getScoreParameter(ScoreEnum.FinalCellCount) == 100.0);
        assertTrue(score1.getScoreParameter(ScoreEnum.FinalSynapseCount) == 1000.0);
        assertTrue(score1.getScoreParameter(ScoreEnum.FinalSynapseCellRatio) == 10.0);
        assertTrue(score1.getScoreParameter(ScoreEnum.MaxCellCount) == 200);
        assertTrue(score1.getScoreParameter(ScoreEnum.MaxSynapseCount) == 5000);
        assertTrue(25.0 - score1.getScoreParameter(ScoreEnum.MaxSynapseCellRatio) < e );
        assertTrue(score1.getScoreParameter(ScoreEnum.FinalMaxCellRatio) == 0.5);
        assertTrue(score1.getScoreParameter(ScoreEnum.FinalMaxSynapseRatio) == 0.2);
        assertTrue(555.0 - score1.getScoreParameter(ScoreEnum.Time) < e);
        assertTrue(0.8 - score1.getScoreParameter(ScoreEnum.LQTrain) < e);
        assertTrue(score1.getScoreParameter(ScoreEnum.RecombinationCounter) == 0.0);


    }

}