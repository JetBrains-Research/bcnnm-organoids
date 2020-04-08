package com.synstorm.common.Utils.EnumTypes;

import com.synstorm.DES.IProceedResponse;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;

/**
 * Created by dvbozhko on 20/02/2017.
 */

@Model_v1

public enum ProceedResponseMethods implements IProceedResponse {
    EmptyResponse(true),
    ObjectAddedResponse(false),
    InitialObjectAddedResponse(false),
    ObjectDeletedResponse(false),
    ObjectProliferatedResponse(false),
    ObjectDifferentiatedResponse(false),
    ObjectMovedResponse(false),
    CompartmentAddedResponse(false),
    AxonFormResponse(false),
    AxonGrownResponse(false),
    InitialAxonGrownResponse(false),
    SynapseAddedResponse(false),
    InitialSynapseAddedResponse(false),
    SynapsesDeletedResponse(false),
    SynapticPowerChangedResponse(false),
    NeurotransmitterReleaseResponse(false),

    CellAddedResponse(false),
    AxonAddedResponse(false),
    DendriteAddedResponse(false),
    AxonalTerminalAddedResponse(false),
    DendriticSpineAddedResponse(false),
    CoordinateObjectAddedResponse(false),
    CellNecrotizeResponse(false),

    SpreadChemicalSignalResponse(false),
    GatherChemicalSignalResponse(false);

    private final boolean isEmpty;

    ProceedResponseMethods(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    @Override
    public boolean isEmpty() {
        return isEmpty;
    }
}
