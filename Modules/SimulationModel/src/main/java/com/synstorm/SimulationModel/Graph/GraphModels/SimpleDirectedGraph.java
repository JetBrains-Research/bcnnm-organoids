package com.synstorm.SimulationModel.Graph.GraphModels;

import com.synstorm.SimulationModel.Graph.Edges.GraphEdge;
import com.synstorm.SimulationModel.Graph.Vertices.GraphVertex;

/**
 * Created by bbrh on 05/05/16.
 */
public class SimpleDirectedGraph extends DirectedDenseGraph<Boolean> {

    private boolean completeByDefault = true;

    public SimpleDirectedGraph() {
    }

    public SimpleDirectedGraph(boolean completeByDefault) {
        this.completeByDefault = completeByDefault;
    }

    @Override
    public void edgeCreate(GraphEdge e) {
        setWeight(e.getFirst(), e.getSecond(), true);
    }

    @Override
    public void edgeRemove(GraphEdge e) {
        setWeight(e.getFirst(), e.getSecond(), false);
    }

    public boolean edgePresent(GraphEdge e) {
        return getWeight(e.getFirst(), e.getSecond());
    }

    @Override
    public void vertexAdd(GraphVertex v) {
        super.vertexAdd(v);
        for (GraphVertex u : vertices.keySet()) {
            if (u != v) {
                setWeight(u, v, completeByDefault);
                setWeight(v, u, completeByDefault);
            } else {
                setWeight(u, v, !completeByDefault);
                setWeight(v, u, !completeByDefault);
            }
        }
    }
}
