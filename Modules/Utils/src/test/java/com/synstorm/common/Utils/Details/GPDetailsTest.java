package com.synstorm.common.Utils.Details;

import com.synstorm.common.Utils.SpaceUtils.CoordinateUtils;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by human-research on 26/05/16.
 */
public class GPDetailsTest {
    Map<Integer, GPDetails> gpMap = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        CoordinateUtils.INSTANCE.initializeUtils(20);
        GPDetails zero = new GPDetails(0, CoordinateUtils.INSTANCE.createCoordinate(1, 1, 1), 20, true, "factor1", "group1");
        GPDetails one = new GPDetails(1, CoordinateUtils.INSTANCE.createCoordinate(2, 2, 2), 1, false, "factor1", "group1");
        GPDetails two = new GPDetails(2, CoordinateUtils.INSTANCE.createCoordinate(4, 4, 4), 5, true, "factor1", "group1");
        GPDetails three = new GPDetails(3, CoordinateUtils.INSTANCE.createCoordinate(5, 5, 5), 10, false, "factor2", "group2");
        GPDetails four = new GPDetails(4, CoordinateUtils.INSTANCE.createCoordinate(10, 1, 1), 20, true, "factor1", "group1");
        gpMap.put(0, zero);
        gpMap.put(1, one);
        gpMap.put(2, two);
        gpMap.put(3, three);
        gpMap.put(4, four);
    }

    @Test
    public void getId() throws Exception {
        assertTrue(gpMap.get(0).getId() == 0);
        assertTrue(gpMap.get(1).getId() == 1);
        assertTrue(gpMap.get(2).getId() == 2);
        assertTrue(gpMap.get(3).getId() == 3);
        assertTrue(gpMap.get(4).getId() == 4);
    }

    @Test
    public void getRadius() throws Exception {
        assertTrue(gpMap.get(0).getRadius() == 20);
        assertTrue(gpMap.get(1).getRadius() == 1);
        assertTrue(gpMap.get(2).getRadius() == 5);
        assertTrue(gpMap.get(3).getRadius() == 10);
        assertTrue(gpMap.get(4).getRadius() == 20);
    }

    @Test
    public void setRadius() throws Exception {
        gpMap.get(0).setRadius(11);
        gpMap.get(1).setRadius(22);
        gpMap.get(2).setRadius(33);
        gpMap.get(3).setRadius(44);
        gpMap.get(4).setRadius(55);
        assertTrue(gpMap.get(0).getRadius() == 11);
        assertTrue(gpMap.get(1).getRadius() == 22);
        assertTrue(gpMap.get(2).getRadius() == 33);
        assertTrue(gpMap.get(3).getRadius() == 44);
        assertTrue(gpMap.get(4).getRadius() == 55);
    }

    @Test
    public void getCoordinate() throws Exception {
        ICoordinate coordinate0 = gpMap.get(0).getCoordinate();
        assertTrue(coordinate0.getX() == 1L);
        assertTrue(coordinate0.getY() == 1L);
        assertTrue(coordinate0.getZ() == 1L);
        ICoordinate coordinate1 = gpMap.get(1).getCoordinate();
        assertTrue(coordinate1.getX() == 2L);
        assertTrue(coordinate1.getY() == 2L);
        assertTrue(coordinate1.getZ() == 2L);
        ICoordinate coordinate2 = gpMap.get(2).getCoordinate();
        assertTrue(coordinate2.getX() == 4L);
        assertTrue(coordinate2.getY() == 4L);
        assertTrue(coordinate2.getZ() == 4L);
        ICoordinate coordinate3 = gpMap.get(3).getCoordinate();
        assertTrue(coordinate3.getX() == 5L);
        assertTrue(coordinate3.getY() == 5L);
        assertTrue(coordinate3.getZ() == 5L);
        ICoordinate coordinate4 = gpMap.get(4).getCoordinate();
        assertTrue(coordinate4.getX() == 10L);
        assertTrue(coordinate4.getY() == 1L);
        assertTrue(coordinate4.getZ() == 1L);
    }

    @Test
    public void updateCoordinate() throws Exception {
        gpMap.get(0).updateCoordinate(0,0,0);
        ICoordinate coordinate0 = gpMap.get(0).getCoordinate();
        assertTrue(coordinate0.getX() == 0L);
        assertTrue(coordinate0.getY() == 0L);
        assertTrue(coordinate0.getZ() == 0L);
    }



    @Test
    public void isCalculateKp() throws Exception {
        assertTrue(gpMap.get(0).isCalculateKp());
        assertFalse(gpMap.get(1).isCalculateKp());
        assertTrue(gpMap.get(2).isCalculateKp());
        assertFalse(gpMap.get(3).isCalculateKp());
        assertTrue(gpMap.get(4).isCalculateKp());
    }

    @Test
    public void setCalculateKp() throws Exception {
        gpMap.get(0).setCalculateKp(true);
        assertTrue(gpMap.get(0).isCalculateKp());

        gpMap.get(1).setCalculateKp(true);
        assertTrue(gpMap.get(1).isCalculateKp());

        gpMap.get(2).setCalculateKp(true);
        assertTrue(gpMap.get(2).isCalculateKp());

        gpMap.get(3).setCalculateKp(true);
        assertTrue(gpMap.get(3).isCalculateKp());

        gpMap.get(4).setCalculateKp(true);
        assertTrue(gpMap.get(4).isCalculateKp());
    }

    @Test
    public void getFactorName() throws Exception {
        assertEquals(gpMap.get(0).getFactorName(), "factor1");
        assertEquals(gpMap.get(1).getFactorName(), "factor1");
        assertEquals(gpMap.get(2).getFactorName(), "factor1");
        assertEquals(gpMap.get(3).getFactorName(), "factor2");
        assertEquals(gpMap.get(4).getFactorName(), "factor1");
    }

    @Test
    public void setFactorName() throws Exception {
        gpMap.get(3).setFactorName("FACTOR");
        assertEquals(gpMap.get(3).getFactorName(), "FACTOR");
        gpMap.get(4).setFactorName("FACTOR");
        assertEquals(gpMap.get(4).getFactorName(), "FACTOR");
    }

    @Test
    public void getGroup() throws Exception {
        assertEquals(gpMap.get(0).getGroup(), "group1");
        assertEquals(gpMap.get(1).getGroup(), "group1");
        assertEquals(gpMap.get(2).getGroup(), "group1");
        assertEquals(gpMap.get(3).getGroup(), "group2");
        assertEquals(gpMap.get(4).getGroup(), "group1");
    }

    @Test
    public void setGroup() throws Exception {
        gpMap.get(0).setGroup("PARTYHARD");
        gpMap.get(1).setGroup("PARTYHARD");
        assertEquals(gpMap.get(0).getGroup(), "PARTYHARD");
        assertEquals(gpMap.get(1).getGroup(), "PARTYHARD");
    }

}