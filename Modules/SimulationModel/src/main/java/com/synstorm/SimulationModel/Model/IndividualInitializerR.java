package com.synstorm.SimulationModel.Model;

import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.SimulationModel.CellLineage.AbstractCells.Neuron;
import com.synstorm.SimulationModel.Containers.ModelContainer;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.IMethodResponse;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.InitialAxonGrownResponse;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.InitialSynapseAddedResponse;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.Mechanisms.MechanismResponse.CoordinateObjectAddedResponse;
import com.synstorm.common.Utils.ModelConfiguration.ModelConfig;
import com.synstorm.common.Utils.SpaceUtils.CoordinateUtils;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Package local enum for processing initial cells addition and connecting of initial synapses between neurons.
 * Created by dvbozhko on 17/06/16.
 */

@Model_v1
enum IndividualInitializerR {
    INSTANCE;

    //region Fields
    //endregion

    //region Constructors
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    public Queue<IEventExecutionResult> addInitialObjects() {
        Queue<IEventExecutionResult> addedResponses = new ArrayDeque<>();
        ModelConfig.INSTANCE.getObjectInstanceList()
                .forEach(item -> addedResponses.add(
                        new CoordinateObjectAddedResponse(-1, item.getILogicObjectDescription().getId(), item.getCoordinate())
                ));
        return addedResponses;
    }

//    public Queue<IMethodResponse> connectInitialSynapses(UUID modelId, Map<Integer, CellId> initialNeurons, Map<CellId, CellObject> cellObjects, IndividualDetails iDetails) {
//        Queue<IMethodResponse> addedResponses = new ArrayDeque<>();
//        initialNeurons.values().stream()
//                .forEach(cellId -> {
//                    Neuron neuron = (Neuron) cellObjects.get(cellId);
//                    neuron.formInitialDendriteTree();
//                    IMethodResponse formAxonResponse = neuron.formInitialAxon();
//                    if (formAxonResponse instanceof AxonFormResponse)
//                        addedResponses.add(formAxonResponse);
//                });
//
//        iDetails.getIndividualSynapses().entrySet().stream()
//                .forEach(item -> {
//                    Neuron neuron = (Neuron) cellObjects.get(initialNeurons.get(item.getKey()));
//                    Map<Neuron, ICoordinate> sinkMap = new LinkedHashMap<>();
//                    item.getValue().stream().forEach(connection -> {
//                        Neuron sinkNeuron = (Neuron) cellObjects.get(initialNeurons.get(connection));
//                        sinkMap.put(sinkNeuron, sinkNeuron.getCoordinate());
//                    });
//                    addedResponses.addAll(createAxon(modelId, neuron, sinkMap));
//                });
//
//        return addedResponses;
//    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    private Queue<IMethodResponse> createAxon(UUID modelId, Neuron neuron, Map<Neuron, ICoordinate> sinkMap) {
        Queue<IMethodResponse> addedResponses = new ArrayDeque<>();
        int growOrder = 0;
        int connectOrder = 0;
        ICoordinate source = neuron.getCoordinate();
        while (sinkMap.size() > 0) {
            Neuron nearestNeuron = nearest(modelId, source, sinkMap);
            ICoordinate nearest = nearestNeuron.getCoordinate();

            while (!source.equals(nearest)) {
                List<ICoordinate> neighborhoodCoordinates = CoordinateUtils.INSTANCE.makeNeighborCoordinates(source);
                ICoordinate toGrow = nextNearest(modelId, nearest, neighborhoodCoordinates);
                if (!toGrow.equals(nearest)) {
                    InitialAxonGrownResponse response = (InitialAxonGrownResponse) neuron.growInitialAxonByCoordinate(source, toGrow);
                    response.setGrowOrder(growOrder);
                    addedResponses.add(response);
                    growOrder++;
                    source = toGrow;
                } else {
                    InitialSynapseAddedResponse response = (InitialSynapseAddedResponse) neuron.connectInitialAxonByCoordinate((CellId) nearestNeuron.getObjectId());
                    response.setConnectOrder(connectOrder);
                    nearestNeuron.activateDendriteInitialSynapse(response.getSynapse());
                    addedResponses.add(response);
                    connectOrder++;
                    break;
                }
            }

            sinkMap.remove(nearestNeuron);
        }

        return addedResponses;
    }

    @NotNull
    private Neuron nearest(UUID uuid, ICoordinate source, Map<Neuron, ICoordinate> sinkMap) {
        return sinkMap.entrySet().stream()
                .min((item1, item2) -> {
                    long d1 = CoordinateUtils.INSTANCE.calculateDistance(source, item1.getValue());
                    long d2 = CoordinateUtils.INSTANCE.calculateDistance(source, item2.getValue());
                    int compare = Long.compare(d1, d2);
                    if (compare == 0) {
                        Short p1 = source.getPriority(item1.getValue());
                        Short p2 = source.getPriority(item2.getValue());
                        compare = p1.compareTo(p2);
                    }

                    if (compare == 0) {
                        if (ModelContainer.INSTANCE.nextDouble(uuid) > 0.5)
                            compare = -1;
                        else
                            compare = 1;
                    }

                    return compare;
                }).get().getKey();
    }

    @NotNull
    private ICoordinate nextNearest(UUID uuid, ICoordinate sink, List<ICoordinate> neighborhoodCoordinates) {
        return neighborhoodCoordinates.stream()
                .min((item1, item2) -> {
                    long d1 = CoordinateUtils.INSTANCE.calculateDistance(sink, item1);
                    long d2 = CoordinateUtils.INSTANCE.calculateDistance(sink, item2);
                    int compare = Long.compare(d1, d2);
                    if (compare == 0) {
                        Short p1 = sink.getPriority(item1);
                        Short p2 = sink.getPriority(item2);
                        compare = p1.compareTo(p2);
                    }

                    if (compare == 0) {
                        if (ModelContainer.INSTANCE.nextDouble(uuid)  > 0.5)
                            compare = -1;
                        else
                            compare = 1;
                    }

                    return compare;
                }).get();
    }
    //endregion
}
