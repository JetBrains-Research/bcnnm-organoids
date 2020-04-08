package com.synstorm.common.Utils.ConfigLoader;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.ConfigWorker.ConfigNode;
import com.synstorm.common.Utils.ConfigWorker.ConfigReader;
import com.synstorm.common.Utils.ConfigurationStrings.FilePathsStrings;
import com.synstorm.common.Utils.ConfigurationStrings.XmlQueryStrings;
import com.synstorm.common.Utils.Details.CalculationConditionsDetails;
import com.synstorm.common.Utils.Details.IDetails;
import com.synstorm.common.Utils.Details.SystemDetails;
import com.synstorm.common.Utils.Details.TrainingDetails;
import com.synstorm.common.Utils.EnumTypes.StatisticType;
import com.synstorm.common.Utils.EnumTypes.TeacherMode;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by Dmitry.Bozhko on 5/27/2015.
 */

@Model_v0
@NonProductionLegacy

public class SystemLoader extends BaseLoader implements ILoader {
    //region Fields
    private Map<String, SystemDetails> systemDetailsMap;
    private Map<TeacherMode, TrainingDetails> trainingDetailsMap;
    private CalculationConditionsDetails calculationConditionsDetails;
    //endregion

    //region Constructors
    public SystemLoader() {
        systemDetailsMap = new HashMap<>();
        trainingDetailsMap = new HashMap<>();
        calculationConditionsDetails = new CalculationConditionsDetails();
    }
    //endregion

    //region Getters and Setters
    @Override
    @Nullable
    public IDetails getDetails(String key) {
        return systemDetailsMap.getOrDefault(key, null);
    }

    @Nullable
    public TrainingDetails getTrainingDetail(TeacherMode mode) {
        return trainingDetailsMap.getOrDefault(mode, null);
    }

    public Set<StatisticType> getAllCalculationConditions() {
        return calculationConditionsDetails.getAllConditions();
    }

    public String getCalculationConditionValue(StatisticType condition) {
        return calculationConditionsDetails.getValue(condition);
    }
    //endregion

    //region Public Methods
    @Override
    public void load(String configFileName, String templateFileName) {
        initialize(configFileName, templateFileName);
        Map<String, String> modelAttributes = configReader.getAttributeValues(XmlQueryStrings.SYSTEM_MODELPARAMETERS_ALL);
        Map<String, String> ntScaleAttributes = configReader.getAttributeValues(XmlQueryStrings.SYSTEM_NTSCALE_PARAMETERS_ALL);
        Map<String, String> exportAttributes = configReader.getAttributeValues(XmlQueryStrings.SYSTEM_EXPORT_PARAMETERS_ALL);
        Map<String, String> trainingAttributes = configReader.getAttributeValues(XmlQueryStrings.SYSTEM_TRAINING_PARAMETERS_ALL);
        List<String> datasets = configReader.getAttributesValueList(XmlQueryStrings.SYSTEM_TRAINING_DATASETS_ALL);
        List<String> calculationConditions = configReader.getAttributesValueList(XmlQueryStrings.SYSTEM_CALCULATION_CONDITIONS_ALL);

        SystemDetails modelDetails = new SystemDetails();
        SystemDetails ntScaleDetails = new SystemDetails();
        SystemDetails exportDetails = new SystemDetails();
        SystemDetails trainingDetails = new SystemDetails();

        modelAttributes.forEach(modelDetails::addAttribute);
        ntScaleAttributes.forEach(ntScaleDetails::addAttribute);
        exportAttributes.forEach(exportDetails::addAttribute);
        trainingAttributes.forEach(trainingDetails::addAttribute);


        systemDetailsMap.put(modelDetails.getValue(XmlQueryStrings.WORD_TYPE_L), modelDetails);
        systemDetailsMap.put(ntScaleDetails.getValue(XmlQueryStrings.WORD_TYPE_L), ntScaleDetails);
        systemDetailsMap.put(exportDetails.getValue(XmlQueryStrings.WORD_TYPE_L), exportDetails);
        systemDetailsMap.put(trainingDetails.getValue(XmlQueryStrings.WORD_TYPE_L), trainingDetails);

        datasets.forEach(datasetType -> {
            String setFileName = configReader.getAttributeValue(XmlQueryStrings.SYSTEM_TRAINING_DATASETS_BY_TYPE
                    + datasetType + XmlQueryStrings.SYSTEM_TRAINING_DATASET_SET_FILENAME);
            String setSize = configReader.getAttributeValue(XmlQueryStrings.SYSTEM_TRAINING_DATASETS_BY_TYPE
                    + datasetType + XmlQueryStrings.SYSTEM_TRAINING_DATASET_SET_SIZE);
            TeacherMode mode = TeacherMode.valueOf(datasetType);
            trainingDetailsMap.put(mode, new TrainingDetails(mode, setFileName, setSize));
        });

        calculationConditions.forEach(condition -> {
            String value = configReader.getAttributeValue(XmlQueryStrings.SYSTEM_CALCULATION_CONDITION_BY_TYPE
                    + condition + XmlQueryStrings.SYSTEM_CALCULATION_CONDITION_VALUE);
            calculationConditionsDetails.addCondition(StatisticType.valueOf(condition), value);
        });
    }

