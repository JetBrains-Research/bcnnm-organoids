package com.synstorm.DES;

@FunctionalInterface
public interface IEventReference {
    IEventExecutionResult execute(IEventParameters params);
}
