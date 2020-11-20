package com.synstorm.DES;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class EventsDispatcher {
    //region Fields
    private long currentTick;
//    private final Queue<ModelEvent> eventsQueue;
    private final TLongObjectMap<List<ModelEvent>> eventsDic;
    private final TIntObjectMap<Map<AllowedEvent, ModelEvent>> events;
    private final List<ModelEvent> startedEvents;
    private final List<ModelEvent> disruptedEvents;
    private final TickCalculationResult calculationResult;

    private final Queue<Long> eventTime;
    //endregion

    //region Constructors
    public EventsDispatcher() {
        currentTick = 0;
//        eventsQueue = new PriorityQueue<>();
        eventTime = new PriorityQueue<>();
        eventsDic = new TLongObjectHashMap<>();
        events = new TIntObjectHashMap<>();
        startedEvents = new ArrayList<>();
        disruptedEvents = new ArrayList<>();
        calculationResult = new TickCalculationResult();
    }
    //endregion

    //region Public Methods
    public void addLogicObjectEvents(int objectId, @NotNull Map<AllowedEvent, ModelEvent> eventsReferences) {
        events.put(objectId, eventsReferences);
    }

    public void deleteLogicObjectEvents(int objectId) {
        events.remove(objectId);
    }

    public void startEvent(int objectId, AllowedEvent eventType, IEventParameters params) {
        final ModelEvent event = events.get(objectId).get(eventType);
        event.setExecutionParameters(params);
        startedEvents.add(event);
    }

    public void startEventWithDelay(int objectId, AllowedEvent eventType, int delay, IEventParameters params) {
        final ModelEvent event = events.get(objectId).get(eventType);
        event.setEventDelay(delay);
        event.setExecutionParameters(params);
        startedEvents.add(event);
    }

    public void disruptEvent(int objectId, AllowedEvent eventType) {
        final ModelEvent event = events.get(objectId).get(eventType);
        disruptedEvents.add(event);
    }

    public void disruptAllEvents(int objectId) {
        events.get(objectId).forEach((k, v) -> disruptEvent(objectId, k));
    }

    public void disruptAllEventsExcept(int objectId, Set<AllowedEvent> exceptEvents) {
        events.get(objectId).forEach((k, v) -> {
            if (!exceptEvents.contains(k)) disruptEvent(objectId, k);
        });
    }

//    public List<IEventExecutionResult> calculateNextTick() {
//        calculationResult.clear();
//
//        do {
//            final ModelEvent event = eventsQueue.remove();
//            currentTick = event.getEventTime();
//            if (event.isActive())
//                calculationResult.addResponse(event.executeEvent());
//            else if (event.isPostponed())
//                addEvent(event);
//
//        } while(eventsQueue.peek() != null && currentTick == eventsQueue.peek().getEventTime());
//
//        return calculationResult.getResponses();
//    }

    public List<IEventExecutionResult> calculateNextTick() {
        calculationResult.clear();
        if (!eventTime.isEmpty())
            currentTick = eventTime.remove();
        else
            return calculationResult.getResponses();

        final List<ModelEvent> curEvents = eventsDic.get(currentTick);

        curEvents.forEach(e -> {
            if (e.isActive())
                calculationResult.addResponse(e.executeEvent());
            else if (e.isPostponed())
                addEvent(e);
        });

        eventsDic.remove(currentTick);
        return calculationResult.getResponses();
    }

    public long updateState() {
        startedEvents.stream().forEach(this::addEvent);
        disruptedEvents.stream().forEach(this::disruptEvent);
        startedEvents.clear();
        disruptedEvents.clear();
        return eventTime.peek() != null ? eventTime.peek() : Long.MAX_VALUE;
//        return eventsQueue.peek() != null ? eventsQueue.peek().getEventTime() : Long.MAX_VALUE;
    }
    //endregion

    //region Private Methods
    private void addEvent(@NotNull final ModelEvent event) {
        if (event.isWaiting()) {
            event.updateEvent(currentTick);
//            eventsQueue.add(event);
//            eventTime.add(event.getEventTime());
            if (!eventsDic.containsKey(event.getEventTime())) {
                eventTime.add(event.getEventTime());
                eventsDic.put(event.getEventTime(), new ArrayList<ModelEvent>() {{
                    add(event);
                }});
            } else {
                eventsDic.get(event.getEventTime()).add(event);
            }
        } else if (event.isWaitingInQueue()) {
            event.postponeEvent(currentTick);
        } else if (event.isPostponed()) {
            event.updatePostponed();
//            eventsQueue.add(event);
//            eventTime.add(event.getEventTime());
            if (!eventsDic.containsKey(event.getEventTime())) {
                eventTime.add(event.getEventTime());
                eventsDic.put(event.getEventTime(), new ArrayList<ModelEvent>() {{
                    add(event);
                }});
            } else {
                eventsDic.get(event.getEventTime()).add(event);
            }
        }
    }

    private void disruptEvent(@NotNull final ModelEvent event) {
        if (event.isActive() || event.isPostponed()) {
            event.inactivateEvent();
        }
    }
    //endregion
}
