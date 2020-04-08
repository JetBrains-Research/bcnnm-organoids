package com.synstorm.SimulationModel.LogicObject.ActionMethodResponse;

import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;

/**
 * Created by dvbozhko on 22/11/2016.
 */
public class NeurotransmitterReleaseResponse implements IMethodResponse {
    //region Fields
    private final CellId presynapticCellId;
    private final CellId postsynapticCellId;
    //endregion

    //region Constructors
    public NeurotransmitterReleaseResponse(CellId preId, CellId postId) {
        presynapticCellId = preId;
        postsynapticCellId = postId;
    }
    //endregion

    //region Getters and Setters
    public CellId getPostsynapticCellId() {
        return postsynapticCellId;
    }

    public CellId getPresynapticCellId() {
        return presynapticCellId;
    }
    //endregion

    //region Public Methods
    @Override
    public ProceedResponseMethods getMethodType() {
        return ProceedResponseMethods.NeurotransmitterReleaseResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
