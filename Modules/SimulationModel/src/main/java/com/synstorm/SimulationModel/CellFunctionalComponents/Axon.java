package com.synstorm.SimulationModel.CellFunctionalComponents;

import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.AxonId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.SimulationModel.Synapses.Synapse;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
import com.synstorm.common.Utils.Details.AxonDetails;
import com.synstorm.common.Utils.EnumTypes.NeurotransmitterType;
import com.synstorm.common.Utils.SpaceUtils.CoordinateUtils;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

import java.util.*;

/**
 * Class for axonal mechanisms representation
 * Created by Dmitry.Bozhko on 11/11/2014.
 */

@Model_v0
@ProductionLegacy
public class Axon {
    //region Fields
    private final AxonId axonId;
    private final CellId neuronId;
    private final int maximumNTCount;
    private final int scale;
    private final double nominalNTUptakingPercentage;
    private int stemLength;
    private int axonConnectionCount;
    private boolean hasGrown;
    private int nominalNeurotransmitterCountPerSynapse;
    private int nominalExpressNTThreshold;
    private int nominalUptakingNeurotransmitterCount;
    private int previousReleaseCount;
    private double releaseThreshold;
    private double continuousReleasePercent;
    private double continuousExpressPercent;
    private double axonalNTChangeProbability;

    private ICoordinate endCoordinate;
    private String neurotransmitter;
    private NeurotransmitterType ntType;
    private List<ICoordinate> coordinateList;

    private final Map<CellId, Integer> axonalConnectionLength;
    private final Map<CellId, Synapse> axonalSynapses;
    private final Map<CellId, Integer> neurotransmitterInVesicles;
    private final Map<CellId, Integer> neurotransmitterInClefts;
    private final Map<CellId, Integer> releaseCountOverTime;

    private final double Kr;
    private final double KrMax;
    private final double KrScalePower;
    private int ScrCur;
    //endregion

    //region Constructors
    public Axon(UUID individualId, CellId cellId, ICoordinate coordinate, AxonDetails axonDetails) {
        axonId = new AxonId(individualId);
        neuronId = cellId;
        scale = ModelLoader.getNTScale();
        ScrCur = ModelLoader.getNTScalePosition();
        maximumNTCount = ModelLoader.getMaximumNTAndReceptors();
        KrScalePower = (-20) / (double) scale;
        Kr = 1 / (double) maximumNTCount;
        KrMax = 5 / (double) maximumNTCount;
        continuousReleasePercent = axonDetails.getReleasingNTPercentage();
        continuousExpressPercent = axonDetails.getExpressingNTPercentage();
        neurotransmitter = axonDetails.getNeurotransmitter();
        ntType = ModelLoader.getNTType(neurotransmitter);
        releaseThreshold = ModelLoader.getNTReleaseThreshold(neurotransmitter);
        nominalNTUptakingPercentage = axonDetails.getUptakeNTPercentage();
        previousReleaseCount = 0;
        stemLength = 0;
        axonConnectionCount = 0;
        coordinateList = new ArrayList<>();
        axonalSynapses = new HashMap<>();
        axonalConnectionLength = new LinkedHashMap<>();
        neurotransmitterInVesicles = new HashMap<>();
        neurotransmitterInClefts = new HashMap<>();
        releaseCountOverTime = new HashMap<>();

        calculateNominalValues();
        endCoordinate = coordinate;
        coordinateList.add(coordinate);
        hasGrown = true;
    }
    //endregion

    //region Getters and Setters
    public AxonId getAxonId() {
        return axonId;
    }

    public ICoordinate getEndCoordinate() {
        return endCoordinate;
    }

    public List<ICoordinate> getCoordinateList() {
        return coordinateList;
    }

    public boolean hasGrown() {
        return hasGrown;
    }

    public void resetHasGrown() {
        hasGrown = false;
    }

    public boolean hasConnection() {
        return axonConnectionCount > 0;
    }

    public Map<CellId, Integer> getAxonalConnectionLength() {
        return axonalConnectionLength;
    }
    //endregion

    //region Public Methods
    public boolean isNTAboveThreshold(CellId id) {
        final int ntInVesicles = neurotransmitterInVesicles.get(id);
        return ntInVesicles >= nominalExpressNTThreshold;
    }

    public void grow(ICoordinate coordinate) {
        ICoordinate last = coordinateList.get(coordinateList.size() - 1);
        if (!coordinate.equals(last)) {
            coordinateList.add(coordinate);
            endCoordinate = coordinate;
            stemLength += CoordinateUtils.INSTANCE.calculateDistance(last, coordinate);
            hasGrown = true;
        }
    }

    public Synapse connect(CellId destinationNeuron, String diffType) {
        axonalConnectionLength.put(destinationNeuron, stemLength + 1);
        axonConnectionCount++;
        hasGrown = true;
        Synapse toAdd = new Synapse(neuronId, destinationNeuron, diffType, neurotransmitter, ntType);
        axonalSynapses.put(destinationNeuron, toAdd);
        setDefaultValues(destinationNeuron);

        return toAdd;
    }

    public void removeConnection(CellId destinationNeuron) {
        axonalConnectionLength.remove(destinationNeuron);
        axonalSynapses.remove(destinationNeuron);
        axonConnectionCount--;
    }

    public int compareReleaseFrequency(double checkP) {
        int result = 0;
        int maxReleaseCount = Collections.max(releaseCountOverTime.values());

        if (maxReleaseCount > previousReleaseCount)
            result = 1;
        else if (maxReleaseCount < previousReleaseCount)
            result = -1;

        double scaleShiftProbability = Math.tanh(0.2 * Math.abs(maxReleaseCount - previousReleaseCount));
        if (scaleShiftProbability < checkP)
            result = 0;

        previousReleaseCount = maxReleaseCount;
        releaseCountOverTime.entrySet().stream()
                .forEach(entry -> entry.setValue(0));

        return result;
    }

