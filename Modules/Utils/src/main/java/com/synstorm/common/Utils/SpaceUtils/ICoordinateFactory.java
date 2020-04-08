package com.synstorm.common.Utils.SpaceUtils;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import org.jetbrains.annotations.Nullable;

/**
 * Created by dvbozhko on 12/22/15.
 */
@Model_v1
public interface ICoordinateFactory<T> {
    T getCapacityCube();

    @Nullable
    ICoordinate<T> createCoordinate(int x, int y, int z);

    @Nullable
    ICoordinate<T> createCoordinate(T comparative);

    @Nullable
    ICoordinate<T> createCoordinate(ICoordinate<T> coordinate, SpaceVector vector);

    @Nullable
    ICoordinate<T> createCoordinate(String x, String y, String z);

    @Nullable
    SpaceVector createSpaceVector(ICoordinate<T> source, ICoordinate<T> dest);

    @Nullable
    ICoordinate<T> getSpatialCoordinate(ICoordinate<T> base, short shift);

    @Nullable
    ICoordinate<T> getBaseSpatialCoordinate(ICoordinate<T> destination, short shift);

    short getSpatialNum(ICoordinate<T> base, ICoordinate<T> destination);

    boolean initializeShiftPosition();
}
