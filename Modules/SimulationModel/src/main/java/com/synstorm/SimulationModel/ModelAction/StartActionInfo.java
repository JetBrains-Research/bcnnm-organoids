package com.synstorm.SimulationModel.ModelAction;

import com.synstorm.SimulationModel.LogicObject.IActionParameters;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.ActionFunctionalType;

/**
 * Created by dvbozhko on 09/03/2017.
 */

@Model_v0
@NonProductionLegacy
public class StartActionInfo {
    //region Fields
    private final CellId cellId;
    private final String actionName;
    private final ActionFunctionalType type;
    private final IActionParameters parameters;
    //endregion

    //region Constructors
    public StartActionInfo(CellId cellId, String actionName, ActionFunctionalType type, IActionParameters parameters) {
        this.cellId = cellId;
        this.actionName = actionName;
        this.type = type;
        this.parameters = parameters;
    }
    //endregion

    //region Getters and Setters
    public CellId getCellId() {
        return cellId;
    }

    public String getActionName() {
        return actionName;
    }

    public ActionFunctionalType getType() {
        return type;
    }

    public IActionParameters getParameters() {
        return parameters;
    }
    //endregion

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
