package com.synstorm.SimulationModel.LogicObjectR;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ICompartmentDescription;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;

@Model_v1
public abstract class Compartment extends AbstractCompartment {
    //region Fields
    private final boolean unique;
    //endregion

    //region Constructors
    public Compartment(int id, int parentId, ILogicObjectDescription logicObjectDescription) {
        super(id, parentId, logicObjectDescription);
        this.unique = ((ICompartmentDescription) logicObjectDescription).isUnique();
    }
    //endregion

    //region Getters and Setters
    public boolean isUnique() {
        return unique;
    }

    public void setOwner(ICompartmentOwner owner) {
        this.owner = owner;
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
