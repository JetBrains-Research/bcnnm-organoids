package com.synstorm.common.Utils.Mechanisms.MechanismResponse;

import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;
import gnu.trove.set.TIntSet;

/**
 * Created by dvbozhko on 07/06/16.
 */
@Model_v1
public class SynapsesDeletedResponse implements IEventExecutionResult {
    //region Fields
    private TIntSet synapses;
    //endregion

    //region Constructors
    public SynapsesDeletedResponse(TIntSet deletedSynapses) {
        synapses = deletedSynapses;
    }
    //endregion

    //region Getters and Setters
    public TIntSet getSynapses() {
        return synapses;
    }
    //endregion

    //region Public Methods
    @Override
    public ProceedResponseMethods getProceedMethod() {
        return ProceedResponseMethods.SynapsesDeletedResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
