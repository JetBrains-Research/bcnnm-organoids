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

public class RandomizerTestGaussian1 {
    private GaussianRandom gaussianRandom;
    private final byte[] seed = new byte[]{1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6};

    @Before
    @PrepareForTest({ModelLoader.class})
    public void setUp() {
        PowerMockito.mockStatic(ModelLoader.class);
        PowerMockito.when(ModelLoader.getSpaceAggressionInit()).thenReturn(1.0);
        gaussianRandom = new GaussianRandom(seed, ModelLoader.getSpaceAggressionInit());
    }

    @PrepareForTest({ModelLoader.class})
    @Test
    public void getRandomsGaussian1() throws IOException {
        int testIterations = 10000000;
        double[] gaussians1 = new double[testIterations];
        for (int i = 0; i < testIterations; i++)
            gaussians1[i] = gaussianRandom.nextGaussianMean1();

        ResultsWriter rw1 = new ResultsWriter(gaussians1);
        rw1.writeResults("RandomizerResultsGaussian1.csv");
    }
}
