package com.synstorm.common.Utils.Randomizer;

import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

/**
 * Created by human-research on 25/07/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ModelLoader.class})

public class RandomizerTestGaussian0 {
    private GaussianRandom rnd;
    private final byte[] seed = new byte[]{1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6};

    @Before
    @PrepareForTest({ModelLoader.class})
    public void setUp() {
        PowerMockito.mockStatic(ModelLoader.class);
        PowerMockito.when(ModelLoader.getSpaceAggressionInit()).thenReturn(1.0);
        rnd = new GaussianRandom(seed, ModelLoader.getSpaceAggressionInit());
    }

    @PrepareForTest({ModelLoader.class})
    @Test
    public void getRandomsGaussian0() throws IOException {
        int testIterations = 10000000;
        double[] gaussians0 = new double[testIterations];
        for (int i = 0; i < testIterations; i++)
            gaussians0[i] = rnd.nextGaussianMean0();

        ResultsWriter rw0 = new ResultsWriter(gaussians0);
        rw0.writeResults("RandomizerResultsGaussian0.csv");
    }
}
