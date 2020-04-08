package com.synstorm.common.Utils.PlatformLoaders.Objects.MechanismsObjects;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.CodeGeneration.ILogicalExpression;
import com.synstorm.common.Utils.ConfigInterfaces.ICondition;
import com.synstorm.common.Utils.ProbabilitiesEvaluation.IDistribution;
import org.jetbrains.annotations.Contract;

@Model_v1
public final class Condition implements ICondition {
    private final ConditionParameter conditionParameter;
    private final ILogicalExpression rule;
    private final IDistribution distribution;
    private final int distributionVariable;
    private final int[] signals;

    public Condition(ConditionParameter conditionParameter, ILogicalExpression rule, int[] signals, IDistribution distribution, int distributionVariable) {
        this.conditionParameter = conditionParameter;
        this.rule = rule;
        this.signals = signals;
        this.distribution = distribution;
        this.distributionVariable = distributionVariable;
    }

    @Contract(pure = true)
    @Override
    public ILogicalExpression getRule() {
        return rule;
    }

    @Contract(pure = true)
    @Override
    public int[] getSignals() {
        return signals;
    }

    @Contract(pure = true)
    @Override
    public IDistribution getDistribution() {
        return distribution;
    }

    @Contract(pure = true)
    @Override
    public int getDistributionVariable() {
        return distributionVariable;
    }

    @Override
    @Contract(pure=true)
    public ConditionParameter getConditionParameter() {
        return conditionParameter;
    }

//    @Override
//    @Contract(pure = true)
//    public int getSelectionSignal() {
//        return conditionParameter.getSelectionLigand();
//    }
//
//    @Contract(pure = true)
//    public SignalSelectionType getSignalSelectionType() {
//        return conditionParameter.getSelectionType();
//    }
//
//
//    @Contract(pure = true)
//    @Override
//    public IDistribution getDistribution() { return conditionParameter.getDistribution();}
}