    @Override
    public void save(String newConfigFilename) throws Exception {
        configReader = new ConfigReader();
        configReader.loadTemplate(FilePathsStrings.FILENAME_TEMPLATES_PATH + FilePathsStrings.FILENAME_SYSTEM_TEMPLATE);

        SystemDetails modelDetails = systemDetailsMap.get(XmlQueryStrings.SYSTEM_MODEL_PARAMETERS);
        SystemDetails ntScaleDetails = systemDetailsMap.get(XmlQueryStrings.SYSTEM_NT_SCALE_PARAMETERS);
        SystemDetails exportDetails = systemDetailsMap.get(XmlQueryStrings.SYSTEM_EXPORT_PARAMETERS);
        SystemDetails trainingDetails = systemDetailsMap.get(XmlQueryStrings.SYSTEM_TRAINING_PARAMETERS);

        //create rootNode
        configReader.createEmptyRootNodeFromTemplate();
        ConfigNode rootNode = configReader.getRootNode();

        ConfigNode modelNode = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(1).get(XmlQueryStrings.WORD_MODEL_U));
        modelNode.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.SYSTEM_MODEL_PARAMETERS);
        modelNode.addAttribute(XmlQueryStrings.WORD_DATE_L, modelDetails.getValue(XmlQueryStrings.WORD_DATE_L));
        modelNode.addAttribute(XmlQueryStrings.WORD_USER_L, modelDetails.getValue(XmlQueryStrings.WORD_USER_L));
        modelNode.addAttribute(XmlQueryStrings.WORD_VERSION_L, modelDetails.getValue(XmlQueryStrings.WORD_VERSION_L));
        modelNode.addAttribute(XmlQueryStrings.WORD_WORKSTATION_L, modelDetails.getValue(XmlQueryStrings.WORD_WORKSTATION_L));
        rootNode.addNode(modelNode);

        ConfigNode ntScaleNode = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(1).get(XmlQueryStrings.WORD_NTSCALE_U));
        ntScaleNode.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.SYSTEM_NT_SCALE_PARAMETERS);
        ntScaleNode.addAttribute(XmlQueryStrings.WORD_SCALE_L, ntScaleDetails.getValue(XmlQueryStrings.WORD_SCALE_L));
        ntScaleNode.addAttribute(XmlQueryStrings.SYSTEM_NT_SCALE_POSITION, ntScaleDetails.getValue(XmlQueryStrings.SYSTEM_NT_SCALE_POSITION));
        ntScaleNode.addAttribute(XmlQueryStrings.SYSTEM_RECEPTORS_SCALE_POSITION, ntScaleDetails.getValue(XmlQueryStrings.SYSTEM_RECEPTORS_SCALE_POSITION));
        ntScaleNode.addAttribute(XmlQueryStrings.SYSTEM_NT_MAXNT_AND_RECEPTORS, ntScaleDetails.getValue(XmlQueryStrings.SYSTEM_NT_MAXNT_AND_RECEPTORS));
        rootNode.addNode(ntScaleNode);

        ConfigNode exportNode = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(1).get(XmlQueryStrings.WORD_EXPORT_U));
        exportNode.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.SYSTEM_EXPORT_PARAMETERS);
        exportNode.addAttribute(XmlQueryStrings.SYSTEM_EXPORT_DBHOST, exportDetails.getValue(XmlQueryStrings.SYSTEM_EXPORT_DBHOST));
        exportNode.addAttribute(XmlQueryStrings.SYSTEM_EXPORT_DBPORT, exportDetails.getValue(XmlQueryStrings.SYSTEM_EXPORT_DBPORT));
        exportNode.addAttribute(XmlQueryStrings.SYSTEM_EXPORT_DBUSER, exportDetails.getValue(XmlQueryStrings.SYSTEM_EXPORT_DBUSER));
        exportNode.addAttribute(XmlQueryStrings.SYSTEM_EXPORT_DBPASSWORD, exportDetails.getValue(XmlQueryStrings.SYSTEM_EXPORT_DBPASSWORD));
        exportNode.addAttribute(XmlQueryStrings.SYSTEM_EXPORT_MODE, exportDetails.getValue(XmlQueryStrings.SYSTEM_EXPORT_MODE));
        exportNode.addAttribute(XmlQueryStrings.SYSTEM_EXPORT_INDIVIDUAL_CHECKPOINT_QUANTUM, exportDetails.getValue(XmlQueryStrings.SYSTEM_EXPORT_INDIVIDUAL_CHECKPOINT_QUANTUM));
        rootNode.addNode(exportNode);

        ConfigNode trainingNode = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(1).get(XmlQueryStrings.WORD_TRAINING_U));
        trainingNode.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.SYSTEM_TRAINING_PARAMETERS);
        trainingNode.addAttribute(XmlQueryStrings.SYSTEM_TRAINING_CLASS_COUNT, trainingDetails.getValue(XmlQueryStrings.SYSTEM_TRAINING_CLASS_COUNT));
        trainingNode.addAttribute(XmlQueryStrings.SYSTEM_TRAINING_LQ_WINDOW_SIZE, trainingDetails.getValue(XmlQueryStrings.SYSTEM_TRAINING_LQ_WINDOW_SIZE));
        trainingNode.addAttribute(XmlQueryStrings.SYSTEM_TRAINING_CLUSTERING_SEED_COUNT, trainingDetails.getValue(XmlQueryStrings.SYSTEM_TRAINING_CLUSTERING_SEED_COUNT));
        ConfigNode datasetsContainer = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(2).get(XmlQueryStrings.WORD_CONTAINER_U));
        datasetsContainer.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.WORD_DATASETS_U);
        trainingDetailsMap.values().forEach(v -> {
            ConfigNode datasetNode = configReader.createNodeFromTemplate
                    (configReader.getConfigTemplate().getNodes(3).get(XmlQueryStrings.WORD_DATASET_U));
            datasetNode.addAttribute(XmlQueryStrings.WORD_DATASET_TYPE_L, String.valueOf(v.getTeacherMode()));
            datasetNode.addAttribute(XmlQueryStrings.WORD_DATASET_FILENAME_L, v.getDatasetFileName());
            datasetNode.addAttribute(XmlQueryStrings.WORD_DATASET_SIZE_L, String.valueOf(v.getSetSize()));
            datasetsContainer.addNode(datasetNode);
        });
        trainingNode.addNode(datasetsContainer);
        rootNode.addNode(trainingNode);

        ConfigNode calcConditionsNode = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(1).get(XmlQueryStrings.SYSTEM_CALCULATION_CONDITIONS));
        calcConditionsNode.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.SYSTEM_CALCULATION_CONDITIONS);
        ConfigNode conditionsContainer = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(2).get(XmlQueryStrings.WORD_CONTAINER_U));
        conditionsContainer.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.WORD_CONDITIONS_U);
        calculationConditionsDetails.getAllConditions().forEach(condition -> {
            ConfigNode conditionNode = configReader.createNodeFromTemplate
                    (configReader.getConfigTemplate().getNodes(3).get(XmlQueryStrings.WORD_CONDITION_U));
            conditionNode.addAttribute(XmlQueryStrings.WORD_TYPE_L, condition.toString());
            conditionNode.addAttribute(XmlQueryStrings.WORD_VALUE_L, calculationConditionsDetails.getValue(condition));
            conditionsContainer.addNode(conditionNode);
        });
        calcConditionsNode.addNode(conditionsContainer);
        rootNode.addNode(calcConditionsNode);

        configReader.writeConfig(newConfigFilename);
    }

    public void initiateEmptyConfiguration() {
        if (systemDetailsMap.size() == 0) {
            SystemDetails modelDetails = new SystemDetails();
            SystemDetails ntScaleDetails = new SystemDetails();
            SystemDetails exportDetails = new SystemDetails();
            SystemDetails trainingDetails = new SystemDetails();

            modelDetails.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.SYSTEM_MODEL_PARAMETERS);
            modelDetails.addAttribute(XmlQueryStrings.WORD_TICKS_L, "10000");

            ntScaleDetails.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.SYSTEM_NT_SCALE_PARAMETERS);
            ntScaleDetails.addAttribute(XmlQueryStrings.WORD_SCALE_L, "200");
            ntScaleDetails.addAttribute(XmlQueryStrings.SYSTEM_NT_SCALE_POSITION, "50");
            ntScaleDetails.addAttribute(XmlQueryStrings.SYSTEM_RECEPTORS_SCALE_POSITION, "50");
            ntScaleDetails.addAttribute(XmlQueryStrings.SYSTEM_NT_MAXNT_AND_RECEPTORS, "0");

            exportDetails.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.SYSTEM_EXPORT_PARAMETERS);
            exportDetails.addAttribute(XmlQueryStrings.SYSTEM_EXPORT_INDIVIDUAL_CHECKPOINT_QUANTUM, "5");
            exportDetails.addAttribute(XmlQueryStrings.SYSTEM_EXPORT_MODE, "Disabled");
            exportDetails.addAttribute(XmlQueryStrings.SYSTEM_EXPORT_DBHOST, "127.0.0.1");
            exportDetails.addAttribute(XmlQueryStrings.SYSTEM_EXPORT_DBPORT, "5432");
            exportDetails.addAttribute(XmlQueryStrings.SYSTEM_EXPORT_DBUSER, "bcnnm_model");
            exportDetails.addAttribute(XmlQueryStrings.SYSTEM_EXPORT_DBPASSWORD, "test_bcnnm");

            trainingDetails.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.SYSTEM_TRAINING_PARAMETERS);
