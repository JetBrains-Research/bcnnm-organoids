package com.synstorm.common.Utils.Coordinates;

import com.synstorm.common.Utils.SimulationEvents.IndividualEvents.ConcentrationChangeEvent;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import org.apache.commons.math3.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static java.lang.Math.*;

public class Space {
    //region Fields
    private final int signalCount;
    private final int size;
    private final int maxCoordinate;
    private final int[] negativeCoordinate;
    private final int[] signalsRadius;
    private final double[][] sValueCoefficients;
    private CellState[] cube;
    private final TIntObjectMap<SignalBeamer> idToObject;
    private final List<int[]> changedObjSignalIds;
    private final EnumMap<ObjectState, ArrayList<int[]>> rs;
    //endregion

    //region Inner classes
    private class CellState {
        public final double[] concentrations;
        public final TIntSet objects;
        public ObjectState objectState;
        public int volumeObject;

        public CellState() {
            volumeObject = -1;
            objectState = ObjectState.NoVolume;
            objects = new TIntHashSet();
            concentrations = new double[signalCount];
            Arrays.fill(concentrations, 0);
        }
    }

    private class SignalBeamer {
        public int x, y, z;
        public final int[] currRadius;
        public final int[] memRadius;
        public ObjectState state;

        SignalBeamer(int x_, int y_, int z_, ObjectState state_) {
            x = x_;
            y = y_;
            z = z_;
            state = state_;

            currRadius = new int[signalCount];
            memRadius = new int[signalCount];

            Arrays.fill(currRadius, -1);
            Arrays.fill(memRadius, -1);
        }

        public boolean hasSignal(int i) {
            return currRadius[i] != -1;
        }
    }
    //endregion

    //region Constructors
    public Space(int spaceSize, int[] signalsRad) {
        size = spaceSize;
        maxCoordinate = size - 1;
        signalCount = signalsRad.length;
        signalsRadius = signalsRad;
        negativeCoordinate = new int[]{-1, -1, -1};
        sValueCoefficients = new double[signalCount][];
        for (int i = 0; i < signalCount; i++)
            sValueCoefficients[i] = new double[signalsRad[i]];

        idToObject = new TIntObjectHashMap<>();
        changedObjSignalIds = new ArrayList<>();
        rs = new EnumMap<>(ObjectState.class);
        reset();
    }
    //endregion

    //region Methods
    public int getSize() {
        return size;
    }

    public int[] getNegativeCoordinate() {
        return negativeCoordinate;
    }

    public int getCellCoint() {
        return (int) idToObject.valueCollection().stream().filter(p -> p.state.ordinal() > ObjectState.NoVolume.ordinal()).count();
    }

    public ArrayList<Integer> getCellIds() {
        ArrayList<Integer> res = new ArrayList<>(); // почему-то конструктор с Arrays.asList(...) не работает
        idToObject.forEachEntry((k, v) -> {
            if (v.state.ordinal() > ObjectState.NoVolume.ordinal()) {
                res.add(k);
                return true;
            }
            return false;
        });

        return res;
    }

    public TIntObjectMap<SignalBeamer> getObjects() {
        return idToObject;
    }

    public List<SignalSpreadState> getSignalPointsState() {
        List<SignalSpreadState> res = new ArrayList<>();

        idToObject.forEachEntry((k, v) -> {
            for(int i = 0; i < v.currRadius.length; ++i) {
                if(v.hasSignal(i)) {
                    res.add(new SignalSpreadState(k, v.currRadius[i], i));
                }
            }
            return true;
        });

        return res;
    }

    public void reset() {
        final int sizeCube = size * size * size;
        cube = new CellState[sizeCube];

        for (int i = 0; i < sizeCube; ++i)
            cube[i] = new CellState();

        for (int i = 0; i < signalCount; ++i)
            for (int j = 0; j < signalsRadius[i]; j++)
                sValueCoefficients[i][j] = Math.pow(1. - 1. * j / signalsRadius[i], 3);

        for (ObjectState objectState : ObjectState.values())
            rs.put(objectState, new ArrayList<>());

        idToObject.clear();
    }

    @Nullable
    public int[] getVector(@NotNull int[] source, @NotNull int[] dest) {
        final int x = dest[0] - source[0];
        final int y = dest[1] - source[1];
        final int z = dest[2] - source[2];

        if (Math.abs(x) < 2 && Math.abs(y) < 2 && Math.abs(z) < 2)
            return new int[]{x, y, z};

        return null;
    }

    public boolean isZeroVector(@NotNull final int[] vector) {
        return (vector[0] == 0) && (vector[1] == 0) && (vector[2] == 0);
    }

