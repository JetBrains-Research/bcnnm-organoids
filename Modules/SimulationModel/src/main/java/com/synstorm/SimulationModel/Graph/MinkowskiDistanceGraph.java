package com.synstorm.SimulationModel.Graph;

import com.synstorm.SimulationModel.Graph.GraphModels.CompleteUndirectedGraph;
import com.synstorm.SimulationModel.Graph.Vertices.GraphVertex;
import com.synstorm.SimulationModel.Graph.Vertices.GraphVertexId;
import com.synstorm.SimulationModel.Graph.Vertices.SpaceVertex;
import com.synstorm.common.Utils.SpaceUtils.CoordinateUtils;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dvbozhko on 08/06/16.
 */
public class MinkowskiDistanceGraph extends CompleteUndirectedGraph<Integer> {
    //region Fields
    private Map<GraphVertexId, ICoordinate> vertexCoordinates = new HashMap<>();
    //endregion

    //region Constructors
    //endregion

    //region Getters and Setters
    public Map<GraphVertexId, ICoordinate> getVertexCoordinates() {
        return vertexCoordinates;
    }
    //endregion

    //region Public Methods
    @Override
    public Integer DistanceFunction(GraphVertex u, GraphVertex v) {
        ICoordinate uCoordinate = vertexCoordinates.get(u.getId());
        ICoordinate vCoordinate = vertexCoordinates.get(v.getId());
        if (uCoordinate == null)
            throw new IllegalArgumentException("Vertex is not in the graph " + u.toString());
        if (vCoordinate == null)
            throw new IllegalArgumentException("Vertex is not in the graph " + v.toString());

        return CoordinateUtils.INSTANCE.calculateDistance(uCoordinate, vCoordinate);
    }

    public GraphVertex addVertexWithCoordinates(Integer id, ICoordinate coordinate) {
        GraphVertexId<Integer> vertexId = new GraphVertexId<>(id);
        vertexCoordinates.put(vertexId, coordinate);
        GraphVertex v = new GraphVertex(vertexId);
        this.vertexAdd(v);
        return v;
    }

    @Override
    public void vertexRemove(GraphVertex v) {
        super.vertexRemove(v);
        vertexCoordinates.remove(v.getId());
    }

    @Override
    public void vertexUpdate(GraphVertex v) {
        vertexCoordinates.put(v.getId(), ((SpaceVertex) v).getCoordinate());
        super.vertexUpdate(v);
    }

    public int getDistance(GraphVertex u, GraphVertex v) {
        return super.getWeight(u, v);
    }

    public int getDistance(GraphVertex u, ICoordinate coordinate) {
        ICoordinate uCoordinate = vertexCoordinates.get(u.getId());
        if (uCoordinate == null)
            throw new IllegalArgumentException("Vertex is not in the graph " + u.toString());

        return CoordinateUtils.INSTANCE.calculateDistance(uCoordinate, coordinate);
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
