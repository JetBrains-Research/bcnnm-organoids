package com.synstorm.SimulationModel.CellLineage.R;

import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.DES.IEventParameters;
import com.synstorm.SimulationModel.Annotations.Mechanism;
import com.synstorm.SimulationModel.LogicObjectR.Cell;
import com.synstorm.SimulationModel.Model.Model;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;
import com.synstorm.common.Utils.Mechanisms.ModelingMechanisms;
import org.jetbrains.annotations.NotNull;

public class StemCell extends Cell {
    //region Fields
    //endregion

    //region Constructors
    public StemCell(int id, int parentId, ILogicObjectDescription logicObjectDescription) {
        super(id, parentId, logicObjectDescription);
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    protected void createMechanismReferences() {
        super.createMechanismReferences();
        mechanismReferences.put(ModelingMechanisms.CellCycleStop, this::cellCycleStop);
    }
    //endregion

    //region Private Methods
    @NotNull
    @Mechanism
    private IEventExecutionResult cellCycleStop(IEventParameters parameters) {
        return Model.emptyResponse;
    }
    //endregion
}
