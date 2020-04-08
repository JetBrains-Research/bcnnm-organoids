package com.synstorm.common.Utils.ConfigInterfaces;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.CodeGeneration.ILogicalExpression;
import com.synstorm.common.Utils.PlatformLoaders.Objects.MechanismsObjects.ConditionParameter;
import com.synstorm.common.Utils.ProbabilitiesEvaluation.IDistribution;

@Model_v1
public interface ICondition {
    ILogicalExpression getRule();
    IDistribution getDistribution();
    int getDistributionVariable();
    int[] getSignals();
    ConditionParameter getConditionParameter();
}
