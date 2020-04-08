package com.synstorm.SimulationModel.CellLineage.R;

import com.synstorm.SimulationModel.Compartments.Axon;
import com.synstorm.SimulationModel.Compartments.DendriteTree;
import com.synstorm.SimulationModel.Connections.Synapse;
import com.synstorm.SimulationModel.LogicObjectR.Cell;
import com.synstorm.SimulationModel.Model.INetworkActivityListener;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;
import com.synstorm.common.Utils.ConfigInterfaces.LogicObjectTypes;
import com.synstorm.common.Utils.TraceMessageWriter.PriorityTraceWriter;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Base abstract class for all types of neurons
 * Created by dvbozhko on 07/06/16.
 */
@Model_v1
public class Neuron extends Cell {
    //region Fields
    private final TIntObjectMap<Synapse> inboundSynapses;
    private final TIntObjectMap<Synapse> outboundSynapses;

    boolean canGenerateSpike;
    boolean isAxonDone;

    short dendriteConnectionsProteinConcentration;
    short dendriteConnectionsProteinConcentrationThreshold;
    short axonConnectionsProteinConcentration;
    short axonConnectionsProteinConcentrationThreshold;
    long signalMovingDuration;

    protected NeuronActivity activity;
    protected INetworkActivityListener networkActivityListener;
    //endregion

    //region Constructors
    public Neuron(int id, int parentId, ILogicObjectDescription logicObjectDescription) {
        super(id, parentId, logicObjectDescription);

        inboundSynapses = new TIntObjectHashMap<>();
        outboundSynapses = new TIntObjectHashMap<>();
        activity = new NeuronActivity();
    }
    //endregion

    //region Getters and Setters
    public void setNetworkActivityListener(INetworkActivityListener listener) {
        networkActivityListener = listener;
    }

    public boolean hasAxon() {
        return uniqueCompartments.containsKey(LogicObjectTypes.Axon);
    }

    public boolean hasDendriteTree() {
        return uniqueCompartments.containsKey(LogicObjectTypes.DendriteTree);
    }

    public Optional<Axon> getAxon() {
        final Axon axon = (Axon) uniqueCompartments.get(LogicObjectTypes.Axon);
        return Optional.ofNullable(axon);
    }

    public List<int[]> getAxonCoordinates() {
        final Optional<Axon> optionalAxon = getAxon();
        if (optionalAxon.isPresent())
            return optionalAxon.get().getCoordinateList();

        PriorityTraceWriter.printf(0, "error: id=%d %s\n", objectId, "doesn't have an axon");
        return new ArrayList<>();
    }

    public Optional<DendriteTree> getDendriteTree() {
        final DendriteTree dendriteTree = (DendriteTree) uniqueCompartments.get(LogicObjectTypes.DendriteTree);
        return Optional.ofNullable(dendriteTree);
    }

    public int[] getAxonConnections() {
        final int[] result = new int[outboundSynapses.size()];
        int idx = 0;

        for (Object synapse : outboundSynapses.values())
            result[idx++] = ((Synapse) synapse).getDestCellId();

        return result;
    }

    public int[] getDendriteConnections() {
        final int[] result = new int[inboundSynapses.size()];
        int idx = 0;

        for (Object synapse : inboundSynapses.values())
            result[idx++] = ((Synapse) synapse).getSourceCellId();

        return result;
    }

    //endregion

    //region Public Methods
    public void addInboundSynapse(Synapse synapse) {
        inboundSynapses.put(synapse.getObjectId(), synapse);
    }

    public void addOutboundSynapse(Synapse synapse) {
        outboundSynapses.put(synapse.getObjectId(), synapse);
    }

    public void removeInboundSynapse(int id) {
        inboundSynapses.remove(id);
    }

    public void removeOutboundSynapse(int id) {
        outboundSynapses.remove(id);
    }

    public boolean isActive() {
        return true;
    }
    //endregion

    //region Package-local Methods

    //endregion

    //region Protected Methods
    protected void startSignalTransmitting() {

    }
    //endregion

    //region Private Methods
    //endregion

    protected class NeuronActivity {
        private int releaseCounter;

        public NeuronActivity() {
            releaseCounter = 0;
        }

        public void incrementRelease() {
            releaseCounter++;
        }

        public void decrementRelease() {
            releaseCounter--;
        }

        public boolean isReleasing() {
            return releaseCounter > 0;
        }
    }
}