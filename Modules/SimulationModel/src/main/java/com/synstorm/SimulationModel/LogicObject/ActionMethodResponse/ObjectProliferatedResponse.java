package com.synstorm.SimulationModel.LogicObject.ActionMethodResponse;

import com.synstorm.SimulationModel.LogicObject.PotentialCellObject;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;

/**
 * Created by dvbozhko on 07/06/16.
 */
public class ObjectProliferatedResponse implements IMethodResponse {
    //region Fields
    private final CellId id;
    private final PotentialCellObject potentialCellObject;
    //endregion

    //region Constructors
    public ObjectProliferatedResponse(CellId cellId, PotentialCellObject pcObject) {
        id = cellId;
        potentialCellObject = pcObject;
    }
    //endregion

    //region Getters and Setters
    public PotentialCellObject getPotentialCellObject() {
        return potentialCellObject;
    }

    public CellId getId() {
        return id;
    }
    //endregion

    //region Public Methods
    @Override
    public ProceedResponseMethods getMethodType() {
        return ProceedResponseMethods.ObjectProliferatedResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
