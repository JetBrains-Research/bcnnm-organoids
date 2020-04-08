package com.synstorm.SimulationModel.ModelProcessors;

import com.synstorm.SimulationModel.ConfigurationVerifier.CVerifier;
import com.synstorm.SimulationModel.Containers.ModelContainer;
import com.synstorm.SimulationModel.Model.Pipeline.ModelPipeline;
import com.synstorm.SimulationModel.ModelAgent.IndividualAgent;
import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
import com.synstorm.common.Utils.ConsoleProgressBar.MultiIndividualProgressBar;
import com.synstorm.common.Utils.Details.IndividualDetails;
import com.synstorm.common.Utils.EvolutionUtils.Gene.Gene;
import com.synstorm.common.Utils.EvolutionUtils.Score.ScoreFormer;
import com.synstorm.common.Utils.EvolutionUtils.Score.ScoreTable;
import com.synstorm.common.Utils.FileModelExport.CsvGeneMappingExporter;
import com.synstorm.common.Utils.ModelStatistic.IndividualStatistics;
import com.synstorm.common.Utils.SimulationEvents.CommonEvents.ActionToGenesMapEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Base abstract class for common behaviour of learning and non-learning
 * individual processor for for multiple agent simulation.
 * Created by dvbozhko on 29/03/2017.
 */
public abstract class MultiAgentProcessor extends BaseModelProcessor {
    //region Fields
    protected final ScoreFormer scoreFormer;
    //endregion

    //region Constructors
    public MultiAgentProcessor() {
        super();
        progressBar = new MultiIndividualProgressBar(experimentName, ModelPipeline.INSTANCE.getStagesLength(), tStart);
        scoreFormer = new ScoreFormer();
        populationVolume = ModelLoader.getPopulationVolume();

        ModelLoader.getScoreRulesSet().stream()
                .forEach(scoreEnum ->
                        ScoreTable.addScoreRule(scoreEnum, ModelLoader.getScoreRuleSortingType(scoreEnum), ModelLoader.getScoreRuleWeight(scoreEnum)));
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    @Override
    protected int calculatePopulationVolume() {
        return ModelLoader.getPopulationVolume();
    }

    @Override
    protected int calculateAvailableProcessors() {
        final int coreCount = super.calculateAvailableProcessors();
        return coreCount == populationVolume ? coreCount : coreCount - 1;
    }

    @Override
    protected boolean startCalculations() throws Exception {
        if (!CVerifier.INSTANCE.isConfigConsistent())
            return false;

        initializeAdditional();
        exportActionGenesMap();
        progressBar.showEmptyBar();

        int individualsToComputeCounter = 0;
        while (individualsToComputeCounter != availableProcessors) {
            submitIndividualAgent(getNextAgent(getNextIndividualToCompute()));
            individualsToComputeCounter++;
        }
        return true;
    }

    @Override
    protected synchronized void proceedIndividualResult(IndividualStatistics statistics) throws Exception {
        ModelContainer.INSTANCE.removeAdditionalElement(statistics.getIndividualId());
        scoreFormer.addScore(scoreFormer.formScore(statistics));

        updateModelStatus();
        submitNextIndividual();
    }

    protected abstract IndividualAgent getNextAgent(IndividualDetails individualDetails) throws Exception;

    protected abstract void initializeAdditional();
    //endregion

    //region Private Methods
    private void updateModelStatus() {
        calculatedAgentsCount++;
        int percentage = (int) ((calculatedAgentsCount / (double) populationVolume) * 100);
        progressBar.updatePercentage(percentage, calculatedAgentsCount);
    }

    private void submitNextIndividual() throws Exception {
        if (calculatedAgentsCount != populationVolume) {
            IndividualDetails iDetails = getNextIndividualToCompute();
            if (iDetails != null)
                submitIndividualAgent(getNextAgent(iDetails));
        }
    }

    private void exportActionGenesMap() {
        final IndividualDetails individual = ModelLoader.getIndividualDetails();
        final List<Gene> genes = individual.getIndividualGenes();
        final Set<String> actions = ModelLoader.getAllActions();
        final CsvGeneMappingExporter geneMappingExporter = new CsvGeneMappingExporter();

        geneMappingExporter.open(fullExperimentPrefix);
        geneMappingExporter.writeHeader(actions.stream().collect(Collectors.joining(",")));
        genes.forEach(gene -> {
            final Map<String, Integer> actionPresentationMap = gene.getActionPresentationMap();
            final List<Integer> genePresentation = new ArrayList<>();
            actions.forEach(action -> {
                final int isPresent = actionPresentationMap.get(action);
                genePresentation.add(isPresent);
            });

            geneMappingExporter.write(new ActionToGenesMapEvent(gene.getName(), genePresentation));
        });
        geneMappingExporter.close();
    }
    //endregion
}
