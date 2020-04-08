package com.synstorm.SimulationModel.ModelTime;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

/**
 * Created by Dmitry.Bozhko on 6/26/2014.
 */

@Model_v0
@NonProductionLegacy
public class ModelTime {
    private Tick currentTick;
//    private long cycleNum;

    public ModelTime() {
        currentTick = new Tick(0L);
//        cycleNum = 0L;
    }

//    public long getCycleNum() throws Exception {
//        return cycleNum;
//    }

    public Tick getCurrentTick() {
        return currentTick;
    }

    public void tick(long count) {
        currentTick = new Tick(count);
    }
}