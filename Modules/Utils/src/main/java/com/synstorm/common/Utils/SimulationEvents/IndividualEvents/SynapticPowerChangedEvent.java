package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ObjectIds.IObjectId;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

/**
 * Class for indicating transmitting start event during modeling.
 * Created by dvbozhko on 06/12/2016.
 */
@Model_v0
@NonProductionLegacy
public class SynapticPowerChangedEvent implements ISimulationEvent {
    //region Fields
    private long tick;
    private int postId;
    private int preId;
    private double synapticPower;
    //endregion

    //region Constructors
    public SynapticPowerChangedEvent(long tick, IObjectId postId, IObjectId preId, double synapticPower) {
        this.tick = tick;
        this.postId = postId.getId();
        this.preId = preId.getId();
        this.synapticPower = synapticPower;
    }
    //endregion

    //region Getters and Setters
    public long getTick() {
        return tick;
    }

    public int getPostId() {
        return postId;
    }

    public int getPreId() {
        return preId;
    }

    public double getSynapticPower() {
        return synapticPower;
    }
    //endregion

    //region Public Methods
    @Override
    public SimulationEvents getEventMethod() {
        return SimulationEvents.SynapticPowerChangedEvent;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
