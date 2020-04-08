package com.synstorm.SimulationModel.ModelAction;

import com.synstorm.SimulationModel.SimulationIdentifiers.ModelActionIds.ModelActionId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.ActionSignalType;
import com.synstorm.common.Utils.EnumTypes.SignalingTopologicalType;
import org.jetbrains.annotations.Contract;

import java.util.HashSet;
import java.util.Set;

/**
 * Class for description of glowing point action.
 * Includes additional elements for signal drop, actual extension for signal spread is set for radius
 * Created by dvbozhko on 09/06/16.
 */

@Model_v0
@NonProductionLegacy
public class GlowingPointActionDescription extends LogicObjectActionDescription {
    //region Fields
    private final double scaledK;
    private double Kp;
    private final boolean calculateKp;
    private int currentObjectInRange;
    private Set<CellId> objIds;
    //endregion

    //region Constructors
    private GlowingPointActionDescription(ModelActionId id, ActionSignalType actionSignalType,
                                          SignalingTopologicalType signalingTopologicalType, int radius, boolean isCalcKp) {
        super(id, actionSignalType, signalingTopologicalType, radius);
        scaledK = 5.274 / (Math.pow(2 * actualExtension, 3) - 1);
        calculateKp = isCalcKp;
        Kp = 1;
        currentObjectInRange = 0;
        objIds = new HashSet<>();
    }

    public GlowingPointActionDescription(ModelActionId actionId, int radius, boolean isCalcKp) {
        super(actionId);
        actualExtension = radius;
        scaledK = 5.274 / (Math.pow(2 * actualExtension, 3) - 1);
        calculateKp = isCalcKp;
        Kp = 1;
        currentObjectInRange = 0;
        objIds = new HashSet<>();
    }

    @Contract("_, _, _, _, _ -> !null")
    public static GlowingPointActionDescription createDummyActionDescription(ModelActionId id, ActionSignalType actionSignalType,
                                                                             SignalingTopologicalType signalingTopologicalType, int radius,
                                                                             boolean isCalcKp) {
        return new GlowingPointActionDescription(id, actionSignalType, signalingTopologicalType, radius, isCalcKp);
    }
    //endregion

    //region Getters and Setters
    @Override
    public int getActualExtension() {
        return actualExtension;
    }

    @Override
    public boolean isKpCalculated() {
        return calculateKp;
    }

    @Override
    public double getKp() {
        return Kp;
    }
    //endregion

    //region Public Methods
    @Override
    public void incrementActualExtension() {

    }

    public void addObjectId(CellId id) {
        if (!objIds.contains(id)) {
            objIds.add(id);
            currentObjectInRange++;
            calculateKp();
        }
    }

    public void removeObjectId(CellId id) {
        if (objIds.contains(id)) {
            objIds.remove(id);
            currentObjectInRange--;
            calculateKp();
        }
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    private void calculateKp() {
        if (calculateKp)
            //Sigmoid function where y in [0, 1] and x in [0, Vmax]
            Kp = 0.5 - Math.tanh(scaledK * currentObjectInRange - 3) / 2;
    }
    //endregion
}
