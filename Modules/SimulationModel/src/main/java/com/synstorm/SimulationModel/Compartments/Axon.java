package com.synstorm.SimulationModel.Compartments;

import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.DES.IEventParameters;
import com.synstorm.SimulationModel.Annotations.Mechanism;
import com.synstorm.SimulationModel.LogicObjectR.Compartment;
import com.synstorm.SimulationModel.Model.Model;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ICondition;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathway;
import com.synstorm.common.Utils.Mechanisms.MechanismResponse.ObjectMovedResponse;
import com.synstorm.common.Utils.Mechanisms.ModelingMechanisms;
import com.synstorm.common.Utils.SignalCoordinateProbability.CoordinateProbabilityR;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class for axonal mechanisms representation
 * Created by Dmitry.Bozhko on 11/11/2014.
 */
@Model_v1
public class Axon extends Compartment {
    //region Fields
//    private final int maximumNTCount;
//    private final int scale;
//    private final double nominalNTUptakingPercentage;
//    private int stemLength;
//    private int axonConnectionCount;
//    private int nominalNeurotransmitterCountPerSynapse;
//    private int nominalExpressNTThreshold;
//    private int nominalUptakingNeurotransmitterCount;
//    private int previousReleaseCount;
//    private double releaseThreshold;
//    private double continuousReleasePercent;
//    private double continuousExpressPercent;
//    private double axonalNTChangeProbability;
//
//    private String neurotransmitter;
//    private NeurotransmitterType ntType;
    private List<int[]> coordinateList;
//
//    private final Map<LObjectId, Integer> axonalConnectionLength;
//    private final Map<CellId, Synapse> axonalSynapses;
//    private final Map<CellId, Integer> neurotransmitterInVesicles;
//    private final Map<CellId, Integer> neurotransmitterInClefts;
//    private final Map<CellId, Integer> releaseCountOverTime;
//
//    private final double Kr;
//    private final double KrMax;
//    private final double KrScalePower;
//    private int ScrCur;
    //endregion

    //region Constructors
//    public Axon(UUID individualId, CellId cellId, ICoordinate coordinate, AxonDetails axonDetails) {
//        neuronId = cellId;
//        scale = ModelLoader.getNTScale();
//        ScrCur = ModelLoader.getNTScalePosition();
//        maximumNTCount = ModelLoader.getMaximumNTAndReceptors();
//        KrScalePower = (-20) / (double) scale;
//        Kr = 1 / (double) maximumNTCount;
//        KrMax = 5 / (double) maximumNTCount;
//        continuousReleasePercent = axonDetails.getReleasingNTPercentage();
//        continuousExpressPercent = axonDetails.getExpressingNTPercentage();
//        neurotransmitter = axonDetails.getNeurotransmitter();
//        ntType = ModelLoader.getNTType(neurotransmitter);
//        releaseThreshold = ModelLoader.getNTReleaseThreshold(neurotransmitter);
//        nominalNTUptakingPercentage = axonDetails.getUptakeNTPercentage();
//        previousReleaseCount = 0;
//        stemLength = 0;
//        axonConnectionCount = 0;
//        coordinateList = new ArrayList<>();
//        axonalSynapses = new HashMap<>();
//        axonalConnectionLength = new LinkedHashMap<>();
//        neurotransmitterInVesicles = new HashMap<>();
//        neurotransmitterInClefts = new HashMap<>();
//        releaseCountOverTime = new HashMap<>();
//
//        calculateNominalValues();
//        coordinateList.add(coordinate);
//    }

    public Axon(int id, int parentId, ILogicObjectDescription logicObjectDescription) {
        super(id, parentId, logicObjectDescription);
        coordinateList = new ArrayList<>();
        coordinateList.add(spaceShell.getCoordinate(parentId));
    }
    //endregion

