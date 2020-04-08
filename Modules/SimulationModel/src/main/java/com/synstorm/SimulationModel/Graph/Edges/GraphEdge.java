package com.synstorm.SimulationModel.Graph.Edges;

import com.synstorm.SimulationModel.Graph.Vertices.GraphVertex;

/**
 * Created by bbrh on 29/04/16.
 */
public class GraphEdge {

    private final GraphVertex first;
    private final GraphVertex second;

    public GraphEdge(GraphVertex first, GraphVertex second) {
        this.first = first;
        this.second = second;
    }

    public GraphVertex getFirst() {
        return first;
    }

    public GraphVertex getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GraphEdge)) return false;

        GraphEdge graphEdge = (GraphEdge) o;

        return first.equals(graphEdge.first) && second.equals(graphEdge.second);
    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
        result = 31 * result + second.hashCode();
        return result;
    }

    public static GraphEdge integerEdge(int firstId, int secondId) {
        return new GraphEdge(new GraphVertex(firstId), new GraphVertex(secondId));
    }
}
