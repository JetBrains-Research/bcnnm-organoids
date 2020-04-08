package com.synstorm.common.Utils.SpaceUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Tests for correct Coordinate utils implementation
 * Created by dvbozhko on 10/22/15.
 */
public class CoordinateUtilsTest {

    @Test
    public void testIllegalDimensionalSizeException() throws Exception {
        List<Boolean> cases = new ArrayList<>();

        try {
            CoordinateUtils.INSTANCE.initializeUtils(-1);
            cases.add(false);
        } catch (IllegalDimensionalSizeException e) {
            cases.add(true);
        }

        try {
            CoordinateUtils.INSTANCE.initializeUtils(0);
            cases.add(false);
        } catch (IllegalDimensionalSizeException e) {
            cases.add(true);
        }

        try {
            CoordinateUtils.INSTANCE.initializeUtils(1);
            cases.add(false);
        } catch (IllegalDimensionalSizeException e) {
            cases.add(true);
        }

        try {
            CoordinateUtils.INSTANCE.initializeUtils(2);
            cases.add(false);
        } catch (IllegalDimensionalSizeException e) {
            cases.add(true);
        }

        assertTrue(!cases.contains(false));
    }

    @Test
    public void testCorrectDimensionalSizeCreation() throws Exception {
        List<Boolean> cases = new ArrayList<>();

        try {
            CoordinateUtils.INSTANCE.initializeUtils(3);
            cases.add(false);
        } catch (IllegalDimensionalSizeException e) {
            cases.add(true);
        }

        try {
            CoordinateUtils.INSTANCE.initializeUtils(30);
            cases.add(false);
        } catch (IllegalDimensionalSizeException e) {
            cases.add(true);
        }

        try {
            CoordinateUtils.INSTANCE.initializeUtils(300);
            cases.add(false);
        } catch (IllegalDimensionalSizeException e) {
            cases.add(true);
        }

        try {
            CoordinateUtils.INSTANCE.initializeUtils(3000);
            cases.add(false);
        } catch (IllegalDimensionalSizeException e) {
            cases.add(true);
        }

        try {
            CoordinateUtils.INSTANCE.initializeUtils(3000000);
            cases.add(false);
        } catch (IllegalDimensionalSizeException e) {
            cases.add(true);
        }

        try {
            CoordinateUtils.INSTANCE.initializeUtils(300000000);
            cases.add(false);
        } catch (IllegalDimensionalSizeException e) {
            cases.add(true);
        }

        assertTrue(!cases.contains(true));
    }

    @Test
    public void testCoordinatesComparativesForSpace() throws Exception {
        int capacity = 100;
        CoordinateUtils.INSTANCE.initializeUtils(capacity);
        ICoordinate c0 = CoordinateUtils.INSTANCE.createCoordinate(-1);
        assertTrue(c0 == null);

        ICoordinate cPrev = CoordinateUtils.INSTANCE.createCoordinate(0, 0, 0);
        int n = 1;
        for (int z = 0; z < capacity; z++) {
            for (int y = 0; y < capacity; y++) {
                for (int x = n; x < capacity; x++) {
                    ICoordinate c = CoordinateUtils.INSTANCE.createCoordinate(x, y, z);
                    assert cPrev != null;
                    assert c != null;
                    assertTrue((int) c.getComparative() - (int) cPrev.getComparative() == 1);
                    cPrev = c;
                }

                n = 0;
            }
        }
    }

    @Test
    public void testCreateCoordinateXYZ() throws Exception {
        CoordinateUtils.INSTANCE.initializeUtils(100);
        ICoordinate c1 = CoordinateUtils.INSTANCE.createCoordinate(32, 85, 79);
        ICoordinate c2 = CoordinateUtils.INSTANCE.createCoordinate(100, 100, 100);
        ICoordinate c3 = CoordinateUtils.INSTANCE.createCoordinate(100, 99, 99);
        ICoordinate c4 = CoordinateUtils.INSTANCE.createCoordinate(0, 0, 0);
        ICoordinate c5 = CoordinateUtils.INSTANCE.createCoordinate(-1, -1, -1);

        assertTrue(c1 != null && c1.toString().equals("(32, 85, 79)"));
        assertTrue(c2 == null);
        assertTrue(c3 == null);
        assertTrue(c4 != null && c4.toString().equals("(0, 0, 0)"));
        assertTrue(c5 == null);
    }

