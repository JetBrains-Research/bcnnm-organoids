package com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.LogicObjectType;

import java.util.UUID;

/**
 * Class identifier for axon objects in the model.
 * Created by dvbozhko on 23/05/16.
 */

@Model_v0
@NonProductionLegacy
public class AxonId extends LObjectId {
    //region Fields
    //endregion

    //region Constructors
    public AxonId(UUID id) {
        super(id, LogicObjectType.Axon);
    }
    //endregion

    //region Getters and Setters
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