    //region Getters and Setters
    public List<int[]> getCoordinateList() {
        return coordinateList;
    }

//    public boolean hasConnection() {
//        return axonConnectionCount > 0;
//    }
//
//    public Map<CellId, Integer> getAxonalConnectionLength() {
//        return axonalConnectionLength;
//    }
//    //endregion
//
//    //region Public Methods
//    public boolean isNTAboveThreshold(CellId id) {
//        final int ntInVesicles = neurotransmitterInVesicles.get(id);
//        return ntInVesicles >= nominalExpressNTThreshold;
//    }
//
//    public void grow(ICoordinate coordinate) {
//        ICoordinate last = coordinateList.get(coordinateList.size() - 1);
//        if (!coordinate.equals(last)) {
//            coordinateList.add(coordinate);
//            stemLength += CoordinateUtils.INSTANCE.calculateDistance(last, coordinate);
//            hasGrown = true;
//        }
//    }
//
//    public Synapse connect(CellId destinationNeuron, String diffType) {
//        axonalConnectionLength.put(destinationNeuron, stemLength + 1);
//        axonConnectionCount++;
//        hasGrown = true;
//        Synapse toAdd = new Synapse(neuronId, destinationNeuron, diffType, neurotransmitter, ntType);
//        axonalSynapses.put(destinationNeuron, toAdd);
//        setDefaultValues(destinationNeuron);
//
//        return toAdd;
//    }
//
//    public void removeConnection(CellId destinationNeuron) {
//        axonalConnectionLength.remove(destinationNeuron);
//        axonalSynapses.remove(destinationNeuron);
//        axonConnectionCount--;
//    }
//
//    public int compareReleaseFrequency(double checkP) {
//        int result = 0;
//        int maxReleaseCount = Collections.max(releaseCountOverTime.values());
//
//        if (maxReleaseCount > previousReleaseCount)
//            result = 1;
//        else if (maxReleaseCount < previousReleaseCount)
//            result = -1;
//
//        double scaleShiftProbability = Math.tanh(0.2 * Math.abs(maxReleaseCount - previousReleaseCount));
//        if (scaleShiftProbability < checkP)
//            result = 0;
//
//        previousReleaseCount = maxReleaseCount;
//        releaseCountOverTime.entrySet().stream()
//                .forEach(entry -> entry.setValue(0));
//
//        return result;
//    }
//
//    public void increaseNominalNTCount(double checkP) {
//        if (ScrCur < scale && axonalNTChangeProbability > checkP) {
//            ScrCur++;
//            calculateNominalValues();
//        }
//    }
//
//    public void decreaseNominalNTCount(double checkP) {
//        if (ScrCur > 0 && axonalNTChangeProbability > checkP) {
//            ScrCur--;
//            calculateNominalValues();
//        }
//    }
//
    //endregion

    //region Protected Methods
    protected void createMechanismReferences() {
        super.createMechanismReferences();
        mechanismReferences.put(ModelingMechanisms.AxonGrow, this::axonGrow);
//        mechanismReferences.put(ModelingMechanisms.AxonFormTerminal, this::axonFormTerminal);
    }
    //endregion

    //region Private Methods
    @NotNull
    @Mechanism
    private IEventExecutionResult axonGrow(IEventParameters parameters) {
        final ISignalingPathway currentPathway = (ISignalingPathway) parameters;
        final ICondition growCondition = currentPathway.getCondition();
        final double checkProbability = Model.INSTANCE.nextDouble();
        final Optional<CoordinateProbabilityR> chkP =
                Optional.ofNullable(spaceShell.getAnyCoordinateCandidate(objectId, checkProbability, growCondition));

        if (chkP.isPresent()) {
            final int[] coordinate = chkP.get().getCoordinate();
            coordinateList.add(coordinate);
            currentPathway.getExecutingOnConditionTrueSignalingPathways()
                    .forEach(event -> startSignalingPathway(pathwaysDescription.get(event)));
            return new ObjectMovedResponse(objectId, coordinate, 1.);
        } else {
            currentPathway.getExecutingOnConditionFalseSignalingPathways()
                    .forEach(event -> startSignalingPathway(pathwaysDescription.get(event)));
        }

        return Model.emptyResponse;
    }

//    private void calculateNominalValues() {
//        final int nominal = (int) Math.round(1 / (Kr + Math.exp(KrScalePower * ScrCur)));
//        nominalNeurotransmitterCountPerSynapse = nominal <= maximumNTCount ? nominal : maximumNTCount;
//        nominalExpressNTThreshold = (int) (nominalNeurotransmitterCountPerSynapse * releaseThreshold);
//        nominalUptakingNeurotransmitterCount = (int) (nominalNeurotransmitterCountPerSynapse * nominalNTUptakingPercentage);
//        nominalUptakingNeurotransmitterCount = nominalUptakingNeurotransmitterCount > 0 ? nominalUptakingNeurotransmitterCount : 1;
//        axonalNTChangeProbability = 1 - (Math.tanh(KrMax * nominalNeurotransmitterCountPerSynapse - 3) + 1) / 2;
//    }
//
//    private void setDefaultValues(CellId id) {
//        releaseCountOverTime.put(id, 0);
//        neurotransmitterInVesicles.put(id, 0);
//        neurotransmitterInClefts.put(id, 0);
//    }
    //endregion
}
