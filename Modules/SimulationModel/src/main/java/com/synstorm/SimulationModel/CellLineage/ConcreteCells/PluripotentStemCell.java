package com.synstorm.SimulationModel.CellLineage.ConcreteCells;

import com.synstorm.SimulationModel.Annotations.SignalingPathway;
import com.synstorm.SimulationModel.CellLineage.AbstractCells.Cell;
import com.synstorm.SimulationModel.Containers.ModelContainer;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.IMethodResponse;
import com.synstorm.SimulationModel.LogicObject.IActionParameters;
import com.synstorm.SimulationModel.LogicObject.InitialPotentialCellObject;
import com.synstorm.SimulationModel.LogicObject.PotentialCellObject;
import com.synstorm.SimulationModel.ModelAction.ActionDispatcher;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import org.jetbrains.annotations.Contract;

/**
 * Class for common pluripotent stem cell.
 * Created by dvbozhko on 07/06/16.
 */

@Model_v0
@NonProductionLegacy
public class PluripotentStemCell extends Cell {
    //region Fields
    //endregion

    //region Constructors
    public PluripotentStemCell(CellId id, PotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
        super(id, potentialCellObject, dispatcher);
    }

    public PluripotentStemCell(CellId id, InitialPotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
        super(id, potentialCellObject, dispatcher);
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    @Override
    protected void initializeMethodsForExecution() {
        super.initializeMethodsForExecution();
        methodMap.put("PluripotentStemCellMove", this::PluripotentStemCellMove);
        methodMap.put("PluripotentStemCellLifeCycle", this::PluripotentStemCellLifeCycle);
        methodMap.put("MSCProliferationFactor", this::MSCProliferationFactor);
    }

    @Override
    protected void initializeAdditionalComponents() {

    }
    //endregion

    //region Private Methods

    //region Signaling Pathways
    @SignalingPathway
    private IMethodResponse PluripotentStemCellMove(IActionParameters parameters) {
        return move();
    }

    @SignalingPathway
    private IMethodResponse PluripotentStemCellLifeCycle(IActionParameters parameters) {
        increaseDeathProteinConcentration();
        return ModelContainer.INSTANCE.returnEmptyResponse();
    }

    @Contract("_ -> !null")
    @SignalingPathway
    private IMethodResponse MSCProliferationFactor(IActionParameters parameters) {
        return ModelContainer.INSTANCE.returnEmptyResponse();
    }
    //endregion

    //endregion
}
