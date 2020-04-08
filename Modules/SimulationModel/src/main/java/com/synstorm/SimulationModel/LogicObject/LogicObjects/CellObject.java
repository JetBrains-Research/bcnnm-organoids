package com.synstorm.SimulationModel.LogicObject.LogicObjects;

import com.synstorm.SimulationModel.Containers.ModelContainer;
import com.synstorm.SimulationModel.LogicObject.ActionMethodParameters.EmptyActionParameters;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.IMethodResponse;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.ObjectAddedResponse;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.ObjectDeletedResponse;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.ObjectProliferatedResponse;
import com.synstorm.SimulationModel.LogicObject.IActionParameters;
import com.synstorm.SimulationModel.LogicObject.IModelActionMethod;
import com.synstorm.SimulationModel.LogicObject.InitialPotentialCellObject;
import com.synstorm.SimulationModel.LogicObject.PotentialCellObject;
import com.synstorm.SimulationModel.ModelAction.ActionDispatcher;
import com.synstorm.SimulationModel.ModelAction.StartActionInfo;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.LObjectId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
import com.synstorm.common.Utils.Details.ActionDetails;
import com.synstorm.common.Utils.Details.CellDetails;
import com.synstorm.common.Utils.EnumTypes.ActionFunctionalType;
import com.synstorm.common.Utils.EnumTypes.CellFunctionalType;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;
import org.jetbrains.annotations.Contract;

import java.util.*;

/**
 * Abstract class for cell object
 * Created by dvbozhko on 06/06/16.
 */

@Model_v0
@NonProductionLegacy
public abstract class CellObject extends SpaceObject {
    //region Fields
    private LObjectId parentId;
    private final boolean movingAbility;
    private ICoordinate nearCoordinateForCreation;
    private final CellFunctionalType cellFunctionalType;
    private final Set<String> receivedFactors;
    private final Set<String> actionList;
    private short deathProteinConcentration;
    private short deathProteinConcentrationThreshold;

    protected String cellProliferationType;
    protected final Map<String, Set<String>> receivedFactorsByAction;
    protected final Map<String, String> proliferationInfo;
    protected final String diffType;
    protected final String createActionName;
    protected final String deleteActionName;
    protected final String differentiateActionName;
    protected final String moveActionName;
    protected final ActionDispatcher actionDispatcher;
//    protected final INetworkActivityListener networkActivityListener;
    protected final Map<String, IModelActionMethod> methodMap;
    //endregion

    //region Constructors
    private CellObject(CellId id, UUID individualId, ActionDispatcher dispatcher,
                       ICoordinate coordinate, String cellType) {
        super(id, individualId, coordinate);
        diffType = cellType;
        differentiateActionName = diffType + "Proliferate";
        moveActionName = diffType + "Move";
        createActionName = diffType + "Create";
        deleteActionName = diffType + "Delete";
        cellProliferationType = "";
        deathProteinConcentration = 0;
        deathProteinConcentrationThreshold = 100;
        actionList = new HashSet<>();
        receivedFactors = new HashSet<>();
        receivedFactorsByAction = new HashMap<>();
        proliferationInfo = new HashMap<>();
        methodMap = new HashMap<>();

        final CellDetails cellDetails = ModelLoader.getCellDetails(diffType);
        cellFunctionalType = cellDetails.getCellFunctionalType();

        cellDetails.getActions().stream()
                .forEach(actionList::add);

        cellDetails.getReceivedFactors().entrySet().stream()
                .forEach(item -> {
                    receivedFactors.addAll(item.getValue());
                    receivedFactorsByAction.put(item.getKey(), new HashSet<>(item.getValue()));
                    actionList.add(item.getKey());
                });

        cellDetails.getEmittedFactors().stream()
                .forEach(actionList::add);

        cellDetails.getProliferationInfoSet().stream()
                .forEach(item -> proliferationInfo.put(item.getFactorName(), item.getCellType()));

        actionDispatcher = dispatcher;
//        networkActivityListener = networkListener;
        movingAbility = actionList.contains(moveActionName);
        initializeNonDoMethods();
        initializeMethodsForExecution();
        initializeAdditionalComponents();
        actionDispatcher.addMethods(id, methodMap);
    }

    public CellObject(CellId id, PotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
        this(id, potentialCellObject.getIndividualId(), dispatcher,
                potentialCellObject.getCoordinate(),
                potentialCellObject.getCellType());
        createInitialActions();
        parentId = potentialCellObject.getParentId();
    }

    public CellObject(CellId id, InitialPotentialCellObject potentialCellObject, ActionDispatcher dispatcher) {
        this(id, potentialCellObject.getIndividualId(), dispatcher,
                potentialCellObject.getCoordinate(),
                potentialCellObject.getCellType());
        createInitialActions(potentialCellObject.getCellActions());
        parentId = potentialCellObject.getParentId();
    }
    //endregion

    //region Getters and Setters
    public LObjectId getParentId() {
        return parentId;
    }

