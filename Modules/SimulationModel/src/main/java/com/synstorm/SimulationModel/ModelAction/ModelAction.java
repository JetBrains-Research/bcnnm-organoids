package com.synstorm.SimulationModel.ModelAction;

import com.synstorm.SimulationModel.Containers.ModelContainer;
import com.synstorm.SimulationModel.LogicObject.ActionMethodResponse.IMethodResponse;
import com.synstorm.SimulationModel.LogicObject.IActionParameters;
import com.synstorm.SimulationModel.LogicObject.IModelActionMethod;
import com.synstorm.SimulationModel.ModelTime.Tick;
import com.synstorm.SimulationModel.SimulationIdentifiers.ModelActionIds.ModelActionId;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
import com.synstorm.common.Utils.Details.ActionDetails;
import com.synstorm.common.Utils.EnumTypes.ActionSignalType;
import com.synstorm.common.Utils.EnumTypes.ActionState;

import java.util.UUID;

/**
 * Class executor for simulation actions.
 * Created by dvbozhko on 06/06/16.
 */

@Model_v0
@NonProductionLegacy
public final class ModelAction {
    //region Fields
    private final ModelActionId id;
    private long endTime;
    private final int duration;
    private final boolean repeatable;
    private ActionState state;
    private final IActionParameters parameters;
    private final ActionSignalType actionSignalType;
    private final IModelActionMethod actionMethod;
    //endregion

    //region Constructors
    ModelAction(ModelActionId actionId, UUID individualId, IModelActionMethod aMethod, IActionParameters params) {
        id = actionId;
        actionMethod = aMethod;
        parameters = params;
        state = ActionState.Active;

        final ActionDetails ad = ModelLoader.getActionDetails(id.getName());
        duration = ad.getDurationByGenes(individualId);
        endTime = params.getStartTick() + duration;
        actionSignalType = ad.getSignalType();
        repeatable = ad.isRepeatable();
    }
    //endregion

    //region Getters and Setters
    ModelActionId getId() {
        return id;
    }

    String getName() {
        return id.getName();
    }

    ActionState getState() {
        return state;
    }

    long getEndTime() {
        return endTime;
    }
    //endregion

    //region Public Methods
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    //endregion

    //region Package-local Methods
    IMethodResponse returnActionResult(Tick currentTick) {
        if (state == ActionState.Disrupted) {
            end();
            return ModelContainer.INSTANCE.returnEmptyResponse();
        }

        IMethodResponse result = execute();
        if (actionSignalType == ActionSignalType.Discrete && !repeatable)
            state = ActionState.Ended;
        else if (state != ActionState.Ended) {
            state = ActionState.Ready;
            endTime = currentTick.getTick() + duration;
        }

        return result;
    }

    void interrupt() {
        if (state == ActionState.Active || state == ActionState.Ready)
            state = ActionState.Disrupted;
    }

    void activate() {
        if (state == ActionState.Ready)
            state = ActionState.Active;
    }

    void end() {
        state = ActionState.Ended;
    }


    //endregion

    //region Private Methods
    private IMethodResponse execute() {
        return actionMethod.execute(parameters);
    }
    //endregion
}