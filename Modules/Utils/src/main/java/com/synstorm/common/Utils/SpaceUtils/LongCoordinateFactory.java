package com.synstorm.common.Utils.SpaceUtils;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import org.jetbrains.annotations.Nullable;

/**
 * Created by dvbozhko on 12/22/15.
 */
@Model_v0
@NonProductionLegacy
public class LongCoordinateFactory extends BaseCoordinateFactory<Long> implements ICoordinateFactory<Long> {
    //region Fields
    //endregion

    //region Constructors
    public LongCoordinateFactory(int capacity) {
        super(capacity);
    }
    //endregion

    //region Getters/Setters
    //endregion

    //region Public methods
    @Nullable
    @Override
    public ICoordinate<Long> createCoordinate(int x, int y, int z) {
        if (isCoordinateInRange(x, y, z))
            return new LongCoordinate(x, y, z, capacityT, capacityTSquare);
        else
            return null;
    }

    @Nullable
    @Override
    public ICoordinate<Long> createCoordinate(Long comparative) {
        if (isCoordinateInRange(comparative))
            return new LongCoordinate(comparative, capacityT, capacityTSquare);
        else
            return null;
    }

    @Nullable
    @Override
    public ICoordinate<Long> getSpatialCoordinate(ICoordinate<Long> base, short shift) {
        return createCoordinate(base.getComparative() - inverseShiftPosition.get(shift));
    }

    @Nullable
    @Override
    public ICoordinate<Long> getBaseSpatialCoordinate(ICoordinate<Long> destination, short shift) {
        return createCoordinate(destination.getComparative() + inverseShiftPosition.get(shift));
    }

    @Override
    public short getSpatialNum(ICoordinate<Long> base, ICoordinate<Long> destination) {
        return shiftPositions.get(base.getComparative() - destination.getComparative());
    }

    @Override
    public Long calculateShift(ICoordinate<Long> source, ICoordinate<Long> dest) {
        return source.getComparative() - dest.getComparative();
    }

    @Override
    protected boolean checkComparativeForZero(Long comparative) {
        return comparative == 0L;
    }
    //endregion

    //region Protected methods
    @Override
    protected void initializeCapacity() {
        capacityT = (long) capacity;
        capacityTSquare = capacityT * capacityT;
        capacityTCube = capacityTSquare * capacityT;
    }

    @Override
    protected boolean isCoordinateInRange(Long comparative) {
        return (comparative.compareTo(0L) >= 0 && comparative.compareTo(capacityTCube) < 0);
    }
    //endregion

    //region Private methods
    //endregion
}
