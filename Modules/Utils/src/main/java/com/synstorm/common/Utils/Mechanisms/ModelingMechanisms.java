package com.synstorm.common.Utils.Mechanisms;

import com.synstorm.DES.IMechanism;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;

@Model_v1

public enum ModelingMechanisms implements IMechanism {
    SpreadChemicalSignal,
    GatherChemicalSignal,
    SignalPointDeletion,
    Divide,
    Differentiate,
    DivideAndDifferentiate,
    Apoptize,
    Necrotize,
    Move,
    CompartmentForm,
    AxonGrow,
    AxonFormTerminal,
    DendriteFormSpine,
    SynapseForm,
    NeurotransmitterRelease,
    NeurotransmitterUptake,
    NeurotransmitterDegrade,
    NeurotransmitterSynthesize,
    CheckNominalNeurotransmitter,
    BindNeurotransmitterToReceptors,
    IncreasePotential,
    DecreasePotential,
    MakeSynapseConstant,
    BreakConnections,
    CheckStimuliReceptors,
    CheckSignals,
    CheckSignalsWithDisruptAll,
    CellCycleStop,
    ProbabilisticDeath
}
