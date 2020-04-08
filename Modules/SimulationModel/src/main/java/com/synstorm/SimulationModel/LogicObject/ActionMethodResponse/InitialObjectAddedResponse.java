package com.synstorm.SimulationModel.LogicObject.ActionMethodResponse;

import com.synstorm.SimulationModel.LogicObject.InitialPotentialCellObject;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;

/**
 * Created by dvbozhko on 17/06/16.
 */
public class InitialObjectAddedResponse implements IMethodResponse {
    //region Fields
    private final InitialPotentialCellObject potentialCellObject;
    //endregion

    //region Constructors
    public InitialObjectAddedResponse(InitialPotentialCellObject pcObject) {
        potentialCellObject = pcObject;
    }
    //endregion

    //region Getters and Setters
    public InitialPotentialCellObject getPotentialCellObject() {
        return potentialCellObject;
    }
    //endregion

    //region Public Methods
    @Override
    public ProceedResponseMethods getMethodType() {
        return ProceedResponseMethods.InitialObjectAddedResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
