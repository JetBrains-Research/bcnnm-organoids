package com.synstorm.SimulationModel.ModelAgent;

import com.synstorm.SimulationModel.Model.IndividualR;
import com.synstorm.common.Utils.ConsoleProgressBar.IProgressBar;
import com.synstorm.common.Utils.ModelStatistic.IndividualStatistics;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * Callable abstract class for multi-thread individual calculation.
 * Created by dvbozhko on 20/08/16.
 */
public abstract class IndividualAgent implements Callable<IndividualStatistics> {
    //region Fields
    private final IIndividualAgentListener listener;
//    protected final Individual individual;
    protected final IndividualR individual;
    protected final IProgressBar progressBar;
    protected MemoryCalculationMethod memCalc;
    //endregion

    //region Constructors
//    public IndividualAgent(Individual individual, IProgressBar progressBar, @NotNull IIndividualAgentListener listener) {
//        this.individual = individual;
//        this.progressBar = progressBar;
//        this.listener = listener;
//    }

    public IndividualAgent(IndividualR individual, IProgressBar progressBar, @NotNull IIndividualAgentListener listener) {
        this.individual = individual;
        this.progressBar = progressBar;
        this.listener = listener;
    }
    //endregion

    //region Getters and Setters
    public UUID getId() {
        return individual.getId();
    }

//    public Individual getIndividual() {
//        return individual;
//    }

    public IndividualR getIndividual() {
        return individual;
    }

    public void setMemCalc(MemoryCalculationMethod memoryCalculationMethod) {
        memCalc = memoryCalculationMethod;
    }
    //endregion

    //region Public Methods
    @Override
    public IndividualStatistics call() throws Exception {
//        try (AffinityLock ignored = AffinityLock.acquireLock()) {
        try {
            startCalculating();
            finishingSuccessfulCalculation();
        } catch (Exception e) {
            finishingFailedCalculation();
            throw new Exception(e);
        }

        return individual.getStatistics();
    }

    public abstract void startCalculating() throws Exception;

    public abstract void endCalculating() throws Exception;
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //protected abstract void calculateIndividualWhile() throws Exception;

    //protected abstract boolean getCalculationCondition();

    protected abstract void statisticsInProgressBar();
    //endregion

    //region Private Methods
    private void finishingSuccessfulCalculation() throws Exception {
        listener.finishingSuccessfulAgentCalculation(this);
    }

    private void finishingFailedCalculation() throws Exception {
        listener.finishingFailedAgentCalculation(this);
    }
    //endregion

    @FunctionalInterface
    public interface MemoryCalculationMethod {
        void execute();
    }
}