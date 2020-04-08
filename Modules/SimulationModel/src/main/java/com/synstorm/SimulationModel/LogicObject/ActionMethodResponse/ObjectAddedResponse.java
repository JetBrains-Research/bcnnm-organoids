package com.synstorm.SimulationModel.LogicObject.ActionMethodResponse;

import com.synstorm.SimulationModel.LogicObject.PotentialCellObject;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;

/**
 * Created by dvbozhko on 07/06/16.
 */
public class ObjectAddedResponse implements IMethodResponse {
    //region Fields
    private final PotentialCellObject potentialCellObject;
    //endregion

    //region Constructors
    public ObjectAddedResponse(PotentialCellObject pcObject) {
        potentialCellObject = pcObject;
    }
    //endregion

    //region Getters and Setters
    public PotentialCellObject getPotentialCellObject() {
        return potentialCellObject;
    }
    //endregion

    //region Public Methods
    @Override
    public ProceedResponseMethods getMethodType() {
        return ProceedResponseMethods.ObjectAddedResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
