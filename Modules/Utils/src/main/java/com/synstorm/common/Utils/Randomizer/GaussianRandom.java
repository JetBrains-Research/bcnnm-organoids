package com.synstorm.common.Utils.Randomizer;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import org.uncommons.maths.random.GaussianGenerator;
import org.uncommons.maths.random.MersenneTwisterRNG;

import java.util.Random;

/**
 * Created by human-research on 14/09/16.
 */

@Model_v0
@NonProductionLegacy
public class GaussianRandom {
    private GaussianGenerator gaussianGeneratorMean0;
    private GaussianGenerator gaussianGeneratorMean1;

    public GaussianRandom(byte[] seed, double spaceAgression) {
        Random rng = new MersenneTwisterRNG(seed);
        this.gaussianGeneratorMean0 = new GaussianGenerator(0, 0.2, rng);
        this.gaussianGeneratorMean1 = new GaussianGenerator(1, spaceAgression / 5.0, rng);
    }

    public double nextGaussianMean1() {
        return gaussianGeneratorMean1.nextValue();
    }

    public double nextGaussianMean0() {
        return gaussianGeneratorMean0.nextValue();
    }
}
