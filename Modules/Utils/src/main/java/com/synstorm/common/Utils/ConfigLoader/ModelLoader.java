package com.synstorm.common.Utils.ConfigLoader;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.ConfigurationStrings.FilePathsStrings;
import com.synstorm.common.Utils.ConfigurationStrings.XmlQueryStrings;
import com.synstorm.common.Utils.Details.*;
import com.synstorm.common.Utils.EnumTypes.*;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Dmitry.Bozhko on 3/25/2015.
 */

@Model_v0
@NonProductionLegacy

public class ModelLoader {
    //region Fields
    private static ILoader sysLoader;
    private static ILoader seedLoader;
    private static ILoader ntLoader;
    private static ILoader aLoader;
    private static ILoader cLoader;
    private static ILoader indLoader;
    private static ILoader spaceLoader;
    private static ILoader evolutionLoader;

    private static IndividualDetails individualDetails;
    //endregion

    //region Constructors
    static {
        sysLoader = new SystemLoader();
        indLoader = new IndividualLoader();
        seedLoader = new SeedsLoader();
        ntLoader = new NTLoader();
        aLoader = new ActionsLoader();
        cLoader = new CellsLoader();
        spaceLoader = new SpaceLoader();
        evolutionLoader = new EvolutionLoader();

        individualDetails = null;
    }

    public static void load(String path) {
        spaceLoader.load(path + FilePathsStrings.FILENAME_SPACE_CONFIG,
                FilePathsStrings.FILENAME_TEMPLATES_PATH + FilePathsStrings.FILENAME_SPACE_TEMPLATE);
        sysLoader.load(path + FilePathsStrings.FILENAME_SYSTEM_CONFIG,
                FilePathsStrings.FILENAME_TEMPLATES_PATH + FilePathsStrings.FILENAME_SYSTEM_TEMPLATE);
        seedLoader.load(path + FilePathsStrings.FILENAME_SEEDS_CONFIG,
                FilePathsStrings.FILENAME_TEMPLATES_PATH + FilePathsStrings.FILENAME_SEEDS_TEMPLATE);
        evolutionLoader.load(path + FilePathsStrings.FILENAME_EVOLUTION_CONFIG,
                FilePathsStrings.FILENAME_TEMPLATES_PATH + FilePathsStrings.FILENAME_EVOLUTION_TEMPLATE);

        ntLoader.load(path + FilePathsStrings.FILENAME_NEUROTRANSMITTER_CONFIG,
                FilePathsStrings.FILENAME_TEMPLATES_PATH + FilePathsStrings.FILENAME_NEUROTRANSMITTER_TEMPLATE);
        aLoader.load(path + FilePathsStrings.FILENAME_ACTIONS_CONFIG,
                FilePathsStrings.FILENAME_TEMPLATES_PATH + FilePathsStrings.FILENAME_ACTIONS_TEMPLATE);
        cLoader.load(path + FilePathsStrings.FILENAME_CELLS_CONFIG,
                FilePathsStrings.FILENAME_TEMPLATES_PATH + FilePathsStrings.FILENAME_CELLS_TEMPLATE);

        indLoader.load(path + FilePathsStrings.FILENAME_INDIVIDUAL_CONFIG,
                FilePathsStrings.FILENAME_TEMPLATES_PATH + FilePathsStrings.FILENAME_INDIVIDUAL_TEMPLATE);
    }

    public static void load(String path, int seedNum) {
        seedLoader = new SeedsLoader(seedNum);
        load(path);
    }

