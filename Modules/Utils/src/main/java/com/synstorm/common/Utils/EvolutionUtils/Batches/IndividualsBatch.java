package com.synstorm.common.Utils.EvolutionUtils.Batches;

import com.synstorm.common.Utils.EvolutionUtils.Score.Score;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by human-research on 15/06/16.
 */
public class IndividualsBatch {
    //region Fields
    private static int batchCounter;
    private int batchId;
    private BlockingQueue<UUID> originalsToCalc;
    private Map<UUID, Score> individualScores;
    private Map<UUID, String> copies;
    //endregion


    //region Constructors
    static {
        batchCounter = 0;
    }

    public IndividualsBatch(int batchCapacity) {
        this.batchId = nextId();
        originalsToCalc = new LinkedBlockingQueue<>(batchCapacity);
        individualScores = new LinkedHashMap<>();
        copies = new LinkedHashMap<>();
    }
    //endregion


    //region Getters and Setters

    public int getBatchId() {
        return batchId;
    }

    public boolean isBatchDone() {
        Map<UUID, Score> tempCollection = new HashMap<>(individualScores);
        copies.keySet().forEach(tempCollection::remove);
        return !tempCollection.values().contains(null);
    }

    public Map<UUID, String> getCopies() {
        return copies;
    }

    //endregion


    //region Public Methods
    public void addOriginalToBatch(UUID uuid) {
        originalsToCalc.add(uuid);
        individualScores.put(uuid, null);
    }

    public void addCopyToBatch(UUID uuid, String hash) {
        copies.put(uuid, hash);
        individualScores.put(uuid, null);
    }


    public int remainingCapacity() {
        return originalsToCalc.remainingCapacity();
    }

    public int batchSize() {
        return originalsToCalc.size();
    }

    @Nullable
    public UUID getNextIndividual() {
        return originalsToCalc.poll();
    }

    public void setIndividualScore(UUID uuid, Score score) {
        individualScores.put(uuid, score);
    }

    public LinkedList<Score> getAllBatchScores() {
        LinkedList<Score> result = new LinkedList<>();
        Iterator<Map.Entry<UUID, Score>> iterator = individualScores.entrySet().iterator();
        while (iterator.hasNext())
            result.add(iterator.next().getValue());

        return result;
    }
    //endregion


    //region Private Methods
    private static int nextId() {
        return batchCounter++;
    }
    //endregion

}
