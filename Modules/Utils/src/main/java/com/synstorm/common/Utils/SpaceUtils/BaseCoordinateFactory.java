package com.synstorm.common.Utils.SpaceUtils;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dvbozhko on 12/22/15.
 */
@Model_v1
public abstract class BaseCoordinateFactory<T> implements ICoordinateFactory<T> {
    //region Fields
    protected final int capacity;
    protected T capacityT;
    protected T capacityTSquare;
    protected T capacityTCube;
    protected Map<T, Short> shiftPositions;
    protected Map<Short, T> inverseShiftPosition;
    //endregion

    //region Constructors
    public BaseCoordinateFactory(int capacity) {
        this.capacity = capacity;
        shiftPositions = new HashMap<>();
        inverseShiftPosition = new HashMap<>();
        initializeCapacity();
    }
    //endregion

    //region Getters/Setters
    public T getCapacityCube() {
        return capacityTCube;
    }
    //endregion

    //region Public methods
    @Nullable
    @Override
    public ICoordinate<T> createCoordinate(ICoordinate<T> coordinate, SpaceVector vector) {
        int x = coordinate.getX() + vector.getX();
        int y = coordinate.getY() + vector.getY();
        int z = coordinate.getZ() + vector.getZ();
        return createCoordinate(x, y, z);
    }

    @Nullable
    @Override
    public ICoordinate<T> createCoordinate(String x, String y, String z) {
        int xI = Integer.parseInt(x);
        int yI= Integer.parseInt(y);
        int zI = Integer.parseInt(z);
        return createCoordinate(xI, yI, zI);
    }

    @Nullable
    @Override
    public SpaceVector createSpaceVector(ICoordinate<T> source, ICoordinate<T> dest) {
        long x = dest.getX() - source.getX();
        long y = dest.getY() - source.getY();
        long z = dest.getZ() - source.getZ();

        if (x >= -1 && x <= 1 && y >= -1 && y <= 1 && z >= -1 && z <= 1)
            try {
                return new SpaceVector((short) x, (short) y, (short) z);
            } catch (Exception e) {
                return null;
            }
        else
            return null;
    }

    @Override
    public boolean initializeShiftPosition() {
        ICoordinate<T> coordinate = createCoordinate(1, 1, 1);
        if (coordinate == null)
            return false;

        short count = 1;
        int x = coordinate.getX();
        int y = coordinate.getY();
        int z = coordinate.getZ();
        for (int i = z - 1; i <= z + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                for (int k = x - 1; k <= x + 1; k++) {
                    ICoordinate<T> neighbor = createCoordinate(k, j, i);
                    if (neighbor == null)
                        return false;

                    T key = calculateShift(coordinate, neighbor);
                    if (!checkComparativeForZero(key))
                        shiftPositions.put(key, count++);
                    else
                        shiftPositions.put(key, (short) 0);
                }
            }
        }

        shiftPositions.entrySet().stream()
                .forEach(item -> inverseShiftPosition.put(item.getValue(), item.getKey()));
        return true;
    }
    //endregion

    //region Protected methods
    protected abstract void initializeCapacity();

    protected abstract boolean isCoordinateInRange(T comparative);

    protected abstract T calculateShift(ICoordinate<T> source, ICoordinate<T> dest);

    protected abstract boolean checkComparativeForZero(T comparative);

    @Contract(pure = true)
    protected boolean isCoordinateInRange(long x, long y, long z) {
        return (x >= 0 && x < capacity && y >= 0 && y < capacity && z >= 0 && z < capacity);
    }
    //endregion

    //region Private methods
    //endregion
}
