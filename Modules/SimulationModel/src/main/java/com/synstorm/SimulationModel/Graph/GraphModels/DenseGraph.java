package com.synstorm.SimulationModel.Graph.GraphModels;

import com.synstorm.SimulationModel.Graph.Vertices.GraphVertex;
import com.synstorm.SimulationModel.Graph.Vertices.GraphVertexId;
import org.jetbrains.annotations.Contract;
import org.junit.Assert;

import java.lang.reflect.Array;
import java.util.HashMap;

/**
 * DenseGraph is undirected simple graph. Backed by a square array (triangle, to be precise) that is used for storing
 * connectivity, weights, etc.
 *
 * Not very efficient if you're planning to remove a lot of Vertices.
 *
 * Created by bbrh on 04/05/16.
 */
public abstract class DenseGraph<T> extends AbstractGraph {

    private int maxNumberOfVertices = 128;
    private final int scaleFactor = 2;
    private int verticesIndexCounter = 0;   // AUCHTUNG: not the same as number of Vs in the graph!

    final HashMap<GraphVertex, Integer> vertices = new HashMap<>();
    T[][] weights;

    DenseGraph() {
        @SuppressWarnings("unchecked") // weights are encapsulated into graphs and all inserts are strictly controlled
        T[][] newWeights = (T[][]) Array.newInstance(Object.class, maxNumberOfVertices, maxNumberOfVertices);
        this.weights = newWeights;
    }

    @Override
    public void vertexAdd(GraphVertex v) {
        Assert.assertNotNull(v);

        if (vertexPresent(v))
            throw new IllegalArgumentException("Vertex "+v+" already in the graph");

        if (v.getId() == null)
            v.setId(generateNextVertexId());
        else
            increaseVertexCounter();


        if (noMoreSpace()) {  // if we're out of space in weights, scale the array
            reinit(scaleFactor);
        }

        vertices.put(v, verticesIndexCounter); // add vertex
    }

    @Override
    public void vertexRemove(GraphVertex v) {
        Assert.assertNotNull(v);

        vertices.remove(v);
    }

    @Override
    public boolean vertexPresent (GraphVertex v) {
        Assert.assertNotNull(v);

        return vertices.containsKey(v);
    }

    @Override
    public void vertexUpdate(GraphVertex v) {
        Assert.assertNotNull(v);

        int oldKey = getVertexIndex(v);
        vertices.remove(v);
        vertices.put(v, oldKey);
    }

    void setWeight(GraphVertex u, GraphVertex v, T weight) {
        Assert.assertNotNull(u);
        Assert.assertNotNull(v);
        Assert.assertNotNull(weight);

        setWeight(getVertexIndex(u), getVertexIndex(v), weight);
    }

    protected T getWeight(GraphVertex u, GraphVertex v) {
        return getWeight(getVertexIndex(u), getVertexIndex(v));
    }

    protected abstract void setWeight(int uId, int vId, T weight);

    protected abstract T getWeight(int uId, int vId);

    /**
     * Counter for naming Vertices.
     * @return A unique int id in a graph
     */
    private GraphVertexId<Integer> generateNextVertexId() {
        return new GraphVertexId<>(verticesIndexCounter++);
    }

    /**
     * If we're adding vertex with pre-assigned id
     */
    private void increaseVertexCounter() {
        verticesIndexCounter++;
    }

    /**
     * @param v
     * @return an internal {@code int} code for the vertex {@param v}
     */
    private int getVertexIndex(GraphVertex v) {
        Assert.assertNotNull(v);

        Integer vertexId = vertices.get(v);
        if (vertexId == null)
            throw new IllegalArgumentException(String.format("%s is not in the graph", v.toString()));
        return vertexId;
    }


    /**
     * @return {@code true} if {@code reinit} call is needed
     */
    @Contract(pure = true)
    private boolean noMoreSpace () {
        return this.verticesIndexCounter >= this.maxNumberOfVertices;
    }

    /**
     * @return Actual number of Vertices in a graph.
     */
    public int countVertices () {
        return this.vertices.keySet().size();
    }

    /**
     * Increases space available for Vertices
     */
    private void reinit(int multiplier) {
        Assert.assertTrue(multiplier > 1);

        int oldInitEdgesSize = maxNumberOfVertices;
        maxNumberOfVertices = oldInitEdgesSize * multiplier;
        @SuppressWarnings("unchecked") // weights are encapsulated into graphs and all inserts are strictly controlled
        T[][] newWeights = (T[][]) Array.newInstance(Object.class, maxNumberOfVertices, maxNumberOfVertices);
        for (int i = 0; i < oldInitEdgesSize; i++) {
            for (int j = 0; j < oldInitEdgesSize; j++) {
                newWeights[i][j] = this.weights[i][j];
            }
        }
        this.weights = newWeights;
    }

}
