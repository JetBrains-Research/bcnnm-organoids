package com.synstorm.common.Utils.TrainingDataProvider;

import org.junit.Test;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

/**
 * Created by dvbozhko on 23/12/2016.
 */
public class CyclicDigitalImagesProviderTest {

    private static final String MNIST_PATH = System.getProperty("user.dir") + File.separator + "data" + File.separator
            + "TrainSamples" + File.separator;
    private static Random rnd = new Random(1854633822L);

    @Test
    public void next() throws Exception {
        assertTrue(testNext(0));
        assertTrue(testNext(8));
        assertTrue(testNext(18));
        assertTrue(testNext(28));
        assertTrue(testNext(31));
        assertTrue(testNext(40));
    }

    private boolean testNext(int providerSize) {
        CyclicDigitalImagesProvider dip = new CyclicDigitalImagesProvider(MNIST_PATH + "avg_train.csv", providerSize);
        UUID id = UUID.randomUUID();
        dip.addIndividual(id, longToBytes(1854633822L));
        boolean result = true;

        for (int i = 0; i < providerSize; i++) {
            result = result && dip.next(id) != null;
        }

        result = result && dip.next(id) == null;

        dip.removeIndividual(id);
        return result;
    }

    private byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES*2);
        buffer.putLong(x);
        return buffer.array();
    }
}