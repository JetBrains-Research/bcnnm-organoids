package com.synstorm.SimulationModel.SpaceShell;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.synstorm.SimulationModel.Containers.ModelContainer;
import com.synstorm.SimulationModel.Graph.SignalIntensityDispatcher;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.ObjectMovedResponse;
import com.synstorm.SimulationModel.LogicObject.LogicObjects.GlowingPoint;
import com.synstorm.SimulationModel.ModelAction.LogicObjectActionDescription;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.AxonId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.LObjectId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
import com.synstorm.common.Utils.EnumTypes.LogicObjectType;
import com.synstorm.common.Utils.SignalCoordinateProbability.SignalProbabilityMap;
import com.synstorm.common.Utils.SpaceUtils.CoordinateUtils;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;
import com.synstorm.common.Utils.SpaceUtils.SpaceVector;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Class for 3 dimensional space where all cell objects and gp are interacting with each other
 * Created by dvbozhko on 09/06/16.
 */

@Model_v0
@NonProductionLegacy
public class DimensionalSpace {
    //region Fields
    private UUID individualId;
    private BiMap<LObjectId, ICoordinate> coordinateMap;
    private BiMap<LObjectId, ICoordinate> permanentBusyCoordinateMap;

    private Map<LObjectId, ICoordinate> axonEndingCoordinateMap;
    private Set<ICoordinate> coordinateSet;
    private Set<ICoordinate> permanentBusyCoordinateSet;
    private Set<GlowingPoint> glowingPoints;

    private SignalIntensityDispatcher siDispatcher;
    //endregion