    @Test
    public void testCreateCoordinateComparative() throws Exception {
        CoordinateUtils.INSTANCE.initializeUtils(100);
        ICoordinate c1 = CoordinateUtils.INSTANCE.createCoordinate(798532);
        ICoordinate c2 = CoordinateUtils.INSTANCE.createCoordinate(1010100);
        ICoordinate c3 = CoordinateUtils.INSTANCE.createCoordinate(1000000);
        ICoordinate c4 = CoordinateUtils.INSTANCE.createCoordinate(0);
        ICoordinate c5 = CoordinateUtils.INSTANCE.createCoordinate(-10101);

        assertTrue(c1 != null && c1.toString().equals("(32, 85, 79)"));
        assertTrue(c2 == null);
        assertTrue(c3 == null);
        assertTrue(c4 != null && c4.toString().equals("(0, 0, 0)"));
        assertTrue(c5 == null);
    }

    @Test
    public void testCreateCoordinateWithVector() throws Exception {
        CoordinateUtils.INSTANCE.initializeUtils(100);
        ICoordinate c11 = CoordinateUtils.INSTANCE.createCoordinate(31, 84, 78);
        ICoordinate c12 = CoordinateUtils.INSTANCE.createCoordinate(32, 85, 79);
        SpaceVector sv = CoordinateUtils.INSTANCE.createSpaceVector(c11, c12);
        SpaceVector svInverse = sv != null ? sv.invert() : null;

        ICoordinate c1res = CoordinateUtils.INSTANCE.createCoordinate(c11, sv);

        ICoordinate c21 = CoordinateUtils.INSTANCE.createCoordinate(99, 99, 99);
        ICoordinate c2res = CoordinateUtils.INSTANCE.createCoordinate(c21, sv);
        ICoordinate c31 = CoordinateUtils.INSTANCE.createCoordinate(0, 0, 0);
        ICoordinate c3res = CoordinateUtils.INSTANCE.createCoordinate(c31, svInverse);

        assertTrue(c1res != null && c1res.toString().equals("(32, 85, 79)"));
        assertTrue(c2res == null);
        assertTrue(c3res == null);
    }

    @Test
    public void testCreateSpaceVector() throws Exception {
        CoordinateUtils.INSTANCE.initializeUtils(100);
        ICoordinate c = CoordinateUtils.INSTANCE.createCoordinate(32, 85, 79);
        ICoordinate c1 = CoordinateUtils.INSTANCE.createCoordinate(31, 86, 79);
        ICoordinate c2 = CoordinateUtils.INSTANCE.createCoordinate(31, 85, 79);
        ICoordinate c3 = CoordinateUtils.INSTANCE.createCoordinate(32, 84, 80);
        ICoordinate c4 = CoordinateUtils.INSTANCE.createCoordinate(30, 84, 80);
        ICoordinate c5 = CoordinateUtils.INSTANCE.createCoordinate(32, 80, 80);
        ICoordinate c6 = CoordinateUtils.INSTANCE.createCoordinate(32, 84, 50);

        SpaceVector sv1 = CoordinateUtils.INSTANCE.createSpaceVector(c, c1);
        SpaceVector sv2 = CoordinateUtils.INSTANCE.createSpaceVector(c, c2);
        SpaceVector sv3 = CoordinateUtils.INSTANCE.createSpaceVector(c, c3);
        SpaceVector sv4 = CoordinateUtils.INSTANCE.createSpaceVector(c, c4);
        SpaceVector sv5 = CoordinateUtils.INSTANCE.createSpaceVector(c, c5);
        SpaceVector sv6 = CoordinateUtils.INSTANCE.createSpaceVector(c, c6);

        assertTrue(sv1 != null && sv1.toString().equals("(-1, 1, 0)"));
        assertTrue(sv2 != null && sv2.toString().equals("(-1, 0, 0)"));
        assertTrue(sv3 != null && sv3.toString().equals("(0, -1, 1)"));
        assertTrue(sv4 == null);
        assertTrue(sv5 == null);
        assertTrue(sv6 == null);
    }

