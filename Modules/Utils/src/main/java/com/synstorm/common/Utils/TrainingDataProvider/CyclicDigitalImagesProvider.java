package com.synstorm.common.Utils.TrainingDataProvider;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import org.jetbrains.annotations.Nullable;
import org.uncommons.maths.random.MersenneTwisterRNG;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Data provider for training samples with possibility of cycling iterations.
 * Original set size may be less than user required. Positions on different cycles are different.
 * Created by dvbozhko on 23/03/2017.
 */
@Model_v0
@NonProductionLegacy
public class CyclicDigitalImagesProvider {
    //region Fields
    private  int classIndex;
    protected final DigitalImageSample[] disSet;
    protected final Map<UUID, AtomicInteger> positions;
    protected final Map<UUID, AtomicInteger> cyclePositions;
    protected final Map<UUID, Random> individualRandoms;
    protected final int actualSize;
    protected final int wantedSize;
    //endregion

    //region Constructors
    public CyclicDigitalImagesProvider(String fileName, int size) {
        positions = new ConcurrentHashMap<>();
        cyclePositions = new ConcurrentHashMap<>();
        individualRandoms = new ConcurrentHashMap<>();
        disSet = initializeDiSet(fileName);
        actualSize = disSet.length;
        wantedSize = size;
        classIndex = 0;
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    public void addIndividual(UUID individualId, byte[] randomSeed) {
        setDefaultPosition(individualId);
        individualRandoms.put(individualId, new MersenneTwisterRNG(randomSeed));
    }

    public void removeIndividual(UUID individualId) {
        positions.remove(individualId);
        cyclePositions.remove(individualId);
        individualRandoms.remove(individualId);
    }

    @Nullable
    public DigitalImageSample next(UUID individualId) {
        if (!hasNext(individualId))
            setDefaultPosition(individualId);

        if (cyclePositions.get(individualId).get() * actualSize + positions.get(individualId).get() < wantedSize)
            return getSampleInCurrentPosition(individualId);
        else
            return null;
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    protected boolean hasNext(UUID individualId) {
        return positions.get(individualId).get() < disSet.length;
    }

    protected DigitalImageSample getSampleInCurrentPosition(UUID individualId) {
        final int curPos = individualRandoms.get(individualId).nextInt(actualSize);
        final DigitalImageSample result = disSet[curPos];
        positions.get(individualId).incrementAndGet();
        return result;
    }

    protected void setDefaultPosition(UUID individualId) {
        positions.put(individualId, new AtomicInteger(0));
        if (cyclePositions.containsKey(individualId))
            cyclePositions.get(individualId).incrementAndGet();
        else
            cyclePositions.put(individualId, new AtomicInteger(0));
    }
    //endregion

    //region Private Methods
    private DigitalImageSample[] initializeDiSet(String fileName) {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            List<DigitalImageSample> set = stream.map(DigitalImageSample::new).collect(Collectors.toList());
            Map<Short, Integer> classes = set.stream()
                    .map(DigitalImageSample::getLabel)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toMap(sClass -> sClass, sClass -> incrementClassIndex()));
            set.forEach(digitalImageSample -> digitalImageSample.setClassIndex(classes.get(digitalImageSample.getLabel())));
            return set.toArray(new DigitalImageSample[set.size()]);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new DigitalImageSample[0];
        }
    }

    private int incrementClassIndex() {
        return classIndex++;
    }
    //endregion
}
