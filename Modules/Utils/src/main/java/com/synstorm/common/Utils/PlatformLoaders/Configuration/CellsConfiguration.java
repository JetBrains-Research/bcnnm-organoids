package com.synstorm.common.Utils.PlatformLoaders.Configuration;

import com.synstorm.common.Utils.PlatformLoaders.Objects.CellObjects.Cell;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CellsConfiguration {
    //region Fields
    private Map<String, Cell> cells;
    //endregion


    //region Constructors

    public CellsConfiguration() {
        this.cells = new HashMap<>();
    }

    //endregion


    //region Getters and Setters
    @Nullable
    public Cell getCell(String cellId) {
        return cells.get(cellId);
    }

    public Collection<Cell> getAllCells() {
        return cells.values();
    }
    //endregion


    //region Public Methods
    public void addCell(Cell cell) {
        cells.put(cell.getId(), cell);
    }
    //endregion


    //region Private Methods

    //endregion

}
