package com.synstorm.SimulationModel.LogicObject;

import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.LObjectId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.Details.IndividualCellDetails;

import java.util.Map;
import java.util.UUID;

/**
 * Created by dvbozhko on 14/06/16.
 */

@Model_v0
@NonProductionLegacy
public class InitialPotentialCellObject extends AbstractPotentialCell implements IPotentialCellObject {
    //region Fields
    private int configId;
    private Map<String, Boolean> cellActions;
    //endregion

    //region Constructors
    public InitialPotentialCellObject(int id, UUID individualId, LObjectId parentId,
                                      IndividualCellDetails individualCellDetails) {
        super(individualId, parentId, individualCellDetails.getCoordinate(), individualCellDetails.getCellType());
        configId = id;
        cellActions = individualCellDetails.getCellActions();
    }
    //endregion

    //region Getters and Setters
    public Map<String, Boolean> getCellActions() {
        return cellActions;
    }

    public int getConfigId() {
        return configId;
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
