package com.synstorm.SimulationModel.SpaceShell;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.synstorm.SimulationModel.Containers.ModelContainer;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.ObjectMovedResponse;
import com.synstorm.SimulationModel.ModelTime.ModelTime;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.LObjectId;
import com.synstorm.common.Utils.SpaceUtils.CoordinateUtils;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by dvbozhko on 10/28/15.
 */
public class DimensionalSpaceTest {
    private DimensionalSpace ds;
    private Method GetNearRandomCoordinate;
    private BiMap<Integer, ICoordinate> cMap;
    private Set<ICoordinate> cSet;
    private BiMap<Integer, ICoordinate> pbcMap;
    private Set<ICoordinate> pbcSet;

    @Before
    public @SuppressWarnings("unchecked") void setUp() throws Exception {
        UUID testModelId = UUID.randomUUID();
        CoordinateUtils.INSTANCE.initializeUtils(4);
        ds = new DimensionalSpace(testModelId);
        ModelContainer.INSTANCE.addAdditionalElement(testModelId, new ModelTime(), ds, longToBytes(1854633822L));
        GetNearRandomCoordinate = DimensionalSpace.class.getDeclaredMethod("getNearRandomCoordinate", ICoordinate.class);
        GetNearRandomCoordinate.setAccessible(true);

        Field coordinateMap = DimensionalSpace.class.getDeclaredField("coordinateMap");
        Field coordinateSet = DimensionalSpace.class.getDeclaredField("coordinateSet");
        Field permanentBusyCoordinateMap = DimensionalSpace.class.getDeclaredField("permanentBusyCoordinateMap");
        Field permanentBusyCoordinateSet = DimensionalSpace.class.getDeclaredField("permanentBusyCoordinateSet");
        coordinateMap.setAccessible(true);
        coordinateSet.setAccessible(true);
        permanentBusyCoordinateMap.setAccessible(true);
        permanentBusyCoordinateSet.setAccessible(true);

        cMap = BiMap.class.cast(coordinateMap.get(ds));
        cMap = (BiMap<Integer, ICoordinate>) coordinateMap.get(ds);
        cSet = (Set<ICoordinate>) coordinateSet.get(ds);
        pbcMap = (BiMap<Integer, ICoordinate>) permanentBusyCoordinateMap.get(ds);
        pbcSet = (Set<ICoordinate>) permanentBusyCoordinateSet.get(ds);
    }

    @Test
    public void testCreateNeighbors() throws Exception {
        CoordinateUtils.INSTANCE.initializeUtils(351);
        long coordCntr = 0;
        for (int i = 0; i < 351; i++) {
            for (int j = 0; j < 351; j++) {
                for (int k = 0; k < 351; k++) {
                    ICoordinate c = CoordinateUtils.INSTANCE.createCoordinate(i, j, k);
                    List<ICoordinate> neighbors = CoordinateUtils.INSTANCE.makeNeighborCoordinates(c);
//                    System.out.printf("Coordinate (%d, %d, %d) has %d neighbors\n", c.getX(), c.getY(), c.getZ(), neighbors.size());
                    coordCntr += (neighbors.size());
                }
            }
        }
        System.out.printf("Total coordinates processed: %d\n", coordCntr);
        assertTrue(true);
    }

    @Test
    public void testAdd1kk() throws Exception {
        CoordinateUtils.INSTANCE.initializeUtils(351);
        BiMap<LObjectId, ICoordinate> coordinateMap = HashBiMap.create();
        BiMap<LObjectId, ICoordinate> permanentBusyCoordinateMap = HashBiMap.create();
        Random rnd = new Random(0L);
        for (int i = 0; i < 1000000; i++) {
            ICoordinate c1 = CoordinateUtils.INSTANCE.createCoordinate(rnd.nextInt(100), rnd.nextInt(100), rnd.nextInt(100));
            if (!coordinateMap.values().contains(c1))
                coordinateMap.put(LObjectId.testId(i), c1);
//            if(ds.tryAddObject(c1))
//                ds.addLObject(LObjectId.testId(i), c1, false);
        }
//        ICoordinate c1 = CoordinateUtils.INSTANCE.createCoordinate(1, 1, 1);
//        ICoordinate c2 = CoordinateUtils.INSTANCE.createCoordinate(1, 0, 0);
//        ds.addLObject(LObjectId.testId(0), c1, false);
//        ds.addLObject(LObjectId.testId(1), c2, true);
//
//        ICoordinate c1prime = cMap.get(0);
//        ICoordinate c2prime = pbcMap.get(1);
//
//        boolean containsCoordinate = cSet.contains(c1) && cSet.contains(c2) && pbcSet.contains(c2);
        assertTrue(true);
    }

