package com.synstorm.SimulationModel.ModelTime;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import org.jetbrains.annotations.NotNull;

/**
 * Class for storing simulation tick.
 * Created by dvbozhko on 02/03/2017.
 */

@Model_v0
@NonProductionLegacy
public class Tick implements Comparable<Tick> {
    //region Fields
    private final long tick;
    //endregion

    //region Constructors
    public Tick(long tick) {
        this.tick = tick;
    }
    //endregion

    //region Getters and Setters
    public long getTick() {
        return tick;
    }
    //endregion

    //region Public Methods
    @Override
    public String toString() {
        return "[Tick:" + String.valueOf(tick) + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tick)) return false;

        Tick that = (Tick) o;

        return (this.tick == that.tick);
    }

    @Override
    public int hashCode() {
        return (int)(tick ^ (tick >>> 32));
    }

    @Override
    public int compareTo(@NotNull Tick o) {
        final long x = this.tick;
        final long y = o.tick;
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
