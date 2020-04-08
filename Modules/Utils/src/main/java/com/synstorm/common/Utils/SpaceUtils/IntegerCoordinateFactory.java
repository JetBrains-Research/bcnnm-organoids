package com.synstorm.common.Utils.SpaceUtils;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import org.jetbrains.annotations.Nullable;

/**
 * Created by dvbozhko on 08/06/16.
 */
@Model_v0
@NonProductionLegacy
public class IntegerCoordinateFactory extends BaseCoordinateFactory<Integer> implements ICoordinateFactory<Integer> {
    //region Fields
    //endregion

    //region Constructors
    public IntegerCoordinateFactory(int capacity) {
        super(capacity);
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Nullable
    @Override
    public ICoordinate<Integer> createCoordinate(int x, int y, int z) {
        if (isCoordinateInRange(x, y, z))
            return new IntegerCoordinate(x, y, z, capacityT, capacityTSquare);
        else
            return null;
    }

    @Nullable
    @Override
    public ICoordinate<Integer> createCoordinate(Integer comparative) {
        if (isCoordinateInRange(comparative))
            return new IntegerCoordinate(comparative, capacityT, capacityTSquare);
        else
            return null;
    }

    @Nullable
    @Override
    public ICoordinate<Integer> getSpatialCoordinate(ICoordinate<Integer> base, short shift) {
        return createCoordinate(base.getComparative() - inverseShiftPosition.get(shift));
    }

    @Nullable
    @Override
    public ICoordinate<Integer> getBaseSpatialCoordinate(ICoordinate<Integer> destination, short shift) {
        return createCoordinate(destination.getComparative() + inverseShiftPosition.get(shift));
    }

    @Override
    public short getSpatialNum(ICoordinate<Integer> base, ICoordinate<Integer> destination) {
        return shiftPositions.get(base.getComparative() - destination.getComparative());
    }

    @Override
    public Integer calculateShift(ICoordinate<Integer> source, ICoordinate<Integer> dest) {
        return source.getComparative() - dest.getComparative();
    }

    @Override
    protected boolean checkComparativeForZero(Integer comparative) {
        return comparative == 0;
    }
    //endregion

    //region Protected Methods
    @Override
    protected void initializeCapacity() {
        capacityT = capacity;
        capacityTSquare = capacity * capacity;
        capacityTCube = capacity * capacity * capacity;
    }

    @Override
    protected boolean isCoordinateInRange(Integer comparative) {
        return (comparative.compareTo(0) >= 0 && comparative.compareTo(capacityTCube) < 0);
    }
    //endregion

    //region Private Methods
    //endregion
}
