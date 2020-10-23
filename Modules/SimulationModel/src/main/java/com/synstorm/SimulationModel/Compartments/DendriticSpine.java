package com.synstorm.SimulationModel.Compartments;

import com.synstorm.SimulationModel.LogicObjectR.Compartment;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;

/**
 * Created by Dmitry.Bozhko on 9/4/2015.
 */
public class DendriticSpine extends Compartment {

    //region Constructors
    public DendriticSpine(int id, int parentId, ILogicObjectDescription logicObjectDescription) {
        super(id, parentId, logicObjectDescription);
    }
    //endregion
}