    public boolean contains(@NotNull final int[] coordinate) {
        final int x = coordinate[0];
        final int y = coordinate[1];
        final int z = coordinate[2];
        final int min = Math.min(Math.min(x, y), z);
        final int max = Math.max(Math.max(x, y), z);
        return min >= 0 && max <= maxCoordinate;
    }

    public int[] getCoordinate(@NotNull int[] source, @NotNull int[] vector) {
        final int x = source[0] + vector[0];
        final int y = source[1] + vector[1];
        final int z = source[2] + vector[2];
        return new int[]{x, y, z};
    }

    public int getSignalRadiusById(int objectId, int signal) {
        final SignalBeamer b = idToObject.get(objectId);
        if(b == null) {
            return -1;
        }

        return b.currRadius[signal];
    }

    public int[] getCoordinateByObject(int objId) {
        if (!idToObject.containsKey(objId))
            return negativeCoordinate;

        final SignalBeamer signalBeamer = idToObject.get(objId);
        return new int[]{signalBeamer.x, signalBeamer.y, signalBeamer.z};
    }

    public boolean createObject(int objId, int[] coordinate, ObjectState state) {
        final int x = coordinate[0];
        final int y = coordinate[1];
        final int z = coordinate[2];

        if (idToObject.containsKey(objId))
            return false;

        final int idx = getFlatCoordinate(x, y, z);
        final CellState cState = cube[idx];
        if (cState.objectState.ordinal() > 0 && state.ordinal() > 0)
            return false;

        cState.objects.add(objId);
        if (state.ordinal() > cState.objectState.ordinal())
            cState.objectState = state;
        if (state.ordinal() > 0)
            cState.volumeObject = objId;
        idToObject.put(objId, new SignalBeamer(x, y, z, state));

        return true;
    }

    public boolean changeObjectState(int objId, ObjectState state) {
        final SignalBeamer b = idToObject.get(objId);
        final int idx = getFlatCoordinate(b.x, b.y, b.z);
        final CellState cState = cube[idx];
        if (cState.volumeObject != -1 && cState.volumeObject != objId)
            return false;

        cState.objectState = state;
        if (cState.objectState.ordinal() == 0)
            cState.volumeObject = -1;
        else
            cState.volumeObject = objId;
        b.state = state;
        return true;
    }

    public boolean isCoordinateMovable(int flatCoordinate) {
        return getCoordinateState(flatCoordinate) != ObjectState.VolumeImmovable;
    }

    public boolean isCoordinateHasVolume(int[] coordinate) {
        return getCoordinateState(coordinate).ordinal() > 0;
    }

    public boolean isObjectHasVolume(int objId) {
        final SignalBeamer b = idToObject.get(objId);
        return b.state.ordinal() > 0;
    }

    public ObjectState getCoordinateState(int[] coordinate) {
        final int x = coordinate[0];
        final int y = coordinate[1];
        final int z = coordinate[2];
        final int idx = getFlatCoordinate(x, y, z);
        return cube[idx].objectState;
    }

    public ObjectState getCoordinateState(int flatCoordinate) {
        return cube[flatCoordinate].objectState;
    }

    public void addSignal(int objId, int type) {
        addSignal(objId, type, 0);
    }

    public void addSignal(int objId, int type, int currentRadius) {
        final SignalBeamer b = idToObject.get(objId);
        if (b.currRadius[type] == -1) {  // if signal is not registered for this object yet
            b.currRadius[type] = currentRadius;
            b.memRadius[type] = currentRadius;
        }
    }

    public void removeObject(int objId) {
        final SignalBeamer b = idToObject.get(objId);
        final int idx = getFlatCoordinate(b.x, b.y, b.z);
        final CellState cState = cube[idx];
        idToObject.remove(objId);
        cState.objects.remove(objId);
        if (b.state.ordinal() > 0) {
            cState.objectState = ObjectState.NoVolume;
            cState.volumeObject = -1;
        }
    }

    public int[] getObjectsByCoordinate(int[] coord) {
        final int x = coord[0];
        final int y = coord[1];
        final int z = coord[2];
        final int idx = getFlatCoordinate(x, y, z);

        return cube[idx].objects.toArray();
    }

    public int getVolumeObjectByCoordinate(int[] coord) {
        final int x = coord[0];
        final int y = coord[1];
        final int z = coord[2];
        final int idx = getFlatCoordinate(x, y, z);
        return cube[idx].volumeObject;
    }

