package com.synstorm.SimulationModel.Graph;

import com.synstorm.SimulationModel.ModelAction.CellActionDescription;
import com.synstorm.SimulationModel.ModelAction.LogicObjectActionDescription;
import com.synstorm.SimulationModel.SimulationIdentifiers.ModelActionIds.ModelActionId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.LObjectId;
import com.synstorm.common.Utils.EnumTypes.ActionFunctionalType;
import com.synstorm.common.Utils.EnumTypes.ActionSignalType;
import com.synstorm.common.Utils.EnumTypes.LogicObjectType;
import com.synstorm.common.Utils.EnumTypes.SignalingTopologicalType;
import com.synstorm.common.Utils.SpaceUtils.CoordinateUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by bbrh on 10/05/16.
 */
public class SignalIntensityDispatcherTest {

    private Set<String> signals = new HashSet<String>() {{
        add("S1");
        add("S2");
    }};
    private SignalIntensityDispatcher sid;

    @Before
    public void setUp() throws Exception {
        CoordinateUtils.INSTANCE.initializeUtils(50);
        sid = new SignalIntensityDispatcher(signals);
        UUID testId = UUID.fromString("00000000-0000-0000-0000-000000000000");

        LObjectId id0 = new LObjectId(testId, LogicObjectType.Cell);
        LObjectId id1 = new LObjectId(testId, LogicObjectType.Cell);

        sid.addObject(LObjectId.testId(0), CoordinateUtils.INSTANCE.createCoordinate(10, 10, 10));
        sid.addObject(LObjectId.testId(1), CoordinateUtils.INSTANCE.createCoordinate(15, 15, 15));
        sid.addObject(LObjectId.testId(2), CoordinateUtils.INSTANCE.createCoordinate(10, 10, 19));

        ModelActionId id01 = new ModelActionId(testId, id0, "S1", ActionFunctionalType.Do);
        ModelActionId id02 = new ModelActionId(testId, id0, "S2", ActionFunctionalType.Do);
        ModelActionId id03 = new ModelActionId(testId, id1, "S2", ActionFunctionalType.Do);

        CellActionDescription ad01 = CellActionDescription.createDummyActionDescription(id01, ActionSignalType.Continuous, SignalingTopologicalType.OuterCell, 10);
        CellActionDescription ad02 = CellActionDescription.createDummyActionDescription(id02, ActionSignalType.Continuous, SignalingTopologicalType.OuterCell, 11);
        CellActionDescription ad03 = CellActionDescription.createDummyActionDescription(id03, ActionSignalType.Continuous, SignalingTopologicalType.OuterCell, 5);

        for (int i = 0; i < 11; i++) {
            ad01.incrementActualExtension();
            ad02.incrementActualExtension();
            ad03.incrementActualExtension();
        }

        sid.updateActionDescriptionsInfo(new ArrayList<LogicObjectActionDescription>() {{
            add(ad01);
            add(ad02);
            add(ad03);
        }});
    }

    @Test
    public void removingObjects() {
        sid.removeObject(LObjectId.testId(1));
    }