//            trainingDetails.addAttribute(XmlQueryStrings.SYSTEM_TRAINING_TRAIN_IMG_NAME, "train-images.idx3-ubyte");
//            trainingDetails.addAttribute(XmlQueryStrings.SYSTEM_TRAINING_TRAIN_LBL_NAME, "train-labels.idx1-ubyte");
//            trainingDetails.addAttribute(XmlQueryStrings.SYSTEM_TRAINING_CNTRL_IMG_NAME, "t10k-images.idx3-ubyte");
//            trainingDetails.addAttribute(XmlQueryStrings.SYSTEM_TRAINING_CNTRL_LBL_NAME, "t10k-labels.idx1-ubyte");
//            trainingDetails.addAttribute(XmlQueryStrings.SYSTEM_TRAINING_TRAIN_SIZE, "10000");
//            trainingDetails.addAttribute(XmlQueryStrings.SYSTEM_TRAINING_CNTRL_SIZE, "500");
//            trainingDetails.addAttribute(XmlQueryStrings.SYSTEM_TRAINING_PRE_TRAIN_SIZE, "100");

            systemDetailsMap.put(modelDetails.getValue(XmlQueryStrings.WORD_TYPE_L), modelDetails);
            systemDetailsMap.put(ntScaleDetails.getValue(XmlQueryStrings.WORD_TYPE_L), ntScaleDetails);
            systemDetailsMap.put(exportDetails.getValue(XmlQueryStrings.WORD_TYPE_L), exportDetails);
            systemDetailsMap.put(trainingDetails.getValue(XmlQueryStrings.WORD_TYPE_L), trainingDetails);
        }
    }

    public Collection<SystemDetails> getAllSystemDetails() {
        return systemDetailsMap.values();
    }
    //endregion

    //region Private Methods
    //endregion
}