    public int getVolumeObjectByNonVolumeObject(int objId) {
        if (!idToObject.containsKey(objId))
            return -1;

        final SignalBeamer signalBeamer = idToObject.get(objId);
        final int idx = getFlatCoordinate(signalBeamer.x, signalBeamer.y, signalBeamer.z);
        return cube[idx].volumeObject;
    }

    public double getConcentration(int[] coord, int type) {
        final int x = coord[0];
        final int y = coord[1];
        final int z = coord[2];
        final int idx = getFlatCoordinate(x, y, z);

        return cube[idx].concentrations[type];
    }

    public ArrayList<int[]> getNeighborhood(int[] coord) {
        final int x = coord[0];
        final int y = coord[1];
        final int z = coord[2];

        ArrayList<int[]> res = new ArrayList<>();

        for (int x_pos = Math.max(x - 1, 0); x_pos <= min(x + 1, maxCoordinate); ++x_pos) {
            for (int y_pos = Math.max(y - 1, 0); y_pos <= min(y + 1, maxCoordinate); ++y_pos) {
                for (int z_pos = Math.max(z - 1, 0); z_pos <= min(z + 1, maxCoordinate); ++z_pos) {
                    res.add(new int[]{x_pos, y_pos, z_pos});
                }
            }
        }

        return res;
    }

    public EnumMap<ObjectState, ArrayList<int[]>> getNeighborsByState(int[] coord, ObjectState lessFilter) {
        final int x = coord[0];
        final int y = coord[1];
        final int z = coord[2];

        rs.forEach((k, v) -> v.clear());

        for (int x_pos = max(x - 1, 0); x_pos <= min(x + 1, maxCoordinate); ++x_pos) {
            for (int y_pos = max(y - 1, 0); y_pos <= min(y + 1, maxCoordinate); ++y_pos) {
                for (int z_pos = max(z - 1, 0); z_pos <= min(z + 1, maxCoordinate); ++z_pos) {
                    final int idx = getFlatCoordinate(x_pos, y_pos, z_pos);
                    final ObjectState state = cube[idx].objectState;
                    if (state.ordinal() <= lessFilter.ordinal())
                        rs.get(state).add(new int[]{x_pos, y_pos, z_pos});
                }
            }
        }

        return rs;
    }

    public EnumMap<ObjectState, ArrayList<int[]>> getNeighborVectorsByState(int[] coord, ObjectState lessFilter, TIntSet forbiddenCoordinates) {
        final int x = coord[0];
        final int y = coord[1];
        final int z = coord[2];

        rs.forEach((k, v) -> v.clear());

        for (int x_pos = max(x - 1, 0); x_pos <= min(x + 1, maxCoordinate); ++x_pos) {
            for (int y_pos = max(y - 1, 0); y_pos <= min(y + 1, maxCoordinate); ++y_pos) {
                for (int z_pos = max(z - 1, 0); z_pos <= min(z + 1, maxCoordinate); ++z_pos) {
                    final int idx = getFlatCoordinate(x_pos, y_pos, z_pos);
                    if (forbiddenCoordinates.contains(idx))
                        continue;
                    final ObjectState state = cube[idx].objectState;
                    if (state.ordinal() <= lessFilter.ordinal())
                        rs.get(state).add(new int[]{x_pos - x, y_pos - y, z_pos - z});
                }
            }
        }

        return rs;
    }

    public void moveObject(int[] moveObj) {
        final int x = moveObj[0];
        final int y = moveObj[1];
        final int z = moveObj[2];
        final int id = moveObj[3];
        moveObject(id, x, y, z);
    }

    public void moveObject(int objId, int x, int y, int z) {
        final SignalBeamer b = idToObject.get(objId);

        final int new_idx = getFlatCoordinate(x, y, z);
        final int old_idx = getFlatCoordinate(b.x, b.y, b.z);
        final CellState oldCState = cube[old_idx];
        final CellState newCState = cube[new_idx];
        final boolean hasVolume = b.state.ordinal() > 0;

        oldCState.objects.remove(objId);
        newCState.objects.add(objId);

        if (hasVolume) {
            oldCState.objectState = ObjectState.NoVolume;
            oldCState.volumeObject = -1;
            newCState.objectState = b.state;
            newCState.volumeObject = objId;
        }

        final int move_dist = Math.max(Math.max(Math.abs(b.x - x), Math.abs(b.y - y)), Math.abs(b.z - z));

        // update
        b.x = x;
        b.y = y;
        b.z = z;

        for (int i = 0; i < signalCount; ++i) {
            if (b.hasSignal(i))
                b.currRadius[i] = Math.max(b.currRadius[i] - move_dist, 0);
        }
    }

