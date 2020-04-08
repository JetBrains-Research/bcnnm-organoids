package com.synstorm.SimulationModel.ModelAction;

import com.synstorm.SimulationModel.Containers.ModelContainer;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.CalculationResult;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.IMethodResponse;
import com.synstorm.SimulationModel.LogicObject.IActionParameters;
import com.synstorm.SimulationModel.LogicObject.IModelActionMethod;
import com.synstorm.SimulationModel.ModelTime.Tick;
import com.synstorm.SimulationModel.SimulationIdentifiers.ModelActionIds.ModelActionId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.CellId;
import com.synstorm.SimulationModel.SimulationIdentifiers.ObjectIds.LObjectId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.ActionFunctionalType;
import com.synstorm.common.Utils.EnumTypes.ActionState;
import org.jetbrains.annotations.Contract;

import java.util.*;

/**
 * Dispatcher class for managing all model processingActionQueue by one cell
 * Created by dvbozhko on 06/06/16.
 */

@Model_v0
@NonProductionLegacy
public final class ActionDispatcher {
    //region Fields
    private final UUID individualId;
    private final Map<LObjectId, Map<String, IModelActionMethod>> lObjectMethodMap;
    private final Queue<Tick> ticks;
    private final Map<Tick, Queue<ModelAction>> processingActionQueue;
    private final Map<ModelActionId, ModelAction> actions;
    private final Map<ModelActionId, IActionParameters> actionParametersMap;
    private final Map<ModelActionId, CellActionDescription> actionDescriptionMap;
    private final EnumMap<ActionState, List<ModelActionId>> stateChangedActions;
    private final Map<LObjectId, Set<String>> disruptedActions;
    private final Set<LObjectId> disruptedIds;
    private final Set<LObjectId> lObjectsMarkedForDeletion;
    private ModelAction currentAction;
    private Tick nextSignificantTick;
    private boolean stateChanged;
    private boolean isAnyDisrupted;
    //endregion

    //region Constructors
    public ActionDispatcher(UUID id) {
        individualId = id;
        lObjectMethodMap = new HashMap<>();
        ticks = new PriorityQueue<>();
        processingActionQueue = new HashMap<>();
        actions = new HashMap<>();
        actionParametersMap = new HashMap<>();
        actionDescriptionMap = new HashMap<>();
        stateChangedActions = new EnumMap<ActionState, List<ModelActionId>>(ActionState.class) {{
            put(ActionState.Ready, new ArrayList<>());
            put(ActionState.Active, new ArrayList<>());
            put(ActionState.Ended, new ArrayList<>());
        }};
        disruptedActions = new HashMap<>();
        disruptedIds = new HashSet<>();
        lObjectsMarkedForDeletion = new HashSet<>();
        nextSignificantTick = new Tick(Long.MAX_VALUE);
        stateChanged = false;
        isAnyDisrupted = false;
    }
    //endregion

    //region Getters and Setters
//    @Contract(" -> !null")
//    public List<LogicObjectActionDescription> getActionDescriptionMap() {
//        return new ArrayList<>(actionDescriptionMap.values());
//    }

    public Tick getNextSignificantTick() {
        return nextSignificantTick;
    }
    //endregion

    //region Public Methods
    public void addMethods(CellId id, Map<String, IModelActionMethod> methodMap) {
        lObjectMethodMap.put(id, methodMap);
        disruptedActions.put(id, new HashSet<>());
    }

    public void removeMethods(CellId id) {
        lObjectMethodMap.remove(id);
        disruptedActions.remove(id);
    }

    public CalculationResult executeActions(Tick tick) {
        ticks.remove();
        CalculationResult result = new CalculationResult();
        Queue<ModelAction> modelActionQueue = processingActionQueue.get(tick);
        while (!modelActionQueue.isEmpty()) {
            final ModelAction action = modelActionQueue.poll();
            currentAction = action;
            final IMethodResponse response = action.returnActionResult(tick);
            result.addResponse(response);
            addStateChangedAction(action.getId(), action.getState());
        }

        currentAction = null;
        return result;
    }

    public void startAction(StartActionInfo actionInfo) {
//        ModelActionId newId = createModelActionId(actionInfo.getCellId(), actionInfo.getActionName());
        ModelActionId newId = createModelActionId(actionInfo);
        addStateChangedAction(newId, ActionState.Active);
        actionParametersMap.put(newId, actionInfo.getParameters());
        if (actionInfo.getType() == ActionFunctionalType.Delete)
            lObjectsMarkedForDeletion.add(actionInfo.getCellId());
    }

    public void disruptAction(LObjectId objectId, String actionName) {
        disruptedActions.get(objectId).add(actionName);
        isAnyDisrupted = true;
    }

    public void endCurrentAction() {
        currentAction.end();
    }

