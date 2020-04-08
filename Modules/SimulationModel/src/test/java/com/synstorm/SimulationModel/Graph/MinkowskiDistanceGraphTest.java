package com.synstorm.SimulationModel.Graph;

import com.synstorm.SimulationModel.Graph.Edges.GraphEdge;
import com.synstorm.SimulationModel.Graph.Vertices.GraphVertex;
import com.synstorm.SimulationModel.Graph.Vertices.GraphVertexId;
import com.synstorm.SimulationModel.Graph.Vertices.SpaceVertex;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;
import com.synstorm.common.Utils.SpaceUtils.IntegerCoordinateFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;


/**
 * Created by bbrh on 15/06/16.
 */

public class MinkowskiDistanceGraphTest {

    private MinkowskiDistanceGraph g;
    private ArrayList<GraphVertex> vertices = new ArrayList<>();
    private int testVerticesAmount = 10;
    private IntegerCoordinateFactory coordinateFactory = new IntegerCoordinateFactory(2*testVerticesAmount);


    @Before
    public void setUp() throws Exception {
        g = new MinkowskiDistanceGraph();
        for (int i = 0; i < testVerticesAmount; i++) {
            vertices.add(g.addVertexWithCoordinates(i, coordinateFactory.createCoordinate(i, i, i)));
        }
    }

    @Test
    public void distanceToArbitraryPoint() {
        ICoordinate a = coordinateFactory.createCoordinate(10, 1, 1);

        GraphVertex gv = new GraphVertex(new GraphVertexId<>(0));
        Assert.assertTrue(g.getDistance(gv, a) == 10);
        GraphVertex gu = new GraphVertex(new GraphVertexId<>(5));
        Assert.assertTrue(g.getDistance(gu, a) == 5);

        
    }

    @Test(expected = IllegalArgumentException.class)
    public void addingSameVertex() {
        g.vertexAdd(vertices.get(0));
        Assert.assertTrue(g.countVertices() == vertices.size());
    }

