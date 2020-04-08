package com.synstorm.common.Utils.ConfigInterfaces.CodeGeneration;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;

@Model_v1
public interface ILogicalExpression {
    boolean compute(double[] args);
}