    public static Map<UUID, IndividualDetails> loadAdditionalIndividuals(String path) {
        final List<IndividualDetails> result = new ArrayList<>();
//        result.add((IndividualDetails) indLoader.getDetails(""));
        final IndividualLoader loader = new IndividualLoader();
        final String templatePath = FilePathsStrings.FILENAME_TEMPLATES_PATH + FilePathsStrings.FILENAME_INDIVIDUAL_TEMPLATE;
        final File[] listOfFiles = new File(path).listFiles();
        for (File file : listOfFiles) {
            if (file.getName().startsWith("Individual")) {
                loader.load(file.getAbsolutePath(), templatePath);
                result.add((IndividualDetails) loader.getDetails(""));
            }
        }

        return result.stream()
                .collect(Collectors.toMap(IndividualDetails::getUuid, details -> details));
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    public static int getNTScale() {
        return Integer.parseInt(((SystemDetails) sysLoader.getDetails(XmlQueryStrings.SYSTEM_NT_SCALE_PARAMETERS))
                .getValue(XmlQueryStrings.WORD_SCALE_L));
    }

    public static int getNTScalePosition() {
        return Integer.parseInt(((SystemDetails) sysLoader.getDetails(XmlQueryStrings.SYSTEM_NT_SCALE_PARAMETERS))
                .getValue(XmlQueryStrings.SYSTEM_NT_SCALE_POSITION));
    }

    public static int getReceptorsScalePosition() {
        return Integer.parseInt(((SystemDetails) sysLoader.getDetails(XmlQueryStrings.SYSTEM_NT_SCALE_PARAMETERS))
                .getValue(XmlQueryStrings.SYSTEM_RECEPTORS_SCALE_POSITION));
    }

    public static int getMaximumNTAndReceptors() {
        return Integer.parseInt(((SystemDetails) sysLoader.getDetails(XmlQueryStrings.SYSTEM_NT_SCALE_PARAMETERS))
                .getValue(XmlQueryStrings.SYSTEM_NT_MAXNT_AND_RECEPTORS));
    }

    public static int getClassCount() {
        return Integer.parseInt(((SystemDetails) sysLoader.getDetails(XmlQueryStrings.SYSTEM_TRAINING_PARAMETERS))
                .getValue(XmlQueryStrings.SYSTEM_TRAINING_CLASS_COUNT));
    }

    public static int getLQWindowSize() {
        return Integer.parseInt(((SystemDetails) sysLoader.getDetails(XmlQueryStrings.SYSTEM_TRAINING_PARAMETERS))
                .getValue(XmlQueryStrings.SYSTEM_TRAINING_LQ_WINDOW_SIZE));
    }

    public static int getClusteringSeedCount() {
        return Integer.parseInt(((SystemDetails) sysLoader.getDetails(XmlQueryStrings.SYSTEM_TRAINING_PARAMETERS))
                .getValue(XmlQueryStrings.SYSTEM_TRAINING_CLUSTERING_SEED_COUNT));
    }

    public static long getDimensionalSpaceCapacity() throws Exception {
        return ((SpaceLoader) spaceLoader).getSpaceVolume();
    }

    public static List<GPDetails> getGPsList() {
        return ((SpaceLoader) spaceLoader).getGPsList();
    }

    public static long getMaximumTicks() {
        return Long.parseLong(((SystemLoader)sysLoader).getCalculationConditionValue(StatisticType.MaxTick));
        /*return Long.parseLong(((SystemDetails) sysLoader.getDetails(XmlQueryStrings.SYSTEM_MODEL_PARAMETERS))
                .getValue(XmlQueryStrings.WORD_TICKS_L));*/
    }

    public static Set<StatisticType> getAllCalculationConditions() {
        return ((SystemLoader)sysLoader).getAllCalculationConditions();
    }

    public static String getCalculationConditionValue(StatisticType condition) {
        return ((SystemLoader)sysLoader).getCalculationConditionValue(condition);
    }

    public static void setMaximumTicks(long maximumTicks) {
        ((SystemDetails) sysLoader.getDetails(XmlQueryStrings.SYSTEM_MODEL_PARAMETERS))
                .addAttribute(XmlQueryStrings.WORD_TICKS_L, String.valueOf(maximumTicks));
    }

    public static int getSeedsCount() {
        return ((SeedsLoader) seedLoader).getSeedsCount();
    }

    public static byte[] getDefaultSeed() {
        return ((SeedsLoader) seedLoader).getDefaultSeed();
    }

    public static byte[] getSeed(int num) {
        return ((SeedsLoader) seedLoader).getSeed(num);
    }

    public static String getStringDefaultSeed() {
        return ((SeedsLoader) seedLoader).getStringDefaultSeed();
    }

    public static void nextSeed() {
        ((SeedsLoader) seedLoader).nextSeed();
    }

    public static ModelWritingMode getWritingMode() {
        return ModelWritingMode.valueOf(((SystemDetails) sysLoader.getDetails(XmlQueryStrings.SYSTEM_EXPORT_PARAMETERS))
                .getValue(XmlQueryStrings.SYSTEM_EXPORT_MODE));
    }

    public static int getIndividualCheckpointQuantum() {
        int result = Integer.parseInt(((SystemDetails) sysLoader.getDetails(XmlQueryStrings.SYSTEM_EXPORT_PARAMETERS))
                .getValue(XmlQueryStrings.SYSTEM_EXPORT_INDIVIDUAL_CHECKPOINT_QUANTUM));
        if (result < 1)
            result = 1;
        else if (result > 100)
            result = 100;

        return result;
    }

    public static String getExportHost() {
        return (((SystemDetails) sysLoader.getDetails(XmlQueryStrings.SYSTEM_EXPORT_PARAMETERS))
                .getValue(XmlQueryStrings.SYSTEM_EXPORT_DBHOST));
    }

    public static String getExportPort() {
        return (((SystemDetails) sysLoader.getDetails(XmlQueryStrings.SYSTEM_EXPORT_PARAMETERS))
                .getValue(XmlQueryStrings.SYSTEM_EXPORT_DBPORT));
    }

    public static String getExportUsername() {
        return (((SystemDetails) sysLoader.getDetails(XmlQueryStrings.SYSTEM_EXPORT_PARAMETERS))
                .getValue(XmlQueryStrings.SYSTEM_EXPORT_DBUSER));
    }

    public static String getExportPassword() {
        return (((SystemDetails) sysLoader.getDetails(XmlQueryStrings.SYSTEM_EXPORT_PARAMETERS))
                .getValue(XmlQueryStrings.SYSTEM_EXPORT_DBPASSWORD));
    }

    public static TrainingDetails getTrainingDetails(TeacherMode teacherMode) {
        return ((SystemLoader)sysLoader).getTrainingDetail(teacherMode);
    }

    public static CellDetails getCellDetails(String cellType) {
        return (CellDetails) cLoader.getDetails(cellType);
    }

    public static Set<String> getAllActions() {
        return ((ActionsLoader) aLoader).getAllActions();
    }

    public static Collection<ActionDetails> getAllActionDetails() {
        return ((ActionsLoader) aLoader).getAllActionDetails();
    }

    public static void printIndividualActionDurations(List<UUID> ids) {
        ((ActionsLoader) aLoader).printIndividualActionDurations(ids);
    }

    public static ActionDetails getActionDetails(String actionName) {
        return (ActionDetails) aLoader.getDetails(actionName);
    }

    public static boolean isEvolutionEnabled() {
        return Boolean.parseBoolean(((EvolutionDetails) evolutionLoader.getDetails(XmlQueryStrings.EVOLUTION_EVOLUTION_SETTINGS))
                .getValue(XmlQueryStrings.EVOLUTION_ENABLE_EVOLUTION));
    }

    public static MutationFormulas getMutationFormula() {
        return MutationFormulas.valueOf(((EvolutionDetails) evolutionLoader.getDetails(XmlQueryStrings.EVOLUTION_MUTATION_PARAMETERS))
                .getValue(XmlQueryStrings.EVOLUTION_MUTATION_FORMULA));
    }

    public static double getSpaceAggressionInit() {
        return Double.parseDouble(((EvolutionDetails) evolutionLoader.getDetails(XmlQueryStrings.EVOLUTION_MUTATION_PARAMETERS))
                .getValue(XmlQueryStrings.EVOLUTION_SPACE_AGGRESSION_INIT));
    }

    public static void setSpaceAggressionInit(double spaceAggression) {
        ((EvolutionDetails) evolutionLoader.getDetails(XmlQueryStrings.EVOLUTION_MUTATION_PARAMETERS))
                .addAttribute(XmlQueryStrings.EVOLUTION_SPACE_AGGRESSION_INIT, String.valueOf(spaceAggression));
    }

    public static double getSpaceAggressionRecombination() {
        return Double.parseDouble(((EvolutionDetails) evolutionLoader.getDetails(XmlQueryStrings.EVOLUTION_MUTATION_PARAMETERS))
                .getValue(XmlQueryStrings.EVOLUTION_SPACE_AGGRESSION_RECOMBINATION));
    }

    public static Set<ScoreEnum> getScoreRulesSet() {
        return ((EvolutionLoader) evolutionLoader).getScoreRulesSet();
    }

    public static Sorting getScoreRuleSortingType(ScoreEnum scoreEnum) {
        return ((EvolutionLoader) evolutionLoader).getRuleSortingType(scoreEnum);
    }

    public static double getScoreRuleWeight(ScoreEnum scoreEnum) {
        return ((EvolutionLoader) evolutionLoader).getRuleWeight(scoreEnum);
    }

    public static int getPopulationVolume() {
        return Integer.parseInt(((EvolutionDetails)evolutionLoader.getDetails(XmlQueryStrings.EVOLUTION_POPULATION_PARAMETERS))
                .getValue(XmlQueryStrings.EVOLUTION_POPULATION_VOLUME));
    }

    public static double getScoreTableForRecombinationCoefficient() {
        return Double.parseDouble(((EvolutionDetails)evolutionLoader.getDetails(XmlQueryStrings.EVOLUTION_POPULATION_PARAMETERS))
                .getValue(XmlQueryStrings.EVOLUTION_MINIMAL_SCORETABLE_FOR_RECOMBINATION));
    }

    public static int getDefaultBatchSize() {
        return Integer.parseInt(((EvolutionDetails)evolutionLoader.getDetails(XmlQueryStrings.EVOLUTION_POPULATION_PARAMETERS))
                .getValue(XmlQueryStrings.EVOLUTION_DEFAULT_BATCH_SIZE));
    }

    public static void resetActionDurationsByGenes() {
        ((ActionsLoader) aLoader).getAllActions().stream()
                .forEach(action -> ((ActionDetails) aLoader.getDetails(action)).clearDurationByGenes());
    }

    public static IndividualDetails getIndividualDetails() {
        if (individualDetails == null)
            return (IndividualDetails) indLoader.getDetails("");
        else
            return individualDetails;
    }

    public static void setIndividualDetails(IndividualDetails indDetails) {
        individualDetails = indDetails;
    }

    public static Map<String, Integer> getCellTypes() {
        return ((CellsLoader) cLoader).getCellTypes();
    }

    public static List<String> getNeurotransmitters() {
        return ((NTLoader) ntLoader).getNeurotransmittersList();
    }

    public static Set<String> getAllFactors() {
        return ((ActionsLoader) aLoader).getAllFactors();
    }

    public static double getNTReleaseThreshold(String neurotransmitter) {
        return ((NeurotransmitterDetails) ntLoader.getDetails(neurotransmitter)).getReleaseThreshold();
    }

    public static NeurotransmitterType getNTType(String neurotransmitter) {
        return ((NeurotransmitterDetails) ntLoader.getDetails(neurotransmitter)).getType();
    }

    public static String getReceptorType(String cellType, String sourceCellType) {
        return ((NTLoader) ntLoader).getReceptorType(
                getCellDetails(cellType).getDendriteDetails().getDendriteReceptors(), sourceCellType);
    }

    public static boolean getEvolutionRecombinationMutations() {
        return Boolean.parseBoolean(((EvolutionDetails) evolutionLoader.getDetails(XmlQueryStrings.EVOLUTION_RECOMBINATION_PARAMETERS))
                .getValue(XmlQueryStrings.EVOLUTION_RECOMBINATION_MUTATIONS));
    }

    public static boolean isRecombinationEnabled() {
        return Boolean.parseBoolean(((EvolutionDetails) evolutionLoader.getDetails(XmlQueryStrings.EVOLUTION_RECOMBINATION_PARAMETERS))
                .getValue(XmlQueryStrings.EVOLUTION_ENABLE_RECOMBINATION));
    }

    public static void saveIndividualDetails(String newConfigFilename, IndividualDetails details) throws Exception {
        ((IndividualLoader) indLoader).save(newConfigFilename, details);
    }
    //endregion

    //region Private Methods
    //endregion
}
