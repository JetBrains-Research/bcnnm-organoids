package com.synstorm.SimulationModel.Graph;

import com.synstorm.SimulationModel.CellLineage.AbstractCells.Neuron;
import com.synstorm.SimulationModel.Graph.Edges.GraphEdge;
import com.synstorm.SimulationModel.Graph.GraphModels.SimpleDirectedGraph;
import com.synstorm.SimulationModel.Graph.Vertices.GraphVertex;
import com.synstorm.SimulationModel.Synapses.Synapse;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by bbrh on 01/06/16.
 */
public class SynapticGraph {

    private SimpleDirectedGraph graph = new SimpleDirectedGraph();
    private Map<GraphEdge, Synapse> edgeSynapseMap = new HashMap<>();
    private Map<GraphVertex, HashSet<GraphVertex>> outgoing = new HashMap<>();
    private Map<GraphVertex, HashSet<GraphVertex>> incoming = new HashMap<>();

    /**
     * creates new synapse
     * @param synapse
     */
    public void createSynapse(Synapse synapse) {
        GraphVertex from = new GraphVertex(synapse.getPresynapticNeuron().getId());
        GraphVertex to = new GraphVertex(synapse.getPostsynapticNeuron().getId());

        Arrays.asList(from, to).stream()
                .filter(v -> !graph.vertexPresent(v))
                .forEach(graph::vertexAdd);

        GraphEdge e = synapseEdge(synapse);
        graph.edgeCreate(e);
        edgeSynapseMap.put(e, synapse);
        cacheEdge(e);
    }

    /**
     * deletes synapse
     * @param synapse
     */
    public void deleteSynapse(Synapse synapse) {
        GraphEdge e = synapseEdge(synapse);
        graph.edgeRemove(e);
        edgeSynapseMap.remove(e);
        unCacheEdge(e);
    }

    /**
     * @param from
     * @return Stream of synapses that starts at neuron {@param from}
     */
    public Stream<Synapse> synapsesFromNeuron(GraphVertex from) {
        if (outgoing.get(from) != null)
            return outgoing.get(from).stream().map(to -> new GraphEdge(from, to)).map(edgeSynapseMap::get);
        else
            return new LinkedHashSet<Synapse>().stream();
    }

    /**
     * @param to
     * @return Stream of synapses that ends at neuron {@param to}
     */
    public Stream<Synapse> synapsesToNeuron(GraphVertex to) {
        if (outgoing.get(to) != null)
            return incoming.get(to).stream().map(from -> new GraphEdge(from, to)).map(edgeSynapseMap::get);
        else
            return new LinkedHashSet<Synapse>().stream();
    }

    /**
     * needs actual implementation
     * @return
     */
    public boolean isActive() {
        return edgeSynapseMap.values().stream()
                .anyMatch(Synapse::isCurrentlyStimulated);
    }

    public List<Synapse> getStimulated() {
        return edgeSynapseMap.values().stream()
                .filter(Synapse::isCurrentlyStimulated)
                .collect(Collectors.toList());
    }

    public List<Synapse> getPreviouslyStimulated() {
        return edgeSynapseMap.values().stream()
                .filter(Synapse::isPreviouslyStimulated)
                .collect(Collectors.toList());
    }

    /**
     * example of cache usage
     * @param from
     * @param to
     */
    public boolean synapsePresent(int from, int to) {
        return outgoing.containsKey(from) && outgoing.get(from).contains(to);
    }

    /**
     * example of cache usage (equivalent of synapsePresent)
     * @param from
     * @param to
     */
    public boolean synapsePresent2(int from, int to) {
        return incoming.containsKey(to) && incoming.get(to).contains(from);
    }

    /**
     * puts an edge into local cache
     * @param e
     */
    private void cacheEdge(GraphEdge e) {
        cacheVerticesPair(outgoing, e.getFirst(), e.getSecond());
        cacheVerticesPair(incoming, e.getSecond(), e.getFirst());
    }

    /**
     * removes an edge from local cache
     * @param e
     */
    private void unCacheEdge(GraphEdge e) {
        unCacheVerticesPair(outgoing, e.getFirst(), e.getSecond());
        unCacheVerticesPair(incoming, e.getSecond(), e.getFirst());
    }

    /**
     * implements bidirectional edge caching, not supposed to be used outside of cacheEdge
     * @param cache
     * @param key
     * @param val
     */
    private void cacheVerticesPair(Map<GraphVertex, HashSet<GraphVertex>> cache,
                                   GraphVertex key,
                                   GraphVertex val) {
        if (!cache.containsKey(key)) {
            cache.put(key, new HashSet<GraphVertex>() {{ add(val); }});
        } else {
            cache.get(key).add(val);
        }
    }

    /**
     * implements bidirectional edge caching, not supposed to be used outside of unCacheEdge
     * @param cache
     * @param key
     * @param val
     */
    private void unCacheVerticesPair(Map<GraphVertex, HashSet<GraphVertex>> cache,
                                     GraphVertex key,
                                     GraphVertex val) {
        if (!cache.containsKey(key)) {
            throw new IllegalArgumentException(String.format("vertex %s is not cached", key));
        } else {
            cache.get(key).remove(val);
        }
    }

    /**
     * some not-so-sweet sugar that wraps tedious conversions
     * @param synapse
     * @return
     */
    private static GraphEdge synapseEdge(Synapse synapse) {
        return GraphEdge.integerEdge(synapse.getPresynapticNeuron().getId(), synapse.getPostsynapticNeuron().getId());
    }

    @FunctionalInterface
    private interface IAddDendriteSynapse {
        void execute(Neuron neuron, Synapse synapse);
    }
}

