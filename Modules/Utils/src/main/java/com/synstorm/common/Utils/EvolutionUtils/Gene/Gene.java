package com.synstorm.common.Utils.EvolutionUtils.Gene;

import com.synstorm.common.Utils.TraceMessageWriter.PriorityTraceWriter;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by human-research on 21/05/15.
 */
public final class Gene {
    private String name;
    private double duration;
    private double mutability;
    private Map<String, Integer> actionPresentationMap = new TreeMap<>();

    public Gene(String name, int duration) {
        this.name = name;
        this.duration = duration;
        this.mutability = 0.0;
    }

    public Gene(String name, double duration, double mutability) {
        this.name = name;
        this.duration = duration;
        this.mutability = mutability;
    }

    public Map<String, Integer> getActionPresentationMap() {
        return actionPresentationMap;
    }

    public String getName() {
        return name;
    }

    public double getDuration() {
        return duration;
    }

    public double getMutability() {
        return mutability;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setMutability(double mutability) {
        this.mutability = mutability;
    }

    public void addAction(String actionName, int present) {
        if (actionPresentationMap.containsKey(actionName)) {
            PriorityTraceWriter.println("Gene " + this.name + " already contains " + actionName + " action. It will be rewritten.", 2);
        }
        actionPresentationMap.put(actionName, present);
    }

    public void removeAction(String actionName) {
        actionPresentationMap.remove(actionName);
    }

    @Override
    public int hashCode() {
        int ascii = 0;
        double coefficient = 15.0;
        Double multiplier = this.mutability * coefficient;
        for (int i = 0; i < name.length(); i++) {
            ascii = ascii + (int)(name.charAt(i) * duration);
        }
        return ascii * multiplier.intValue();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }
}
