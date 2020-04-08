package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

/**
 * Class for exporting concrete cell type for logging.
 * Created by dvbozhko on 22/06/16.
 */
@Model_v0
@NonProductionLegacy
public class CellTypeExportEvent implements ISimulationEvent {
    //region Fields
    private int typeId;
    private String typeName;
    //endregion

    //region Constructors
    public CellTypeExportEvent(String typeName, int typeId) {
        this.typeName = typeName;
        this.typeId = typeId;
    }
    //endregion

    //region Getters and Setters
    public int getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }
    //endregion

    //region Public Methods
    @Override
    public SimulationEvents getEventMethod() {
        return SimulationEvents.CellTypeExportEvent;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
