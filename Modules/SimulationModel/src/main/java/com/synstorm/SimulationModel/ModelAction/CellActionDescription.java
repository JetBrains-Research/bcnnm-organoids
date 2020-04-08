package com.synstorm.SimulationModel.ModelAction;

import com.synstorm.SimulationModel.SimulationIdentifiers.ModelActionIds.ModelActionId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
import com.synstorm.common.Utils.Details.ActionDetails;
import com.synstorm.common.Utils.EnumTypes.ActionSignalType;
import com.synstorm.common.Utils.EnumTypes.SignalingTopologicalType;
import com.synstorm.common.Utils.EnumTypes.TransactionRecordType;
import org.jetbrains.annotations.Contract;

/**
 * Created by dvbozhko on 09/06/16.
 */

@Model_v0
@NonProductionLegacy
public class CellActionDescription extends LogicObjectActionDescription {
    //region Fields
    private int neighborhood;
    //endregion

    //region Constructors
    private CellActionDescription(ModelActionId id, ActionSignalType actionSignalType,
                                  SignalingTopologicalType signalingTopologicalType, int actualExtension) {
        super(id, actionSignalType, signalingTopologicalType, actualExtension);
    }

    public CellActionDescription(ModelActionId actionId) {
        super(actionId);
        final ActionDetails ad = ModelLoader.getActionDetails(actionId.getName());
        neighborhood = ad.getNeighborhood();
        actualExtension = 0;
    }

    @Contract("_, _, _, _ -> !null")
    public static CellActionDescription createDummyActionDescription(ModelActionId id, ActionSignalType actionSignalType,
                                                                 SignalingTopologicalType signalingTopologicalType, int actualExtension) {
        return new CellActionDescription(id, actionSignalType, signalingTopologicalType, actualExtension);
    }
    //endregion

    //region Getters and Setters
    @Override
    public int getActualExtension() {
        return actualExtension;
    }

    @Override
    public boolean isKpCalculated() {
        return false;
    }

    @Override
    public double getKp() {
        return 1;
    }
    //endregion

    //region Public Methods
    @Override
    public void incrementActualExtension() {
        if (transactionRecordType.equals(TransactionRecordType.Insert) && transactionRecordType.equals(TransactionRecordType.Delete))
            return;

        if (signalingTopologicalType != SignalingTopologicalType.InnerCell && actionSignalType != ActionSignalType.Discrete && actualExtension < neighborhood)
            actualExtension++;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
