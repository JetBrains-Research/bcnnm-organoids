package com.synstorm.DES;

public final class WrappedModelEvent extends ModelEvent {
    //region Fields
    private final IEventWrapperReference eventWrapperReference;
    //endregion

    //region Constructors
    public WrappedModelEvent(IEventWrapperReference eventWrapper, IEventReference event, int duration, int priority) {
        super(event, duration, priority);
        eventWrapperReference = eventWrapper;
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    public IEventExecutionResult executeEvent() {
        eventState = EventState.Waiting;
        return eventWrapperReference.execute(eventReference, executionParameters);
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
