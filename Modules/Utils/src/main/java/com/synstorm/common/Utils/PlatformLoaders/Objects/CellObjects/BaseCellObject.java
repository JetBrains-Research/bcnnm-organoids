package com.synstorm.common.Utils.PlatformLoaders.Objects.CellObjects;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ICellObjectDescription;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathway;
import com.synstorm.common.Utils.ConfigInterfaces.LogicObjectTypes;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Model_v1
public abstract class BaseCellObject implements ICellObjectDescription {
    //region Fields
    private String id;
    private LogicObjectTypes baseType;
    private Set<ISignalingPathway> signalingPathways;
    private Map<String, BaseCellObject> compartmentMap;
    //endregion

    //region Constructors
    public BaseCellObject(String id, LogicObjectTypes baseType) {
        this.id = id;
        this.baseType = baseType;
        this.signalingPathways = new HashSet<>();
        this.compartmentMap = new HashMap<>();
    }
    //endregion

    //region Getters and Setters
    @Override
    @Contract(pure = true)
    public String getId() {
        return id;
    }

    @Override
    @Contract(pure = true)
    public Set<ISignalingPathway> getSignalingPathways() {
        return signalingPathways;
    }

    @Override
    @Contract(pure = true)
    public LogicObjectTypes getBaseType() {
        return baseType;
    }

    @NotNull
    @Override
    public ICellObjectDescription getCompartment(String compartmentId) {
        return compartmentMap.get(compartmentId);
    }
    //endregion

    //region Public Methods
    public void addCompartment(CellCompartment cellCompartment) {
        compartmentMap.put(cellCompartment.getId(), cellCompartment);
    }

    public Map<String, ILogicObjectDescription> getAllObjectDescriptions() {
        final Map<String, ILogicObjectDescription> result = new HashMap<>();
        recursiveDescriptionAdd(this, result);
        return result;
    }

    public void addSp(ISignalingPathway sp) {
        signalingPathways.add(sp);
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    protected Map<String, BaseCellObject> getCompartmentMap() {
        return compartmentMap;
    }
    //endregion

    //region Private Methods
    private void recursiveDescriptionAdd(BaseCellObject description, Map<String, ILogicObjectDescription> cellObjectDescriptionMap) {
        cellObjectDescriptionMap.put(description.getId(), description);
        description.getCompartmentMap().forEach((e, v) -> recursiveDescriptionAdd(v, cellObjectDescriptionMap));
    }
    //endregion
}
