package com.synstorm.SimulationModel.LogicObjectR;

import com.synstorm.DES.*;
import com.synstorm.SimulationModel.Annotations.Mechanism;
import com.synstorm.SimulationModel.Model.Model;
import com.synstorm.SimulationModel.SpaceShell.Shell;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObject;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathway;
import com.synstorm.common.Utils.EnumTypes.ProceedResponseMethods;
import com.synstorm.common.Utils.Mechanisms.MechanismParameters.ChemicalSignalParameters;
import com.synstorm.common.Utils.Mechanisms.MechanismResponse.ChemicalSignalResponse;
import com.synstorm.common.Utils.Mechanisms.ModelingMechanisms;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Abstract class for logic object (cell, glowing point, axon, radial glia, etc).
 * All logic objects are owned by individual and present in space.
 * Created by dvbozhko on 06/06/16.
 */
@Model_v1
public abstract class LogicObject implements ILogicObject {
    //region Fields
    protected final int objectId;
    protected final int parentId;
    protected final Shell spaceShell;
    protected final ILogicObjectDescription description;
    protected final Map<IMechanism, IEventReference> mechanismReferences;
    protected final Map<AllowedEvent, ISignalingPathway> pathwaysDescription;

    private final EventsDispatcher eventsDispatcher;
    //endregion

    //region Constructors
    public LogicObject(int id, int pId, ILogicObjectDescription logicObjectDescription) {
        objectId = id;
        parentId = pId;
        eventsDispatcher = Model.INSTANCE.getEventsDispatcher();
        spaceShell = Model.INSTANCE.getSpaceShell();
        mechanismReferences = new HashMap<>();
        description = logicObjectDescription;
        pathwaysDescription = description.getSignalingPathways().stream()
                .collect(Collectors.toMap(ISignalingPathway::getPathway, i -> i));

        createMechanismReferences();
        initSignallingPathways(description.getSignalingPathways());

        pathwaysDescription.values().stream()
                .filter(ISignalingPathway::isInitial)
                .forEach(this::startSignalingPathway);
    }
    //endregion

    //region Getters and Setters
    public int getObjectId() {
        return objectId;
    }

    public String getType() {
        return description.getId();
    }

    public ILogicObjectDescription getDescription() {
        return description;
    }

    //endregion

    //region Public Methods
    public void startSignalingPathway(ISignalingPathway pathway) {
        eventsDispatcher.startEvent(objectId, pathway.getPathway(), pathway);
    }

    public void startSignalingPathway(ISignalingPathway pathway, int delay) {
        eventsDispatcher.startEventWithDelay(objectId, pathway.getPathway(), delay, pathway);
    }

    public void disruptSignalingPathway(AllowedEvent event) {
        eventsDispatcher.disruptEvent(objectId, event);
    }

    public void disruptAllSignalingPathways() {
        eventsDispatcher.disruptAllEvents(objectId);
    }

    public void disruptAllSignalingPathwaysExcept(Set<AllowedEvent> events) {
        eventsDispatcher.disruptAllEventsExcept(objectId, events);
    }

    public void markAsDeleted() {
        eventsDispatcher.disruptAllEvents(objectId);
        eventsDispatcher.deleteLogicObjectEvents(objectId);
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    protected void createMechanismReferences() {
        mechanismReferences.put(ModelingMechanisms.SpreadChemicalSignal, this::spreadChemicalSignal);
        mechanismReferences.put(ModelingMechanisms.GatherChemicalSignal, this::gatherChemicalSignal);
    }

    protected void initSignallingPathways(Set<ISignalingPathway> pathwayDescriptions) {
        final Map<AllowedEvent, ModelEvent> events = new HashMap<>();
        for (ISignalingPathway pathwayDescription : pathwayDescriptions) {
            final AllowedEvent aEvent = pathwayDescription.getPathway();
            final IEventWrapperReference wrapper = this::commonMechanismExecution;
            final IEventReference eReference = mechanismReferences.get(aEvent.getMechanism());
            final int duration = pathwayDescription.getDuration();
            final int priority = pathwayDescription.getPriority();
            final ModelEvent mEvent = new WrappedModelEvent(wrapper, eReference, duration, priority);
            events.put(aEvent, mEvent);
        }

        eventsDispatcher.addLogicObjectEvents(objectId, events);
    }
    //endregion

    //region Private Methods
    @NotNull
    private IEventExecutionResult commonMechanismExecution(IEventReference eventRef, IEventParameters parameters) {
        final ISignalingPathway currentPathway = (ISignalingPathway) parameters;
        final IEventExecutionResult result = eventRef.execute(parameters);
        currentPathway.getExecutingDefaultSignalingPathways()
                .forEach(event -> startSignalingPathway(pathwaysDescription.get(event)));
        currentPathway.getDisruptingDefaultSignalingPathways()
                .forEach(this::disruptSignalingPathway);

        return result;
    }

    @NotNull
    @Mechanism
    private IEventExecutionResult spreadChemicalSignal(IEventParameters parameters) {
        final ISignalingPathway currentPathway = (ISignalingPathway) parameters;

        final boolean conditionResult = currentPathway.getCondition() == null || spaceShell.checkObjectPredicate(
                objectId,
                currentPathway.getCondition()
        );

        if (conditionResult) {
            final ChemicalSignalParameters params = (ChemicalSignalParameters) currentPathway.getExecutingParameters();
            final int chemSignal = params.getChemicalSignal();
            final int maxRadius = params.getMaxRadius();
            spaceShell.registerSignal(objectId, chemSignal);
            final int curRadius = spaceShell.spreadSignal(objectId, params.getChemicalSignal());
            if (curRadius < maxRadius) {
                currentPathway.getExecutingOnConditionTrueSignalingPathways()
                        .forEach(event -> startSignalingPathway(pathwaysDescription.get(event)));
            } else {
                currentPathway.getExecutingOnConditionFalseSignalingPathways()
                        .forEach(event -> startSignalingPathway(pathwaysDescription.get(event)));
            }
            return new ChemicalSignalResponse(objectId, ProceedResponseMethods.SpreadChemicalSignalResponse, chemSignal);
        }

        return Model.emptyResponse;
    }

    @NotNull
    @Mechanism
    private IEventExecutionResult gatherChemicalSignal(IEventParameters parameters) {
        final ISignalingPathway currentPathway = (ISignalingPathway) parameters;

        final boolean conditionResult = currentPathway.getCondition() == null || spaceShell.checkObjectPredicate(
                objectId,
                currentPathway.getCondition()
        );

        if (conditionResult) {
            final ChemicalSignalParameters params = (ChemicalSignalParameters) currentPathway.getExecutingParameters();
            final int chemSignal = params.getChemicalSignal();
            final int maxRadius = params.getMaxRadius();
            spaceShell.registerSignal(objectId, chemSignal);
            final int curRadius = spaceShell.gatherSignal(objectId, params.getChemicalSignal());
            if (curRadius > 0) {
                currentPathway.getExecutingOnConditionTrueSignalingPathways()
                        .forEach(event -> startSignalingPathway(pathwaysDescription.get(event)));
            } else {
                currentPathway.getExecutingOnConditionFalseSignalingPathways()
                        .forEach(event -> startSignalingPathway(pathwaysDescription.get(event)));
            }
            return new ChemicalSignalResponse(objectId, ProceedResponseMethods.GatherChemicalSignalResponse, chemSignal);
        }

        return Model.emptyResponse;
    }
    //endregion
}
