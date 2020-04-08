package com.synstorm.SimulationModel.LogicObject.ActionMethodResponse;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for storing all responses for given Cell on calculated tick.
 * Created by dvbozhko on 06/06/16.
 */
public class CalculationResult {
    //region Fields
    private final List<IMethodResponse> responses;
    private boolean hasUpdatingSpaceResponse;
    //endregion

    //region Constructors
    public CalculationResult() {
        responses = new ArrayList<>();
        hasUpdatingSpaceResponse = false;
    }
    //endregion

    //region Getters and Setters
    public List<IMethodResponse> getResponses() {
        return responses;
    }

    public boolean hasUpdatingSpaceResponse() {
        return hasUpdatingSpaceResponse;
    }
    //endregion

    //region Public Methods
    public void addResponse(IMethodResponse response) {
        if (!(response instanceof EmptyResponse)) {
            responses.add(response);
            hasUpdatingSpaceResponse |= isUpdatingSpaceResponse(response);
        }
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    @Contract(value = "null -> false", pure = true)
    private boolean isUpdatingSpaceResponse(IMethodResponse response) {
        return (response instanceof ObjectMovedResponse) || (response instanceof ObjectAddedResponse)
                || (response instanceof ObjectDeletedResponse);
    }
    //endregion
}
