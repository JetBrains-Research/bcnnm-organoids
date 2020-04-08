package com.synstorm.common.Utils.Randomizer;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.ConfigurationStrings.ConstantValues;
import org.uncommons.maths.random.MersenneTwisterRNG;

import java.util.Random;
import java.util.UUID;

/**
 * Created by human-research on 14/09/16.
 */

@Model_v0
@NonProductionLegacy
public class UUIDRandom {
    //region Fields
    private static UUIDRandom instance;
    private Random rng;
    //endregion

    //region Constructors
    private UUIDRandom() {
        rng = new MersenneTwisterRNG(ConstantValues.UUID_DEFAULT_SEED.getBytes());
    }

    //endregion

    //region Getters and Setters
    public static synchronized UUIDRandom getInstance() {
        if (instance == null)
            instance = new UUIDRandom();

        return instance;
    }
    //endregion

    //region Public Methods
    public UUID nextUUID() {
        byte[] randomBytes = new byte[16];
        rng.nextBytes(randomBytes);
        randomBytes[6] &= 0x0f;  /* clear version        */
        randomBytes[6] |= 0x40;  /* set to version 4     */
        randomBytes[8] &= 0x3f;  /* clear variant        */
        randomBytes[8] |= 0x80;  /* set to IETF variant  */
        long msb = 0;
        long lsb = 0;
        assert randomBytes.length == 16 : "data must be 16 bytes in length";
        for (int i = 0; i < 8; i++)
            msb = (msb << 8) | (randomBytes[i] & 0xff);
        for (int i = 8; i < 16; i++)
            lsb = (lsb << 8) | (randomBytes[i] & 0xff);

        return new UUID(msb, lsb);
    }
    //endregion


    //region Private Methods

    //endregion

}
