//package com.synstorm.SimulationModel.ModelProcessors;
//
//import com.synstorm.SimulationModel.Model.Individual;
//import com.synstorm.SimulationModel.ModelAgent.MultiIndividualAgent;
//import com.synstorm.SimulationModel.ModelAgent.IndividualAgent;
//import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
//import com.synstorm.common.Utils.Details.IndividualDetails;
//import com.synstorm.common.Utils.EnumTypes.ScoreEnum;
//import com.synstorm.common.Utils.FileModelExport.ScoreTableExporter;
//import com.synstorm.common.Utils.ModelExport.EmptyExporter;
//import com.synstorm.common.Utils.ModelExport.IModelDataExporter;
//import com.synstorm.common.Utils.SimulationEvents.ScoreTableEvents.ScoreTableFormRowEvent;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
///**
// * Class for processing multi agent simulation over learning individual.
// * Processing with with multiple individual configurations and one common seed for learning and individual each.
// * Created by dvbozhko on 30/03/2017.
// */
//public class MultiConfigurationIndividualProcessor extends MultiConfigurationProcessor {
//    //region Fields
//    private ScoreTableExporter scoreTableExporter;
//    //endregion
//
//    //region Constructors
//    public MultiConfigurationIndividualProcessor() {
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
//    protected void proceedAdditionalActionsBeforeFinish() {
//        scoreFormer.getScoreTable().getScoreTable().forEach(score -> {
//            IndividualDetails curIndividual = individualDetails.get(score.getIndividualID());
//            Map<ScoreEnum, Double> scoreMap = new LinkedHashMap<>();
//            ModelLoader.getScoreRulesSet().stream()
//                    .forEach(scoreEnum -> scoreMap.put(scoreEnum, score.getScoreParameters().get(scoreEnum)));
//
//            scoreTableExporter.write(new ScoreTableFormRowEvent(
//                    curIndividual,
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
//        return new Individual(individualDetails, new ArrayList<>(), statExporter, individualSeed);
//    }
//
//    @Override
//    protected IndividualAgent getNextAgent(IndividualDetails individualDetails) throws Exception {
//        Individual individual = formIndividualObject(individualDetails);
//        return new MultiIndividualAgent(individual, progressBar, this);
//    }
//
//    @Override
//    protected void initializeAdditional() {
//        scoreTableExporter.writeHeader(createStatisticHeader());
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
