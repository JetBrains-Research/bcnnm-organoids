package com.synstorm.common.Utils.EnumTypes;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

/**
 * Created by dvbozhko on 11/24/15.
 */

@Model_v0
@NonProductionLegacy

public enum TeacherMode {
    Initial,
    PreTrain,
    Train,
    Control,
    Done;

    private TeacherMode nextMode;

    static {
        Initial.nextMode = PreTrain;
        PreTrain.nextMode = Train;
        Train.nextMode = Control;
        Control.nextMode = Done;
    }

    public TeacherMode getNextMode() {
        return nextMode;
    }
}
