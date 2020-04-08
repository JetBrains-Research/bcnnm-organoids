package com.synstorm.SimulationModel.Synapses;

import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
import com.synstorm.common.Utils.EnumTypes.NeurotransmitterType;

/**
 * Created by Dmitry.Bozhko on 6/19/2015.
 */

@Model_v0
@ProductionLegacy
public class Synapse {
    //region Fields
    private CellId presynapticNeuron;
    private CellId postsynapticNeuron;
    private String presynapticNeuronType;
    private double synapticPower;
    private double synapticPowerChangeProbability;
    private boolean isCurrentlyStimulated;
    private boolean isPreviouslyStimulated;
    private final int maxNeurotransmitterCount;
    private int releasedNeurotransmitterCount;
    private int boundNeurotransmitterCount;
//    private ISynapseStimulationListener dSpineListener;
    private final double KrMax;

    private String neurotransmitterName;
    private NeurotransmitterType neurotransmitterType;
    //endregion

    //region Constructors
    private Synapse() {
        synapticPower = 0;
        synapticPowerChangeProbability = 1;
        releasedNeurotransmitterCount = 0;
        boundNeurotransmitterCount = 0;
        maxNeurotransmitterCount = ModelLoader.getMaximumNTAndReceptors();
        KrMax = 5 / (double) maxNeurotransmitterCount;
        isCurrentlyStimulated = false;
        isPreviouslyStimulated = false;
    }

    public Synapse(CellId presynapticNeuron, CellId postsynapticNeuron, String presynapticNeuronType, String ntName, NeurotransmitterType ntType) {
        this();
        this.presynapticNeuron = presynapticNeuron;
        this.postsynapticNeuron = postsynapticNeuron;
        this.presynapticNeuronType = presynapticNeuronType;
        this.neurotransmitterName = ntName;
        this.neurotransmitterType = ntType;
    }
    //endregion

    //region Getters and Setters
    public CellId getPresynapticNeuron() {
        return presynapticNeuron;
    }

    public CellId getPostsynapticNeuron() {
        return postsynapticNeuron;
    }

    public String getPresynapticNeuronType() {
        return presynapticNeuronType;
    }

    public double getSynapticPower() {
        return synapticPower;
    }

    public double getSynapticPowerChangeProbability() {
        return synapticPowerChangeProbability;
    }

    public boolean isCurrentlyStimulated() {
        return isCurrentlyStimulated;
    }

    public boolean isPreviouslyStimulated() {
        return isPreviouslyStimulated;
    }

    public int getReleasedNeurotransmitterCount() {
        return releasedNeurotransmitterCount;
    }

    public String getNeurotransmitterName() {
        return neurotransmitterName;
    }

    public NeurotransmitterType getNeurotransmitterType() {
        return neurotransmitterType;
    }
    //endregion

    //region Public Methods
//    public void addDendriticSpineListener(ISynapseStimulationListener listener) {
//        if (dSpineListener == null)
//            dSpineListener = listener;
//    }

//    public void activate() {
//    }

    public int addNeurotransmitterToSynapse(int neurotransmitterCount) {
        releasedNeurotransmitterCount += neurotransmitterCount;
        isPreviouslyStimulated = isCurrentlyStimulated;

        if (releasedNeurotransmitterCount > maxNeurotransmitterCount)
            releasedNeurotransmitterCount = maxNeurotransmitterCount;

        if (releasedNeurotransmitterCount > 0)
            isCurrentlyStimulated = true;
//        dSpineListener.updateActiveReceptors();
        return releasedNeurotransmitterCount;
    }

    public int removeNeurotransmitterFromSynapse(int neurotransmitterCount) {
        releasedNeurotransmitterCount -= neurotransmitterCount;
        isPreviouslyStimulated = isCurrentlyStimulated;

        if (releasedNeurotransmitterCount <= 0) {
            releasedNeurotransmitterCount = 0;
            if (boundNeurotransmitterCount == 0)
                isCurrentlyStimulated = false;
        }

//        dSpineListener.updateActiveReceptors();
        return releasedNeurotransmitterCount;
    }

    public void bindNeurotransmitter(int neurotransmitterCount) {
        boundNeurotransmitterCount += neurotransmitterCount;
        releasedNeurotransmitterCount -= neurotransmitterCount;
    }

    public void unbindNeurotransmitter(int neurotransmitterCount) {
        boundNeurotransmitterCount -= neurotransmitterCount;
        releasedNeurotransmitterCount += neurotransmitterCount;
    }

    public void calculateSynapticPower(int receptorsCount) {
        //That's how we get sigmoid func where y in [0, 1] and x in [0, maxNeurotransmitterCount]
        synapticPower = (Math.tanh(KrMax * receptorsCount - 3) + 1) / 2;
        synapticPowerChangeProbability = 1 - synapticPower;
    }
    //endregion

    //region Private Methods
    //endregion
}
