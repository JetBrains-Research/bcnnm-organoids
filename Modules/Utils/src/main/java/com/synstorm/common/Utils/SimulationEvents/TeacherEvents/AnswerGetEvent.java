package com.synstorm.common.Utils.SimulationEvents.TeacherEvents;

import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ObjectIds.IObjectId;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class for getting answer event during modeling.
 * Created by dvbozhko on 21/06/16.
 */
public class AnswerGetEvent implements ISimulationEvent {
    //region Fields
    private final int answerId;
    private final long tick;
    private final int label;
    private final int length;
    private final String jsonAnswer;
    private final Map<Integer, Double> vector;
    //endregion

    //region Constructors
    public AnswerGetEvent(long tick, int answerId, int label, int length, Map<IObjectId, Double> vector, String jsonAnswer) {
        this.tick = tick;
        this.answerId = answerId;
        this.label = label;
        this.length = length;
        this.vector = new LinkedHashMap<>();
        vector.entrySet().stream().forEach(e -> this.vector.put(e.getKey().getId(), e.getValue()));
        this.jsonAnswer = jsonAnswer;
    }

    public AnswerGetEvent(long tick, int answerId, int label, int length,  Map<IObjectId, Double> vector) {
        this.tick = tick;
        this.answerId = answerId;
        this.label = label;
        this.length = length;
        this.vector = new LinkedHashMap<>();
        vector.entrySet().stream().forEach(e -> this.vector.put(e.getKey().getId(), e.getValue()));
        this.jsonAnswer = "";
    }
    //endregion

    //region Getters and Setters
    public int getAnswerId() {
        return answerId;
    }

    public long getTick() {
        return tick;
    }

    public int getLabel() {
        return label;
    }

    public int getLength() {
        return length;
    }

    public Map<Integer, Double> getVector() {
        return vector;
    }

    public String getJsonAnswer() {
        return jsonAnswer;
    }
    //endregion

    //region Public Methods
    @Override
    public SimulationEvents getEventMethod() {
        return SimulationEvents.AnswerGetEvent;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
