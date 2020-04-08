package com.synstorm.SimulationModel.Graph;

import com.synstorm.SimulationModel.Graph.Edges.GraphEdge;
import com.synstorm.SimulationModel.Graph.GraphModels.SimpleUndirectedGraph;
import com.synstorm.SimulationModel.Graph.Vertices.GraphVertex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by bbrh on 10/05/16.
 */
public class SimpleUndirectedGraphTest {
    private SimpleUndirectedGraph g;
    private ArrayList<GraphVertex> vertices = new ArrayList<>();
    private int testVerticesNumber = 10;

    @Before
    public void setUp() throws Exception {
        initGraph(true);
    }

    @Test
    public void testGraphCompleteByDefault() {
        for (int i = 0; i < testVerticesNumber; i++) {
            for (int j = 0; j < testVerticesNumber; j++) {
                if (i == j)
                    continue;
                Assert.assertTrue(
                        String.format("No edge between Vertices %d and %d", i, j),
                        g.edgePresent(GraphEdge.integerEdge(i, j)));
            }
        }
    }

    @Test
    public void testGraphHaveNoEdgesIfNeeded() {
        initGraph(false);
        for (int i = 0; i < testVerticesNumber; i++) {
            for (int j = 0; j < testVerticesNumber; j++) {
                if (i != j) Assert.assertFalse(
                        String.format("Edge exists between Vertices %d and %d", i, j),
                        g.edgePresent(GraphEdge.integerEdge(i, j)));
            }
        }
    }

    @Test
    public void testEdgeCreateWorks() {
        initGraph(false);

        g.edgeCreate(GraphEdge.integerEdge(0, 1));
        Assert.assertTrue(g.edgePresent(GraphEdge.integerEdge(0, 1)));
        Assert.assertTrue(g.edgePresent(GraphEdge.integerEdge(1, 0)));
        Assert.assertFalse(g.edgePresent(GraphEdge.integerEdge(8, 1)));
        Assert.assertFalse(g.edgePresent(GraphEdge.integerEdge(0, 8)));

        g.edgeCreate(GraphEdge.integerEdge(2, 1));
        Assert.assertTrue(g.edgePresent(GraphEdge.integerEdge(2, 1)));
        Assert.assertTrue(g.edgePresent(GraphEdge.integerEdge(1, 2)));
        Assert.assertFalse(g.edgePresent(GraphEdge.integerEdge(0, 2)));

        g.edgeRemove(GraphEdge.integerEdge(2, 1));
        Assert.assertFalse(g.edgePresent(GraphEdge.integerEdge(2, 1)));
        Assert.assertFalse(g.edgePresent(GraphEdge.integerEdge(1, 2)));
    }

    @Test
    public void testEdgeRemoveWorks() {
        Assert.assertTrue(g.edgePresent(GraphEdge.integerEdge(0, 1)));

        g.edgeRemove(GraphEdge.integerEdge(0, 1));
        Assert.assertFalse(g.edgePresent(GraphEdge.integerEdge(0, 1)));
        Assert.assertFalse(g.edgePresent(GraphEdge.integerEdge(1, 0)));

        g.edgeRemove(GraphEdge.integerEdge(5, 4));
        Assert.assertFalse(g.edgePresent(GraphEdge.integerEdge(5, 4)));
        Assert.assertFalse(g.edgePresent(GraphEdge.integerEdge(4, 5)));
    }

    @Test
    public void testCountVertices() {
        Assert.assertTrue(g.countVertices() == testVerticesNumber);
    }

    @Test
    public void testVertexRemove() {
        g.vertexRemove(vertices.get(0));
        Assert.assertFalse(g.vertexPresent(vertices.get(0)));
        Assert.assertTrue(g.countVertices() == testVerticesNumber-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deletedVertexHaveNoEdges() {
        g.vertexRemove(vertices.get(0));
        g.edgePresent(GraphEdge.integerEdge(0, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deletedVertexHaveNoEdges2() {
        g.vertexRemove(vertices.get(0));
        g.edgePresent(GraphEdge.integerEdge(1, 0));
    }

    protected void initGraph(boolean connected) {
        g = new SimpleUndirectedGraph(connected);
        for (int i = 0; i < testVerticesNumber; i++) {
            GraphVertex v = new GraphVertex(i);
            vertices.add(v);
            g.vertexAdd(v);
        }
    }
}
