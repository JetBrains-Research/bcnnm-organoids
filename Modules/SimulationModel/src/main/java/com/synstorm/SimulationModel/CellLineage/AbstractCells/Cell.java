package com.synstorm.SimulationModel.CellLineage.AbstractCells;

import com.synstorm.SimulationModel.Annotations.SignalingPathway;
import com.synstorm.SimulationModel.Containers.ModelContainer;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.IMethodResponse;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.ObjectMovedResponse;
import com.synstorm.SimulationModel.LogicObject.IActionParameters;
import com.synstorm.SimulationModel.LogicObject.InitialPotentialCellObject;
import com.synstorm.SimulationModel.LogicObject.LogicObjects.CellObject;
import com.synstorm.SimulationModel.LogicObject.PotentialCellObject;
import com.synstorm.SimulationModel.ModelAction.ActionDispatcher;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.ActionFunctionalType;
import com.synstorm.common.Utils.EnumTypes.SignalSelectionType;
import com.synstorm.common.Utils.SignalCoordinateProbability.CoordinateProbability;
import com.synstorm.common.Utils.SignalCoordinateProbability.SignalProbability;
import com.synstorm.common.Utils.SignalCoordinateProbability.SignalProbabilityMap;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Base abstract class for all types of cells
 * Created by dvbozhko on 07/06/16.
 */

@Model_v0
@NonProductionLegacy
public abstract class Cell extends CellObject {
    //region Fields
    //endregion

    //region Constructors
    public Cell(CellId id, PotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
        super(id, potentialCellObject, dispatcher);
    }

    public Cell(CellId id, InitialPotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
        super(id, potentialCellObject, dispatcher);
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    @Override
    protected void initializeMethodsForExecution() {
        methodMap.put("CheckProliferationSignals", this::CheckProliferationSignals);
    }

    protected IMethodResponse move() {
        double checkProbability = ModelContainer.INSTANCE.nextDouble(individualId);
        Set<String> signals = receivedFactorsByAction.get(moveActionName);
        SignalProbability signalProbability = new SignalProbability(signals);
        Map<String, SignalSelectionType> signalSelection = signals.stream()
                .collect(Collectors.toMap(
                        signal -> signal,
                        signal -> SignalSelectionType.Gradient
                ));
        Predicate<SignalProbability> predicate = signals.stream()
                .map(signal -> signalProbability.isIntensityMore(signal, 0.))
                .reduce(Predicate::and)
                .get();
        SignalProbabilityMap spMap =
                ModelContainer.INSTANCE.affectingFreeNeighborProbabilities(individualId, objectId, signals);

        CoordinateProbability coordinateProbability = spMap.getCoordinateCandidate(checkProbability, predicate, signalSelection);
        if (coordinateProbability != null)
            return new ObjectMovedResponse(objectId, coordinateProbability.getCoordinate(), coordinateProbability.getProbability());

        return ModelContainer.INSTANCE.returnEmptyResponse();
    }
    //endregion

    //region Private Methods
    private String chooseProliferationType() {
        String returnSignalType = "";
        double checkProbability = ModelContainer.INSTANCE.nextDouble(individualId);
        Set<String> signals = proliferationInfo.keySet().stream().collect(Collectors.toSet());
        SignalProbability signalProbability = new SignalProbability(signals);
        Map<String, SignalSelectionType> signalSelection = signals.stream()
                .collect(Collectors.toMap(
                        signal -> signal,
                        signal -> SignalSelectionType.Gradient
                ));
        Predicate<SignalProbability> predicate = signals.stream()
                .map(signal -> signalProbability.isIntensityMore(signal, 0.))
                .reduce(Predicate::and)
                .get();
        SignalProbabilityMap spMap =
                ModelContainer.INSTANCE.affectingCurrentProbabilities(individualId, objectId, signals);

        String signalCandidate = spMap.getSignalCandidateByCoordinate(checkProbability, coordinate, predicate, signalSelection);
        if (!signalCandidate.equals(""))
            returnSignalType = proliferationInfo.get(signalCandidate);

        return returnSignalType;
    }

    //region Signaling Pathways
    @SignalingPathway
    private IMethodResponse CheckProliferationSignals(IActionParameters parameters) {
        cellProliferationType = chooseProliferationType();
        if (!cellProliferationType.equals("")) {
            actionDispatcher.disruptAllActions(objectId);
            startAction(differentiateActionName, ActionFunctionalType.Proliferate);
        }

        return ModelContainer.INSTANCE.returnEmptyResponse();
    }
    //endregion

    //endregion
}
