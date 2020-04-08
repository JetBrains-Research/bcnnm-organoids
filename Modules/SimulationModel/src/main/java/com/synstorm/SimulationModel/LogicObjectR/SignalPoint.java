package com.synstorm.SimulationModel.LogicObjectR;

import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.DES.IEventParameters;
import com.synstorm.SimulationModel.Annotations.Mechanism;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;
import com.synstorm.common.Utils.Mechanisms.MechanismResponse.ObjectDeletedResponse;
import com.synstorm.common.Utils.Mechanisms.ModelingMechanisms;
import org.jetbrains.annotations.NotNull;

@Model_v1
public class SignalPoint extends LogicObject {
    //region Fields
    //endregion

    //region Constructors
    public SignalPoint(int id, int parentId, ILogicObjectDescription logicObjectDescription) {
        super(id, parentId, logicObjectDescription);
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
        mechanismReferences.put(ModelingMechanisms.SignalPointDeletion, this::signalPointDeletion);
    }
    //endregion

    //region Private Methods
    @NotNull
    @Mechanism
    private IEventExecutionResult signalPointDeletion(IEventParameters parameters) {
        return new ObjectDeletedResponse(objectId);
    }
    //endregion
}
