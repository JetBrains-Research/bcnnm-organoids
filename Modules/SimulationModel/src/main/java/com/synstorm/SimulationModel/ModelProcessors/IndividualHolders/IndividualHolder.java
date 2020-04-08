package com.synstorm.SimulationModel.ModelProcessors.IndividualHolders;

import com.synstorm.common.Utils.Details.IndividualDetails;

import java.util.UUID;

/**
 * Created by dvbozhko on 30/03/2017.
 */
public class IndividualHolder {
    //region Fields
    private final UUID uuid;
    private final IndividualDetails individualDetails;
    private final byte[] individualSeed;
    //endregion

    //region Constructors
    public IndividualHolder(UUID uuid, IndividualDetails individualDetails, byte[] individualSeed) {
        this.uuid = uuid;
        this.individualDetails = individualDetails;
        this.individualSeed = individualSeed;
    }
    //endregion

    //region Getters and Setters
    public UUID getUuid() {
        return uuid;
    }

    public IndividualDetails getIndividualDetails() {
        return individualDetails;
    }

    public byte[] getIndividualSeed() {
        return individualSeed;
    }
    //endregion

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
