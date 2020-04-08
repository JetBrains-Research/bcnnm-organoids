package com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds;

import com.synstorm.SimulationModel.Containers.ModelContainer;
import com.synstorm.SimulationModel.Model.Model;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.LogicObjectType;
import com.synstorm.common.Utils.ObjectIds.IObjectId;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Class identifier for all kinds of logic objects in the model.
 * Created by dvbozhko on 23/05/16.
 */

@Model_v0
@NonProductionLegacy
public class LObjectId implements Comparable<LObjectId>, IObjectId {
    //region Fields
    private int id;
    private LogicObjectType type;
    //endregion

    private LObjectId() {
        id = -1;
        type = LogicObjectType.Empty;
    }

    private LObjectId(int id) {
        this.id = id;
        type = LogicObjectType.Test;
    }

    public LObjectId(UUID id, LogicObjectType type) {
        this.id = ModelContainer.INSTANCE.nextLObjectId(id);
        this.type = type;
    }

    public LObjectId(LogicObjectType type) {
        this.id = Model.INSTANCE.nextLObjectId();
        this.type = type;
    }

    public LObjectId(int id, LogicObjectType type) {
        this.id = id;
        this.type = type;
    }

    @Contract(" -> !null")
    public static LObjectId emptyId() {
        return new LObjectId();
    }

    @Contract("_ -> !null")
    public static LObjectId testId(int id) {
        return new LObjectId(id);
    }
    //endregion

    //region Getters and Setters
    public int getId() {
        return id;
    }

    public LogicObjectType getType() {
        return type;
    }
    //endregion

    //region Public Methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LObjectId)) return false;

        LObjectId that = (LObjectId) o;

        return (this.id == that.id) && (this.type == that.type);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public int compareTo(@NotNull LObjectId o) {
        return Integer.compare(getId(), o.getId());
    }

    @Override
    public int hashCode() {
        return id;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
