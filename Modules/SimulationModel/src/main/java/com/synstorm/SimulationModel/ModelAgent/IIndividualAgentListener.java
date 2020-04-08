package com.synstorm.SimulationModel.ModelAgent;

/**
 * Listener for IndividualAgent. Notifies about ending of calculation.
 * Created by dvbozhko on 22/08/16.
 */
public interface IIndividualAgentListener {
    void finishingSuccessfulAgentCalculation(IndividualAgent agent) throws Exception;
    void finishingFailedAgentCalculation(IndividualAgent agent) throws Exception;
}
