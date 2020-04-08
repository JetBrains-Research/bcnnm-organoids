package com.synstorm.common.Utils.SimulationEvents.TeacherEvents;

import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.EnumTypes.TeacherMode;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

/**
 * Class for getting LQ calculated event during modeling.
 * Created by dvbozhko on 18/01/2017.
 */
public class LQCalculatedEvent implements ISimulationEvent {
    //region Fields
    private final TeacherMode mode;
    private final double[] lqs;
    //endregion

    //region Constructors
    public LQCalculatedEvent(TeacherMode mode, double[] lqs) {
        this.mode = mode;
        this.lqs = lqs;
    }
    //endregion

    //region Getters and Setters
    public TeacherMode getMode() {
        return mode;
    }

    public double[] getLqs() {
        return lqs;
    }
    //endregion

    //region Public Methods
    @Override
    public SimulationEvents getEventMethod() {
        return SimulationEvents.LQCalculatedEvent;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
