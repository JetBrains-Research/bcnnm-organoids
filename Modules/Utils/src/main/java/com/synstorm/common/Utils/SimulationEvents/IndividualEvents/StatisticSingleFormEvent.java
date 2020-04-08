package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.EnumTypes.StatisticType;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;

/**
 * Created by dvbozhko on 21/06/16.
 */
@Model_v0
@ProductionLegacy
public class StatisticSingleFormEvent<T> implements ISimulationEvent {
    //region Fields
    private int id;
    private StatisticType type;
    private T value;
    //endregion

    //region Constructors
    public StatisticSingleFormEvent(StatisticType type, T value) {
        this.id = 0;
        this.type = type;
        this.value = value;
    }
    //endregion

    //region Getters and Setters
    public int getId() {
        return id;
    }

    public StatisticType getType() {
        return type;
    }

    public int getStatisticId() {
        return type.getId();
    }

    public T getValue() {
        return value;
    }
    //endregion

    //region Public Methods
    @Override
    public SimulationEvents getEventMethod() {
        return SimulationEvents.StatisticSingleFormEvent;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
