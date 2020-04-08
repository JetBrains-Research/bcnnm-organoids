package com.synstorm.SimulationModel.ModelProcessors;

import com.synstorm.SimulationModel.Utils.ModelStrings;
import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
import com.synstorm.common.Utils.Details.IndividualDetails;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;

/**
 * Base abstract class for common behaviour of learning and non-learning
 * individual processor for multiple agent simulation with multiple individual configurations
 * and common seeds.
 * Created by dvbozhko on 30/03/2017.
 */
public abstract class MultiConfigurationProcessor extends MultiAgentProcessor {
    //region Fields
    protected final Map<UUID, IndividualDetails> individualDetails;
    private final Queue<UUID> individualIds;
    //endregion

    //region Constructors
    public MultiConfigurationProcessor() {
        super();
        individualDetails = ModelLoader.loadAdditionalIndividuals(ModelStrings.CONFIG_PATH + configName);
        individualIds = new ArrayDeque<>();
        individualDetails.forEach((key, value) -> individualIds.add(key));
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    @Nullable
    @Override
    protected IndividualDetails getNextIndividualToCompute() throws Exception {
        final UUID id = individualIds.poll();
        if (id != null) {
            final IndividualDetails details = individualDetails.get(id);
            recalculateActions(details);
            return details;
        } else
            return null;
    }
    //endregion

    //region Private Methods
    //endregion
}
