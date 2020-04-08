package com.synstorm.SimulationModel.ModelProcessors.IndividualHolders;

import com.synstorm.common.Utils.Details.IndividualDetails;

import java.util.UUID;

/**
 * Created by dvbozhko on 30/03/2017.
 */
public class LearningIndividualHolder extends IndividualHolder {
    //region Fields
    private final byte[] teacherSeed;
    //endregion

    //region Constructors
    public LearningIndividualHolder(UUID uuid, IndividualDetails individualDetails, byte[] individualSeed, byte[] teacherSeed) {
        super(uuid, individualDetails, individualSeed);
        this.teacherSeed = teacherSeed;
    }
    //endregion

    //region Getters and Setters
    public byte[] getTeacherSeed() {
        return teacherSeed;
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
