package com.synstorm.common.Utils.SpaceUtils;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

/**
 * Created by dvbozhko on 10/22/15.
 */
@Model_v0
@NonProductionLegacy
public class BigIntegerCoordinateFactory extends BaseCoordinateFactory<BigInteger> implements ICoordinateFactory<BigInteger> {
    //region Fields
    //endregion

    //region Constructors
    public BigIntegerCoordinateFactory(int capacity) {
        super(capacity);
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Nullable
    @Override
    public ICoordinate<BigInteger> createCoordinate(int x, int y, int z) {
        if (isCoordinateInRange(x, y, z))
            return new BigIntegerCoordinate(x, y, z, capacityT, capacityTSquare);
        else
            return null;
    }

    @Nullable
    @Override
    public ICoordinate<BigInteger> createCoordinate(BigInteger comparative) {
        if (isCoordinateInRange(comparative))
            return new BigIntegerCoordinate(comparative, capacityT, capacityTSquare);
        else
            return null;
    }

    @Nullable
    @Override
    public ICoordinate<BigInteger> getSpatialCoordinate(ICoordinate<BigInteger> base, short shift) {
        return createCoordinate(base.getComparative().subtract(inverseShiftPosition.get(shift)));
    }

    @Nullable
    @Override
    public ICoordinate<BigInteger> getBaseSpatialCoordinate(ICoordinate<BigInteger> destination, short shift) {
        return createCoordinate(destination.getComparative().add(inverseShiftPosition.get(shift)));
    }

    @Override
    public short getSpatialNum(ICoordinate<BigInteger> base, ICoordinate<BigInteger> destination) {
        return shiftPositions.get(base.getComparative().subtract(destination.getComparative()));
    }

    @Override
    public BigInteger calculateShift(ICoordinate<BigInteger> source, ICoordinate<BigInteger> dest) {
        return source.getComparative().subtract(dest.getComparative());
    }

    @Override
    protected boolean checkComparativeForZero(BigInteger comparative) {
        return comparative.equals(BigInteger.ZERO);
    }
    //endregion

    //region Protected methods
    @Override
    protected void initializeCapacity() {
        capacityT = BigInteger.valueOf(capacity);
        capacityTSquare = capacityT.multiply(capacityT);
        capacityTCube = capacityTSquare.multiply(capacityT);
    }

    @Override
    protected boolean isCoordinateInRange(BigInteger comparative) {
        return (comparative.compareTo(BigInteger.ZERO) >= 0 && comparative.compareTo(capacityTCube) < 0);
    }
    //endregion

    //region Private Methods
    //endregion
}