package com.synstorm.SimulationModel.LogicObject.LogicObjects;

import com.synstorm.SimulationModel.Containers.ModelContainer;
import com.synstorm.SimulationModel.ModelTime.Tick;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.LObjectId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

import java.util.UUID;

/**
 * Abstract class for logic object (cell, glowing point, axon, radial glia, etc).
 * All logic objects are owned by individual and present in space.
 * Created by dvbozhko on 06/06/16.
 */

@Model_v0
@NonProductionLegacy
public abstract class LogicObject {
    //region Fields
    protected final LObjectId objectId;
    protected final UUID individualId;
    //endregion

    //region Constructors
    public LogicObject(LObjectId id, UUID individualId) {
        this.objectId = id;
        this.individualId = individualId;
    }
    //endregion

    //region Getters and Setters
    public LObjectId getObjectId() {
        return objectId;
    }

    public UUID getIndividualId() {
        return individualId;
    }
    //endregion

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    protected Tick getCurrentTick() {
        return ModelContainer.INSTANCE.getCurrentTick(individualId);
    }
    //endregion

    //region Private Methods
    //endregion
}
