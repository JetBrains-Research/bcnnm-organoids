package com.synstorm.SimulationModel.Graph;

import com.synstorm.SimulationModel.Graph.Edges.GraphEdge;
import com.synstorm.SimulationModel.Graph.GraphModels.SimpleDirectedGraph;
import com.synstorm.SimulationModel.Graph.Vertices.GraphVertex;
import com.synstorm.SimulationModel.Graph.Vertices.GraphVertexId;
import com.synstorm.SimulationModel.Graph.Vertices.SpaceVertex;
import com.synstorm.SimulationModel.ModelAction.CellActionDescription;
import com.synstorm.SimulationModel.ModelAction.LogicObjectActionDescription;
import com.synstorm.SimulationModel.ModelAction.LogicObjectsActionsDictionary;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.LObjectId;
import com.synstorm.common.Utils.SignalCoordinateProbability.SignalProbabilityMap;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;
import org.jetbrains.annotations.Contract;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class for calculating chemical signal spreading probabilities in given coordinates.
 * Created by dvbozhko on 07/06/16.
 */
public class SignalIntensityDispatcher {
    //region Fields
    private final LogicObjectsActionsDictionary loaDictionary;
    private final MinkowskiDistanceGraph distanceGraph = new MinkowskiDistanceGraph();
    private final Map<String, SimpleDirectedGraph> acceptance = new HashMap<>();
    //endregion

