package com.synstorm.DES;

@FunctionalInterface
public interface IEventWrapperReference {
    IEventExecutionResult execute(IEventReference eventReference, IEventParameters params);
}
