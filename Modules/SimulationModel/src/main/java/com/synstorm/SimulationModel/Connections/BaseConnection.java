package com.synstorm.SimulationModel.Connections;

import com.synstorm.SimulationModel.LogicObjectR.LogicObject;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;

@Model_v1
public abstract class BaseConnection extends LogicObject {
    //region Fields
    private int sourceId;
    private int destId;
    private int sourceCellId;
    private int destCellId;
    private boolean connected;
    //endregion

    //region Constructors
    public BaseConnection(int id, int pId, ILogicObjectDescription logicObjectDescription) {
        super(id, pId, logicObjectDescription);
        connected = false;
    }
    //endregion

    //region Getters and Setters
    public int getSourceId() {
        return sourceId;
    }

    public int getDestId() {
        return destId;
    }

    public int getSourceCellId() {
        return sourceCellId;
    }

    public int getDestCellId() {
        return destCellId;
    }
    //endregion

    //region Public Methods
    public void connect(int src, int dst, int srcCell, int dstCell) {
        if (!connected) {
            sourceId = src;
            destId = dst;
            sourceCellId = srcCell;
            destCellId = dstCell;
        }
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
