package com.synstorm.common.Utils.EvolutionUtils.Batches;

import com.synstorm.common.Utils.EnumTypes.UUIDAnswerType;
import com.synstorm.common.Utils.EvolutionUtils.Score.Score;

import java.util.*;

/**
 * Created by human-research on 10/08/16.
 */
public class BatchProcessor {
    //region Fields
    private Deque<IndividualsBatch> computingBatches;
    private Deque<IndividualsBatch> waitingBatches;
    private Map<UUID, Integer> indBatch;
    private Map<String, UUID> uniques;
    private Map<String, Score> uniqueScores;
    private int duplicatesCounter;
    private int calculatedAgentsCount;

    //endregion

    //region Constructors
    public BatchProcessor() {
        computingBatches = new ArrayDeque<>();
        waitingBatches = new ArrayDeque<>();
        indBatch = new LinkedHashMap<>();
        uniques = new HashMap<>();
        uniqueScores = new HashMap<>();
        duplicatesCounter = 0;
        calculatedAgentsCount = 0;
    }
    //endregion

    //region Getters and Setters
    public int getCalculatedAgentsCount() {
        return calculatedAgentsCount;
    }

    public int getDuplicatesCounter() {
        return duplicatesCounter;
    }

    public int getWaitingBatchesQuantity() {
        return waitingBatches.size();
    }

    public int getComputingBatchesQuantity() {
        return computingBatches.size();
    }

    public UUID getUnique(String hash) {
        return uniques.get(hash);
    }
    //endregion

    //region Public Methods
    /**
     * This method returns UUID of next individual from last batch in "computing" deque
     * If computingBatches not contains batches we'll move a batch from waitingBatches
     * @return UUID
     */
    
    public UUIDAnswer getNextIndividualToCompute() {
        boolean haveMoreWaitingBatches = false;
        if (computingBatches.size() == 0 || computingBatches.getLast().batchSize() == 0)
            haveMoreWaitingBatches = moveBatchFromWaitingToCompute();

        IndividualsBatch lastBatch = computingBatches.peekLast();
        if (lastBatch == null)
            return new UUIDAnswer(null, UUIDAnswerType.PopulationNoIndividuals);

        UUID nextIndividual = lastBatch.getNextIndividual();
        if (nextIndividual != null) {
            indBatch.put(nextIndividual, lastBatch.getBatchId());
            return new UUIDAnswer(nextIndividual, UUIDAnswerType.Original);
        } else if (haveMoreWaitingBatches)
            return getNextIndividualToCompute();
        else
            return new UUIDAnswer(null, UUIDAnswerType.PopulationNoIndividuals);
    }

    /**
     * To use this method, please check remaining capacity of population in EvolutionProcessor
     * If remaining capacity >= defaultBatchSize, use defaultBatchSize; if not - use remaining capacity
     * @param batchSize
     * @param UUIDsMap
     */
    public int formNewBatch(int batchSize, Map<UUID, String> UUIDsMap) {
        IndividualsBatch nextBatch = new IndividualsBatch(batchSize);
        for (Map.Entry<UUID, String> nextEntry : UUIDsMap.entrySet()) {
            UUID uuid = nextEntry.getKey();
            String hash = nextEntry.getValue();
            if (!uniques.containsKey(hash)) {
                uniques.put(hash, uuid);
                nextBatch.addOriginalToBatch(uuid);
            } else
                nextBatch.addCopyToBatch(uuid, hash);
        }
        waitingBatches.add(nextBatch);
        return nextBatch.getBatchId();
    }

    /**
     * This method adds score of Individual to target batch.
     * Please don't remember to call checkFirstComputingBatch() after adding the score
     * @param uuid
     * @param score
     */
    public void addUniqueScore(UUID uuid, Score score) {
        uniqueScores.put(score.getActionsHash(), score);
        int batchID = indBatch.get(uuid);
        Optional<IndividualsBatch> batch = computingBatches.stream()
                .filter(element -> element.getBatchId() == batchID)
                .findFirst();

        if (batch.isPresent()) {
            batch.get().setIndividualScore(uuid, score);
            calculatedAgentsCount++;
            indBatch.remove(uuid);
        }
    }

    /**
     * This method checks first batch in "computing" deque and returns the list of scores in right order.
     * If no batches in "computing" or first batch haven't done this method returns null
     * @return List
     */
    public List<List<Score>> checkFirstComputingBatch(List<List<Score>> batchScores) {
        IndividualsBatch firstBatch = computingBatches.peekFirst();
        List<Score> result = new LinkedList<>();
        if (firstBatch != null && firstBatch.isBatchDone()) {
            proceedScoresOfCopies(firstBatch);
            firstBatch.getAllBatchScores().stream().forEachOrdered(result::add);
            computingBatches.poll();
            batchScores.add(result);
            checkFirstComputingBatch(batchScores);
        }
        return batchScores;
    }
    //endregion

    //region Private Methods
    private void proceedScoresOfCopies(IndividualsBatch individualsBatch) {
        Map<UUID, String> copies = individualsBatch.getCopies();
        for (UUID uuid : copies.keySet()) {
            Score score = Score.GetCopy(uniqueScores.get(copies.get(uuid)), uuid);
            individualsBatch.setIndividualScore(uuid, score);
            duplicatesCounter++;
            calculatedAgentsCount++;
        }
    }

    private boolean moveBatchFromWaitingToCompute() {
        if (waitingBatches.size() > 0) {
            IndividualsBatch nextBatch = waitingBatches.poll();
            computingBatches.add(nextBatch);
            return true;
        }

        return false;
    }
    //endregion

}
