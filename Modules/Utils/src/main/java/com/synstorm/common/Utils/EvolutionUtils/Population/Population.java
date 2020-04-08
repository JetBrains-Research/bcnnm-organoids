package com.synstorm.common.Utils.EvolutionUtils.Population;

import com.synstorm.common.Utils.Details.IndividualDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by human-research on 26/05/16.
 */
public class Population {
    //region Fields
    private final int populationVolume;
    private final IndividualDetails initialIndividual;
    private final Map<UUID, IndividualDetails> individuals;
    //endregion


    //region Constructors

    public Population(IndividualDetails initialIndividual, int populationVolume) {
        this.populationVolume = populationVolume;
        this.initialIndividual = initialIndividual;
        individuals = new HashMap<>();
        addIndividual(initialIndividual);
    }

    //endregion


    //region Getters and Setters

    public int getPopulationVolume() {
        return populationVolume;
    }

    public IndividualDetails getInitialIndividual() {
        return initialIndividual;
    }

    public int getPopulationSize() {
        return individuals.size();
    }

    public IndividualDetails getIndividualDetails(UUID uuid) {
        return individuals.get(uuid);
    }

    public Map<UUID, IndividualDetails> getIndividuals() {
        return individuals;
    }

    //endregion


    //region Public Methods
    public void addIndividual(IndividualDetails individualDetails) {
        individuals.put(individualDetails.getUuid(), individualDetails);
    }
    //endregion


    //region Private Methods

    //endregion

}
