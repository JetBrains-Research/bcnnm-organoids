package com.synstorm.SimulationModel.Graph.GraphModels;

/**
 * Exactly what class name says: this is a directed graph, optimized for objects with a lot of Edges.
 *
 * Created by bbrh on 05/05/16.
 */
public abstract class DirectedDenseGraph<T> extends DenseGraph<T> {

    protected void setWeight(int uId, int vId, T weight) {
        this.weights[vId][uId] = weight;
    }

    protected T getWeight(int uId, int vId) {
        return this.weights[vId][uId];
    }
}
