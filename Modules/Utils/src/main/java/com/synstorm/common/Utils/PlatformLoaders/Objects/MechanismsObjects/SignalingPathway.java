package com.synstorm.common.Utils.PlatformLoaders.Objects.MechanismsObjects;

import com.synstorm.DES.AllowedEvent;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathway;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathwayParameters;
import com.synstorm.common.Utils.Mechanisms.ModelingMechanisms;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Model_v1
public final class SignalingPathway implements ISignalingPathway {
    //region Fields
    private final SpId spId;
    private final int duration;
    private final boolean initial;
    private Condition condition;
    private ISignalingPathwayParameters executeParameter;
    private Map<ConditionType, Map<CascadeAction, Set<AllowedEvent>>> cascadePathways;
    //endregion


    //region Constructors

    public SignalingPathway(ModelingMechanisms mechanism, String id, double duration, boolean initial) {
        this.spId = new SpId(mechanism, id);
        this.duration = (int) Math.round(duration);
        this.initial = initial;
        this.cascadePathways = new HashMap<>();
        initCascadePathways();
    }

    //endregion

    //region Getters and Setters


    @Contract(pure = true)
    @Override
    public AllowedEvent getPathway() {
        return spId;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Contract(pure = true)
    public int getDuration() {
        return duration;
    }

    @Contract(pure = true)
    public boolean isInitial() {
        return initial;
    }

    @Override
    public ISignalingPathwayParameters getExecutingParameters() {
        return executeParameter;
    }

    @Contract(pure = true)
    public Condition getCondition() {
        return condition;
    }

    @Override
    public Set<AllowedEvent> getExecutingOnConditionTrueSignalingPathways() {
        return cascadePathways.get(ConditionType.OnConditionTrue).get(CascadeAction.Execute);
    }

    @Override
    public Set<AllowedEvent> getExecutingOnConditionFalseSignalingPathways() {
        return cascadePathways.get(ConditionType.OnConditionFalse).get(CascadeAction.Execute);
    }

    @Override
    public Set<AllowedEvent> getExecutingDefaultSignalingPathways() {
        return cascadePathways.get(ConditionType.Default).get(CascadeAction.Execute);
    }

    @Override
    public Set<AllowedEvent> getDisruptingOnConditionTrueSignalingPathways() {
        return cascadePathways.get(ConditionType.OnConditionTrue).get(CascadeAction.Disrupt);
    }

    @Override
    public Set<AllowedEvent> getDisruptingOnConditionFalseSignalingPathways() {
        return cascadePathways.get(ConditionType.OnConditionFalse).get(CascadeAction.Disrupt);
    }

    @Override
    public Set<AllowedEvent> getDisruptingDefaultSignalingPathways() {
        return cascadePathways.get(ConditionType.Default).get(CascadeAction.Disrupt);
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public void setExecuteParameter(String type, Object[] parameter) {
        this.executeParameter = MechanismParametersFactory.INSTANCE.createExecuteParameter(type, parameter);
    }


    //endregion


    //region Public Methods
    public void addSignalingPathway(ConditionType conditionType, CascadeAction cascadeAction, ISignalingPathway sp) {
        cascadePathways.get(conditionType).get(cascadeAction).add(sp.getPathway());
    }
    //endregion


    //region Private Methods
    private void initCascadePathways() {
        for (ConditionType cType : ConditionType.values()) {
            Map<CascadeAction, Set<AllowedEvent>> events = new HashMap<>();
            for (CascadeAction cascadeAction : CascadeAction.values())
                events.put(cascadeAction, new HashSet<>());
            cascadePathways.put(cType, events);
        }
    }
    //endregion
}
