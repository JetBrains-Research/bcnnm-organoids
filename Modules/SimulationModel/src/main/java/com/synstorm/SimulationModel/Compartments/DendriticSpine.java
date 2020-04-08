package com.synstorm.SimulationModel.Compartments;

import com.synstorm.SimulationModel.LogicObjectR.Compartment;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;

/**
 * Created by Dmitry.Bozhko on 9/4/2015.
 */
public class DendriticSpine extends Compartment {
    //region Fields
//    private final UUID individualId;
//    private CellId sourceId;
//    private Synapse synapse;
//    private String receptorsType;
//    private int receptorsCount;
//    private double stimuliThreshold;
//    private final int receptorCountForSpike;
//    private int stimulatedReceptorsCountOverTime;
//    private int previousStimulatedReceptorsCount;
//    private boolean isConstant;
//    private boolean isStimulatedInPeriod;
//    private boolean isSpiked;
//
//    private final double Kr;
//    private final double KrScalePower;
//    private int ScrCur;
//
//    private final Queue<Integer> preparedToBind;
//    private final Queue<Integer> bound;
//    private final Queue<Integer> toDecrease;
    //endregion

    //region Constructors
    public DendriticSpine(int id, int parentId, ILogicObjectDescription logicObjectDescription) {
        super(id, parentId, logicObjectDescription);
    }
    //endregion

    //region Getters and Setters
//    public boolean isStimulatedInPeriod() {
//        if (isStimulatedInPeriod) {
//            isStimulatedInPeriod = false;
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean isSpiked() {
//        if (isSpiked) {
//            isSpiked = false;
//            return true;
//        }
//
//        return false;
//    }
//
//    public int getReceptorCountForSpike() {
//        return receptorCountForSpike;
//    }
//
//    public Synapse getSynapse() {
//        return synapse;
//    }
//
//    public String getReceptorsType() {
//        return receptorsType;
//    }
//    //endregion
//
//    //region Public Methods
//    public boolean checkStimuliReceptors(int scale, double checkP) {
////        if (!isStimulatedInPeriod()) {
////            if (synapse.getSynapticPowerChangeProbability() > checkP) {
//                decreasePlasticity();
//                return true;
////            }
////        }
//
////        if (stimulatedReceptorsCountOverTime != previousStimulatedReceptorsCount) {
////            double scaleShiftProbability = Math.tanh(0.2 * Math.abs(stimulatedReceptorsCountOverTime - previousStimulatedReceptorsCount));
////            if (scaleShiftProbability > checkP) {
////                if (stimulatedReceptorsCountOverTime > previousStimulatedReceptorsCount)
////                    increasePlasticity(scale);
////                else if (stimulatedReceptorsCountOverTime < previousStimulatedReceptorsCount)
////                    decreasePlasticity();
////                return true;
////            }
////        }
//
////        return false;
//    }
//
//    public void increasePlasticity(int scale) {
//        if (ScrCur < scale && !isConstant) {
//            ScrCur++;
//            modifyPlasticity();
//        }
//    }
//
//    public void decreasePlasticity() {
//        if (ScrCur > 0 && !isConstant) {
//            ScrCur--;
//            modifyPlasticity();
//        }
//    }
//
//    public void makeSynapsesConstant(double powerThresholdForConstant) {
////        if (isConstant)
////            return;
////
////        if (synapse.getSynapticPower() >= powerThresholdForConstant)
////            isConstant = true;
//
//        isConstant = synapse.getSynapticPowerChangeProbability() >= ModelContainer.INSTANCE.nextDouble(individualId);
//    }
//
//    public boolean breakSynapse() {
//        return !isConstant && ModelContainer.INSTANCE.nextDouble(individualId) >= synapse.getSynapticPower();
//    }
//
//    public void reserveForBinding() {
//        final int releasedNt = synapse.getReleasedNeurotransmitterCount();
//        synapse.bindNeurotransmitter(releasedNt);
//        preparedToBind.add(releasedNt);
//    }
//
//    public void bindNtToReceptors() {
//        final int readyForBind = preparedToBind.remove();
//
//        if (readyForBind <= receptorsCount) {
//            bound.add(readyForBind);
//        } else {
//            final int residualNt = readyForBind - receptorsCount;
//            bound.add(receptorsCount);
//            synapse.unbindNeurotransmitter(residualNt); //we unbind residual for uptaking/degrading
//        }
//    }
//
//    public int activateReceptors(int scale, double checkP) {
//        final int boundReceptors = bound.remove();
//        stimulatedReceptorsCountOverTime += boundReceptors;
//        toDecrease.add(boundReceptors);
//        synapse.unbindNeurotransmitter(boundReceptors);
//        isStimulatedInPeriod = true;
////        if (synapse.getSynapticPowerChangeProbability() > checkP)
//            increasePlasticity(scale);
//        return boundReceptors;
//    }
//
//    public int deactivateReceptors() {
//        return toDecrease.remove();
//    }
    //endregion

    //region Private Methods
//    private int calculateReceptorsCount(int scalePosition) {
//        return (int) Math.round(1 / (Kr + Math.exp(KrScalePower * scalePosition)));
//    }
//
//    private int calculateReceptorCountForSpike(double stimuliThreshold) {
//        return (int) Math.round(receptorsCount * stimuliThreshold);
//    }
//
//    private void modifyPlasticity() {
//        int nReceptors = calculateReceptorsCount(ScrCur);
//        synapse.calculateSynapticPower(nReceptors);
//        receptorsCount = nReceptors;
//        previousStimulatedReceptorsCount = stimulatedReceptorsCountOverTime;
//        stimulatedReceptorsCountOverTime = 0;
//    }
    //endregion
}