    public int spreadSignal(int objId, int type) {
        final SignalBeamer b = idToObject.get(objId);

        if (b.memRadius[type] >= signalsRadius[type])
            return b.memRadius[type]; // achieved the maximum radius

        ++b.memRadius[type];
        changedObjSignalIds.add(new int[] {objId, type});

        return b.memRadius[type];
    }

    public int gatherSignal(int objId, int type) {
        final SignalBeamer b = idToObject.get(objId);

        if (b.memRadius[type] == 0)
            return b.memRadius[type]; // the radius is already minimum;

        --b.memRadius[type];
        changedObjSignalIds.add(new int[] {objId, type});

        return b.memRadius[type];
    }

    @Contract(pure = true)
    public int getFlatCoordinate(int[] coordinate) {
        final int x = coordinate[0];
        final int y = coordinate[1];
        final int z = coordinate[2];
        return size * (x * size + y) + z;
    }

    public List<ConcentrationChangeEvent> recalculateSignals() {
        List<ConcentrationChangeEvent> events = new ArrayList<>();

        if (changedObjSignalIds.size() > 0) {
            changedObjSignalIds.forEach(ids -> recalculateSignal(idToObject.get(ids[0]), ids[1], events));
            changedObjSignalIds.clear();
        }

        return events;
    }

    public int calcVectors() {
        final int[] source = new int[]{1, 1, 1};
        final ArrayList<int[]> neighbors = getNeighborhood(source);
        final ArrayList<int[]> vectors = new ArrayList<>();
        neighbors.forEach(c -> vectors.add(getVector(source, c)));
//        vectors.forEach(v1 -> {
//            System.out.printf("(%d, %d, %d)\n", v1[0], v1[1], v1[2]);
//            vectors.forEach(v2 -> {
//                final int dotProd = v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2];
//                final double magnitude1 = Math.sqrt(v1[0] * v1[0] + v1[2] * v1[2] + v1[2] * v1[2]);
//                final double magnitude2 = Math.sqrt(v2[0] * v2[0] + v2[2] * v2[2] + v2[2] * v2[2]);
//                final double angle = Math.acos(dotProd / (magnitude1 * magnitude2));
//            });
//        });

        final double[] sum = new double[]{0.};
        final double[] p = new double[27];
        final int[] freq = new int[27];
        final int[] v1 = vectors.get(0);

        final double magnitude1 = Math.sqrt(v1[0] * v1[0] + v1[1] * v1[1] + v1[2] * v1[2]);
        for (int i = 0; i < vectors.size(); ++i) {
            final int[] v2 = vectors.get(i);
            final int dotProd = v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2];
            final double magnitude2 = Math.sqrt(v2[0] * v2[0] + v2[1] * v2[1] + v2[2] * v2[2]);
            p[i] = Math.round(dotProd / (magnitude1 * magnitude2) * 10000) / 10000. + 1;
            p[i] = Math.pow(p[i], 5);
            sum[0] += p[i];
        }

        for (int i = 0; i < vectors.size(); ++i) {
            final int[] v2 = vectors.get(i);
            p[i] = p[i] / sum[0];
            System.out.printf("(%d, %d, %d): p = %f\n", v2[0], v2[1], v2[2], p[i]);
        }

        final Random rnd = new Random(3);
        for (int i = 0; i < 1000000; ++i) {
            final double actualP = rnd.nextDouble();
            double s = 0.;
            for (int j = 0; j < p.length; ++j) {
                s += p[j];
                if (actualP <= s) {
                    freq[j]++;
                    break;
                }
            }
        }

        for (int i = 0; i < p.length; ++i) {
            System.out.printf("F(%d) = %d\n", i, freq[i]);
        }

