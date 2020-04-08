package com.synstorm.common.Utils.SpaceUtils;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;

/**
 * Created by dvbozhko on 12/22/15.
 */
@Model_v0
public class SpaceVector {
    //region Fields
    private short x;
    private short y;
    private short z;
    //endregion

    //region Constructors
    SpaceVector(short x, short y, short z) throws Exception {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    //endregion

    //region Getters and Setters
    public short getX() {
        return x;
    }

    public short getY() {
        return y;
    }

    public short getZ() {
        return z;
    }
    //endregion

    //region Public Methods
    public SpaceVector invert() throws Exception {
        return new SpaceVector((short) (0 - x), (short) (0 - y), (short) (0 - z));
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

    //region Private Methods
    //endregion
}
