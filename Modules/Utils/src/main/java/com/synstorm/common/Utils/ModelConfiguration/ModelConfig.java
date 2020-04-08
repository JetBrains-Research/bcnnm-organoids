package com.synstorm.common.Utils.ModelConfiguration;

import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SpaceObjects.IObjectInstance;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ModelConfig {
    INSTANCE;

    //region Fields
    private final Map<String, ILogicObjectDescription> logicObjectDescriptionMap = new HashMap<>();
    private final List<IObjectInstance> objectInstanceList = new ArrayList<>();
    private int capacity = 0;
    private int[] signalsRadius = new int[0];
    //endregion

    //region Constructors
    //endregion

    //region Getters and Setters
    @NotNull
    public ILogicObjectDescription getLDescription(String lType) {
        return logicObjectDescriptionMap.get(lType);
    }

    @Contract(pure = true)
    public List<IObjectInstance> getObjectInstanceList() {
        return objectInstanceList;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int[] getSignalsRadius() {
        return signalsRadius;
    }

    public void setSignalsRadius(int[] signalRadius) {
        this.signalsRadius = signalRadius;
    }
    //endregion

    //region Public Methods
    public boolean addLogicObjectDescription(String id, ILogicObjectDescription logicObjectDescription) {
        if (logicObjectDescriptionMap.containsKey(id))
            return false;

        logicObjectDescriptionMap.put(id, logicObjectDescription);
        return true;
    }

    public void addCellObjectDescriptions(Map<String, ILogicObjectDescription> logicObjectDescriptions) {
        logicObjectDescriptionMap.putAll(logicObjectDescriptions);
    }

    public void addObjectInstance(IObjectInstance objectInstance) {
        objectInstanceList.add(objectInstance);
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