    @Test
    public void testCreateCoordinateByStrings() throws Exception {
        CoordinateUtils.INSTANCE.initializeUtils(100);
        ICoordinate c1 = CoordinateUtils.INSTANCE.createCoordinate("32", "85", "79");
        ICoordinate c2 = CoordinateUtils.INSTANCE.createCoordinate("100", "100", "100");
        ICoordinate c3 = CoordinateUtils.INSTANCE.createCoordinate("100", "99", "99");
        ICoordinate c4 = CoordinateUtils.INSTANCE.createCoordinate("0", "0", "0");
        ICoordinate c5 = CoordinateUtils.INSTANCE.createCoordinate("-1", "-1", "-1");

        assertTrue(c1 != null && c1.toString().equals("(32, 85, 79)"));
        assertTrue(c2 == null);
        assertTrue(c3 == null);
        assertTrue(c4 != null && c4.toString().equals("(0, 0, 0)"));
        assertTrue(c5 == null);
    }

    @Test
    public void testGetSpatialCoordinate() throws Exception {
        CoordinateUtils.INSTANCE.initializeUtils(100);
        List<ICoordinate> cList = new ArrayList<>();
        ICoordinate c = CoordinateUtils.INSTANCE.createCoordinate(32, 85, 79);
        for (short i = 0; i <= 26; i++)
            cList.add(CoordinateUtils.INSTANCE.getSpatialCoordinate(c, i));

        assertTrue(cList.get(0).toString().equals("(32, 85, 79)"));
        assertTrue(cList.get(1).toString().equals("(31, 84, 78)"));
        assertTrue(cList.get(2).toString().equals("(32, 84, 78)"));
        assertTrue(cList.get(3).toString().equals("(33, 84, 78)"));
        assertTrue(cList.get(4).toString().equals("(31, 85, 78)"));
        assertTrue(cList.get(5).toString().equals("(32, 85, 78)"));
        assertTrue(cList.get(6).toString().equals("(33, 85, 78)"));
        assertTrue(cList.get(7).toString().equals("(31, 86, 78)"));
        assertTrue(cList.get(8).toString().equals("(32, 86, 78)"));
        assertTrue(cList.get(9).toString().equals("(33, 86, 78)"));
        assertTrue(cList.get(10).toString().equals("(31, 84, 79)"));
        assertTrue(cList.get(11).toString().equals("(32, 84, 79)"));
        assertTrue(cList.get(12).toString().equals("(33, 84, 79)"));
        assertTrue(cList.get(13).toString().equals("(31, 85, 79)"));
        assertTrue(cList.get(14).toString().equals("(33, 85, 79)"));
        assertTrue(cList.get(15).toString().equals("(31, 86, 79)"));
        assertTrue(cList.get(16).toString().equals("(32, 86, 79)"));
        assertTrue(cList.get(17).toString().equals("(33, 86, 79)"));
        assertTrue(cList.get(18).toString().equals("(31, 84, 80)"));
        assertTrue(cList.get(19).toString().equals("(32, 84, 80)"));
        assertTrue(cList.get(20).toString().equals("(33, 84, 80)"));
        assertTrue(cList.get(21).toString().equals("(31, 85, 80)"));
        assertTrue(cList.get(22).toString().equals("(32, 85, 80)"));
        assertTrue(cList.get(23).toString().equals("(33, 85, 80)"));
        assertTrue(cList.get(24).toString().equals("(31, 86, 80)"));
        assertTrue(cList.get(25).toString().equals("(32, 86, 80)"));
        assertTrue(cList.get(26).toString().equals("(33, 86, 80)"));
    }

