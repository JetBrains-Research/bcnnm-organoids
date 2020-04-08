package com.synstorm.SimulationModel.Compartments;

import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.DES.IEventParameters;
import com.synstorm.SimulationModel.Annotations.Mechanism;
import com.synstorm.SimulationModel.Connections.Synapse;
import com.synstorm.SimulationModel.LogicObjectR.Compartment;
import com.synstorm.SimulationModel.Model.Model;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ICondition;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathway;
import com.synstorm.common.Utils.Mechanisms.MechanismParameters.ObjectCreateParameters;
import com.synstorm.common.Utils.Mechanisms.MechanismResponse.SynapseAddedResponse;
import com.synstorm.common.Utils.Mechanisms.ModelingMechanisms;
import org.jetbrains.annotations.NotNull;

@Model_v1
public class AxonalTerminal extends Compartment {
    //region Fields
    private int nominalUptakingNeurotransmitterCount;
    private int nominalNeurotransmitterCountPerSynapse;
    private int neurotransmitterInVesicles;
    private int neurotransmitterInCleft;
    private int releaseCountOverTime;
    private double continuousReleasePercent;
    private double continuousExpressPercent;

    private double nominalNTUptakingPercentage;

    private Synapse synapse;
    //endregion

    //region Constructors
    public AxonalTerminal(int id, int parentId, ILogicObjectDescription logicObjectDescription) {
        super(id, parentId, logicObjectDescription);

        neurotransmitterInVesicles = 0;
        neurotransmitterInCleft = 0;
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    protected void createMechanismReferences() {
        super.createMechanismReferences();
        mechanismReferences.put(ModelingMechanisms.SynapseForm, this::synapseForm);
        mechanismReferences.put(ModelingMechanisms.NeurotransmitterRelease, this::neurotransmitterRelease);
        mechanismReferences.put(ModelingMechanisms.NeurotransmitterUptake, this::neurotransmitterUptake);
        mechanismReferences.put(ModelingMechanisms.NeurotransmitterDegrade, this::neurotransmitterDegrade);
        mechanismReferences.put(ModelingMechanisms.NeurotransmitterSynthesize, this::neurotransmitterSynthesize);
    }
    //endregion

    //region Private Methods
    @NotNull
    @Mechanism
    private IEventExecutionResult synapseForm(IEventParameters parameters) {
        final ISignalingPathway currentPathway = (ISignalingPathway) parameters;
        final ObjectCreateParameters params = (ObjectCreateParameters) currentPathway.getExecutingParameters();
        final ICondition connectCondition = currentPathway.getCondition();
        final boolean chkP = currentPathway.getCondition() == null || spaceShell.checkObjectPredicate(objectId, connectCondition);
        if (chkP) {
            final int cellIdToConnect = spaceShell.getCellIdByNonVolumeId(objectId);
            if (cellIdToConnect != -1)
                return new SynapseAddedResponse(objectId, cellIdToConnect, params.getDiffType());
        }

        return Model.emptyResponse;
    }

    @NotNull
    @Mechanism
    private IEventExecutionResult neurotransmitterRelease(IEventParameters parameters) {
//        final ISignalingPathway currentPathway = (ISignalingPathway) parameters;
//
//        int continuousNtReleaseCount = (int) (continuousReleasePercent * nominalNeurotransmitterCountPerSynapse);
//        if (neurotransmitterInVesicles > 0) {
//            continuousNtReleaseCount = neurotransmitterInVesicles > continuousNtReleaseCount ?
//                    continuousNtReleaseCount : neurotransmitterInVesicles;
//            neurotransmitterInVesicles = neurotransmitterInVesicles - continuousNtReleaseCount;
////            neurotransmitterInCleft = synapse.addNeurotransmitterToSynapse(continuousNtReleaseCount);
//            releaseCountOverTime++;
//        }

        return Model.emptyResponse;
    }

    @NotNull
    @Mechanism
    private IEventExecutionResult neurotransmitterUptake(IEventParameters parameters) {
//        final ISignalingPathway currentPathway = (ISignalingPathway) parameters;
//
//        final int ntToUptake = neurotransmitterInCleft > nominalUptakingNeurotransmitterCount ?
//                nominalUptakingNeurotransmitterCount : neurotransmitterInCleft;
//
////        final int newNtInCleft = synapse.removeNeurotransmitterFromSynapse(ntToUptake);
//        final int newNtInVesicles = neurotransmitterInVesicles + ntToUptake;
//        neurotransmitterInVesicles = nominalNeurotransmitterCountPerSynapse > newNtInVesicles ?
//                newNtInVesicles : nominalNeurotransmitterCountPerSynapse;
//
//        neurotransmitterInCleft = newNtInCleft;

        return Model.emptyResponse;
    }

    @NotNull
    @Mechanism
    private IEventExecutionResult neurotransmitterDegrade(IEventParameters parameters) {
//        final ISignalingPathway currentPathway = (ISignalingPathway) parameters;
//
//        final int ntInCleft = neurotransmitterInCleft;
//        int ntToDegrade = (int) (ntInCleft * nominalNTUptakingPercentage);
//        if (ntInCleft > 0 && ntToDegrade == 0)
//            ntToDegrade = 1;
//
//        ntToDegrade = ntInCleft > ntToDegrade ? ntToDegrade : ntInCleft;
//
//        final int newNtInCleft = synapse.removeNeurotransmitterFromSynapse(ntToDegrade);
//        neurotransmitterInCleft = newNtInCleft;

        return Model.emptyResponse;
    }

    @NotNull
    @Mechanism
    private IEventExecutionResult neurotransmitterSynthesize(IEventParameters parameters) {
//        final ISignalingPathway currentPathway = (ISignalingPathway) parameters;
//
//        final int ntCountInVesicle = neurotransmitterInVesicles;
//        final int nominalNtInVesicle = nominalNeurotransmitterCountPerSynapse;
//        if (ntCountInVesicle < nominalNtInVesicle) {
//            int toAdd = ntCountInVesicle + (int) Math.round(continuousExpressPercent * nominalNtInVesicle);
//            if (toAdd < nominalNtInVesicle)
//                neurotransmitterInVesicles = toAdd;
//            else
//                neurotransmitterInVesicles = nominalNtInVesicle;
//        }

//        return ntCountInVesicle < nominalNtInVesicle;

        return Model.emptyResponse;
    }
    //endregion
}
