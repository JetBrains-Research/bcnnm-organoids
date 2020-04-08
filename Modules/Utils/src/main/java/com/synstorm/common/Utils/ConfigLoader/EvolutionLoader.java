package com.synstorm.common.Utils.ConfigLoader;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.ConfigWorker.ConfigNode;
import com.synstorm.common.Utils.ConfigWorker.ConfigReader;
import com.synstorm.common.Utils.ConfigurationStrings.FilePathsStrings;
import com.synstorm.common.Utils.ConfigurationStrings.XmlQueryStrings;
import com.synstorm.common.Utils.Details.EvolutionDetails;
import com.synstorm.common.Utils.Details.IDetails;
import com.synstorm.common.Utils.EnumTypes.ScoreEnum;
import com.synstorm.common.Utils.EnumTypes.Sorting;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by human-research on 16/02/16.
 */
@Model_v0
@NonProductionLegacy

/**
 * This class' functionality will be replaced with FCC project - distributed computations system
 */

public class EvolutionLoader extends BaseLoader implements ILoader {
    //region Fields
    private Map<String, EvolutionDetails> evolutionDetailsMap;
    private Map<ScoreEnum, Sorting> scoreRules;
    private Map<ScoreEnum, Double> scoreWeights;
    //endregion

    //region Constructors
    public EvolutionLoader() {
        this.evolutionDetailsMap = new HashMap<>();
        this.scoreRules = new LinkedHashMap<>();
        this.scoreWeights = new HashMap<>();
    }
    //endregion

    //region Getters and Setters
    @Override
    @Nullable
    public IDetails getDetails(String key) {
        return evolutionDetailsMap.getOrDefault(key, null);
    }
    //endregion

    //region Public Methods
    @Override
    public void load(String configFileName, String templateFileName) {
        initialize(configFileName, templateFileName);
        Map<String, String> evolutionSettings = configReader.getAttributeValues(XmlQueryStrings.EVOLUTION_SETTINGS_ALL);
        Map<String, String> populationParameters = configReader.getAttributeValues(XmlQueryStrings.EVOLUTION_POPULATION_PARAMETERS_ALL);
        Map<String, String> mutationParameters = configReader.getAttributeValues(XmlQueryStrings.EVOLUTION_MUTATION_PARAMETERS_ALL);
        Map<String, String> recombinationParameters = configReader.getAttributeValues(XmlQueryStrings.EVOLUTION_RECOMBINATION_PARAMETERS_ALL);
        List<String> scoreRulesRows = configReader.getAttributesValueList(XmlQueryStrings.EVOLUTION_RECOMBINATION_SCORE_RULES_ALL);

        EvolutionDetails settingsDetails = new EvolutionDetails();
        EvolutionDetails populationDetails = new EvolutionDetails();
        EvolutionDetails mutationDetails = new EvolutionDetails();
        EvolutionDetails recombinationDetails = new EvolutionDetails();

        evolutionSettings.entrySet().stream()
                .forEach(item -> settingsDetails.addAttribute(item.getKey(), item.getValue()));
        populationParameters.entrySet().stream()
                .forEach(item -> populationDetails.addAttribute(item.getKey(), item.getValue()));
        mutationParameters.entrySet().stream()
                .forEach(item -> mutationDetails.addAttribute(item.getKey(), item.getValue()));
        recombinationParameters.entrySet().stream()
                .forEach(item -> recombinationDetails.addAttribute(item.getKey(), item.getValue()));

        evolutionDetailsMap.put(settingsDetails.getValue(XmlQueryStrings.WORD_TYPE_L), settingsDetails);
        evolutionDetailsMap.put(populationDetails.getValue(XmlQueryStrings.WORD_TYPE_L), populationDetails);
        evolutionDetailsMap.put(mutationDetails.getValue(XmlQueryStrings.WORD_TYPE_L), mutationDetails);
        evolutionDetailsMap.put(recombinationDetails.getValue(XmlQueryStrings.WORD_TYPE_L), recombinationDetails);

        scoreRulesRows.stream()
                .forEach(scoreRuleNum -> {
                    ScoreEnum scoreEnum = ScoreEnum.valueOf(configReader.getAttributeValue(XmlQueryStrings.EVOLUTION_RECOMBINATION_SCORE_RULE_BY_NUM
                            + scoreRuleNum + XmlQueryStrings.EVOLUTION_SCORE_ENUM));
                    Sorting sorting = Sorting.valueOf(configReader.getAttributeValue(XmlQueryStrings.EVOLUTION_RECOMBINATION_SCORE_RULE_BY_NUM
                            + scoreRuleNum + XmlQueryStrings.EVOLUTION_SCORE_SORTING_TYPE));
                    double weight = Double.parseDouble(configReader.getAttributeValue(XmlQueryStrings.EVOLUTION_RECOMBINATION_SCORE_RULE_BY_NUM
                            + scoreRuleNum + XmlQueryStrings.EVOLUTION_SCORE_WEIGHT));
                    scoreRules.put(scoreEnum, sorting);
                    scoreWeights.put(scoreEnum, weight);
                });
    }