    public String getCellType() {
        return diffType;
    }

    public CellFunctionalType getCellFunctionalType() {
        return cellFunctionalType;
    }

    public boolean getMovingAbility() {
        return movingAbility;
    }

    public ICoordinate getNearCoordinateForCreation() {
        return nearCoordinateForCreation;
    }

    public Set<String> getReceivedFactors() {
        return receivedFactors;
    }

//    public List<LogicObjectActionDescription> getActionDescriptions() {
//        return actionDispatcher.getActionDescriptionMap();
//    }

    public void setCoordinate(ICoordinate newCoordinate) {
        coordinate = newCoordinate;
    }

    public void setNearCoordinateForCreation(ICoordinate coordinate) {
        nearCoordinateForCreation = coordinate;
    }
    //endregion

    //region Public Methods
//    public void refreshActionStates(Tick currentTick) {
//        actionDispatcher.updateActionsState(currentTick);
//    }

    public void startAction(String actionName, ActionFunctionalType type) {
        if (actionList.contains(actionName))
            actionDispatcher.startAction(
                    new StartActionInfo((CellId) objectId, actionName, type, getCurrentTickActionParameters()));
    }

    public void startAction(String actionName, ActionFunctionalType type, IActionParameters parameters) {
        if (actionList.contains(actionName))
            actionDispatcher.startAction(new StartActionInfo((CellId) objectId, actionName, type, parameters));
    }

    public void disruptAction(String actionName) {
        actionDispatcher.disruptAction(objectId, actionName);
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    protected abstract void initializeMethodsForExecution();

    protected abstract void initializeAdditionalComponents();

    protected IActionParameters getCurrentTickActionParameters() {
        return new EmptyActionParameters(ModelContainer.INSTANCE.getCurrentTick(individualId).getTick());
    }

    protected void endCurrentAction() {
        actionDispatcher.endCurrentAction();
    }

    protected void increaseDeathProteinConcentration() {
        deathProteinConcentration++;
        if (deathProteinConcentration >= deathProteinConcentrationThreshold) {
            actionDispatcher.disruptAllActions(objectId);
            startAction(deleteActionName, ActionFunctionalType.Delete);
        }
    }
    //endregion

    //region Private Methods
    private void initializeNonDoMethods() {
        IModelActionMethod createMethod = this::create;
        IModelActionMethod proliferateMethod = this::proliferate;
        IModelActionMethod deleteMethod = this::delete;
        Map<ActionFunctionalType, IModelActionMethod> nonDoContainer = new HashMap<ActionFunctionalType, IModelActionMethod>() {{
            put(ActionFunctionalType.Create, createMethod);
            put(ActionFunctionalType.Proliferate, proliferateMethod);
            put(ActionFunctionalType.Delete, deleteMethod);
        }};

        actionList.stream()
                .filter(action -> ModelLoader.getActionDetails(action).getActionFunctionalType() != ActionFunctionalType.Do)
                .forEach(action -> methodMap.put(action,
                        nonDoContainer.get(ModelLoader.getActionDetails(action).getActionFunctionalType())));
    }

    private void createInitialActions() {
        actionList.stream()
                .map(ModelLoader::getActionDetails)
                .filter(ActionDetails::isInitial)
                .forEach(action -> startAction(action.getName(), action.getActionFunctionalType()));

//        refreshActionStates(ModelContainer.INSTANCE.getCurrentTick(individualId));
    }

    private void createInitialActions(Map<String, Boolean> modifiedCellActions) {
        actionList.stream()
                .map(ModelLoader::getActionDetails)
                .filter(ActionDetails::isInitial)
                .filter(action -> !modifiedCellActions.containsKey(action.getName()))
                .forEach(action -> startAction(action.getName(), action.getActionFunctionalType()));

        actionList.stream()
                .map(ModelLoader::getActionDetails)
                .filter(action -> !action.isInitial())
                .filter(action -> modifiedCellActions.containsKey(action.getName()))
                .forEach(action -> startAction(action.getName(), action.getActionFunctionalType()));

//        refreshActionStates(ModelContainer.INSTANCE.getCurrentTick(individualId));
    }

    @Contract("_ -> !null")
    private IMethodResponse create(IActionParameters parameters) {
        if (nearCoordinateForCreation == null) {
            disruptAction(createActionName);
            return ModelContainer.INSTANCE.returnEmptyResponse();
        } else
            return new ObjectAddedResponse(
                    new PotentialCellObject(individualId, objectId, nearCoordinateForCreation, diffType));
    }

    @Contract("_ -> !null")
    private IMethodResponse proliferate(IActionParameters parameters) {
        return new ObjectProliferatedResponse(
                (CellId) objectId,
                new PotentialCellObject(individualId, parentId, coordinate, cellProliferationType));
    }

    @Contract("_ -> !null")
    private IMethodResponse delete(IActionParameters parameters) {
        return new ObjectDeletedResponse((CellId) objectId);
    }
    //endregion
}