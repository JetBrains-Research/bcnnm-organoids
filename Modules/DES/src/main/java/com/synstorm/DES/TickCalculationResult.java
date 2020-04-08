package com.synstorm.DES;

import java.util.ArrayList;
import java.util.List;

public class TickCalculationResult {
    //region Fields
    private final List<IEventExecutionResult> responses;
    //endregion

    //region Constructors
    public TickCalculationResult() {
        responses = new ArrayList<>();
    }
    //endregion

    //region Public Methods
    public void addResponse(IEventExecutionResult response) {
        if (!response.getProceedMethod().isEmpty())
            responses.add(response);
    }

    public List<IEventExecutionResult> getResponses() {
        return responses;
    }

    public void clear() {
        responses.clear();
    }
    //endregion
}