    @Test
    public void checkDistances() {
        Assert.assertTrue(g.getDistance(vertices.get(0), vertices.get(1)) == 1);
        Assert.assertTrue(g.getDistance(vertices.get(0), vertices.get(1)) ==
                g.getDistance(vertices.get(1), vertices.get(0)));
        Assert.assertTrue(g.getDistance(vertices.get(0), vertices.get(1)) ==
                g.getDistance(vertices.get(4), vertices.get(5)));

        Assert.assertTrue(g.getDistance(vertices.get(0), vertices.get(2)) ==
                g.getDistance(vertices.get(0), vertices.get(1)) +
                        g.getDistance(vertices.get(1), vertices.get(2)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDeletedDoesNotExistTo() {
        GraphVertex vertexToDelete = new GraphVertex(new GraphVertexId(1));
        GraphVertex otherVertex = new GraphVertex(new GraphVertexId(2));
        g.vertexRemove(vertexToDelete);
        g.getDistance(vertexToDelete, otherVertex);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDeletedDoesNotExistFrom() {
        GraphVertex vertexToDelete = new GraphVertex(new GraphVertexId(1));
        GraphVertex otherVertex = new GraphVertex(new GraphVertexId(2));
        g.vertexRemove(vertexToDelete);
        g.getDistance(otherVertex, vertexToDelete);
    }

    @Test
    public void ensureDistanceToSelfIsZero() {
        GraphVertex vertex = new GraphVertex(new GraphVertexId(2));
        Assert.assertTrue(g.getDistance(vertex, vertex) == 0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCannotCreateEdgesManually() {
        g.edgeCreate(new GraphEdge(
                new GraphVertex(new GraphVertexId<>(0)),
                new GraphVertex(new GraphVertexId<>(1))
        ));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCannotRemoveEdgesManually() {
        g.edgeRemove(new GraphEdge(
                new GraphVertex(new GraphVertexId<>(0)),
                new GraphVertex(new GraphVertexId<>(1))
        ));
    }

    @Test
    public void twoVerticesWithSameCoords() {
        int c = testVerticesAmount+1;
        GraphVertex u = g.addVertexWithCoordinates(c, coordinateFactory.createCoordinate(c, c, c));
        GraphVertex v = g.addVertexWithCoordinates(c+1, coordinateFactory.createCoordinate(c, c, c));
        Assert.assertTrue(g.getDistance(u, v) == 0);
    }

//
//    @Test
//    public void checkCoordinatesPreservedAfterEnteringTheGraph() {
//        int x = 213;
//        int y = 786;
//        int z = 111;
//        SpaceVertex u = (SpaceVertex) g.addVertexWithCoordinates(testVerticesAmount+1, coordinateFactory.createCoordinate(x, y, z));
//        Assert.assertTrue(Arrays.equals(u.getCoordinate(), new int[] {x, y, z}));
//    }
//

//    @Test
//    public void checkCoordinatesPreservedAfterEnteringTheGraph2() {
//        int[] coordinates = new int[] {123, 124, 9};
//        SpaceVertex u = (SpaceVertex) g.addVertexWithCoordinates(testVerticesAmount+1,
//                coordinateFactory.createCoordinate(coordinates[0], coordinates[1], coordinates[2]));
//        Assert.assertTrue(Arrays.equals(u.getCoordinates(), coordinates));
//    }

    /**
     * Testing Vertices movement
     */

    @Test
    public void testMove() {
        GraphVertex gvU = vertices.get(0);
        GraphVertex gvV = vertices.get(1);

        SpaceVertex u = SpaceVertex.assignCoordinate(gvU, g.getVertexCoordinates().get(gvU.getId()));
        SpaceVertex v = SpaceVertex.assignCoordinate(gvV, g.getVertexCoordinates().get(gvV.getId()));
        Assert.assertTrue(g.getDistance(u, v) == 1);

        int x1 = v.getCoordinate().getX() + 1;
        int y1 = v.getCoordinate().getY() + 1;
        int z1 = v.getCoordinate().getZ() + 1;
        v.setCoordinate(coordinateFactory.createCoordinate(x1, y1, z1));
        Assert.assertTrue(g.getDistance(u, v) == 1); // Should not change before update

        g.vertexUpdate(v);
        Assert.assertTrue(g.getDistance(u, v) == 2); // Should be 2 now

        int x2 = v.getCoordinate().getX();
        int y2 = v.getCoordinate().getY() + 10;
        int z2 = v.getCoordinate().getZ();
        v.setCoordinate(coordinateFactory.createCoordinate(x2, y2, z2));
        g.vertexUpdate(v);
        Assert.assertTrue(g.getDistance(u, v) == 12); // Should increase

        int x3 = 19;
        int y3 = v.getCoordinate().getY();
        int z3 = v.getCoordinate().getZ();
        v.setCoordinate(coordinateFactory.createCoordinate(x3, y3, z3));
        g.vertexUpdate(v);
        Assert.assertTrue(g.getDistance(u, v) == 19); // Negatives are fine
    }

    /**
     * Testing graph Vertices
     */
    @Test
    public void testEquality() {
        GraphVertex u = new GraphVertex(new GraphVertexId(1));
        GraphVertex v = new GraphVertex(new GraphVertexId(1));
        GraphVertex w = new GraphVertex(new GraphVertexId(11));
        Assert.assertTrue(u.equals(u));
        Assert.assertTrue(u.equals(v));
        Assert.assertFalse(u.equals(w));
    }

    @Test
    public void testVertexIdEquality() {
        GraphVertexId u = new GraphVertexId(1);
        GraphVertexId v = new GraphVertexId(1);
        GraphVertexId w = new GraphVertexId(11);
        Assert.assertTrue(u.equals(u));
        Assert.assertTrue(u.equals(v));
        Assert.assertFalse(u.equals(w));
    }
}