    public void disruptAllActions(LObjectId objectId) {
        final Set<String> disruptedSet = disruptedActions.get(objectId);
        actions.entrySet().forEach(actionsEntry -> {
            final ModelActionId modelActionId = actionsEntry.getKey();
            if (modelActionId.getObjectId().equals(objectId))
                disruptedSet.add(modelActionId.getName());
        });
        isAnyDisrupted = true;
    }

    public void updateActionsState(Tick currentTick) {
        if (stateChanged) {
            startActions(stateChangedActions.get(ActionState.Active));
            restartActions(stateChangedActions.get(ActionState.Ready));
            endActions(stateChangedActions.get(ActionState.Ended));
            stateChanged = false;
        }

        if (isAnyDisrupted) {
            actions.entrySet()
                    .forEach(actionEntry -> {
                        final ModelActionId modelActionId = actionEntry.getKey();
                        final LObjectId objectId = modelActionId.getObjectId();
                        if (disruptedActions.containsKey(objectId)) {
                            final Set<String> disruptedSet = disruptedActions.get(objectId);
                            final boolean thisAction = disruptedSet.contains(modelActionId.getName());
                            if (thisAction) {
                                actionEntry.getValue().interrupt();
                                disruptedIds.add(objectId);
                            }
                        }
                    });
            disruptedIds.forEach(disruptedId -> disruptedActions.get(disruptedId).clear());
            disruptedIds.clear();
            isAnyDisrupted = false;
        }

        processingActionQueue.remove(currentTick);
        updateNextSignificantTick();
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    private void updateNextSignificantTick() {
        nextSignificantTick = ticks.size() > 0 ? ticks.peek() : new Tick(Long.MAX_VALUE);
    }

//    @Contract("_, _ -> !null")
//    private ModelActionId createModelActionId(CellId id, String actionName) {
//        return new ModelActionId(individualId, id, actionName);
//    }

    @Contract("_ -> !null")
    private ModelActionId createModelActionId(StartActionInfo actionInfo) {
        return new ModelActionId(individualId, actionInfo.getCellId(), actionInfo.getActionName(), actionInfo.getType());
    }

    private void createNewAction(ModelActionId modelActionId) {
        final IModelActionMethod method = lObjectMethodMap.get(modelActionId.getObjectId()).get(modelActionId.getName());
        final IActionParameters parameters = actionParametersMap.get(modelActionId);
        final ModelAction modelAction = new ModelAction(modelActionId, individualId, method, parameters);
        final CellActionDescription actionDescription = new CellActionDescription(modelActionId);
        addActionToQueue(modelAction);
        actionDescriptionMap.put(modelActionId, actionDescription);
        actionParametersMap.remove(modelActionId);
        ModelContainer.INSTANCE.addChangedActionDescriptionInfo(individualId, actionDescription);
    }

    private void addActionToQueue(ModelAction modelAction) {
        final Tick endTick = new Tick(modelAction.getEndTime());
        final ModelActionId modelActionId = modelAction.getId();
        if (!actions.containsKey(modelActionId))
            actions.put(modelActionId, modelAction);

        if (!processingActionQueue.containsKey(endTick)) {
            ticks.add(endTick);
            processingActionQueue.put(endTick, new ArrayDeque<ModelAction>() {{
                add(modelAction);
            }});
        } else
            processingActionQueue.get(endTick).add(modelAction);
    }

    private void addStateChangedAction(ModelActionId id, ActionState state) {
        stateChanged = true;
        stateChangedActions.get(state).add(id);
    }

    private void startActions(List<ModelActionId> actionIdList) {
        actionIdList.forEach(actionId -> {
            if (!lObjectsMarkedForDeletion.contains(actionId.getObjectId()))
                createNewAction(actionId);
            else if (actionId.getType() == ActionFunctionalType.Delete)
                createNewAction(actionId);
        });
        stateChangedActions.get(ActionState.Active).clear();
        lObjectsMarkedForDeletion.clear();
    }

    private void restartActions(List<ModelActionId> actionIdList) {
        actionIdList
                .forEach(actionId -> {
                    final ModelAction action = actions.get(actionId);
                    action.activate();
                    CellActionDescription actionDescription = actionDescriptionMap.get(actionId);
                    actionDescription.incrementActualExtension();
                    addActionToQueue(action);
                });

        stateChangedActions.get(ActionState.Ready).clear();
    }

    private void endActions(List<ModelActionId> actionIdList) {
        actionIdList
                .forEach(actionId -> {
                    final CellActionDescription actionDescription = actionDescriptionMap.get(actionId);
                    actionDescription.deleteAction();
                    ModelContainer.INSTANCE.addChangedActionDescriptionInfo(individualId, actionDescription);
                    actionDescriptionMap.remove(actionId);
                    actions.remove(actionId);
                });

        stateChangedActions.get(ActionState.Ended).clear();
    }
    //endregion
}
