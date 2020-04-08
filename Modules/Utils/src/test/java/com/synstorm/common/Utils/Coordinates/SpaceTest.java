package com.synstorm.common.Utils.Coordinates;

import com.google.common.math.DoubleMath;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SpaceTest {
    private Space sp = new Space(100, new int[]{1, 1, 1, 1, 1, 10, 1, 1, 1, 1});

    @Test
    public void testMakeNeighborCoordinates() throws Exception {
        ArrayList<int[]> coords = sp.getNeighborhood(new int[]{5, 5, 5});

        assertTrue(coords.size() == 26);

        assertTrue(isCoordInArray(new int[]{4, 4, 4}, coords));
        assertTrue(isCoordInArray(new int[]{4, 4, 5}, coords));
        assertTrue(isCoordInArray(new int[]{4, 4, 6}, coords));
        assertTrue(isCoordInArray(new int[]{4, 5, 4}, coords));
        assertTrue(isCoordInArray(new int[]{4, 5, 5}, coords));
        assertTrue(isCoordInArray(new int[]{4, 5, 6}, coords));
        assertTrue(isCoordInArray(new int[]{4, 6, 4}, coords));
        assertTrue(isCoordInArray(new int[]{4, 6, 5}, coords));
        assertTrue(isCoordInArray(new int[]{4, 6, 6}, coords));

        assertTrue(isCoordInArray(new int[]{5, 4, 4}, coords));
        assertTrue(isCoordInArray(new int[]{5, 4, 5}, coords));
        assertTrue(isCoordInArray(new int[]{5, 4, 6}, coords));
        assertTrue(isCoordInArray(new int[]{5, 5, 4}, coords));
        assertTrue(isCoordInArray(new int[]{5, 5, 6}, coords));
        assertTrue(isCoordInArray(new int[]{5, 6, 4}, coords));
        assertTrue(isCoordInArray(new int[]{5, 6, 5}, coords));
        assertTrue(isCoordInArray(new int[]{5, 6, 6}, coords));

        assertTrue(isCoordInArray(new int[]{6, 4, 4}, coords));
        assertTrue(isCoordInArray(new int[]{6, 4, 5}, coords));
        assertTrue(isCoordInArray(new int[]{6, 4, 6}, coords));
        assertTrue(isCoordInArray(new int[]{6, 5, 4}, coords));
        assertTrue(isCoordInArray(new int[]{6, 5, 5}, coords));
        assertTrue(isCoordInArray(new int[]{6, 5, 6}, coords));
        assertTrue(isCoordInArray(new int[]{6, 6, 4}, coords));
        assertTrue(isCoordInArray(new int[]{6, 6, 5}, coords));
        assertTrue(isCoordInArray(new int[]{6, 6, 6}, coords));
    }

    @Test
    public void testMakeNeighborBorder() throws Exception {
        ArrayList<int[]> coords = sp.getNeighborhood(new int[]{0, 0, 0});

        assertTrue(coords.size() == 7);

        assertTrue(isCoordInArray(new int[]{0, 0, 1}, coords));
        assertTrue(isCoordInArray(new int[]{0, 1, 0}, coords));
        assertTrue(isCoordInArray(new int[]{0, 1, 1}, coords));


        assertTrue(isCoordInArray(new int[]{1, 0, 0}, coords));
        assertTrue(isCoordInArray(new int[]{1, 0, 1}, coords));
        assertTrue(isCoordInArray(new int[]{1, 1, 0}, coords));
        assertTrue(isCoordInArray(new int[]{1, 1, 1}, coords));
    }

    @Test
    public void testGetCapacity() throws Exception {
        assertTrue(sp.getSize() == 100);
    }

    @Test
    public void testObjectCreation() throws Exception {
        sp.reset();

        int[] coord = {5, 5, 5};
        int objId = 100;
        int type = 5;
        int max_r = 10;

        sp.createObject(objId, coord, ObjectState.NoVolume);
        sp.addSignal(objId, type);

        int test_id[] = sp.getObjectsByCoordinate(coord);

        assertTrue(test_id.length == 1);
        assertTrue(IntStream.of(test_id).anyMatch(x -> x == objId));
    }

    @Test
    public void testObjectCreationDeletionWithVolume() throws Exception {
        sp.reset();

        int[] coord = {5, 5, 5};
        int objIdNoVolume = 100;
        int objIdVolume = 101;

        sp.createObject(objIdNoVolume, coord, ObjectState.NoVolume);
        sp.createObject(objIdVolume, coord, ObjectState.VolumeMovable);

        int checkVolumeObject = sp.getVolumeObjectByCoordinate(coord);
        assertTrue(checkVolumeObject == objIdVolume);

        boolean isStateChanged = sp.changeObjectState(objIdNoVolume, ObjectState.VolumeMovable);
        assertTrue(!isStateChanged);

        isStateChanged = sp.changeObjectState(objIdVolume, ObjectState.VolumeImmovable);
        assertTrue(isStateChanged);

        checkVolumeObject = sp.getVolumeObjectByCoordinate(coord);
        assertTrue(checkVolumeObject == objIdVolume);

        isStateChanged = sp.changeObjectState(objIdVolume, ObjectState.VolumeMovable);
        assertTrue(isStateChanged);

        checkVolumeObject = sp.getVolumeObjectByCoordinate(coord);
        assertTrue(checkVolumeObject == objIdVolume);

        isStateChanged = sp.changeObjectState(objIdVolume, ObjectState.NoVolume);
        assertTrue(isStateChanged);

        checkVolumeObject = sp.getVolumeObjectByCoordinate(coord);
        assertTrue(checkVolumeObject == -1);

        isStateChanged = sp.changeObjectState(objIdNoVolume, ObjectState.VolumeMovable);
        assertTrue(isStateChanged);

        checkVolumeObject = sp.getVolumeObjectByCoordinate(coord);
        assertTrue(checkVolumeObject == objIdNoVolume);
    }

    @Test
    public void testSpreadGather() throws Exception {
        sp.reset();

        int[] coord = {5, 5, 5};
        int objId = 100;
        int type = 5;
        int max_r = 10;

        sp.createObject(objId, coord, ObjectState.NoVolume);
        sp.addSignal(objId, type);

        for (int i = 0; i < max_r * 2; ++i) {
            sp.spreadSignal(objId, type);
            sp.recalculateSignals();
        }

        for (int i = max_r * 2; i >= 0; --i) {
            sp.gatherSignal(objId, type);
            sp.recalculateSignals();
        }

        sp.spreadSignal(objId, type);
        sp.recalculateSignals();
        sp.gatherSignal(objId, type);
        sp.recalculateSignals();

        for (int x = 0; x < 100; ++x) {
            for (int y = 0; y < 100; ++y) {
                for (int z = 0; z < 100; ++z) {
                    if (!DoubleMath.fuzzyEquals(sp.getConcentration(new int[]{x, y, z}, 5), 0., 1e-10)) {
                        System.out.println(sp.getConcentration(new int[]{x, y, z}, 5));
                    }
                    assertTrue(DoubleMath.fuzzyEquals(sp.getConcentration(new int[]{x, y, z}, 5), 0., 1e-5));
                }
            }
        }
    }

    @Test
    public void testVectors() {
        int k = sp.calcVectors();
        assertEquals(27, k);
    }

    private boolean isCoordInArray(int[] coord, ArrayList<int[]> arr) {
        for (int[] target : arr) {
            if (Arrays.equals(coord, target)) {
                return true;
            }
        }

        return false;
    }
}

