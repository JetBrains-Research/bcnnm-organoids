//package com.synstorm.SimulationModel.ModelProcessors;
//
//import com.synstorm.SimulationModel.Model.Individual;
//import com.synstorm.SimulationModel.ModelAgent.MultiIndividualAgent;
//import com.synstorm.SimulationModel.ModelAgent.IndividualAgent;
//import com.synstorm.SimulationModel.ModelProcessors.IndividualHolders.IndividualHolder;
//import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
//import com.synstorm.common.Utils.Details.IndividualDetails;
//import com.synstorm.common.Utils.EnumTypes.ScoreEnum;
//import com.synstorm.common.Utils.FileModelExport.ScoreTableExporter;
//import com.synstorm.common.Utils.ModelExport.EmptyExporter;
//import com.synstorm.common.Utils.ModelExport.IModelDataExporter;
//import com.synstorm.common.Utils.SimArgs.SimulationArguments;
//import com.synstorm.common.Utils.SimulationEvents.ScoreTableEvents.ScoreTableFormRowEvent;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.UUID;
//
///**
// * Class for processing multi agent simulation over non-learning fixed tick simulation of one individual.
// * Processing with common individual configuration and multiple seeds for individual.
// * Created by dvbozhko on 30/03/2017.
// */
//public class MultiSeedIndividualProcessor extends MultiSeedProcessor {
//    //region Fields
//    private ScoreTableExporter scoreTableExporter;
//    //endregion
//
//    //region Constructors
//    public MultiSeedIndividualProcessor() {
//        super();
//    }
//    //endregion
//
//    //region Getters and Setters
//    //endregion
//
//    //region Public Methods
//    //endregion
//
//    //region Package-local Methods
//    //endregion
//
//    //region Protected Methods
//    @Override
//    protected IndividualAgent getNextAgent(IndividualDetails individualDetails) throws Exception {
//        IndividualHolder individualHolder = makeIndividualHolderObject(individualDetails);
//        Individual individual = formIndividualObject(individualHolder.getIndividualDetails());
//        return new MultiIndividualAgent(individual, progressBar, this);
//    }
//
//    @Override
//    protected void proceedAdditionalActionsBeforeFinish() {
//        scoreFormer.getScoreTable().getScoreTable().stream().forEach(score -> {
//            final UUID individualId = score.getIndividualID();
//            IndividualHolder individualHolder = individualHolderMap.get(individualId);
//            Map<ScoreEnum, Double> scoreMap = new LinkedHashMap<>();
//            byte[] individualSeed = individualHolder.getIndividualSeed();
//            ModelLoader.getScoreRulesSet().stream()
//                    .forEach(scoreEnum -> scoreMap.put(scoreEnum, score.getScoreParameters().get(scoreEnum)));
//
//            scoreTableExporter.write(new ScoreTableFormRowEvent(
//                    individualHolder.getIndividualDetails(),
//                    individualSeed,
//                    scoreMap,
//                    score.getRating()
//            ));
//        });
//
//        scoreFormer.printScoreTable(true, 10);
//    }
//
//    @Override
//    protected Individual formIndividualObject(IndividualDetails individualDetails) throws Exception {
//        IModelDataExporter statExporter = new EmptyExporter();
//        statExporter.open(fullExperimentPrefix + individualDetails.getUuid().toString());
//        return new Individual(individualDetails, new ArrayList<>(), statExporter,
//                individualHolderMap.get(individualDetails.getUuid()).getIndividualSeed());
//    }
//
//    protected void initializeAdditional() {
//        scoreTableExporter.writeHeader(createStatisticHeader());
//    }
//
//    @NotNull
//    protected IndividualHolder makeIndividualHolderObject(IndividualDetails individualDetails) {
//        final UUID nextUUID = individualDetails.getUuid();
//
//        byte[] individualSeed;
//        if (SimulationArguments.INSTANCE.isRandomIndividualSeeds())
//            individualSeed = generateSeed();
//        else
//            individualSeed = ModelLoader.getDefaultSeed();
//
//        final IndividualHolder result = new IndividualHolder(nextUUID, individualDetails, individualSeed);
//        individualHolderMap.put(nextUUID, result);
//        return result;
//    }
//    //endregion
//
//    //region Private Methods
//    @NotNull
//    private String createStatisticHeader() {
//        StringBuilder header = new StringBuilder();
//        ModelLoader.getScoreRulesSet().stream()
//                .forEach(scoreEnum -> header.append(scoreEnum.toString()).append(","));
//        ModelLoader.getIndividualDetails().getIndividualGeneNames().stream()
//                .forEach(geneName -> header.append(geneName).append(","));
//        return header.toString();
//    }
//    //endregion
//}
