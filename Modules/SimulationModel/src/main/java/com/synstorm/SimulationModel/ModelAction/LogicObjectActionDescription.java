package com.synstorm.SimulationModel.ModelAction;

import com.synstorm.SimulationModel.SimulationIdentifiers.ModelActionIds.ModelActionId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.LObjectId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
import com.synstorm.common.Utils.Details.ActionDetails;
import com.synstorm.common.Utils.EnumTypes.ActionSignalType;
import com.synstorm.common.Utils.EnumTypes.SignalingTopologicalType;
import com.synstorm.common.Utils.EnumTypes.TransactionRecordType;

/**
 * Created by dvbozhko on 09/06/16.
 */

@Model_v0
@NonProductionLegacy
public abstract class LogicObjectActionDescription {
    //region Fields
    private final ModelActionId id;
    protected final ActionSignalType actionSignalType;
    protected final SignalingTopologicalType signalingTopologicalType;
    protected TransactionRecordType transactionRecordType;
    protected int actualExtension;
    //endregion

    //region Constructors
    protected LogicObjectActionDescription(ModelActionId id, ActionSignalType actionSignalType,
                              SignalingTopologicalType signalingTopologicalType, int actualExtension) {
        this.id = id;
        this.actualExtension = actualExtension;
        this.actionSignalType = actionSignalType;
        this.signalingTopologicalType = signalingTopologicalType;
        transactionRecordType = TransactionRecordType.Insert;
    }

    public LogicObjectActionDescription(ModelActionId actionId) {
        id = actionId;
        final ActionDetails ad = ModelLoader.getActionDetails(actionId.getName());
        actionSignalType = ad.getSignalType();
        signalingTopologicalType = ad.getSignalingTopologicalType();
        transactionRecordType = TransactionRecordType.Insert;
    }
    //endregion

    //region Getters and Setters
    public ModelActionId getId() {
        return id;
    }

    public String getActionName() {
        return id.getName();
    }

    public LObjectId getObjId() {
        return id.getObjectId();
    }

    public abstract int getActualExtension();

    public abstract boolean isKpCalculated();

    public abstract double getKp();

    public TransactionRecordType getTransactionRecordType() {
        return transactionRecordType;
    }
    //endregion

    //region Public Methods
    public abstract void incrementActualExtension();

    public void deleteAction() {
        transactionRecordType = TransactionRecordType.Delete;
    }

    public void resetActionState() {
        transactionRecordType = TransactionRecordType.NotChanged;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
