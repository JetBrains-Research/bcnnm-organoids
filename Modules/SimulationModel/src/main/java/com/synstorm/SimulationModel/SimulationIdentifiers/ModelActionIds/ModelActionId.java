package com.synstorm.SimulationModel.SimulationIdentifiers.ModelActionIds;

import com.synstorm.SimulationModel.Containers.ModelContainer;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.LObjectId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.ActionFunctionalType;

import java.util.UUID;

/**
 * Class identifier for actions in the model.
 * Created by dvbozhko on 27/05/16.
 */

@Model_v0
@NonProductionLegacy
public class ModelActionId {
    //region Fields
    private final LObjectId objectId;
    private final String name;



    private final ActionFunctionalType type;
    private final long id;
    //endregion

    //region Constructors
    public ModelActionId(UUID id, LObjectId objId, String actionName, ActionFunctionalType type) {
        this.objectId = objId;
        this.name = actionName;
        this.type = type;
        this.id = ModelContainer.INSTANCE.nextModelActionId(id);
    }
    //endregion

    //region Getters and Setters
    public LObjectId getObjectId() {
        return objectId;
    }

    public String getName() {
        return name;
    }

    public ActionFunctionalType getType() {
        return type;
    }

    public long getId() {
        return id;
    }
    //endregion

    //region Public Methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModelActionId)) return false;

        ModelActionId that = (ModelActionId) o;

        return (this.id == that.id) && (this.objectId == that.objectId) && (this.name.equals(that.name));
    }

    @Override
    public String toString() {
        return "[id:" + String.valueOf(id) + ";name:" + name + ";objectId:" + objectId + "]";
    }

    @Override
    public int hashCode() {
        return (int)(id ^ (id >>> 32));
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
