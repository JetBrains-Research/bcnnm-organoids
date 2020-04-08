package com.synstorm.common.Utils.Details;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by human-research on 24/05/16.
 */
public class AxonDetailsTest {
    AxonDetails testDetails = new AxonDetails("Glu", "0.8", "0.5", "0.3");

    @Test
    public void getExpressingNTPercentage() throws Exception {
        assertTrue(testDetails.getExpressingNTPercentage() == 0.8);
    }

    @Test
    public void getReleasingNTPercentage() throws Exception {
        assertTrue(testDetails.getReleasingNTPercentage() == 0.5);
    }

    @Test
    public void getUptakeNTPercentage() throws Exception {
        assertTrue(testDetails.getUptakeNTPercentage() == 0.3);
    }

    @Test
    public void getNeurotransmitter() throws Exception {
        assertEquals(testDetails.getNeurotransmitter(), "Glu");
    }

    @Test
    public void hasAxon() throws Exception {
        assertTrue(testDetails.hasAxon());
    }

    @Test
    public void setNeurotransmitter() throws Exception {
        testDetails.setNeurotransmitter("GABA");
        assertEquals(testDetails.getNeurotransmitter(), "GABA");
    }

    @Test
    public void setExpressingNTPercentage() throws Exception {
        testDetails.setExpressingNTPercentage(0.99);
        assertTrue(testDetails.getExpressingNTPercentage() == 0.99);
    }

    @Test
    public void setReleasingNTPercentage() throws Exception {
        testDetails.setReleasingNTPercentage(0.95);
        assertTrue(testDetails.getReleasingNTPercentage() == 0.95);
    }

    @Test
    public void setUptakeNTPercentage() throws Exception {
        testDetails.setUptakeNTPercentage(0.7);
        assertTrue(testDetails.getUptakeNTPercentage() == 0.7);
    }

    @Test
    public void setHasAxon() throws Exception {
        testDetails.setHasAxon(false);
        assertFalse(testDetails.hasAxon());
        testDetails.setHasAxon(true);
        assertTrue(testDetails.hasAxon());
    }

}