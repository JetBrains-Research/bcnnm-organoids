package com.synstorm.SimulationModel.SpaceShell;

import com.synstorm.SimulationModel.Model.Model;
import com.synstorm.common.Utils.ConfigInterfaces.CodeGeneration.ILogicalExpression;
import com.synstorm.common.Utils.ConfigInterfaces.ICondition;
import com.synstorm.common.Utils.Coordinates.ObjectState;
import com.synstorm.common.Utils.Coordinates.SignalSpreadState;
import com.synstorm.common.Utils.Coordinates.Space;
import com.synstorm.common.Utils.Coordinates.TraumaCellSelector;
import com.synstorm.common.Utils.EnumTypes.SignalSelectionType;
import com.synstorm.common.Utils.Mechanisms.MechanismResponse.ObjectMovedResponse;
import com.synstorm.common.Utils.SignalCoordinateProbability.CoordinateProbabilityR;
import com.synstorm.common.Utils.SignalCoordinateProbability.SignalsProbability;
import com.synstorm.common.Utils.SimulationEvents.IndividualEvents.ConcentrationChangeEvent;
import com.synstorm.common.Utils.SimulationEvents.IndividualEvents.ObjectMoveEventR;
import gnu.trove.list.array.TDoubleArrayList;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Shell {
    //region Fields
    private final Space space;
    //endregion

    //region Constructors
    public Shell(int spaceSize, int[] signalsRadius) {
        space = new Space(spaceSize, signalsRadius);
    }
    //endregion

    //region Public Methods
    public boolean addNeighbor(int source, int neighbour, ObjectState state) {
        final int[] cSource = space.getCoordinateByObject(source);
        final int[] cNeighbour = shiftCoordinates(cSource);
        if (!space.contains(cNeighbour))
            return false;

        return addObject(neighbour, cNeighbour, state);
    }

    public boolean replaceObject(int origin, int newObj, ObjectState state) {
        final int[] cOrigin = space.getCoordinateByObject(origin);
        space.removeObject(origin);
        return space.createObject(newObj, cOrigin, state);
    }

    public boolean addObject(int objectId, int[] coordinate, ObjectState state) {
        return space.createObject(objectId, coordinate, state);
    }

    public void registerSignal(int objectId, int type) {
        space.addSignal(objectId, type);
    }

    public void removeObject(int objectId) {
        space.removeObject(objectId);
    }

    public void moveLObjects(Set<ObjectMovedResponse> movingDetails) {
        final long tick = Model.INSTANCE.getCurrentTick();
        ArrayList<int[]> objectsToMove = getMovingObjectWithoutConflicts(movingDetails);
        objectsToMove.forEach(obj -> moveLObject(obj, tick));
    }

    public int spreadSignal(int objId, int type) {
        return space.spreadSignal(objId, type);
    }

    public int gatherSignal(int objId, int type) {
        return space.gatherSignal(objId, type);
    }

    public void recalculateSignalSpreading() {
        //        space.recalculateSignals();

        List<ConcentrationChangeEvent> events = space.recalculateSignals();

        // foreach cannot mutate
        // apply make a copy and I want to avoid it
        for(int i = 0; i < events.size(); ++i)
        {
            events.get(i).setTick(Model.INSTANCE.getNextTick());
        }

        events.forEach(Model.INSTANCE::proceedExportAndStatistic);
    }

    public boolean updateObjectState(int objectId, ObjectState state) {
        return space.changeObjectState(objectId, state);
    }

    public int getCellIdByCoordinate(int[] coordinate) {
        if (space.isCoordinateHasVolume(coordinate))
            return space.getVolumeObjectByCoordinate(coordinate);
        else
            return -1;
    }

    public int getCellIdByNonVolumeId(int objId) {
        return space.getVolumeObjectByNonVolumeObject(objId);
    }

    public int[] getNegativeCoordinate() {
        return space.getNegativeCoordinate();
    }

    public List<SignalSpreadState> getSignalPointsState() {
        return space.getSignalPointsState();
    }

    @Nullable
    public CoordinateProbabilityR getFreeCoordinateCandidate(int objectId, double checkProbability, ICondition condition) {
        final int[] cSource = space.getCoordinateByObject(objectId);
        final ArrayList<int[]> checkCoordinates = getNeighborhoodEmptyCoordinates(cSource);
        return getCoordinateCandidate(checkProbability, condition, cSource, checkCoordinates);
    }

    @Nullable
    public CoordinateProbabilityR getAnyCoordinateCandidate(int objectId, double checkProbability, ICondition condition) {
        final int[] cSource = space.getCoordinateByObject(objectId);
        final ArrayList<int[]> checkCoordinates = getNeighborhood(cSource);
        return getCoordinateCandidate(checkProbability, condition, cSource, checkCoordinates);
    }

    public boolean checkObjectPredicate(int objectId, ICondition condition) {
        final int[] cSource = space.getCoordinateByObject(objectId);
        return checkPredicateAtCoordinate(cSource, condition);
    }

    public int[] getCoordinate(int objectId) {
        return space.getCoordinateByObject(objectId);
    }

    public int getSignalRadiusById(int objectId, int signal) { return space.getSignalRadiusById(objectId, signal); }

    public ArrayList<Integer> selectCellsForTrauma(double ratio, TIntSet valid) {
        final TraumaCellSelector cellSelector = new TraumaCellSelector(space);
        return cellSelector.getCells(ratio, valid, Model.INSTANCE.getRandom());
    }
    //endregion

    //region Private Methods
    private void moveLObject(@NotNull int[] movingObject, long tick) {
        final int id = movingObject[3];
        final int[] currentCoordinate = space.getCoordinateByObject(id);
        final ObjectMoveEventR objectMoveEvent = new ObjectMoveEventR(tick, id, movingObject, currentCoordinate);
        Model.INSTANCE.proceedExportAndStatistic(objectMoveEvent);
        space.moveObject(movingObject);
    }

    private ArrayList<int[]> getMovingObjectWithoutConflicts(@NotNull Set<ObjectMovedResponse> movingDetails) {
        final ArrayList<int[]> objectsToMove = new ArrayList<>();
        final TIntIntHashMap potentiallyOccupied = new TIntIntHashMap();
        final TDoubleArrayList probabilities = new TDoubleArrayList();
        int idx = 0;

        for (ObjectMovedResponse mDetails : movingDetails) {
            final int[] toOccupy = mDetails.getCoordinate();
            if (space.isCoordinateHasVolume(toOccupy) && space.isObjectHasVolume(mDetails.getId()))                     // Potential coordinate were busy during cell division
                continue;

            final int flatCoord = space.getFlatCoordinate(toOccupy);
            final int[] toAdd = Arrays.copyOf(toOccupy, 4);
            toAdd[3] = mDetails.getId();

            if (space.isObjectHasVolume(mDetails.getId())) {                                                            // Moving object with volume
                if (!potentiallyOccupied.contains(flatCoord)) {
                    objectsToMove.add(toAdd);
                    potentiallyOccupied.put(flatCoord, idx);
                    probabilities.add(mDetails.getProbability());
                    ++idx;
                } else {
                    final int switchIdx = potentiallyOccupied.get(flatCoord);
                    final double p1 = probabilities.get(switchIdx);
                    final double p2 = mDetails.getProbability();
                    if (p2 > p1 || ((p2 == p1) && Model.INSTANCE.nextDouble() > 0.5)) {
                        objectsToMove.remove(switchIdx);
                        objectsToMove.add(switchIdx, toAdd);
                        probabilities.setQuick(switchIdx, p2);
                    }
                }
            } else {                                                                                                    // Moving object without volume
                objectsToMove.add(toAdd);
                ++idx;
            }
        }

        return objectsToMove;
    }

    private int[] shiftCoordinates(int[] entryCoordinate) {
        final long tick = Model.INSTANCE.getCurrentTick();
        final TIntHashSet shiftTrack = new TIntHashSet();
        shiftTrack.add(space.getFlatCoordinate(entryCoordinate));

        final int stepRestriction = 5;
        final int checkCount = stepRestriction + 1;
        final int trackSize = checkCount + 1;
        final int[] ids = new int[checkCount];
        final int[][] track = new int[trackSize][3];
        final int[][] sVector = new int[trackSize][3];
        int shiftedIdx = -1;
        track[0] = entryCoordinate;
        sVector[0] = getNearRandomMovableVector(entryCoordinate, shiftTrack);

        for (int i = 1; i < trackSize; ++i) {
            final int prevIdx = i - 1;
            if (space.isZeroVector(sVector[prevIdx]))
                return space.getNegativeCoordinate();

            track[i] = space.getCoordinate(track[prevIdx], sVector[prevIdx]);
            if (space.contains(track[i])) {
                if (!space.isCoordinateHasVolume(track[i]))
                    break;

                final int flatCoord = space.getFlatCoordinate(track[i]);
                if (space.isCoordinateMovable(flatCoord) && !shiftTrack.contains(flatCoord)) {
                    sVector[i] = sVector[prevIdx];
                    ids[prevIdx] = space.getVolumeObjectByCoordinate(track[i]);
                    shiftTrack.add(space.getFlatCoordinate(track[i]));
                    ++shiftedIdx;
                    continue;
                }
            }

            sVector[prevIdx] = getNearRandomMovableVector(track[prevIdx], shiftTrack);
            --i;
        }

        if (shiftedIdx == stepRestriction)
            return space.getNegativeCoordinate();

        for (int i = shiftedIdx; i >= 0; --i) {
            final ObjectMoveEventR objectMoveEvent = new ObjectMoveEventR(tick, ids[i], track[i + 2], track[i + 1]);
            Model.INSTANCE.proceedExportAndStatistic(objectMoveEvent);
            space.moveObject(ids[i], track[i + 2][0], track[i + 2][1], track[i + 2][2]);
        }

        return new int[]{track[1][0], track[1][1], track[1][2]};
    }

    private boolean checkPredicateAtCoordinate(int[] checkCoordinate, @NotNull ICondition condition) {
        boolean ruleResult = true;
        final int[] signals = condition.getSignals();

        if (condition.getRule() != null) {
            final ILogicalExpression predicate = condition.getRule();
            final double[] signalsValues = new double[signals.length];
            for (int i = 0; i < signals.length; i++) {
                signalsValues[i] = space.getConcentration(checkCoordinate, signals[i]);
            }

            ruleResult = predicate.compute(signalsValues);
            if (ruleResult && condition.getDistribution() != null) {
                final double signalValue = space.getConcentration(checkCoordinate, condition.getDistributionVariable());
                final double eventProbability = condition.getDistribution().getEventProbability(new double[]{signalValue});
                final double eventRandom = Model.INSTANCE.nextDouble();
                ruleResult = eventRandom >= eventProbability;
            }
        }

        return ruleResult;
    }

    @Nullable
    private CoordinateProbabilityR getCoordinateCandidate(double checkProbability, @NotNull ICondition condition, @NotNull int[] coordinate,  @NotNull ArrayList<int[]> checkCoordinates) {
        final int selectionSignal = condition.getConditionParameter().getSelectionLigand();
        final SignalSelectionType selectionType = condition.getConditionParameter().getSelectionType();
        final double currentIntensity = space.getConcentration(coordinate, selectionSignal);

        final SignalsProbability signalP = new SignalsProbability();
        for (int[] checkCoordinate : checkCoordinates) {
            if (checkPredicateAtCoordinate(checkCoordinate, condition)) {
                final double selectionIntensity = space.getConcentration(checkCoordinate, selectionSignal);
                final double grad = selectionType.calcProbability(currentIntensity, selectionIntensity);
                if (grad > 0) {
                    signalP.addSignalProbability(selectionType.calcProbability(currentIntensity, selectionIntensity));
                    signalP.addCoordinateProbability(checkCoordinate);
                }
            }
        }

        return signalP.getCoordinateCandidate(checkProbability);
    }

    private ArrayList<int[]> getNeighborhood(int[] coordinate) {
        return space.getNeighborhood(coordinate);
    }

    private ArrayList<int[]> getNeighborhoodEmptyCoordinates(int[] coordinate) {
        final EnumMap<ObjectState, ArrayList<int[]>> ambit = space.getNeighborsByState(coordinate, ObjectState.NoVolume);
        return ambit.get(ObjectState.NoVolume);
    }

    private int[] getNearRandomMovableVector(int[] coordinate, TIntSet forbiddenCoordinates) {
        final EnumMap<ObjectState, ArrayList<int[]>> ambit = space.getNeighborVectorsByState(coordinate, ObjectState.VolumeMovable, forbiddenCoordinates);
        return getNearRandomMovableVector(ambit);
    }

    private int[] getNearRandomMovableVector(@NotNull EnumMap<ObjectState, ArrayList<int[]>> ambit) {
        final ArrayList<int[]> noVolume = ambit.get(ObjectState.NoVolume);
        final ArrayList<int[]> volumeMovable = ambit.get(ObjectState.VolumeMovable);
        if (noVolume.size() > 0) {
            final int randIndex = Model.INSTANCE.nextInt(noVolume.size());
            return noVolume.get(randIndex);
        } else if (volumeMovable.size() > 0) {
            int randIndex = Model.INSTANCE.nextInt(volumeMovable.size());
            return volumeMovable.get(randIndex);
        } else
            return new int[]{0, 0, 0};
    }
    //endregion
}