        return vectors.size();
    }

    private void recalculateSignal(@NotNull SignalBeamer b, int signal, List<ConcentrationChangeEvent> events) {
        final int cycles = b.memRadius[signal] - b.currRadius[signal];
        int prevRadius = b.currRadius[signal];
        if (cycles > 0) {
            double changeVal = spreadSignal(b, signal);

            ConcentrationChangeEvent currentEvent = new ConcentrationChangeEvent(-1, "Spread", signal,
                    new int[]{b.x, b.y, b.z},
                    prevRadius, changeVal);
            events.add(currentEvent);
        }
        else if (cycles < 0)
        {
            double changeVal = gatherSignal(b, signal);

            ConcentrationChangeEvent currentEvent = new ConcentrationChangeEvent(-1, "Gather", signal,
                    new int[]{b.x, b.y, b.z},
                    prevRadius, changeVal);
            events.add(currentEvent);
        }
    }

    public int[] getBorderlines() {
        int x_low = Integer.MAX_VALUE, x_high = -1;
        int y_low = Integer.MAX_VALUE, y_high = -1;
        int z_low = Integer.MAX_VALUE, z_high = -1;


        for (TIntObjectIterator<SignalBeamer> it = idToObject.iterator(); it.hasNext(); ) {
            it.advance();

            SignalBeamer b = it.value();

            x_low = min(x_low, b.x);
            x_high = max(x_high, b.x);

            y_low = min(y_low, b.y);
            y_high = max(y_high, b.y);

            z_low = min(z_low, b.z);
            z_high = max(z_high, b.z);
        }

        return new int[]{x_low, x_high, y_low, y_high, z_low, z_high};
    }

    public ArrayList<Pair<Integer, Integer>> getNearestObjects(int[] coordinate) {
        final int x = coordinate[0];
        final int y = coordinate[1];
        final int z = coordinate[2];

        ArrayList<Pair<Integer, Integer>> distances = new ArrayList<>();

        for (TIntObjectIterator<SignalBeamer> it = idToObject.iterator(); it.hasNext(); ) {
            it.advance();

            Integer dist = max(max(abs(z - it.value().z), abs(y - it.value().y)), abs(x - it.value().x));

            distances.add(new Pair<>(it.key(), dist));
        }

        distances.sort(Comparator.comparing(Pair::getValue));

        return distances;
    }

    private double spreadSignal(@NotNull SignalBeamer b, int type) {
        final int r = b.currRadius[type];
        final double signalPower = 1.;
        final double val = signalPower * sValueCoefficients[type][r];
        if (r == 0)
            cube[size * (b.x * size + b.y) + b.z].concentrations[type] += val;
        else
            iterateCube(b, type, val);

        ++b.currRadius[type];

        return val;
    }

    private double gatherSignal(@NotNull SignalBeamer b, int type) {
        --b.currRadius[type];

        final int r = b.currRadius[type];
        final double signalPower = -1.;
        final double val = signalPower * sValueCoefficients[type][r];
        if (r == 0)
            cube[size * (b.x * size + b.y) + b.z].concentrations[type] += val;
        else
            iterateCube(b, type, val);

        return val;
    }

    @Contract(pure = true)
    private int getFlatCoordinate(int x, int y, int z) {
        return size * (x * size + y) + z;
    }

    private void iterateCube(@NotNull SignalBeamer b, int type, double val) {
        final int left = b.x - b.currRadius[type];
        final int right = b.x + b.currRadius[type];

        final int bottom = b.z - b.currRadius[type];
        final int top = b.z + b.currRadius[type];

        final int front = b.y - b.currRadius[type];
        final int rear = b.y + b.currRadius[type];


        if (left >= 0) {
            // face 1
            iterateFace(left, left,
                    front, rear,
                    bottom, top,
                    type, val);
        }

        if (right <= maxCoordinate) {
            // face 5
            iterateFace(right, right,
                    front, rear,
                    bottom, top,
                    type, val);
        }

        if (front >= 0) {
            // face 2
            // we should reduce the limits of some coordinates because we iterated them on previous faces
            iterateFace(left + 1, right - 1,
                    front, front,
                    bottom, top,
                    type, val);
        }

        if (rear <= maxCoordinate) {
            // face 3
            iterateFace(left + 1, right - 1,
                    rear, rear,
                    bottom, top,
                    type, val);
        }

        if (bottom >= 0) {
            // face 4
            iterateFace(left + 1, right - 1,
                    front + 1, rear - 1,
                    bottom, bottom,
                    type, val);
        }

        if (top <= maxCoordinate) {
            // face 6
            iterateFace(left + 1, right - 1,
                    front + 1, rear - 1,
                    top, top,
                    type, val);
        }
    }

    private void iterateFace(int x_low, int x_high, int y_low, int y_high, int z_low, int z_high,
                             int type, double val) {
        x_low = Math.max(x_low, 0);
        x_high = min(x_high, maxCoordinate);

        y_low = Math.max(y_low, 0);
        y_high = min(y_high, maxCoordinate);

        z_low = Math.max(z_low, 0);
        z_high = min(z_high, maxCoordinate);

        for (int x = x_low; x <= x_high; ++x) {
            for (int y = y_low; y <= y_high; ++y) {
                for (int z = z_low; z <= z_high; ++z) {
                    cube[size * (x * size + y) + z].concentrations[type] += val;
                }
            }
        }
    }
    //endregion
}