package com.synstorm.SimulationModel.LogicObject.LogicObjects;

import com.synstorm.SimulationModel.LogicObject.InitialPotentialCellObject;
import com.synstorm.SimulationModel.LogicObject.PotentialCellObject;
import com.synstorm.SimulationModel.ModelAction.ActionDispatcher;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.ConfigurationStrings.ClassPathsStrings;

import java.lang.reflect.Constructor;

/**
 * Created by dvbozhko on 06/06/16.
 */

@Model_v0
@NonProductionLegacy
public enum CellsFactory {
    INSTANCE;

    public CellObject createCell(PotentialCellObject potentialCellObject, ActionDispatcher dispatcher) throws Exception {
        CellId cellId = new CellId(potentialCellObject.getIndividualId());
        return createCell(cellId, potentialCellObject, dispatcher);
    }

    public CellObject createCell(CellId id, PotentialCellObject potentialCellObject, ActionDispatcher dispatcher) throws Exception {
        Class<?> classReference = Class.forName(ClassPathsStrings.CELL_LINEAGE_CLASSPATH + potentialCellObject.getCellType());
        Constructor<?> constructor = classReference.getConstructor(CellId.class, PotentialCellObject.class, ActionDispatcher.class);
        return (CellObject) constructor.newInstance(id, potentialCellObject, dispatcher);
    }

    public CellObject createCell(InitialPotentialCellObject potentialCellObject, ActionDispatcher dispatcher) throws Exception {
        CellId cellId = new CellId(potentialCellObject.getIndividualId());
        return createCell(cellId, potentialCellObject, dispatcher);
    }

    public CellObject createCell(CellId id, InitialPotentialCellObject potentialCellObject, ActionDispatcher dispatcher) throws Exception {
        Class<?> classReference = Class.forName(ClassPathsStrings.CELL_LINEAGE_CLASSPATH + potentialCellObject.getCellType());
        Constructor<?> constructor = classReference.getConstructor(CellId.class, InitialPotentialCellObject.class, ActionDispatcher.class);
        return (CellObject) constructor.newInstance(id, potentialCellObject, dispatcher);
    }
}
