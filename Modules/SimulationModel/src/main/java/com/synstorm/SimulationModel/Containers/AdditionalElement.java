package com.synstorm.SimulationModel.Containers;

import com.synstorm.SimulationModel.ModelAction.LogicObjectActionDescription;
import com.synstorm.SimulationModel.ModelTime.ModelTime;
import com.synstorm.SimulationModel.ModelTime.Tick;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.LObjectId;
import com.synstorm.SimulationModel.SpaceShell.DimensionalSpace;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.SignalCoordinateProbability.SignalProbabilityMap;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;
import org.uncommons.maths.random.MersenneTwisterRNG;

import java.util.*;

/**
 * Class for storing outside elements for individual:
 * Time, space, individual random numbers, objects and actions identifiers.
 * Created by dvbozhko on 07/06/16.
 */

@Model_v0
@ProductionLegacy
class AdditionalElement {
    //region Fields
    private final UUID id;
    private final ModelTime time;
    private final DimensionalSpace space;
    private final Random random;
    private final List<LogicObjectActionDescription> cellActionDescriptions;
    private long randomCount;
    private int lObjectIdCounter;
    private long modelActionIdCounter;
    //endregion

    //region Constructors
    AdditionalElement(UUID individualId, ModelTime modelTime, DimensionalSpace dimensionalSpace, byte[] individualSeed) {
        id = individualId;
        time = modelTime;
        space = dimensionalSpace;
        random = new MersenneTwisterRNG(individualSeed);
        cellActionDescriptions = new ArrayList<>();
        randomCount = 0L;
        lObjectIdCounter = 0;
        modelActionIdCounter = 0L;
    }
    //endregion

    //region Getters/Setters
    public UUID getIndividualId() {
        return id;
    }

    public long getRandomCount() {
        return randomCount;
    }
    //endregion

    //region Public methods
    Tick getCurrentTick() {
        return time.getCurrentTick();
    }

    int nextLObjectId() {
        return lObjectIdCounter++;
    }

    long nextModelActionId() {
        return modelActionIdCounter++;
    }

    double nextDouble() {
        randomCount++;
        return random.nextDouble();
    }

    int nextInt() {
        randomCount++;
        return random.nextInt();
    }

    int nextInt(int bound) {
        randomCount++;
        return random.nextInt(bound);
    }

    CellId getCellIdByCoordinate(ICoordinate coordinate) {
        return space.getIdByCoordinate(coordinate);
    }

    SignalProbabilityMap affectingCurrentProbabilities(LObjectId objectId, Set<String> signals) {
        return space.affectingCurrentProbabilities(objectId, signals);
    }

    SignalProbabilityMap affectingAllNeighborProbabilities(LObjectId objectId, Set<String> signals) {
        return space.affectingAllNeighborProbabilities(objectId, signals);
    }

    SignalProbabilityMap affectingFreeNeighborProbabilities(LObjectId objectId, Set<String> signals) {
        return space.affectingFreeNeighborProbabilities(objectId, signals);
    }

    boolean matchIdWithCoordinate(CellId objectId, ICoordinate coordinate) {
        return space.matchIdWithCoordinate(objectId, coordinate);
    }

    void addChangedActionDescriptionInfo(LogicObjectActionDescription cellActionDescription) {
        cellActionDescriptions.add(cellActionDescription);
    }

    void updateActionDescriptionsInfo() {
        space.updateActionDescriptionsInfo(cellActionDescriptions);
        cellActionDescriptions.clear();
    }
    //endregion

    //region Private methods
    //endregion
}
