package com.synstorm.SimulationModel.Graph.GraphModels;

import com.synstorm.SimulationModel.Graph.Edges.GraphEdge;
import com.synstorm.SimulationModel.Graph.Vertices.GraphVertex;

/**
 * Created by bbrh on 29/04/16.
 */
public abstract class CompleteUndirectedGraph<T> extends UndirectedDenseGraph<T> {


    protected abstract T DistanceFunction(GraphVertex u, GraphVertex v);

    /**
     * Replaces vertex with same {@code GraphVertexId}. Recalculates distances after update.
     * Will raise an exception if no such vertex exists.
     * @param v vertex to update
     */
    @Override
    public void vertexUpdate(GraphVertex v) {
        super.vertexUpdate(v);
        updateWeightsForVertex(v);
    }

    @Override
    public void vertexAdd(GraphVertex v) {
        super.vertexAdd(v);
        updateWeightsForVertex(v);
    }

    @Override
    public void edgeCreate(GraphEdge e) {
        throw new UnsupportedOperationException("The graph is complete");
    }

    @Override
    public void edgeRemove(GraphEdge e) {
        throw new UnsupportedOperationException("The graph is complete");
    }

    /**
     * Automatically calculate all weights for vertex.
     * @param v vertex that you want to update weights for
     */
    private void updateWeightsForVertex(GraphVertex v) {
        for (GraphVertex u : vertices.keySet()) {
            setWeight(u, v, DistanceFunction(u, v));
        }
    }
}
