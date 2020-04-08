package com.synstorm.common.Utils.SpaceUtils;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

/**
 * Created by dvbozhko on 12/22/15.
 */
@Model_v0
@NonProductionLegacy
public class BigIntegerCoordinate extends BaseCoordinate<BigInteger> implements ICoordinate<BigInteger> {
    //region Fields
    private BigInteger comparative;
    //endregion

    //region Constructors
    BigIntegerCoordinate(int x, int y, int z, @NotNull BigInteger capacity, @NotNull BigInteger capacitySquare) {
        super(x, y, z);

        BigInteger xB = BigInteger.valueOf(x);
        BigInteger yB = BigInteger.valueOf(y);
        BigInteger zB = BigInteger.valueOf(z);

        comparative = capacitySquare.multiply(zB).add(capacity.multiply(yB)).add(xB);
        hashCode = comparative.hashCode();
    }

    BigIntegerCoordinate(BigInteger comparative, BigInteger capacityBig, BigInteger capacitySquareBig) {
        super(comparative, capacityBig, capacitySquareBig);
    }
    //endregion

    //region Getters and Setters
    public BigInteger getComparative() {
        return comparative;
    }
    //endregion

    //region Public Methods
    @Override
    public int compareTo(@NotNull ICoordinate o) {
        return comparative.compareTo((BigInteger) o.getComparative());
    }
    //endregion

    //region Protected methods
    @Override
    protected void initializeFromComparative(BigInteger id, BigInteger capacity, BigInteger capacitySquare) {
        this.comparative = id;

        BigInteger bigZ = comparative.divide(capacitySquare);
        BigInteger residueZ = comparative.subtract(capacitySquare.multiply(bigZ));
        BigInteger bigY = residueZ.divide(capacity);
        BigInteger bigX = residueZ.subtract(capacity.multiply(bigY));

        x = bigX.intValue();
        y = bigY.intValue();
        z = bigZ.intValue();
    }
    //endregion

    //region Private Methods
    //endregion
}