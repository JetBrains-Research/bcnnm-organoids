package com.synstorm.DES;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ModelEvent implements Comparable<ModelEvent> {
    //region Fields
    private final long id;
    private final int eventPriority;
    private long eventTime;
    private long postponeTime;
    private int eventDuration;
    private int eventDelay;
    protected EventState eventState;
    protected final IEventReference eventReference;
    protected IEventParameters executionParameters;
    //endregion

    private static long modelActionIdCounter = 0;

    //region Constructors
    public ModelEvent(IEventReference reference, int duration) {
        this(reference, duration, 0);
    }

    public ModelEvent(IEventReference reference, int duration, int priority) {
        id = incrementId();
        eventPriority = priority;
        eventTime = 0;
        postponeTime = 0;
        eventDuration = duration;
        eventDelay = 0;
        eventState = EventState.Waiting;
        eventReference = reference;
    }
    //endregion

    private static long incrementId() {
        return ++modelActionIdCounter;
    }

    //region Public Methods
    @Contract(pure = true)
    public long getEventTime() {
        return eventTime;
    }

    @Contract(pure = true)
    public boolean isActive() {
        return eventState == EventState.Active;
    }

    @Contract(pure = true)
    public boolean isWaiting() {
        return eventState == EventState.Waiting;
    }

    @Contract(pure = true)
    public boolean isWaitingInQueue() {
        return eventState == EventState.WaitingInQueue;
    }

    @Contract(pure = true)
    public boolean isPostponed() {
        return eventState == EventState.Postponed;
    }

    public void setExecutionParameters(IEventParameters params) {
        executionParameters = params;
    }

    public void setEventDelay(int delay) {
        eventDelay = delay;
    }

    public IEventExecutionResult executeEvent() {
        eventState = EventState.Waiting;
        return eventReference.execute(executionParameters);
    }

    public void updateEvent(long tick) {
        eventTime = tick + eventDelay + eventDuration;
        eventState = EventState.Active;
        eventDelay = 0;
    }

    public void postponeEvent(long tick) {
        postponeTime = tick + eventDuration;
        eventState = EventState.Postponed;
    }

    public void updatePostponed() {
        eventTime = postponeTime;
        eventState = EventState.Active;
    }

    public void inactivateEvent() {
        eventState = EventState.WaitingInQueue;
    }

    @Override
    @Contract(pure = true)
    public int hashCode() {
        return (int)(id ^ (id >>> 32));
    }

    @Override
    @Contract(pure = true)
    public int compareTo(@NotNull ModelEvent o) {
        final int timeCmp = Long.compare(eventTime, o.eventTime);
        if (timeCmp != 0)
            return timeCmp;

        final int priorityCmp = Integer.compare(eventPriority, o.eventPriority);
        if (priorityCmp != 0)
            return priorityCmp;

        return Long.compare(id, o.id);
    }
    //endregion
}
