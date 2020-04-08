package com.synstorm.common.Utils.SpaceUtils;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dvbozhko on 10/21/15.
 */
@Model_v0
@NonProductionLegacy
public enum CoordinateUtils {
    INSTANCE;

    //region Fields
    private int capacity;
    private ICoordinateFactory coordinateFactory;
    private boolean isInitialized = false;
    private CoordinateCache cache;
    //endregion

    //region Constructors
    public boolean initializeUtils(int spaceCapacity) throws IllegalDimensionalSizeException {
        if (spaceCapacity < 3)
            throw new IllegalDimensionalSizeException();

        capacity = spaceCapacity;
        cache = new CoordinateCache();
        initializeFactory();
        isInitialized = coordinateFactory.initializeShiftPosition();
        return isInitialized;
    }
    //endregion

    //region Getters and Setters
    @Contract(pure = true)
    public boolean isIsInitialized() {
        return isInitialized;
    }

    public Object getVolume() {
        return coordinateFactory.getCapacityCube();
    }

    public int getCachedCount() {
        return cache.getCachedCount();
    }

    public long getGetFromCacheCount() {
        return cache.getCacheGetsCount();
    }
    //endregion

    //region Public Methods
    @Nullable
    public ICoordinate createCoordinate(int x, int y, int z) {
        ICoordinate result = coordinateFactory.createCoordinate(x, y, z);
//        cache.add(result);
        return result;
    }

    @Nullable
    public ICoordinate createCoordinate(int comparative) {
        ICoordinate result = coordinateFactory.createCoordinate(comparative);
//        cache.add(result);
        return result;
    }

    @Nullable
    public ICoordinate createCoordinate(long comparative) {
        ICoordinate result = coordinateFactory.createCoordinate(comparative);
//        cache.add(result);
        return result;
    }

    @Nullable
    public ICoordinate createCoordinate(BigInteger comparative) {
        ICoordinate result = coordinateFactory.createCoordinate(comparative);
//        cache.add(result);
        return result;
    }

    @Nullable
    public ICoordinate createCoordinate(ICoordinate coordinate, SpaceVector vector) {
        ICoordinate result = coordinateFactory.createCoordinate(coordinate, vector);
//        cache.add(result);
        return result;
    }

    @Nullable
    public ICoordinate createCoordinate(String x, String y, String z) {
        ICoordinate result = coordinateFactory.createCoordinate(x, y, z);
//        cache.add(result);
        return result;
    }

    @Nullable
    public SpaceVector createSpaceVector(ICoordinate source, ICoordinate dest) {
        return coordinateFactory.createSpaceVector(source, dest);
    }

    @Nullable
    public ICoordinate getSpatialCoordinate(ICoordinate base, short shift) {
        return coordinateFactory.getSpatialCoordinate(base, shift);
    }

    @Nullable
    public ICoordinate getBaseSpatialCoordinate(ICoordinate destination, short shift) {
        return coordinateFactory.getBaseSpatialCoordinate(destination, shift);
    }

    public short getSpatialNum(ICoordinate base, ICoordinate destination) {
        return coordinateFactory.getSpatialNum(base, destination);
    }

    public int calculateDistance(@NotNull ICoordinate first, @NotNull ICoordinate second) {
        int result;
        int x1 = first.getX();
        int x2 = second.getX();
        int y1 = first.getY();
        int y2 = second.getY();
        int z1 = first.getZ();
        int z2 = second.getZ();

        int xDiff = Math.abs(x1 - x2);
        int yDiff = Math.abs(y1 - y2);
        int zDiff = Math.abs(z1 - z2);

        result = Math.max(xDiff, Math.max(yDiff, zDiff));

        return result;
    }

    public ICoordinate getFromCache(int x, int y, int z) {
        return cache.getFromCache(x, y, z);
    }

    public List<ICoordinate> makeNeighborCoordinates(@NotNull ICoordinate coordinate) {
        List<ICoordinate> result = new ArrayList<>();

        int x = coordinate.getX();
        int y = coordinate.getY();
        int z = coordinate.getZ();

        for (int i = z - 1; i <= z + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                for (int k = x - 1; k <= x + 1; k++) {
                    if (!(i == z && j == y && k == x)) {
//                        ICoordinate neighbor = cache.getFromCache(k, j, i);
//                        if (neighbor == null)
//                            neighbor = createCoordinate(k, j, i);
                        ICoordinate neighbor = createCoordinate(k, j, i);
                        if (neighbor != null)
                            result.add(neighbor);
                    }
                }
            }
        }

        return result;
    }

    @Contract(pure = true)
    public int getCapacity() {
        return capacity;
    }

    public void clearCache() {
        cache.clearCache();
    }
    //endregion

    //region Private Methods
    private void initializeFactory() {
        boolean isInteger = capacity <= 1290;                       // Maximum size of cubic edge for coordinates with int comparative
        boolean isLong = capacity >= 1290 && capacity <= 2097151;   // Maximum size of cubic edge for coordinates with long comparative

        if (isInteger)
            coordinateFactory = new IntegerCoordinateFactory(capacity);
        else if (isLong)
            coordinateFactory = new LongCoordinateFactory(capacity);
        else
            coordinateFactory = new BigIntegerCoordinateFactory(capacity);
    }
    //endregion

    private class CoordinateCache {
        private Map<Integer, Map<Integer, Map<Integer, ICoordinate>>> cacheMap = new ConcurrentHashMap<>();
        private int cachedCount = 0;
        private long cacheGetsCount = 0;

        public int getCachedCount() {
            return cachedCount;
        }

        public long getCacheGetsCount() {
            return cacheGetsCount;
        }

        @Nullable
        public ICoordinate getFromCache(int x, int y, int z) {
//            try {
                if (cacheMap.containsKey(x))
                    if (cacheMap.get(x).containsKey(y))
                        if (cacheMap.get(x).get(y).containsKey(z)) {
                            cacheGetsCount++;
                            return cacheMap.get(x).get(y).get(z);
                        }

//            } catch (NullPointerException ex) {
//                ex.printStackTrace();
//            }
            return null;
        }

        public void add(ICoordinate coordinate) {
            if (coordinate == null)
                return;

            int x = coordinate.getX();
            int y = coordinate.getY();
            int z = coordinate.getZ();

            if (cacheMap.containsKey(x))
                if (cacheMap.get(x).containsKey(y))
                    if (cacheMap.get(x).get(y).containsKey(z))
                        return;
                    else
                        cacheMap.get(x).get(y).put(z, coordinate);
                else
                    cacheMap.get(x).put(y, addZ(z, coordinate));
            else
                cacheMap.put(x, addYZ(y, z, coordinate));

            cachedCount++;
        }

        public void clearCache() {
            cacheMap.clear();
        }

        @NotNull
        @Contract("_, _ -> !null")
        private Map<Integer, ICoordinate> addZ(int z, ICoordinate coordinate) {
            return new ConcurrentHashMap<Integer, ICoordinate>() {{
                put(z, coordinate);
            }};
        }

        @NotNull
        @Contract("_, _, _ -> !null")
        private Map<Integer, Map<Integer, ICoordinate>> addYZ(int y, int z, ICoordinate coordinate) {
            return new ConcurrentHashMap<Integer, Map<Integer, ICoordinate>>() {{
                put(y, addZ(z, coordinate));
            }};
        }
    }
}