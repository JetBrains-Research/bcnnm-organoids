package com.synstorm.SimulationModel.LogicObjectR;

import com.synstorm.DES.AllowedEvent;
import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.DES.IEventParameters;
import com.synstorm.SimulationModel.Annotations.Mechanism;
import com.synstorm.SimulationModel.Model.Model;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ICondition;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathway;
import com.synstorm.common.Utils.Mechanisms.MechanismParameters.ObjectCreateParameters;
import com.synstorm.common.Utils.Mechanisms.MechanismParameters.ProbabilityParameters;
import com.synstorm.common.Utils.Mechanisms.MechanismResponse.*;
import com.synstorm.common.Utils.Mechanisms.ModelingMechanisms;
import com.synstorm.common.Utils.SignalCoordinateProbability.CoordinateProbabilityR;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

/**
 * Abstract class for cell object
 * Created by dvbozhko on 06/06/16.
 */
@Model_v1
public abstract class Cell extends AbstractCompartment {
    //region Fields
    //endregion

    //region Constructors
    public Cell(int id, int parentId, ILogicObjectDescription logicObjectDescription) {
        super(id, parentId, logicObjectDescription);
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    protected void createMechanismReferences() {
        super.createMechanismReferences();
        mechanismReferences.put(ModelingMechanisms.Divide, this::divide);
        mechanismReferences.put(ModelingMechanisms.Differentiate, this::differentiate);
        mechanismReferences.put(ModelingMechanisms.DivideAndDifferentiate, this::divideAndDifferentiate);
        mechanismReferences.put(ModelingMechanisms.Apoptize, this::apoptize);
        mechanismReferences.put(ModelingMechanisms.Necrotize, this::necrotize);
        mechanismReferences.put(ModelingMechanisms.Move, this::move);
        mechanismReferences.put(ModelingMechanisms.ProbabilisticDeath, this::probabilisticDeath);
    }
    //endregion

    //region Private Methods
    @NotNull
    @Mechanism
    private IEventExecutionResult divide(IEventParameters parameters) {
        return new CellAddedResponse(objectId, description.getId());
    }

    @NotNull
    @Mechanism
    private IEventExecutionResult differentiate(IEventParameters parameters) {
        final ISignalingPathway currentPathway = (ISignalingPathway) parameters;
        final ObjectCreateParameters params = (ObjectCreateParameters) currentPathway.getExecutingParameters();
        return new ObjectDifferentiatedResponse(objectId, params.getDiffType());
    }

    @NotNull
    @Mechanism
    private IEventExecutionResult divideAndDifferentiate(IEventParameters parameters) {
        final ISignalingPathway currentPathway = (ISignalingPathway) parameters;
        final ObjectCreateParameters params = (ObjectCreateParameters) currentPathway.getExecutingParameters();
        return new CellAddedResponse(objectId, params.getDiffType());
    }

    @NotNull
    @Mechanism
    private IEventExecutionResult apoptize(IEventParameters parameters) {
        return new ObjectDeletedResponse(objectId);
    }

    @NotNull
    @Mechanism
    private IEventExecutionResult probabilisticDeath(IEventParameters parameters) {
        final ISignalingPathway currentPathway = (ISignalingPathway) parameters;
        final ProbabilityParameters params = (ProbabilityParameters) currentPathway.getExecutingParameters();
        if (Model.INSTANCE.nextDouble() < params.getProbability()) {
            final Set<AllowedEvent> conditionalEvents = currentPathway.getExecutingOnConditionTrueSignalingPathways();
            conditionalEvents.stream()
                    .forEach(event -> startSignalingPathway(pathwaysDescription.get(event)));
            disruptAllSignalingPathwaysExcept(conditionalEvents);
        }

        return Model.emptyResponse;
    }

    @NotNull
    @Mechanism
    private IEventExecutionResult necrotize(IEventParameters parameters) {
        final ISignalingPathway currentPathway = (ISignalingPathway) parameters;
        final ObjectCreateParameters params = (ObjectCreateParameters) currentPathway.getExecutingParameters();
        return new CellNecrotizeResponse(objectId, params.getDiffType());
    }

    @NotNull
    @Mechanism
    private IEventExecutionResult move(IEventParameters parameters) {
        final ISignalingPathway currentPathway = (ISignalingPathway) parameters;
        final ICondition moveCondition = currentPathway.getCondition();
        final double checkProbability = Model.INSTANCE.nextDouble();
        final Optional<CoordinateProbabilityR> chkP =
                Optional.ofNullable(spaceShell.getFreeCoordinateCandidate(objectId, checkProbability, moveCondition));

        if (chkP.isPresent())
            return new ObjectMovedResponse(objectId, chkP.get().getCoordinate(), chkP.get().getProbability());

        return Model.emptyResponse;
    }
    //endregion
}