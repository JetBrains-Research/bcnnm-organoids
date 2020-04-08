package com.synstorm.SimulationModel.LogicObject.LogicObjects;

import com.synstorm.SimulationModel.ModelAction.GlowingPointActionDescription;
import com.synstorm.SimulationModel.SimulationIdentifiers.ModelActionIds.ModelActionId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.GlowingPointId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.Details.GPDetails;
import com.synstorm.common.Utils.EnumTypes.ActionFunctionalType;
import com.synstorm.common.Utils.SpaceUtils.CoordinateUtils;

import java.util.UUID;

/**
 * Class for glowing point which spread signal by concrete type in space
 * Created by dvbozhko on 06/06/16.
 */

@Model_v0
@NonProductionLegacy
public class GlowingPoint extends SignalPoint {
    //region Fields
    private final GlowingPointActionDescription gpActionDescription;
    //endregion

    //region Constructors
    public GlowingPoint(GlowingPointId id, UUID individualId, GPDetails details) {
        super(id, individualId, CoordinateUtils.INSTANCE.createCoordinate(
                details.getCoordinate().getX(),
                details.getCoordinate().getY(),
                details.getCoordinate().getZ()));

        gpActionDescription = new GlowingPointActionDescription(
                new ModelActionId(individualId, objectId, details.getFactorName(), ActionFunctionalType.Do),
                details.getRadius(), details.isCalculateKp());
    }
    //endregion

    //region Getters and Setters
    public GlowingPointActionDescription getGpActionDescription() {
        return gpActionDescription;
    }

    public String getFactorName() {
        return gpActionDescription.getActionName();
    }
    //endregion

    //region Public Methods
    public void addObjectId(CellId id) {
        gpActionDescription.addObjectId(id);
    }

    public void removeObjectId(CellId id) {
        gpActionDescription.removeObjectId(id);
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
