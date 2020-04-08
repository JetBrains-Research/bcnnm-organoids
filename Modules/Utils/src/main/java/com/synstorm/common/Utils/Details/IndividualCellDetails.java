package com.synstorm.common.Utils.Details;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.CellFunctionalType;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by human-research on 27/08/15.
 */

@Model_v0
@NonProductionLegacy

public class IndividualCellDetails implements IDetails {
    //region Fields
    private int id;
    private String tagName;
    private String cellType;
    private CellFunctionalType cellFunctionalType;
    private ICoordinate coordinate;
    private Map<String, Boolean> cellActions;
    //endregion

    //region Constructors
    private IndividualCellDetails(String cellType, CellFunctionalType cellFunctionalType, ICoordinate coordinate,
                                  Map<String, Boolean> cellActions) {
        this.cellType = cellType;
        this.cellFunctionalType = cellFunctionalType;
        this.coordinate = coordinate;
        this.cellActions = cellActions;
    }

    public IndividualCellDetails(int id, String cellType, CellFunctionalType cellFunctionalType,
                                 ICoordinate coordinate, Map<String, Boolean> cellActions) {
        this(cellType, cellFunctionalType, coordinate, cellActions);
        this.id = id;
    }

    public IndividualCellDetails(String id, String cellType, CellFunctionalType cellFunctionalType,
                                 ICoordinate coordinate, Map<String, Boolean> cellActions) {
        this(cellType, cellFunctionalType, coordinate, cellActions);
        this.id = Integer.parseInt(id);
    }
    //endregion

    //region Getters and Setters
    public int getId() {
        return id;
    }

    public String getCellType() {
        return cellType;
    }

    public void setCellType(String cellType) {
        this.cellType = cellType;
        cellActions.clear();
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public CellFunctionalType getCellFunctionalType() {
        return cellFunctionalType;
    }

    public void setCellFunctionalType(CellFunctionalType cellFunctionalType) {
        this.cellFunctionalType = cellFunctionalType;
    }

    public ICoordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(ICoordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Map<String, Boolean> getCellActions() {
        return cellActions;
    }

    //endregion

    //region Public Methods
    public void addCellAction(String action, Boolean isInitial) {
        cellActions.put(action, isInitial);
    }

    public void removeCellAction(String action) {
        cellActions.remove(action);
    }

    public IndividualCellDetails makeCopy() {
        Map<String, Boolean> tempCellActions = new TreeMap<>();
        for (Map.Entry<String, Boolean> entry : this.getCellActions().entrySet()) {
            String nString = String.valueOf(entry.getKey());
            Boolean nBoolean = entry.getValue();
            tempCellActions.put(nString, nBoolean);
        }
        return new IndividualCellDetails(String.valueOf(this.getId()), this.getCellType(), this.getCellFunctionalType(), this.getCoordinate(), tempCellActions);
    }
    //endregion

    //region Private Methods
    //endregion
}