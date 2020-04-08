package com.synstorm.SimulationModel.CellFunctionalComponents;

import com.synstorm.SimulationModel.Containers.ModelContainer;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.SimulationModel.Synapses.Synapse;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
import com.synstorm.common.Utils.Details.DendriteDetails;

import java.util.*;

/**
 * Created by Dmitry.Bozhko on 6/19/2015.
 */

@Model_v0
@ProductionLegacy
public class DendriteTree implements IDendriticSpineListener {
    //region Fields
    private final UUID individualId;
    private CellId neuronId;
    private int dendriteConnectionsCount;
    private double powerThresholdForConstant;
    private double nominalStimuliThreshold;
    private Map<CellId, DendriticSpine> dendriticSpineMap;

    private int receptorsCountForActivation;
    private int activeReceptorsSumOverTime;

    private boolean isAboveThreshold;
    private final int scale;

    private final Queue<CellId> stimulatingSynapses;
    private final Queue<CellId> activatingSynapses;
    private final Queue<CellId> deactivatingSynapses;
    //endregion

    //region Constructors
    public DendriteTree(UUID individualId, CellId neuronId, DendriteDetails dendriteDetails) {
        this.individualId = individualId;
        this.neuronId = neuronId;
        this.powerThresholdForConstant = dendriteDetails.getConstantThreshold();
        this.nominalStimuliThreshold = dendriteDetails.getStimuliThreshold();
        this.scale = ModelLoader.getNTScale();
        this.dendriteConnectionsCount = 0;
        this.dendriticSpineMap = new HashMap<>();
        this.stimulatingSynapses = new LinkedList<>();
        this.activatingSynapses = new LinkedList<>();
        this.deactivatingSynapses = new LinkedList<>();

        receptorsCountForActivation = 0;
        activeReceptorsSumOverTime = 0;
        isAboveThreshold = false;
    }
    //endregion

    //region Getters and Setters
    public boolean isAboveThreshold() {
        if (activeReceptorsSumOverTime >= receptorsCountForActivation && !isAboveThreshold)
            isAboveThreshold = true;
        else if (activeReceptorsSumOverTime < receptorsCountForActivation && isAboveThreshold)
            isAboveThreshold = false;

        return isAboveThreshold;
    }
    //endregion

    //region Public Methods
    public void addConnection(String receptorType, Synapse synapse) {
        CellId id  = synapse.getPresynapticNeuron();
        DendriticSpine dSpine = new DendriticSpine(individualId, id, synapse, receptorType, nominalStimuliThreshold);
        dendriticSpineMap.put(id, dSpine);
        dendriteConnectionsCount++;
        receptorsCountForActivation += dSpine.getReceptorCountForSpike();
    }

    public void removeConnection(CellId id) {
        dendriticSpineMap.remove(id);
        dendriteConnectionsCount--;
    }

    public boolean hasConnection() {
        return dendriteConnectionsCount > 0;
    }

    public void addToStimulated(CellId id) {
        stimulatingSynapses.add(id);
        dendriticSpineMap.get(id).reserveForBinding();
    }

    public void bindNeurotransmitterToReceptors() {
        final CellId stimulating = stimulatingSynapses.remove();
        dendriticSpineMap.get(stimulating).bindNtToReceptors();
        activatingSynapses.add(stimulating);
    }

    public boolean calculateActiveReceptorsOverTime() {
        final CellId activating = activatingSynapses.remove();
        activeReceptorsSumOverTime += dendriticSpineMap.get(activating).activateReceptors(scale, ModelContainer.INSTANCE.nextDouble(individualId));
        deactivatingSynapses.add(activating);
        return activeReceptorsSumOverTime >= receptorsCountForActivation;
    }

    public boolean decreaseActiveReceptorsOverTime() {
        final CellId deactivating = deactivatingSynapses.remove();
        activeReceptorsSumOverTime -= dendriticSpineMap.get(deactivating).deactivateReceptors();
        return activeReceptorsSumOverTime < receptorsCountForActivation;
    }

    public Set<Synapse> checkStimuliReceptors() {
        Set<Synapse> result = new HashSet<>();
        dendriticSpineMap.entrySet().stream()
                .filter(item -> item.getValue().checkStimuliReceptors(scale, ModelContainer.INSTANCE.nextDouble(individualId)))
                .forEach(item -> result.add(item.getValue().getSynapse()));

        return result;
    }

    public void makeSynapsesConstant() {
        dendriticSpineMap.entrySet().stream()
                .forEach(item -> item.getValue().makeSynapsesConstant(powerThresholdForConstant));
    }

    public Set<Synapse> breakConnections() {
        Set<Synapse> synapsesForDeletion = new HashSet<>();
        dendriticSpineMap.entrySet().stream().forEach(item -> {
            if (item.getValue().breakSynapse())
                synapsesForDeletion.add(item.getValue().getSynapse());
        });

        return synapsesForDeletion;
    }

    public void strengthenSpikedSynapses() {
        dendriticSpineMap.values().stream().forEach(item -> {
            if (item.isSpiked())
                item.increasePlasticity(scale);
        });
    }

    public void rebalanceSynapses() {
        dendriticSpineMap.values().stream().forEach(item -> {
            if (item.isSpiked())
                item.decreasePlasticity();
            else
                item.increasePlasticity(scale);
        });
    }

    @Override
    public void updateActiveReceptorCount(int delta) {
    }
    //endregion

    //region Private Methods
    //endregion
}