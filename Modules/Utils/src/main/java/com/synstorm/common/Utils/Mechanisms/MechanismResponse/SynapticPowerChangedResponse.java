package com.synstorm.common.Utils.Mechanisms.MechanismResponse;

import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;
import gnu.trove.set.TIntSet;

/**
 * Created by
 * dvbozhko on 06/12/2016.
 */
@Model_v1
public class SynapticPowerChangedResponse implements IEventExecutionResult {
    //region Fields
    private final TIntSet synapses;
    //endregion

    //region Constructors
    public SynapticPowerChangedResponse(TIntSet synapses) {
        this.synapses = synapses;
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
        return ProceedResponseMethods.SynapticPowerChangedResponse;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
