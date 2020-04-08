package com.synstorm.common.Utils.SimulationEvents.IndividualEvents;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ObjectIds.IObjectId;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dvbozhko on 21/06/16.
 */
@Model_v0
@NonProductionLegacy
public class CheckpointNodeFormEvent implements ISimulationEvent {
    //region Fields
    private long tick;
    private int id;
    private String type;
    private ICoordinate coordinate;
    private List<ICoordinate> axonCoordinates;
    private Set<Integer> synapses;
    //endregion

    //region Constructors
    public CheckpointNodeFormEvent(long tick, IObjectId id, String type, ICoordinate coordinate, List<ICoordinate> axonCoordinates, Set<IObjectId> synapses) {
        this.tick = tick;
        this.id = id.getId();
        this.type = type;
        this.coordinate = coordinate;
        this.axonCoordinates = new ArrayList<>();
        this.synapses = new LinkedHashSet<>();
        this.axonCoordinates.addAll(axonCoordinates);
        synapses.stream().forEach(synapse -> this.synapses.add(synapse.getId()));
    }

    public CheckpointNodeFormEvent(long tick, IObjectId id, String type, ICoordinate coordinate) {
        this.tick = tick;
        this.id = id.getId();
        this.type = type;
        this.coordinate = coordinate;
        this.axonCoordinates = new ArrayList<>();
        this.synapses = new LinkedHashSet<>();
    }
    //endregion

    //region Getters and Setters
    public long getTick() {
        return tick;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public ICoordinate getCoordinate() {
        return coordinate;
    }

    public List<ICoordinate> getAxonCoordinates() {
        return axonCoordinates;
    }

    public Set<Integer> getSynapses() {
        return synapses;
    }
    //endregion

    //region Public Methods
    @Override
    public SimulationEvents getEventMethod() {
        return SimulationEvents.CheckpointNodeFormEvent;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
