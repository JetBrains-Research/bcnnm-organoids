package com.synstorm.SimulationModel.Containers;

import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.EmptyResponse;
import com.synstorm.SimulationModel.ModelAction.CellActionDescription;
import com.synstorm.SimulationModel.ModelTime.ModelTime;
import com.synstorm.SimulationModel.ModelTime.Tick;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.LObjectId;
import com.synstorm.SimulationModel.SpaceShell.DimensionalSpace;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.SignalCoordinateProbability.SignalProbabilityMap;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Container singleton class for storing additional elements for all calculating individuals.
 * Created by dvbozhko on 07/06/16.
 */

@Model_v0
@ProductionLegacy
public enum ModelContainer {
    INSTANCE;

    private EmptyResponse emptyResponse = new EmptyResponse();
    private Map<UUID, AdditionalElement> additionalElementMap = new ConcurrentHashMap<>();

    //region Public methods
    @Contract(pure = true)
    public EmptyResponse returnEmptyResponse() {
        return emptyResponse;
    }

    public synchronized void addAdditionalElement(UUID individualId, ModelTime modelTime,
                                                  DimensionalSpace dimensionalSpace, byte[] individualSeed) {
        AdditionalElement additionalElement = new AdditionalElement(individualId, modelTime, dimensionalSpace, individualSeed);
        additionalElementMap.put(additionalElement.getIndividualId(), additionalElement);
//        MultiLObjectCounter.INSTANCE.addId(individualId);
    }

    public synchronized void removeAdditionalElement(UUID id) {
        additionalElementMap.remove(id);
//        MultiLObjectCounter.INSTANCE.removeId(id);
    }

    public Tick getCurrentTick(UUID id) {
        return additionalElementMap.get(id).getCurrentTick();
    }

    public int nextLObjectId(UUID id) {
        return additionalElementMap.get(id).nextLObjectId();
    }

    public long nextModelActionId(UUID id) {
        return additionalElementMap.get(id).nextModelActionId();
    }

    public long getRandomCount(UUID id) {
        return additionalElementMap.get(id).getRandomCount();
    }

    public double nextDouble(UUID id) {
        return additionalElementMap.get(id).nextDouble();
    }

    public int nextInt(UUID id) {
        return additionalElementMap.get(id).nextInt();
    }

    public int nextInt(UUID id, int bound) {
        return additionalElementMap.get(id).nextInt(bound);
    }

    @Nullable
    public CellId getCellIdByCoordinate(UUID id, ICoordinate coordinate) {
        return additionalElementMap.get(id).getCellIdByCoordinate(coordinate);
    }

    public SignalProbabilityMap affectingCurrentProbabilities(UUID id, LObjectId objectId, Set<String> signals) {
        return additionalElementMap.get(id).affectingCurrentProbabilities(objectId, signals);
    }

    public SignalProbabilityMap affectingAllNeighborProbabilities(UUID id, LObjectId objectId, Set<String> signals) {
        return additionalElementMap.get(id).affectingAllNeighborProbabilities(objectId, signals);
    }

    public SignalProbabilityMap affectingFreeNeighborProbabilities(UUID id, LObjectId objectId, Set<String> signals) {
        return additionalElementMap.get(id).affectingFreeNeighborProbabilities(objectId, signals);
    }

    public void addChangedActionDescriptionInfo(UUID id, CellActionDescription cellActionDescription) {
        additionalElementMap.get(id).addChangedActionDescriptionInfo(cellActionDescription);
    }

    public void updateActionDescriptionsInfo(UUID id) {
        additionalElementMap.get(id).updateActionDescriptionsInfo();
    }
    //endregion
}