    @Test
    public void removingNonExistingObjects() {
        sid.removeObject(LObjectId.testId(123));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addingSameObjectTwice() {
        sid.addObject(LObjectId.testId(1), CoordinateUtils.INSTANCE.createCoordinate(11, 11, 11));
    }

//    @Test
//    public void objectInZoneOfTwoObjectsAndSignals() {
//        Map<Integer, Map<String, Integer>> affectingObjects;
//        sid.moveObject(LObjectId.testId(2), CoordinateUtils.INSTANCE.createCoordinate(10, 10, 20));
//        affectingObjects = sid.getAffectingObjectsCurrent(2, signals);
//        boolean currentInZoneO0S1 = affectingObjects.get(0).containsKey("S1");
//        boolean currentInZoneO0S2 = affectingObjects.get(0).containsKey("S2");
//        boolean currentInZoneO1S2 = affectingObjects.get(1).containsKey("S2");
//        boolean currentInZone = currentInZoneO0S1 && currentInZoneO0S2 && currentInZoneO1S2;
//
//        affectingObjects = sid.getAffectingObjectsNeighbors(2, signals);
//        boolean neighborsInZoneO0S1 = affectingObjects.get(0).containsKey("S1");
//        boolean neighborsInZoneO0S2 = affectingObjects.get(0).containsKey("S2");
//        boolean neighborsInZoneO1S2 = affectingObjects.get(1).containsKey("S2");
//        boolean neighborsInZone = neighborsInZoneO0S1 && neighborsInZoneO0S2 && neighborsInZoneO1S2;
//
//        Assert.assertTrue(currentInZone && neighborsInZone);
//    }
//
//    @Test
//    public void objectInZoneOfOneObjectAndSignal() {
//        Map<Integer, Map<String, Integer>> affectingObjects;
//        sid.moveObject(LObjectId.testId(2), CoordinateUtils.INSTANCE.createCoordinate(10, 10, 21));
//        affectingObjects = sid.getAffectingObjectsCurrent(2, signals);
//        boolean currentInZoneO0S1 = !affectingObjects.get(0).containsKey("S1");
//        boolean currentInZoneO0S2 = affectingObjects.get(0).containsKey("S2");
//        boolean currentInZoneO1S2 = !affectingObjects.containsKey(1);
//        boolean currentInZone = currentInZoneO0S1 && currentInZoneO0S2 && currentInZoneO1S2;
//
//        affectingObjects = sid.getAffectingObjectsNeighbors(2, signals);
//        boolean neighborsInZoneO0S1 = affectingObjects.get(0).containsKey("S1");
//        boolean neighborsInZoneO0S2 = affectingObjects.get(0).containsKey("S2");
//        boolean neighborsInZoneO1 = affectingObjects.get(1).containsKey("S2");
//        boolean neighborsInZone = neighborsInZoneO0S1 && neighborsInZoneO0S2 && neighborsInZoneO1;
//
//        Assert.assertTrue(currentInZone && neighborsInZone);
//    }
//
//    @Test
//    public void objectInOneSignalNeighborZone() {
//        Map<Integer, Map<String, Integer>> affectingObjects;
//        sid.moveObject(LObjectId.testId(2), CoordinateUtils.INSTANCE.createCoordinate(10, 10, 22));
//        affectingObjects = sid.getAffectingObjectsCurrent(2, signals);
//        boolean currentInZoneO0 = !affectingObjects.containsKey(0);
//        boolean currentInZoneO1 = !affectingObjects.containsKey(1);
//        boolean currentInZone = currentInZoneO0 && currentInZoneO1;
//
//        affectingObjects = sid.getAffectingObjectsNeighbors(2, signals);
//        boolean neighborsInZoneO0S1 = !affectingObjects.get(0).containsKey("S1");
//        boolean neighborsInZoneO0S2 = affectingObjects.get(0).containsKey("S2");
//        boolean neighborsInZoneO1S2 = !affectingObjects.containsKey(1);
//        boolean neighborsInZone = neighborsInZoneO0S1 && neighborsInZoneO0S2 && neighborsInZoneO1S2;
//
//        Assert.assertTrue(currentInZone && neighborsInZone);
//    }
//
//    @Test
//    public void objectOutOfSignalsZone() {
//        Map<Integer, Map<String, Integer>> affectingObjects;
//        sid.moveObject(LObjectId.testId(2), CoordinateUtils.INSTANCE.createCoordinate(10, 10, 23));
//        affectingObjects = sid.getAffectingObjectsCurrent(2, signals);
//        boolean currentInZoneO0 = !affectingObjects.containsKey(0);
//        boolean currentInZoneO1 = !affectingObjects.containsKey(1);
//        boolean currentInZone = currentInZoneO0 && currentInZoneO1;
//
//        affectingObjects = sid.getAffectingObjectsNeighbors(2, signals);
//        boolean neighborsInZoneO0 = !affectingObjects.containsKey(0);
//        boolean neighborsInZoneO1 = !affectingObjects.containsKey(1);
//        boolean neighborsInZone = neighborsInZoneO0 && neighborsInZoneO1;
//
//        Assert.assertTrue(currentInZone && neighborsInZone);
//    }
}
