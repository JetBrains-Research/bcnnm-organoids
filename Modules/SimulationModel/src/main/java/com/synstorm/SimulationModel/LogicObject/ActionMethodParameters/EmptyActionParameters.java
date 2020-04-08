package com.synstorm.SimulationModel.LogicObject.ActionMethodParameters;

import com.synstorm.SimulationModel.LogicObject.IActionParameters;

/**
 * Created by dvbozhko on 09/03/2017.
 */
public class EmptyActionParameters implements IActionParameters {
    //region Fields
    private final long startTick;
    //endregion

    //region Constructors
    public EmptyActionParameters(long startTick) {
        this.startTick = startTick;
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    @Override
    public long getStartTick() {
        return startTick;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
