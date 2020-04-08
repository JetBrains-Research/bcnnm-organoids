package com.synstorm.SimulationModel.Graph.Vertices;

/**
 * Created by bbrh on 29/04/16.
 */
public class GraphVertexId<T> {

    private final T id;

    public GraphVertexId(T id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GraphVertexId)) return false;

        GraphVertexId<?> that = (GraphVertexId<?>) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