    public void increaseNominalNTCount(double checkP) {
        if (ScrCur < scale && axonalNTChangeProbability > checkP) {
            ScrCur++;
            calculateNominalValues();
        }
    }

    public void decreaseNominalNTCount(double checkP) {
        if (ScrCur > 0 && axonalNTChangeProbability > checkP) {
            ScrCur--;
            calculateNominalValues();
        }
    }

    public Synapse releaseNeurotransmitter(CellId id) {
        final Synapse synapse = axonalSynapses.get(id);
        final int ntInVesicles = neurotransmitterInVesicles.get(id);
//        int continuousNtReleaseCount = (int) Math.round(continuousReleasePercent * nominalNeurotransmitterCountPerSynapse);
        int continuousNtReleaseCount = (int) (continuousReleasePercent * nominalNeurotransmitterCountPerSynapse);
        if (ntInVesicles > 0) { // && continuousNtReleaseCount > 0) {
            continuousNtReleaseCount = ntInVesicles > continuousNtReleaseCount ? continuousNtReleaseCount : ntInVesicles;
            final int remainNtCountInVesicles = ntInVesicles - continuousNtReleaseCount;
            final int newNtInCleft = synapse.addNeurotransmitterToSynapse(continuousNtReleaseCount);
            neurotransmitterInVesicles.replace(id, remainNtCountInVesicles);
            neurotransmitterInClefts.replace(id, newNtInCleft);
            releaseCountOverTime.replace(id, releaseCountOverTime.get(id) + 1);
        }

        return synapse;
    }

    public Synapse uptakeNeurotransmitter(CellId id) {
        final Synapse synapse = axonalSynapses.get(id);
        final int ntInCleft = neurotransmitterInClefts.get(id);
        final int ntInVesicles = neurotransmitterInVesicles.get(id);
        int ntToUptake = ntInCleft > nominalUptakingNeurotransmitterCount ?
                nominalUptakingNeurotransmitterCount : ntInCleft;
//        int ntToUptake = (int) (ntInCleft * nominalNTUptakingPercentage);
//        if (ntInCleft > 0 && ntToUptake == 0)
//            ntToUptake = 1;

        ntToUptake = ntInCleft > ntToUptake ? ntToUptake : ntInCleft;

        final int newNtInCleft = synapse.removeNeurotransmitterFromSynapse(ntToUptake);
        int newNtInVesicles = ntInVesicles + ntToUptake;
        newNtInVesicles = nominalNeurotransmitterCountPerSynapse > newNtInVesicles ?
                newNtInVesicles : nominalNeurotransmitterCountPerSynapse;

        neurotransmitterInClefts.replace(id, newNtInCleft);
        neurotransmitterInVesicles.replace(id, newNtInVesicles);
        return synapse;
    }

    public Synapse degradeNeurotransmitter(CellId id) {
        final Synapse synapse = axonalSynapses.get(id);
        final int ntInCleft = neurotransmitterInClefts.get(id);
//        int ntToDegrade = (int) (nominalNeurotransmitterCountPerSynapse * nominalNTUptakingPercentage);
        int ntToDegrade = (int) (ntInCleft * nominalNTUptakingPercentage);
        if (ntInCleft > 0 && ntToDegrade == 0)
            ntToDegrade = 1;

        ntToDegrade = ntInCleft > ntToDegrade ? ntToDegrade : ntInCleft;

        final int newNtInCleft = synapse.removeNeurotransmitterFromSynapse(ntToDegrade);
        neurotransmitterInClefts.replace(id, newNtInCleft);
        return synapse;
    }

    public boolean expressNeurotransmitter(CellId id) {
        final int ntCountInVesicle = neurotransmitterInVesicles.get(id);
        final int nominalNtInVesicle = nominalNeurotransmitterCountPerSynapse;
        if (ntCountInVesicle < nominalNtInVesicle) {
            int toAdd = ntCountInVesicle + (int) Math.round(continuousExpressPercent * nominalNtInVesicle);
            if (toAdd < nominalNtInVesicle)
                neurotransmitterInVesicles.replace(id, toAdd);
            else
                neurotransmitterInVesicles.replace(id, nominalNtInVesicle);
        }

        return ntCountInVesicle < nominalNtInVesicle;
    }
    //endregion

    //region Private Methods
    private void calculateNominalValues() {
        final int nominal = (int) Math.round(1 / (Kr + Math.exp(KrScalePower * ScrCur)));
        nominalNeurotransmitterCountPerSynapse = nominal <= maximumNTCount ? nominal : maximumNTCount;
        nominalExpressNTThreshold = (int) (nominalNeurotransmitterCountPerSynapse * releaseThreshold);
        nominalUptakingNeurotransmitterCount = (int) (nominalNeurotransmitterCountPerSynapse * nominalNTUptakingPercentage);
        nominalUptakingNeurotransmitterCount = nominalUptakingNeurotransmitterCount > 0 ? nominalUptakingNeurotransmitterCount : 1;
        axonalNTChangeProbability = 1 - (Math.tanh(KrMax * nominalNeurotransmitterCountPerSynapse - 3) + 1) / 2;
    }

    private void setDefaultValues(CellId id) {
        releaseCountOverTime.put(id, 0);
        neurotransmitterInVesicles.put(id, 0);
        neurotransmitterInClefts.put(id, 0);
    }
    //endregion
}