    //region Constructors
    public DimensionalSpace(UUID id) {
        individualId = id;
        coordinateMap = HashBiMap.create();
        permanentBusyCoordinateMap = HashBiMap.create();
        axonEndingCoordinateMap = new HashMap<>();
        permanentBusyCoordinateSet = new HashSet<>();
        coordinateSet = new HashSet<>();
        glowingPoints = new HashSet<>();
        siDispatcher = new SignalIntensityDispatcher(ModelLoader.getAllFactors());
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    public boolean isCoordinateBusy(ICoordinate coordinate) {
        return coordinateSet.contains(coordinate);
    }

    public ICoordinate getCoordinateById(CellId id) {
        return coordinateMap.get(id);
    }

    public CellId getIdByCoordinate(ICoordinate coordinate) {
        return (CellId) coordinateMap.inverse().get(coordinate);
    }

    public boolean matchIdWithCoordinate(CellId id, ICoordinate coordinate) {
        return coordinateMap.get(id).equals(coordinate);
    }

    public void addGlowingPoint(GlowingPoint glowingPoint) {
        glowingPoints.add(glowingPoint);
        siDispatcher.addObject(glowingPoint.getObjectId(), glowingPoint.getCoordinate());
        siDispatcher.updateActionDescriptionsInfo(new ArrayList<LogicObjectActionDescription>() {{
            add(glowingPoint.getGpActionDescription());
        }});
    }

    public void updateCellsForGpWithKp(CellId cellId, Set<String> signals) {
        siDispatcher.updateCellsForGpWithKp(cellId, signals);
    }

    public boolean tryAddObject(ICoordinate coordinate) {
        if (coordinate == null)
            return false;

        boolean tryShift = true;
        if (!isCoordinateEmpty(coordinate))
            tryShift = shiftCoordinates(coordinate);

        return tryShift;
    }

    public void addLObject(LObjectId lObjectId, ICoordinate coordinate, boolean permanent) {
        registerLObject(lObjectId, coordinate, permanent);
        siDispatcher.addObject(lObjectId, coordinate);
    }

    public void addAxon(AxonId axonId, ICoordinate coordinate) {
        axonEndingCoordinateMap.put(axonId, coordinate);
        siDispatcher.addObject(axonId, coordinate);
    }

    public void moveAxonEnding(AxonId axonId, ICoordinate coordinate) {
        axonEndingCoordinateMap.replace(axonId, coordinate);
        siDispatcher.moveObject(axonId, coordinate);
    }

    public void deleteAxon(AxonId axonId) {
        axonEndingCoordinateMap.remove(axonId);
        siDispatcher.removeObject(axonId);
    }

    public void removeLObject(LObjectId lObjectId) {
        ICoordinate coordinateToRemove = coordinateMap.get(lObjectId);

        coordinateMap.remove(lObjectId);
        coordinateSet.remove(coordinateToRemove);

        if (permanentBusyCoordinateMap.containsKey(lObjectId)) {
            permanentBusyCoordinateMap.remove(lObjectId);
            permanentBusyCoordinateSet.remove(coordinateToRemove);
        }

        siDispatcher.removeObject(lObjectId);
    }

    public void updateActionDescriptionsInfo(List<LogicObjectActionDescription> adList) {
        siDispatcher.updateActionDescriptionsInfo(adList);
    }

    public void setSignalIgnore(LObjectId receiverVertexId, LObjectId emitterVertexId, String signal) {
        siDispatcher.setSignalIgnore(receiverVertexId, emitterVertexId, signal);
    }

    public void setSignalReceive(LObjectId receiverVertexId, LObjectId emitterVertexId, String signal) {
        siDispatcher.setSignalReceive(receiverVertexId, emitterVertexId, signal);
    }

    public SignalProbabilityMap affectingCurrentProbabilities(LObjectId objectId, Set<String> signals) {
        ICoordinate lObjCoordinate = getCoordinateByObjectId(objectId);
        List<ICoordinate> checkCoordinates = new ArrayList<ICoordinate>() {{
            add(lObjCoordinate);
        }};
        return siDispatcher.getSignalProbabilityMap(objectId, signals, checkCoordinates);
    }

    public SignalProbabilityMap affectingAllNeighborProbabilities(LObjectId objectId, Set<String> signals) {
        ICoordinate lObjCoordinate = getCoordinateByObjectId(objectId);
        List<ICoordinate> checkCoordinates = CoordinateUtils.INSTANCE.makeNeighborCoordinates(lObjCoordinate);
        checkCoordinates.add(lObjCoordinate);
        return siDispatcher.getSignalProbabilityMap(objectId, signals, checkCoordinates);
    }

    public SignalProbabilityMap affectingFreeNeighborProbabilities(LObjectId objectId, Set<String> signals) {
        ICoordinate lObjCoordinate = getCoordinateByObjectId(objectId);
        List<ICoordinate> checkCoordinates = CoordinateUtils.INSTANCE
                .makeNeighborCoordinates(lObjCoordinate).stream()
                .filter(coordinate -> !coordinateSet.contains(coordinate))
                .collect(Collectors.toList());
        checkCoordinates.add(lObjCoordinate);
        return siDispatcher.getSignalProbabilityMap(objectId, signals, checkCoordinates);
    }

    public void updatePermanencyAfterProliferation(LObjectId lObjectId, boolean permanent) {
        if (permanent && !permanentBusyCoordinateMap.containsKey(lObjectId)) {
            permanentBusyCoordinateMap.put(lObjectId, coordinateMap.get(lObjectId));
            permanentBusyCoordinateSet.add(coordinateMap.get(lObjectId));
        }
    }

    public void moveLObjects(Set<ObjectMovedResponse> movingDetails) {
        Set<LObjectId> objectsWithChangedPositions = new HashSet<>();
        Map<LObjectId, ICoordinate> movedWithPreviousCoordinates = new HashMap<>();
        Map<ICoordinate, Entry<LObjectId, Double>> objectsToMove = getMovingObjectWithoutConflicts(movingDetails);
        objectsToMove.entrySet().stream()
                .forEach(i -> movedWithPreviousCoordinates
                        .put(i.getValue().getKey(), moveLObject(i, objectsWithChangedPositions)));
    }

    @Nullable
    public ICoordinate getNearRandomCoordinateForCreation(ICoordinate coordinate) {
        return getNearRandomCoordinate(coordinate);
    }
    //endregion

    //region Private Methods
    private ICoordinate moveLObject(Entry<ICoordinate, Entry<LObjectId, Double>> movingObjectEntry,
                                    Set<LObjectId> objectsWithChangedPositions) {
        LObjectId objId = movingObjectEntry.getValue().getKey();
        ICoordinate currentObjectCoordinate = coordinateMap.get(objId);
        ICoordinate destinationObjectCoordinate = movingObjectEntry.getKey();
        coordinateSet.remove(currentObjectCoordinate);
        coordinateSet.add(destinationObjectCoordinate);
        coordinateMap.replace(objId, destinationObjectCoordinate);
        objectsWithChangedPositions.add(objId);
        siDispatcher.moveObject(objId, destinationObjectCoordinate);

        return currentObjectCoordinate;
    }

    private Map<ICoordinate, Entry<LObjectId, Double>> getMovingObjectWithoutConflicts
            (Set<ObjectMovedResponse> movingDetails) {
        Map<ICoordinate, Entry<LObjectId, Double>> objectsToMove = new HashMap<>();

        for (ObjectMovedResponse mDetails : movingDetails) {
            if (permanentBusyCoordinateMap.containsKey(mDetails.getId()))
                continue;
            ICoordinate toOccupy = mDetails.getCoordinate();
            if (coordinateSet.contains(toOccupy))
                continue;

            if (!objectsToMove.containsKey(toOccupy)) {
                objectsToMove.put(toOccupy, new SimpleEntry<>(mDetails.getId(), mDetails.getProbability()));
            } else {
                double p1 = objectsToMove.get(toOccupy).getValue();
                double p2 = mDetails.getProbability();
                if (p2 > p1 || ((p2 == p1) && ModelContainer.INSTANCE.nextDouble(individualId) > 0.5))
                    objectsToMove.put(toOccupy, new SimpleEntry<>(mDetails.getId(), mDetails.getProbability()));
            }
        }

        return objectsToMove;
    }

    private boolean shiftCoordinates(ICoordinate entryCoordinate) {
        if (CoordinateUtils.INSTANCE.getVolume().equals(BigInteger.valueOf(coordinateMap.size())))
            return false;

        BiMap<ICoordinate, LObjectId> inverseCoordinateMap = coordinateMap.inverse();
        BiMap<LObjectId, ICoordinate> changedCoordinates = HashBiMap.create();

        ICoordinate rndNearCoordinate = getNearRandomCoordinate(entryCoordinate);
        if (rndNearCoordinate == null)
            return false;

        SpaceVector sVector = CoordinateUtils.INSTANCE.createSpaceVector(entryCoordinate, rndNearCoordinate);
        ICoordinate toCheck;
        ICoordinate toShift = entryCoordinate;
        Set<ICoordinate> track = new HashSet<ICoordinate>() {{ add(entryCoordinate);}};

        while (true) {
            LObjectId objToChange = inverseCoordinateMap.get(toShift);
            toCheck = CoordinateUtils.INSTANCE.createCoordinate(toShift, sVector);

            if (toCheck == null || permanentBusyCoordinateSet.contains(toCheck) || track.contains(toCheck)) {
                rndNearCoordinate = getNearRandomCoordinate(toShift, track);
                if (rndNearCoordinate == null)
                    return false;

                sVector = CoordinateUtils.INSTANCE.createSpaceVector(toShift, rndNearCoordinate);
                continue;
            }

            track.add(toShift);
            toShift = toCheck;
            changedCoordinates.put(objToChange, toShift);

            if (!coordinateSet.contains(toCheck))
                break;
        }

        changedCoordinates.entrySet().stream()
                .forEach(item -> coordinateMap.remove(item.getKey()));

        changedCoordinates.entrySet().stream()
                .forEach(item -> coordinateMap.put(item.getKey(), item.getValue()));

        coordinateSet.remove(entryCoordinate);
        coordinateSet.add(toCheck);

        return true;
    }

    private void registerLObject(LObjectId lObjectId, ICoordinate coordinate, boolean permanent) {
        coordinateMap.put(lObjectId, coordinate);
        coordinateSet.add(coordinate);
        if (permanent) {
            permanentBusyCoordinateMap.put(lObjectId, coordinate);
            permanentBusyCoordinateSet.add(coordinate);
        }
    }

    private ICoordinate getCoordinateByObjectId(LObjectId objectId) {
        return objectId.getType() == LogicObjectType.Axon
                ? axonEndingCoordinateMap.get(objectId) : coordinateMap.get(objectId);
    }

    @Nullable
    private ICoordinate getNearRandomCoordinate(ICoordinate coordinate, Set<ICoordinate> track) {
        List<ICoordinate> nearCoordinates = CoordinateUtils.INSTANCE.makeNeighborCoordinates(coordinate);
        track.stream().forEach(item -> {
            if (nearCoordinates.contains(item))
                nearCoordinates.remove(item);
        });

        return getNearRandomCoordinate(nearCoordinates);
    }

    @Nullable
    private ICoordinate getNearRandomCoordinate(ICoordinate coordinate) {
        List<ICoordinate> nearCoordinates = CoordinateUtils.INSTANCE.makeNeighborCoordinates(coordinate);
        return getNearRandomCoordinate(nearCoordinates);
    }

    @Nullable
    private ICoordinate getNearRandomCoordinate(List<ICoordinate> nearCoordinates) {
        List<ICoordinate> emptyCoordinates = new ArrayList<>();
        Iterator<ICoordinate> iterator = nearCoordinates.iterator();

        while (iterator.hasNext()) {
            ICoordinate coordinate = iterator.next();
            if (isCoordinateEmpty(coordinate))
                emptyCoordinates.add(coordinate);
            else if (isCoordinatePermanentlyBusy(coordinate))
                iterator.remove();
        }

        if (emptyCoordinates.size() > 0) {
            int randIndex = ModelContainer.INSTANCE.nextInt(individualId, emptyCoordinates.size());
            return emptyCoordinates.get(randIndex);
        } else if (nearCoordinates.size() > 0) {
            int randIndex = ModelContainer.INSTANCE.nextInt(individualId, nearCoordinates.size());
            return nearCoordinates.get(randIndex);
        } else
            return null;
    }

    private boolean isCoordinateEmpty(ICoordinate coordinate) {
        return !coordinateSet.contains(coordinate);
    }

    private boolean isCoordinatePermanentlyBusy(ICoordinate coordinate) {
        return permanentBusyCoordinateSet.contains(coordinate);
    }
    //endregion
}
