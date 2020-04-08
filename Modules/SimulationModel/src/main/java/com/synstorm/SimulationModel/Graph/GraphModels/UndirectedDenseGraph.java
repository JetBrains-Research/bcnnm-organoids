package com.synstorm.SimulationModel.Graph.GraphModels;

/**
 * Undirected graph, optimized for objects with a lot of Edges.
 * Created by bbrh on 05/05/16.
 */
public abstract class UndirectedDenseGraph<T> extends DenseGraph<T> {

    protected void setWeight(int uId, int vId, T weight) {
        if (uId > vId)
            this.weights[uId][vId] = weight;
        else
            this.weights[vId][uId] = weight;
    }

    protected T getWeight(int uId, int vId) {
        return uId > vId ? this.weights[uId][vId] : this.weights[vId][uId];
    }

}