    @Override
    public void save(String newConfigFilename) throws Exception {
        configReader = new ConfigReader();
        configReader.loadTemplate(FilePathsStrings.FILENAME_TEMPLATES_PATH + FilePathsStrings.FILENAME_EVOLUTION_TEMPLATE);

        EvolutionDetails evolutionSettings = evolutionDetailsMap.get(XmlQueryStrings.EVOLUTION_EVOLUTION_SETTINGS);
        EvolutionDetails populationParameters = evolutionDetailsMap.get(XmlQueryStrings.EVOLUTION_POPULATION_PARAMETERS);
        EvolutionDetails mutationParameters = evolutionDetailsMap.get(XmlQueryStrings.EVOLUTION_MUTATION_PARAMETERS);
        EvolutionDetails recombinationParameters = evolutionDetailsMap.get(XmlQueryStrings.EVOLUTION_RECOMBINATION_PARAMETERS);

        configReader.createEmptyRootNodeFromTemplate();
        ConfigNode rootNode = configReader.getRootNode();

        ConfigNode settingsNode = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(1).get(XmlQueryStrings.WORD_SETTINGS_U));
        settingsNode.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.EVOLUTION_EVOLUTION_SETTINGS);
        settingsNode.addAttribute(XmlQueryStrings.EVOLUTION_ENABLE_EVOLUTION, String.valueOf(evolutionSettings.getValue(XmlQueryStrings.EVOLUTION_ENABLE_EVOLUTION)));
        rootNode.addNode(settingsNode);

        ConfigNode populationNode = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(1).get(XmlQueryStrings.WORD_POPULATION_U));
        populationNode.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.EVOLUTION_POPULATION_PARAMETERS);
        populationNode.addAttribute(XmlQueryStrings.EVOLUTION_POPULATION_VOLUME, populationParameters.getValue(XmlQueryStrings.EVOLUTION_POPULATION_VOLUME));
        populationNode.addAttribute(XmlQueryStrings.EVOLUTION_MINIMAL_SCORETABLE_FOR_RECOMBINATION, populationParameters.getValue(XmlQueryStrings.EVOLUTION_MINIMAL_SCORETABLE_FOR_RECOMBINATION));
        populationNode.addAttribute(XmlQueryStrings.EVOLUTION_DEFAULT_BATCH_SIZE, populationParameters.getValue(XmlQueryStrings.EVOLUTION_DEFAULT_BATCH_SIZE));
        rootNode.addNode(populationNode);

        ConfigNode mutationNode = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(1).get(XmlQueryStrings.WORD_MUTATION_U));
        mutationNode.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.EVOLUTION_MUTATION_PARAMETERS);
        mutationNode.addAttribute(XmlQueryStrings.EVOLUTION_SPACE_AGGRESSION_INIT, mutationParameters.getValue(XmlQueryStrings.EVOLUTION_SPACE_AGGRESSION_INIT));
        mutationNode.addAttribute(XmlQueryStrings.EVOLUTION_SPACE_AGGRESSION_RECOMBINATION, mutationParameters.getValue(XmlQueryStrings.EVOLUTION_SPACE_AGGRESSION_RECOMBINATION));
        mutationNode.addAttribute(XmlQueryStrings.EVOLUTION_MUTATION_FORMULA, mutationParameters.getValue(XmlQueryStrings.EVOLUTION_MUTATION_FORMULA));
        rootNode.addNode(mutationNode);

        ConfigNode recombinationNode = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(1).get(XmlQueryStrings.WORD_RECOMBINATION_U));
        recombinationNode.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.EVOLUTION_RECOMBINATION_PARAMETERS);
        recombinationNode.addAttribute(XmlQueryStrings.EVOLUTION_RECOMBINATION_MUTATIONS,
                recombinationParameters.getValue(XmlQueryStrings.EVOLUTION_RECOMBINATION_MUTATIONS));

        ConfigNode scoreRulesContainer = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(2).get(XmlQueryStrings.WORD_CONTAINER_U));
        scoreRulesContainer.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.WORD_SCORE_RULES_U);

        int counter = 0;
        for (ScoreEnum scoreEnum : scoreRules.keySet()) {
            ConfigNode scoreRuleNode = configReader.createNodeFromTemplate
                    (configReader.getConfigTemplate().getNodes(3).get(XmlQueryStrings.WORD_SCORE_RULE_U));
            scoreRuleNode.addAttribute(XmlQueryStrings.WORD_NUM_L, String.valueOf(counter++));
            scoreRuleNode.addAttribute(XmlQueryStrings.WORD_SCORE_ENUM_L, scoreEnum.toString());
            scoreRuleNode.addAttribute(XmlQueryStrings.WORD_SORTING_TYPE_L, scoreRules.get(scoreEnum).toString());
            scoreRuleNode.addAttribute(XmlQueryStrings.WORD_WEIGHT_L, String.valueOf(scoreWeights.get(scoreEnum)));
            scoreRulesContainer.addNode(scoreRuleNode);
        }
        recombinationNode.addNode(scoreRulesContainer);
        rootNode.addNode(recombinationNode);

        configReader.writeConfig(newConfigFilename);
    }

    public void addScoreRule(ScoreEnum scoreEnum, Sorting sortingType, double weight) {
        scoreRules.put(scoreEnum, sortingType);
        scoreWeights.put(scoreEnum, weight);
    }

    public void removeScoreRule(ScoreEnum scoreEnum) {
        scoreRules.remove(scoreEnum);
        scoreWeights.remove(scoreEnum);
    }

    public void setScoreRuleSorting(ScoreEnum scoreEnum, Sorting sortingType) {
        scoreRules.put(scoreEnum, sortingType);
    }

    public void setScoreRuleWeight(ScoreEnum scoreEnum, double weight) {
        scoreWeights.put(scoreEnum, weight);
    }

    public Set<ScoreEnum> getScoreRulesSet() { return scoreRules.keySet(); }

    public Sorting getRuleSortingType(ScoreEnum scoreEnum) { return scoreRules.get(scoreEnum); }

    public double getRuleWeight(ScoreEnum scoreEnum) { return scoreWeights.get(scoreEnum); }

    public void initiateEmptyConfiguration() {
        if (evolutionDetailsMap.size() == 0) {
            EvolutionDetails settingsDetails = new EvolutionDetails();
            EvolutionDetails progenyDetails = new EvolutionDetails();
            EvolutionDetails mutationDetails = new EvolutionDetails();
            EvolutionDetails recombinationDetails = new EvolutionDetails();

            settingsDetails.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.EVOLUTION_EVOLUTION_SETTINGS);
            settingsDetails.addAttribute(XmlQueryStrings.EVOLUTION_ENABLE_EVOLUTION, "true");

            progenyDetails.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.EVOLUTION_PROGENY_PARAMETERS);
            progenyDetails.addAttribute(XmlQueryStrings.EVOLUTION_PROGENY_COUNT, "25");
            progenyDetails.addAttribute(XmlQueryStrings.EVOLUTION_INDIVIDUAL_COUNT_IN_PROGENY, "20");

            mutationDetails.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.EVOLUTION_MUTATION_PARAMETERS);
            mutationDetails.addAttribute(XmlQueryStrings.EVOLUTION_SPACE_AGGRESSION_INIT, "0.1");
            mutationDetails.addAttribute(XmlQueryStrings.EVOLUTION_MUTATION_FORMULA, "LinearFormula1");

            recombinationDetails.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.EVOLUTION_RECOMBINATION_PARAMETERS);
            recombinationDetails.addAttribute(XmlQueryStrings.EVOLUTION_TOP_SELECTION, "0.2");
            recombinationDetails.addAttribute(XmlQueryStrings.EVOLUTION_TOP_RECOMBINATION, "0.2");

            evolutionDetailsMap.put(settingsDetails.getValue(XmlQueryStrings.WORD_TYPE_L), settingsDetails);
            evolutionDetailsMap.put(progenyDetails.getValue(XmlQueryStrings.WORD_TYPE_L), progenyDetails);
            evolutionDetailsMap.put(mutationDetails.getValue(XmlQueryStrings.WORD_TYPE_L), mutationDetails);
            evolutionDetailsMap.put(recombinationDetails.getValue(XmlQueryStrings.WORD_TYPE_L), recombinationDetails);
        }
    }

    public Collection<EvolutionDetails> getAllEvolutionDetails() {
        return evolutionDetailsMap.values();
    }
    //endregion

    //region Private Methods
    //endregion
}