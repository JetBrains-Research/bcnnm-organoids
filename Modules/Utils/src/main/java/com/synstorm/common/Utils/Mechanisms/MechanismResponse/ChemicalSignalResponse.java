package com.synstorm.common.Utils.Mechanisms.MechanismResponse;

import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.DES.IProceedResponse;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;

/**
 * Created by human-research on 2019-01-24.
 */

@Model_v1
public class ChemicalSignalResponse implements IEventExecutionResult {
    //region Fields
    private final int objectId;
    private final ProceedResponseMethods proceedResponseMethod;
    private final int signalId;
    //endregion


    //region Constructors

    public ChemicalSignalResponse(int objectId, ProceedResponseMethods proceedResponseMethod, int signalId) {
        this.objectId = objectId;
        this.proceedResponseMethod = proceedResponseMethod;
        this.signalId = signalId;
    }
    //endregion


    //region Getters and Setters
    @Override
    public IProceedResponse getProceedMethod() {
        return proceedResponseMethod;
    }
    //endregion


    //region Public Methods
    public int getObjectId() {
        return objectId;
    }

    public int getSignalId() {
        return signalId;
    }

    //endregion


    //region Private Methods

    //endregion

}