    @Test
    public void testAddLObjectFree() throws Exception {
        ICoordinate c1 = CoordinateUtils.INSTANCE.createCoordinate(1, 1, 1);
        ICoordinate c2 = CoordinateUtils.INSTANCE.createCoordinate(1, 0, 0);
        ds.addLObject(LObjectId.testId(0), c1, false);
        ds.addLObject(LObjectId.testId(1), c2, true);

        ICoordinate c1prime = cMap.get(0);
        ICoordinate c2prime = pbcMap.get(1);

        boolean containsCoordinate = cSet.contains(c1) && cSet.contains(c2) && pbcSet.contains(c2);
        assertTrue(c1.equals(c1prime) && c2.equals(c2prime) && containsCoordinate);
    }

    @Test
    public void testAddLObjectBusy() throws Exception {
        ICoordinate c = CoordinateUtils.INSTANCE.createCoordinate(1, 1, 1);
        List<ICoordinate> neighbors = CoordinateUtils.INSTANCE.makeNeighborCoordinates(c);
        ds.addLObject(LObjectId.testId(0), c, false);

        for (int i = 0; i < 13; i++)
            ds.addLObject(LObjectId.testId(i + 1), neighbors.get(i), true);

        for (int i = 13; i < 26; i++)
            ds.addLObject(LObjectId.testId(i + 1), neighbors.get(i), false);

        boolean isAddedMovable = ds.tryAddObject(c);
        ds.addLObject(LObjectId.testId(27), c, false);
        boolean isAddedPermanent = ds.tryAddObject(c);
        ds.addLObject(LObjectId.testId(28), c, true);
        boolean containsObjects = cMap.containsKey(27) && cMap.containsKey(28) && pbcMap.containsKey(28);

        assertTrue(isAddedMovable && isAddedPermanent && containsObjects);
    }

    @Test
    public void testAddLObjectFullVolume() throws Exception {
        ICoordinate c = CoordinateUtils.INSTANCE.createCoordinate(1, 1, 1);
        List<Boolean> isAdded = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            isAdded.add(ds.tryAddObject(c));
            ds.addLObject(LObjectId.testId(i), c, false);
        }

