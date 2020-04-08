package com.synstorm.SimulationModel.Graph.Vertices;

/**
 * Created by bbrh on 29/04/16.
 */
public class GraphVertex {

    private GraphVertexId<Integer> id;

    public GraphVertex() {

    }

    public GraphVertex(Integer id) {
        this(new GraphVertexId<>(id));
    }

    public GraphVertex(GraphVertexId<Integer> id) {
        this.id = id;
    }

    public GraphVertexId<Integer> getId() {
        return id;
    }

    public void setId(GraphVertexId<Integer> id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GraphVertex)) return false;

        GraphVertex that = (GraphVertex) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "GraphVertex[" + (id != null ? id.toString() : "~") + "]";
    }
}