    @Test
    public void testGetBaseSpatialCoordinate() throws Exception {
        CoordinateUtils.INSTANCE.initializeUtils(100);
        List<ICoordinate> cList = new ArrayList<>();

        cList.add(CoordinateUtils.INSTANCE.createCoordinate(32, 85, 79));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(31, 84, 78));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(32, 84, 78));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(33, 84, 78));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(31, 85, 78));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(32, 85, 78));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(33, 85, 78));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(31, 86, 78));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(32, 86, 78));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(33, 86, 78));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(31, 84, 79));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(32, 84, 79));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(33, 84, 79));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(31, 85, 79));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(33, 85, 79));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(31, 86, 79));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(32, 86, 79));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(33, 86, 79));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(31, 84, 80));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(32, 84, 80));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(33, 84, 80));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(31, 85, 80));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(32, 85, 80));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(33, 85, 80));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(31, 86, 80));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(32, 86, 80));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(33, 86, 80));

        for (short i = 26; i >= 0; i--) {
            ICoordinate coordinate = cList.get(i);
            ICoordinate c = CoordinateUtils.INSTANCE.getBaseSpatialCoordinate(coordinate, i);
            assertTrue(c != null && c.toString().equals("(32, 85, 79)"));
        }
    }

    @Test
    public void testGetSpatialNum() throws Exception {
        CoordinateUtils.INSTANCE.initializeUtils(100);
        List<ICoordinate> cList = new ArrayList<>();
        ICoordinate c = CoordinateUtils.INSTANCE.createCoordinate(32, 85, 79);

        cList.add(CoordinateUtils.INSTANCE.createCoordinate(32, 85, 79));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(31, 84, 78));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(32, 84, 78));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(33, 84, 78));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(31, 85, 78));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(32, 85, 78));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(33, 85, 78));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(31, 86, 78));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(32, 86, 78));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(33, 86, 78));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(31, 84, 79));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(32, 84, 79));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(33, 84, 79));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(31, 85, 79));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(33, 85, 79));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(31, 86, 79));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(32, 86, 79));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(33, 86, 79));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(31, 84, 80));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(32, 84, 80));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(33, 84, 80));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(31, 85, 80));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(32, 85, 80));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(33, 85, 80));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(31, 86, 80));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(32, 86, 80));
        cList.add(CoordinateUtils.INSTANCE.createCoordinate(33, 86, 80));

        short counter = 0;
        for (ICoordinate coordinate : cList) {
            assertTrue(counter == CoordinateUtils.INSTANCE.getSpatialNum(c, coordinate));
            counter++;
        }
    }

    @Test
    public void testCalculateDistance() throws Exception {
        CoordinateUtils.INSTANCE.initializeUtils(100);
        ICoordinate c = CoordinateUtils.INSTANCE.createCoordinate(32, 85, 79);
        ICoordinate c1 = CoordinateUtils.INSTANCE.createCoordinate(18, 18, 18);
        ICoordinate c2 = CoordinateUtils.INSTANCE.createCoordinate(90, 85, 50);
        ICoordinate c3 = CoordinateUtils.INSTANCE.createCoordinate(50, 75, 24);

        assert c != null;
        assert c1 != null;
        assertTrue(CoordinateUtils.INSTANCE.calculateDistance(c, c1) == 67);
        assert c2 != null;
        assertTrue(CoordinateUtils.INSTANCE.calculateDistance(c, c2) == 58);
        assert c3 != null;
        assertTrue(CoordinateUtils.INSTANCE.calculateDistance(c, c3) == 55);
    }

    @Test
    public void testMakeNeighborCoordinates() throws Exception {
        CoordinateUtils.INSTANCE.initializeUtils(100);
        ICoordinate c1 = CoordinateUtils.INSTANCE.createCoordinate(32, 85, 79);

        ICoordinate c2 = CoordinateUtils.INSTANCE.createCoordinate(0, 0, 0);
        ICoordinate c3 = CoordinateUtils.INSTANCE.createCoordinate(99, 0, 0);
        ICoordinate c4 = CoordinateUtils.INSTANCE.createCoordinate(0, 99, 0);
        ICoordinate c5 = CoordinateUtils.INSTANCE.createCoordinate(0, 0, 99);
        ICoordinate c6 = CoordinateUtils.INSTANCE.createCoordinate(99, 99, 0);
        ICoordinate c7 = CoordinateUtils.INSTANCE.createCoordinate(99, 0, 99);
        ICoordinate c8 = CoordinateUtils.INSTANCE.createCoordinate(0, 99, 99);
        ICoordinate c9 = CoordinateUtils.INSTANCE.createCoordinate(99, 99, 99);

        ICoordinate c10 = CoordinateUtils.INSTANCE.createCoordinate(49, 0, 0);
        ICoordinate c11 = CoordinateUtils.INSTANCE.createCoordinate(0, 49, 0);
        ICoordinate c12 = CoordinateUtils.INSTANCE.createCoordinate(0, 0, 49);
        ICoordinate c13 = CoordinateUtils.INSTANCE.createCoordinate(49, 99, 99);
        ICoordinate c14 = CoordinateUtils.INSTANCE.createCoordinate(99, 49, 99);
        ICoordinate c15 = CoordinateUtils.INSTANCE.createCoordinate(99, 99, 49);
        ICoordinate c16 = CoordinateUtils.INSTANCE.createCoordinate(49, 0, 99);
        ICoordinate c17 = CoordinateUtils.INSTANCE.createCoordinate(0, 49, 99);
        ICoordinate c18 = CoordinateUtils.INSTANCE.createCoordinate(99, 0, 49);
        ICoordinate c19 = CoordinateUtils.INSTANCE.createCoordinate(49, 99, 0);
        ICoordinate c20 = CoordinateUtils.INSTANCE.createCoordinate(99, 49, 0);
        ICoordinate c21 = CoordinateUtils.INSTANCE.createCoordinate(0, 99, 49);

        ICoordinate c22 = CoordinateUtils.INSTANCE.createCoordinate(49, 49, 0);
        ICoordinate c23 = CoordinateUtils.INSTANCE.createCoordinate(0, 49, 49);
        ICoordinate c24 = CoordinateUtils.INSTANCE.createCoordinate(49, 0, 49);
        ICoordinate c25 = CoordinateUtils.INSTANCE.createCoordinate(49, 49, 99);
        ICoordinate c26 = CoordinateUtils.INSTANCE.createCoordinate(99, 49, 49);
        ICoordinate c27 = CoordinateUtils.INSTANCE.createCoordinate(49, 99, 49);

        assert c1 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c1).size() == 26);

        assert c2 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c2).size() == 7);
        assert c3 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c3).size() == 7);
        assert c4 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c4).size() == 7);
        assert c5 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c5).size() == 7);
        assert c6 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c6).size() == 7);
        assert c7 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c7).size() == 7);
        assert c8 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c8).size() == 7);
        assert c9 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c9).size() == 7);

        assert c10 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c10).size() == 11);
        assert c11 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c11).size() == 11);
        assert c12 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c12).size() == 11);
        assert c13 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c13).size() == 11);
        assert c14 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c14).size() == 11);
        assert c15 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c15).size() == 11);
        assert c16 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c16).size() == 11);
        assert c17 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c17).size() == 11);
        assert c18 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c18).size() == 11);
        assert c19 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c19).size() == 11);
        assert c20 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c20).size() == 11);
        assert c21 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c21).size() == 11);

        assert c22 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c22).size() == 17);
        assert c23 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c23).size() == 17);
        assert c24 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c24).size() == 17);
        assert c25 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c25).size() == 17);
        assert c26 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c26).size() == 17);
        assert c27 != null;
        assertTrue(CoordinateUtils.INSTANCE.makeNeighborCoordinates(c27).size() == 17);
    }

    @Test
    public void testGetCapacity() throws Exception {
        CoordinateUtils.INSTANCE.initializeUtils(100);
        assertTrue(CoordinateUtils.INSTANCE.getCapacity() == 100);
    }
}