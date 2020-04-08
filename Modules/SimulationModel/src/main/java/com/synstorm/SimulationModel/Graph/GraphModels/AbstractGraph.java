package com.synstorm.SimulationModel.Graph.GraphModels;

import com.synstorm.SimulationModel.Graph.Edges.GraphEdge;
import com.synstorm.SimulationModel.Graph.Vertices.GraphVertex;

/**
 * Abstract class for working with graphs.
 * It is expected that unused methods of concrete classes would throw an exception (for example, {@code edgeRemove}
 * of complete graph doesn't make much sense).
 *
 * Created by bbrh on 29/04/16.
 */
abstract class AbstractGraph {

    /**
     * @param v vertex to add
     */
    public abstract void vertexAdd(GraphVertex v);

    /**
     * @param v vertex to remove
     */
    public abstract void vertexRemove(GraphVertex v);

    /**
     * @param v vertex to check for presence in the graph
     * @return {@code True} if v is in the graph
     */
    public abstract boolean vertexPresent(GraphVertex v);

    /**
     * @param v vertex to update
     */
    public abstract void vertexUpdate(GraphVertex v);

    public abstract void edgeCreate(GraphEdge e);

    public abstract void edgeRemove(GraphEdge e);
}
