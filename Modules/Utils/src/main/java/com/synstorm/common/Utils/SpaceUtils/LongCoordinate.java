package com.synstorm.common.Utils.SpaceUtils;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import org.jetbrains.annotations.NotNull;

/**
 * Created by dvbozhko on 12/22/15.
 */
@Model_v0
@NonProductionLegacy
public class LongCoordinate extends BaseCoordinate<Long> implements ICoordinate<Long> {
    //region Fields
    private long comparative;
    //endregion

    //region Constructors
    LongCoordinate(long x, long y, long z, Long capacity, Long capacitySquare) {
        super(x, y, z);

        comparative = capacitySquare * z + capacity * y + x;
        hashCode = Long.valueOf(comparative).hashCode();
    }

    LongCoordinate(Long comparative, Long capacity, Long capacitySquare) {
        super(comparative, capacity, capacitySquare);
    }
    //endregion

    //region Getters and Setters
    public Long getComparative() {
        return comparative;
    }
    //endregion

    //region Public Methods
    @Override
    public int compareTo(@NotNull ICoordinate o) {
        return Long.compare(comparative, (long) o.getComparative());
    }
    //endregion

    //region Protected Methods
    @Override
    protected void initializeFromComparative(Long id, Long capacity, Long capacitySquare) {
        this.comparative = id;

        z = (int) (comparative / capacitySquare);
        long residueZ = comparative - (capacitySquare * z);
        y = (int) (residueZ / capacity);
        x = (int) (residueZ - (capacity * y));
    }
    //endregion

    //region Private Methods
    //endregion
}