        boolean finalAdded = ds.tryAddObject(c);
        assertTrue(!isAdded.contains(false) && !finalAdded);
    }

    @Test
    public void testAddLObjectFullPermanent() throws Exception {
        ICoordinate c = CoordinateUtils.INSTANCE.createCoordinate(1, 1, 1);
        List<ICoordinate> neighbors = CoordinateUtils.INSTANCE.makeNeighborCoordinates(c);
        ds.addLObject(LObjectId.testId(0), c, false);

        for (int i = 0; i < 26; i++)
            ds.addLObject(LObjectId.testId(i + 1), neighbors.get(i), true);

        boolean isAdded = ds.tryAddObject(c);
        assertTrue(!isAdded);
    }

    @Test
    public void testRemoveLObject() throws Exception {
        ICoordinate c = CoordinateUtils.INSTANCE.createCoordinate(1, 1, 1);
        List<ICoordinate> neighbors = CoordinateUtils.INSTANCE.makeNeighborCoordinates(c);
        ds.addLObject(LObjectId.testId(0), c, false);
        for (int i = 0; i < 13; i++)
            ds.addLObject(LObjectId.testId(i + 1), neighbors.get(i), true);

        for (int i = 13; i < 26; i++)
            ds.addLObject(LObjectId.testId(i + 1), neighbors.get(i), false);

        for (int i = 0; i < 27; i++)
            ds.removeLObject(LObjectId.testId(i));

        assertTrue(cMap.size() == 0 && cSet.size() == 0 && pbcMap.size() == 0 && pbcSet.size() == 0);
    }

    @Test
    public void testUpdatePermanencyAfterProliferation() throws Exception {
        ICoordinate c = CoordinateUtils.INSTANCE.createCoordinate(1, 1, 1);
        List<ICoordinate> neighbors = CoordinateUtils.INSTANCE.makeNeighborCoordinates(c);
        ds.addLObject(LObjectId.testId(0), c, false);

        for (int i = 0; i < 26; i++)
            ds.addLObject(LObjectId.testId(i + 1), neighbors.get(i), false);

        for (int i = 13; i < 26; i++)
            ds.updatePermanencyAfterProliferation(LObjectId.testId(i), true);

        boolean isPermanent = pbcMap.size() == 13;
        for (int i = 13; i < 26; i++)
            isPermanent = isPermanent & pbcMap.containsKey(i);

        assertTrue(isPermanent);
    }

    @Test
    public void testMoveLObjects() throws Exception {
        List<ICoordinate> startCoordinates = new ArrayList<>();
        List<ICoordinate> moveCoordinates = new ArrayList<>();
        List<Double> probabilities = new ArrayList<>();
        Set<ObjectMovedResponse> movingDetails = new LinkedHashSet<>();
        int idPerm = 20;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ICoordinate cBottom = CoordinateUtils.INSTANCE.createCoordinate(j, i, 0);
                ICoordinate cTop = CoordinateUtils.INSTANCE.createCoordinate(j, i, 2);
                ds.addLObject(LObjectId.testId(idPerm++), cBottom, true);
                ds.addLObject(LObjectId.testId(idPerm++), cTop, true);
            }
        }

        startCoordinates.add(CoordinateUtils.INSTANCE.createCoordinate(0, 0, 1));
        startCoordinates.add(CoordinateUtils.INSTANCE.createCoordinate(0, 2, 1));
        startCoordinates.add(CoordinateUtils.INSTANCE.createCoordinate(1, 0, 1));
        startCoordinates.add(CoordinateUtils.INSTANCE.createCoordinate(1, 1, 1));
        startCoordinates.add(CoordinateUtils.INSTANCE.createCoordinate(1, 2, 1));
        startCoordinates.add(CoordinateUtils.INSTANCE.createCoordinate(3, 0, 1));
        startCoordinates.add(CoordinateUtils.INSTANCE.createCoordinate(2, 1, 1));
        startCoordinates.add(CoordinateUtils.INSTANCE.createCoordinate(2, 2, 1));
        startCoordinates.add(CoordinateUtils.INSTANCE.createCoordinate(2, 3, 1));

        moveCoordinates.add(CoordinateUtils.INSTANCE.createCoordinate(0, 1, 1));
        moveCoordinates.add(CoordinateUtils.INSTANCE.createCoordinate(0, 1, 1));
        moveCoordinates.add(CoordinateUtils.INSTANCE.createCoordinate(2, 0, 1));
        moveCoordinates.add(CoordinateUtils.INSTANCE.createCoordinate(1, 0, 1));
        moveCoordinates.add(CoordinateUtils.INSTANCE.createCoordinate(0, 1, 1));
        moveCoordinates.add(CoordinateUtils.INSTANCE.createCoordinate(3, 1, 1));
        moveCoordinates.add(CoordinateUtils.INSTANCE.createCoordinate(3, 1, 1));
        moveCoordinates.add(CoordinateUtils.INSTANCE.createCoordinate(2, 1, 1));
        moveCoordinates.add(CoordinateUtils.INSTANCE.createCoordinate(2, 2, 1));

        probabilities.add(0.2);
        probabilities.add(0.7);
        probabilities.add(0.5);
        probabilities.add(0.01);
        probabilities.add(0.71);
        probabilities.add(0.81);
        probabilities.add(0.809);
        probabilities.add(1.);
        probabilities.add(1.);

        for (int i = 0; i < startCoordinates.size(); i++) {
            ds.addLObject(LObjectId.testId(i), startCoordinates.get(i), false);
            ObjectMovedResponse objectMovedResponse = new ObjectMovedResponse(LObjectId.testId(i), moveCoordinates.get(i), probabilities.get(i));
            movingDetails.add(objectMovedResponse);
        }

        ds.moveLObjects(movingDetails);
        boolean firstCase = cMap.get(0).equals(startCoordinates.get(0)) && cMap.get(1).equals(startCoordinates.get(1))
                && cMap.get(4).equals(moveCoordinates.get(4));
        boolean secondCase = cMap.get(2).equals(moveCoordinates.get(2)) && cMap.get(3).equals(startCoordinates.get(3));
        boolean thirdCase = cMap.get(5).equals(moveCoordinates.get(5)) && cMap.get(6).equals(startCoordinates.get(6))
                && cMap.get(7).equals(startCoordinates.get(7)) && cMap.get(8).equals(startCoordinates.get(8));

        assertTrue(firstCase && secondCase && thirdCase);
    }

    @Test
    public void testGetNearRandomCoordinateFree() throws Exception {
        ICoordinate c = CoordinateUtils.INSTANCE.createCoordinate(1, 1, 1);

        ICoordinate rndC = (ICoordinate) GetNearRandomCoordinate.invoke(ds, c);
        assertTrue(rndC != null && rndC.equals(CoordinateUtils.INSTANCE.createCoordinate(1, 0, 0)));
    }

    @Test
    public void testGetNearRandomCoordinateHalfBusy() throws Exception {
        ICoordinate c = CoordinateUtils.INSTANCE.createCoordinate(1, 1, 1);
        List<ICoordinate> neighbors = CoordinateUtils.INSTANCE.makeNeighborCoordinates(c);
        ds.addLObject(LObjectId.testId(0), c, false);
        for (int i = 0; i <= 13; i++)
            ds.addLObject(LObjectId.testId(i + 1), neighbors.get(i), false);

        ICoordinate rndC = (ICoordinate) GetNearRandomCoordinate.invoke(ds, c);
        assertTrue(rndC != null && rndC.equals(CoordinateUtils.INSTANCE.createCoordinate(2, 0, 2)));
    }

    @Test
    public void testGetNearRandomCoordinateOneFree() throws Exception {
        ICoordinate c = CoordinateUtils.INSTANCE.createCoordinate(1, 1, 1);
        List<ICoordinate> neighbors = CoordinateUtils.INSTANCE.makeNeighborCoordinates(c);
        ds.addLObject(LObjectId.testId(0), c, false);
        for (int i = 0; i < 25; i++)
            ds.addLObject(LObjectId.testId(i + 1), neighbors.get(i), false);

        ICoordinate rndC = (ICoordinate) GetNearRandomCoordinate.invoke(ds, c);
        assertTrue(rndC != null && rndC.equals(CoordinateUtils.INSTANCE.createCoordinate(2, 2, 2)));
    }

    @Test
    public void testGetNearRandomCoordinateBusyMovable() throws Exception {
        ICoordinate c = CoordinateUtils.INSTANCE.createCoordinate(1, 1, 1);
        List<ICoordinate> neighbors = CoordinateUtils.INSTANCE.makeNeighborCoordinates(c);
        ds.addLObject(LObjectId.testId(0), c, false);
        for (int i = 0; i < 26; i++)
            ds.addLObject(LObjectId.testId(i + 1), neighbors.get(i), false);

        ICoordinate rndC = (ICoordinate) GetNearRandomCoordinate.invoke(ds, c);
        assertTrue(rndC != null && rndC.equals(CoordinateUtils.INSTANCE.createCoordinate(1, 0, 0)));
    }

    @Test
    public void testGetNearRandomCoordinateHalfBusyMovable() throws Exception {
        ICoordinate c = CoordinateUtils.INSTANCE.createCoordinate(1, 1, 1);
        List<ICoordinate> neighbors = CoordinateUtils.INSTANCE.makeNeighborCoordinates(c);
        ds.addLObject(LObjectId.testId(0), c, false);
        for (int i = 0; i < 13; i++)
            ds.addLObject(LObjectId.testId(i + 1), neighbors.get(i), true);

        for (int i = 13; i < 26; i++)
            ds.addLObject(LObjectId.testId(i + 1), neighbors.get(i), false);

        ICoordinate rndC = (ICoordinate) GetNearRandomCoordinate.invoke(ds, c);
        assertTrue(rndC != null && rndC.equals(CoordinateUtils.INSTANCE.createCoordinate(0, 2, 1)));
    }

    @Test
    public void testGetNearRandomCoordinateOneBusyMovable() throws Exception {
        ICoordinate c = CoordinateUtils.INSTANCE.createCoordinate(1, 1, 1);
        List<ICoordinate> neighbors = CoordinateUtils.INSTANCE.makeNeighborCoordinates(c);
        ds.addLObject(LObjectId.testId(0), c, false);
        for (int i = 0; i < 25; i++)
            ds.addLObject(LObjectId.testId(i + 1), neighbors.get(i), true);

        ds.addLObject(LObjectId.testId(26), neighbors.get(25), false);

        ICoordinate rndC = (ICoordinate) GetNearRandomCoordinate.invoke(ds, c);
        assertTrue(rndC != null && rndC.equals(CoordinateUtils.INSTANCE.createCoordinate(2, 2, 2)));
    }

    @Test
    public void testGetNearRandomCoordinateFullPermanent() throws Exception {
        ICoordinate c = CoordinateUtils.INSTANCE.createCoordinate(1, 1, 1);
        List<ICoordinate> neighbors = CoordinateUtils.INSTANCE.makeNeighborCoordinates(c);
        ds.addLObject(LObjectId.testId(0), c, false);
        for (int i = 0; i < 26; i++)
            ds.addLObject(LObjectId.testId(i + 1), neighbors.get(i), true);

        ICoordinate rndC = (ICoordinate) GetNearRandomCoordinate.invoke(ds, c);
        assertTrue(rndC == null);
    }

    private byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES*2);
        buffer.putLong(x);
        return buffer.array();
    }
}