package com.synstorm.SimulationModel.ModelProcessors;

import com.synstorm.SimulationModel.ModelProcessors.IndividualHolders.IndividualHolder;
import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
import com.synstorm.common.Utils.Details.IndividualDetails;
import com.synstorm.common.Utils.Randomizer.UUIDRandom;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.uncommons.maths.random.MersenneTwisterRNG;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Base abstract class for common behaviour of learning and non-learning
 * individual processor for multiple agent simulation with one common individual configuration
 * and multiple seeds.
 * Created by dvbozhko on 30/03/2017.
 */
public abstract class MultiSeedProcessor extends MultiAgentProcessor {
    //region Fields
    protected final Map<UUID, IndividualHolder> individualHolderMap;
    private final Random random;
    private int returnedIndividualCount;
    //endregion

    //region Constructors
    public MultiSeedProcessor() {
        super();
        individualHolderMap = new ConcurrentHashMap<>();
        random = new MersenneTwisterRNG(ModelLoader.getDefaultSeed());
        returnedIndividualCount = 0;
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    @NotNull
    protected abstract IndividualHolder makeIndividualHolderObject(IndividualDetails individualDetails);

    @Override
    protected @Nullable IndividualDetails getNextIndividualToCompute() throws Exception {
        if (++returnedIndividualCount <= populationVolume) {
            final UUID nextUUID = UUIDRandom.getInstance().nextUUID();
            final IndividualDetails nextIndividualDetails = ModelLoader.getIndividualDetails().makeCopy(nextUUID);
            recalculateActions(nextIndividualDetails);
            return nextIndividualDetails;
        } else
            return null;
    }

    @NotNull
    protected byte[] generateSeed() {
        final StringBuilder result = new StringBuilder();
        final Integer[] arrayOfRandomIntegers = new Integer[16];
        for (int i = 0; i < 16; i++)
            arrayOfRandomIntegers[i] = random.nextInt();

        for (int i = arrayOfRandomIntegers.length - 1; i >= 0; i--){
            String currentValue = String.valueOf(arrayOfRandomIntegers[i]);
            int currentValueLength = currentValue.length();
            int cursor = random.nextInt(currentValueLength - 1) + 1;
            result.append(currentValue.substring(cursor, cursor + 1));
        }
        return result.toString().getBytes();
    }
    //endregion

    //region Private Methods
    //endregion
}
