//package com.synstorm.SimulationModel.ModelAgent;
//
//import com.synstorm.SimulationModel.Model.Individual;
//import com.synstorm.common.Utils.ConsoleProgressBar.IProgressBar;
//import org.jetbrains.annotations.NotNull;
//
///**
// * Callable class for calculating non-learning individuals in separate thread.
// * Uses for multiple individuals calculation inside evolution simulation.
// * Created by dvbozhko on 25/08/16.
// */
//public class MultiIndividualAgent extends IndividualAgent {
//    //region Fields
//    //endregion
//
//    //region Constructors
//    public MultiIndividualAgent(Individual individual, IProgressBar progressBar, @NotNull IIndividualAgentListener listener) {
//        super(individual, progressBar, listener);
//    }
//    //endregion
//
//    //region Getters and Setters
//    //endregion
//
//    //region Public Methods
//    @Override
//    public void startCalculating() throws Exception {
//        calculateIndividualWhile();
//    }
//
//    @Override
//    public void endCalculating() {
//        statisticsInProgressBar();
//    }
//    //endregion
//
//    //region Package-local Methods
//    //endregion
//
//    //region Protected Methods
//    protected void calculateIndividualWhile() throws Exception {
//        while (getCalculationCondition()) {
//            individual.calculateState();
////            memCalc.execute();
//        }
//    }
//
//    @Override
//    protected boolean getCalculationCondition() {
//        return !individual.checkConditions();
//    }
//
//    @Override
//    protected void statisticsInProgressBar() {
//
//    }
//    //endregion
//
//    //region Private Methods
//    //endregion
//}