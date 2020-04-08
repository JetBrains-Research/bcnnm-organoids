package com.synstorm.common.Utils.SpaceUtils;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import org.jetbrains.annotations.NotNull;

/**
 * Created by dvbozhko on 12/22/15.
 */
@Model_v1
public abstract class BaseCoordinate<T> implements ICoordinate<T> {
    //region Fields
    protected int x;
    protected int y;
    protected int z;
    protected int hashCode;
    //endregion

    //region Constructors
    protected BaseCoordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    protected BaseCoordinate(T comparative, T capacity, T capacitySquare) {
        initializeFromComparative(comparative, capacity, capacitySquare);
    }
    //endregion

    //region Getters/Setters
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }
    //endregion

    //region Public methods
    @Override
    public short getPriority(@NotNull ICoordinate coordinate) {
        if (this.compareTo(coordinate) == 0)
            return 0;

        boolean isFixedYZ = this.x != coordinate.getX() && this.y == coordinate.getY() && this.z == coordinate.getZ();
        boolean isFixedXZ = this.x == coordinate.getX() && this.y != coordinate.getY() && this.z == coordinate.getZ();
        boolean isFixedXY = this.x == coordinate.getX() && this.y == coordinate.getY() && this.z != coordinate.getZ();

        if (isFixedYZ || isFixedXZ || isFixedXY)
            return 1;

        boolean isFixedX = this.x == coordinate.getX() && this.y != coordinate.getY() && this.z != coordinate.getZ();
        boolean isFixedY = this.x != coordinate.getX() && this.y == coordinate.getY() && this.z != coordinate.getZ();
        boolean isFixedZ = this.x != coordinate.getX() && this.y != coordinate.getY() && this.z == coordinate.getZ();

        if (isFixedX || isFixedY || isFixedZ)
            return 2;

        return 3;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BaseCoordinate))
            return false;
        if (obj == this)
            return true;

        BaseCoordinate chkCoordinate = (BaseCoordinate) obj;

        return (this.x == chkCoordinate.x && this.y == chkCoordinate.y && this.z == chkCoordinate.z);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(x);
        stringBuilder.append(", ");
        stringBuilder.append(y);
        stringBuilder.append(", ");
        stringBuilder.append(z);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
    //endregion

    //region Protected methods
    protected abstract void initializeFromComparative(T id, T capacity, T capacitySquare);
    //endregion

    //region Private methods
    //endregion
}
