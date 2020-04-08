package com.synstorm.common.Utils.EvolutionUtils.Batches;

import com.synstorm.common.Utils.EnumTypes.UUIDAnswerType;

import java.util.UUID;

/**
 * Created by human-research on 23/12/2016.
 */
public class UUIDAnswer {
    private UUID uuid;
    private UUIDAnswerType uuidAnswerType;
    //region Fields

    //endregion


    //region Constructors

    public UUIDAnswer(UUID uuid, UUIDAnswerType uuidAnswerType) {
        this.uuid = uuid;
        this.uuidAnswerType = uuidAnswerType;
    }

    //endregion


    //region Getters and Setters

    public UUID getUuid() {
        return uuid;
    }

    public UUIDAnswerType getUuidAnswerType() {
        return uuidAnswerType;
    }

    //endregion


    //region Public Methods

    //endregion


    //region Private Methods

    //endregion

}
