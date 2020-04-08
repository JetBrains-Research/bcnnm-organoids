package com.synstorm.common.Utils.SpaceUtils;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Created by dvbozhko on 28/04/2017.
 */
@Model_v1
public class ByteArrCoordinate {
    //region Fields
    private final int x;
    private final int y;
    private final int z;
    private final byte[] coordinate;
    //endregion

    @Contract(pure = true)
    public static int byteArrayToInt(@NotNull byte[] b) {
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static byte[] intToByteArray(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    //region Constructors
    public ByteArrCoordinate(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        final byte[] bX = intToByteArray(x);
        final byte[] bY = intToByteArray(y);
        final byte[] bZ = intToByteArray(z);
        coordinate = new byte[]{bX[0], bX[1], bX[2], bX[3],
                bY[0], bY[1], bY[2], bY[3],
                bZ[0], bZ[1], bZ[2], bZ[3]};
    }

    public ByteArrCoordinate(@NotNull final byte[] coord) {
        coordinate = coord;
        final byte[] bX = new byte[]{coord[0], coord[1], coord[2], coord[3]};
        final byte[] bY = new byte[]{coord[4], coord[5], coord[6], coord[7]};
        final byte[] bZ = new byte[]{coord[8], coord[9], coord[10], coord[11]};
        x = byteArrayToInt(bX);
        y = byteArrayToInt(bY);
        z = byteArrayToInt(bZ);
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    public ByteArrCoordinate[] getNeighbors() {
        return new ByteArrCoordinate[]{
                new ByteArrCoordinate(x - 1, y, z),
                new ByteArrCoordinate(x + 1, y, z),
                new ByteArrCoordinate(x, y - 1, z),
                new ByteArrCoordinate(x, y + 1, z),
                new ByteArrCoordinate(x, y, z - 1),
                new ByteArrCoordinate(x, y, z + 1),
                new ByteArrCoordinate(x - 1, y - 1, z),
                new ByteArrCoordinate(x - 1, y + 1, z),
                new ByteArrCoordinate(x + 1, y - 1, z),
                new ByteArrCoordinate(x + 1, y + 1, z),
                new ByteArrCoordinate(x - 1, y, z - 1),
                new ByteArrCoordinate(x - 1, y, z + 1),
                new ByteArrCoordinate(x + 1, y, z - 1),
                new ByteArrCoordinate(x + 1, y, z + 1),
                new ByteArrCoordinate(x , y - 1, z - 1),
                new ByteArrCoordinate(x , y - 1, z + 1),
                new ByteArrCoordinate(x , y + 1, z - 1),
                new ByteArrCoordinate(x , y + 1, z + 1),
                new ByteArrCoordinate(x - 1, y - 1, z - 1),
                new ByteArrCoordinate(x - 1, y - 1, z + 1),
                new ByteArrCoordinate(x - 1, y + 1, z - 1),
                new ByteArrCoordinate(x - 1, y + 1, z + 1),
                new ByteArrCoordinate(x + 1, y - 1, z - 1),
                new ByteArrCoordinate(x + 1, y - 1, z + 1),
                new ByteArrCoordinate(x + 1, y + 1, z - 1),
                new ByteArrCoordinate(x + 1, y + 1, z + 1),
        };
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coordinate);
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