    //region Constructors
    public SignalIntensityDispatcher(Set<String> signals) {
        loaDictionary = new LogicObjectsActionsDictionary();
        signals.forEach(signal -> acceptance.put(signal, new SimpleDirectedGraph()));
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    /**
     * Returns map with signal intensities for given cell object, set of signals and coordinate list
     *
     * @param objectId          id of object of interest
     * @param signals           set of interesting chemical signals
     * @param checkCoordinates  list of coordinates of interest
     * @return                  SignalProbabilityMap object
     */
    public SignalProbabilityMap getSignalProbabilityMap(LObjectId objectId, Set<String> signals, List<ICoordinate> checkCoordinates) {
        Assert.assertNotNull(signals);

        final SignalProbabilityMap spMap = new SignalProbabilityMap(signals);
        spMap.addCoordinates(checkCoordinates);

        // vertex of interest
        final GraphVertex receiverVertex = new GraphVertex(objectId.getId());

        // data from loaDictionary
        final Set<LogicObjectActionDescription> potentialAffectors = loaDictionary.actionDescriptionsForSignals(signals);

        // filtering potential affectors and calculating intensity for signals on given coordinates
        potentialAffectors.forEach(affector -> {
            final String signalName = affector.getActionName();
            final LObjectId emitterId = affector.getObjId();
            if (!signalIgnored(signalName, receiverVertex, emitterId)) {
                final int extension = affector.getActualExtension();
                final int distanceObj = distanceTo(receiverVertex, emitterId);
                if (distanceObj < extension + 1) {
                    checkCoordinates
                            .forEach(coordinate -> {
                                final int distance = distanceTo(emitterId, coordinate);
                                final double intensity = getIntensity(affector, distance);
                                spMap.addSignalIntensity(coordinate, signalName, intensity);
                            });
                }
            }
        });

        return spMap;
    }

    public void updateCellsForGpWithKp(CellId cellId, Set<String> signals) {
        // vertex of interest
        final GraphVertex receiverVertex = new GraphVertex(cellId.getId());

        loaDictionary.getGpWithKpSignals(signals).entrySet()
                .forEach(e -> {
                    if (distanceGraph.vertexPresent(receiverVertex)) {
                        if (distanceTo(receiverVertex, e.getKey().getObjectId()) < e.getValue().getActualExtension())
                            e.getValue().addObjectId(cellId);
                        else
                            e.getValue().removeObjectId(cellId);
                    } else
                        e.getValue().removeObjectId(cellId);
                });
    }

    public void addObject(LObjectId objectId, ICoordinate coordinate) {
        distanceGraph.addVertexWithCoordinates(objectId.getId(), coordinate);
        acceptance.values().forEach(signal -> signal.vertexAdd(new GraphVertex(objectId.getId())));
    }

    public void removeObject(LObjectId objectId) {
        final GraphVertex v = new GraphVertex(objectId.getId());
        distanceGraph.vertexRemove(v);
        acceptance.values().forEach(signal -> signal.vertexRemove(v));
    }

    public void updateActionDescriptionsInfo(List<LogicObjectActionDescription> adList) {
        loaDictionary.updateActionDescriptionsInfo(adList);
    }

    /**
     * Moves object with id {@param objectId} to {@param coordinates}
     *
     * @param objectId   id of object to move
     * @param coordinate new coordinate for object
     */
    public void moveObject(LObjectId objectId, ICoordinate coordinate) {
        final SpaceVertex v = new SpaceVertex(coordinate);
        v.setId(new GraphVertexId<>(objectId.getId()));
        distanceGraph.vertexUpdate(v);
    }

    /**
     * Sets signal {@param signal} to ignore state from {@param emitterVertexId} to {@param receiverVertexId}
     *
     * @param receiverVertexId  id of object receiving signal
     * @param emitterVertexId   id of object emitting signal
     * @param signal            name of signal
     */
    public void setSignalIgnore(LObjectId receiverVertexId, LObjectId emitterVertexId, String signal) {
        if (acceptance.containsKey(signal))
            acceptance.get(signal).edgeRemove(integerEdge(emitterVertexId.getId(), receiverVertexId.getId()));
    }

    /**
     * Sets signal {@param signal} to receive state from {@param emitterVertexId} to {@param receiverVertexId}
     *
     * @param receiverVertexId  id of object receiving signal
     * @param emitterVertexId   id of object emitting signal
     * @param signal            name of signal
     */
    public void setSignalReceive(LObjectId receiverVertexId, LObjectId emitterVertexId, String signal) {
        if (acceptance.containsKey(signal))
            acceptance.get(signal).edgeCreate(integerEdge(emitterVertexId.getId(), receiverVertexId.getId()));
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    private double getIntensity(LogicObjectActionDescription loaDescription, int distance) {
        if (loaDescription instanceof CellActionDescription)
            return getPRemoteness(
                    loaDescription.getActualExtension(),
                    distance);
        else
            return getPRemoteness(
                    loaDescription.getActualExtension(),
                    distance,
                    loaDescription.getKp());
    }

    private double getPRemoteness(double neighborhood, double distance) {
        final double normalizeRemoteness = (neighborhood - distance) / neighborhood;

        if (normalizeRemoteness < 0)
            return 0;
        else
            return Math.pow(normalizeRemoteness, 2);
    }

    private double getPRemoteness(double neighborhood, double distance, double Kp) {
        return getPRemoteness(neighborhood, distance) * Kp;
    }

    private int distanceTo(GraphVertex receiver, LObjectId emitterId) {
        final GraphVertex emitter = new GraphVertex(emitterId.getId());
        return distanceGraph.getDistance(receiver, emitter);
    }

    private int distanceTo(LObjectId emitterId, ICoordinate checkCoordinate) {
        final GraphVertex emitter = new GraphVertex(emitterId.getId());
        return distanceGraph.getDistance(emitter, checkCoordinate);
    }

    private boolean signalIgnored(String signal, GraphVertex receiver, LObjectId emitterId) {
        final GraphVertex emitter = new GraphVertex(emitterId.getId());
        return !acceptance.get(signal).edgePresent(new GraphEdge(emitter, receiver));
    }

    @Contract("_, _ -> !null")
    private GraphEdge integerEdge(int vId, int uId) {
        return new GraphEdge(
                new GraphVertex(new GraphVertexId<>(vId)),
                new GraphVertex(new GraphVertexId<>(uId))
        );
    }
    //endregion
}
