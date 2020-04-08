package com.synstorm.common.Utils.SpaceUtils;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import org.jetbrains.annotations.NotNull;

/**
 * Created by dvbozhko on 08/06/16.
 */
@Model_v0
@NonProductionLegacy
public class IntegerCoordinate extends BaseCoordinate<Integer> implements ICoordinate<Integer> {
    //region Fields
    private int comparative;
    //endregion

    //region Constructors
    IntegerCoordinate(int x, int y, int z, Integer capacity, Integer capacitySquare) {
        super(x, y, z);

        comparative = capacitySquare * z + capacity * y + x;
        hashCode = Long.valueOf(comparative).hashCode();
    }

    IntegerCoordinate(Integer comparative, Integer capacity, Integer capacitySquare) {
        super(comparative, capacity, capacitySquare);
    }
    //endregion

    //region Getters and Setters
    @Override
    public Integer getComparative() {
        return comparative;
    }
    //endregion

    //region Public Methods
    @Override
    public int compareTo(@NotNull ICoordinate o) {
        return Integer.compare(comparative, (int) o.getComparative());
    }
    //endregion

    //region Protected Methods
    @Override
    protected void initializeFromComparative(Integer id, Integer capacity, Integer capacitySquare) {
        this.comparative = id;

        z = comparative / capacitySquare;
        int residueZ = comparative - (capacitySquare * z);
        y = residueZ / capacity;
        x = residueZ - (capacity * y);
    }
    //endregion

    //region Private Methods
    //endregion
}
