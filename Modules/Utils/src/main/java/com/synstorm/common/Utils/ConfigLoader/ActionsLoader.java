package com.synstorm.common.Utils.ConfigLoader;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.ConfigWorker.ConfigNode;
import com.synstorm.common.Utils.ConfigWorker.ConfigReader;
import com.synstorm.common.Utils.ConfigurationStrings.FilePathsStrings;
import com.synstorm.common.Utils.ConfigurationStrings.XmlQueryStrings;
import com.synstorm.common.Utils.Details.ActionDetails;
import com.synstorm.common.Utils.Details.IDetails;
import com.synstorm.common.Utils.EnumTypes.ActionFunctionalType;
import com.synstorm.common.Utils.EnumTypes.ActionSignalType;
import com.synstorm.common.Utils.EnumTypes.SignalingTopologicalType;
import com.synstorm.common.Utils.TraceMessageWriter.PriorityTraceWriter;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Dmitry.Bozhko on 3/25/2015.
 */
@Model_v0
@NonProductionLegacy
public class ActionsLoader extends BaseLoader implements ILoader {
    //region Fields
    private Map<String, ActionDetails> actionDetailsMap;
    //endregion

    //region Constructors
    public ActionsLoader() {
        actionDetailsMap = new TreeMap<>();
    }
    //endregion

    //region Getters and Setters
    @Override
    @Nullable
    public IDetails getDetails(String key) {
        return actionDetailsMap.getOrDefault(key, null);
    }
    //endregion

    //region Public Methods
    @Override
    public void load(String configFileName, String templateFileName) {
        initialize(configFileName, templateFileName);

        List<String> actionRows = configReader.getAttributesValueList(XmlQueryStrings.ATTR_NAME);
        for (String actionRow : actionRows) {
            String neighborhood = configReader.getAttributeValue(XmlQueryStrings.ACTION_BY_NAME + actionRow +
                    XmlQueryStrings.ACTION_ATTR_NEIGHBORHOOD);
            String signalType = configReader.getAttributeValue(XmlQueryStrings.ACTION_BY_NAME + actionRow +
                    XmlQueryStrings.ACTION_ATTR_STYPE);
            String stType = configReader.getAttributeValue(XmlQueryStrings.ACTION_BY_NAME + actionRow +
                    XmlQueryStrings.ACTION_ATTR_IO);
            String functionalType = configReader.getAttributeValue(XmlQueryStrings.ACTION_BY_NAME + actionRow +
                    XmlQueryStrings.ACTION_ATTR_FTYPE);
            String duration = configReader.getAttributeValue(XmlQueryStrings.ACTION_BY_NAME + actionRow +
                    XmlQueryStrings.ATTR_DURATION);
            String initial = configReader.getAttributeValue(XmlQueryStrings.ACTION_BY_NAME + actionRow +
                    XmlQueryStrings.ACTION_ATTR_INITIAL);
            String repeatable = configReader.getAttributeValue(XmlQueryStrings.ACTION_BY_NAME + actionRow +
                    XmlQueryStrings.ACTION_ATTR_REPEATABLE);

            ActionDetails actionDetails = new ActionDetails(actionRow, neighborhood, signalType,
                    stType, functionalType, duration, initial, repeatable);

            actionDetailsMap.put(actionRow, actionDetails);
        }
    }

    @Override
    public void save(String newConfigFilename) throws Exception {
        configReader = new ConfigReader();
        configReader.loadTemplate(FilePathsStrings.FILENAME_TEMPLATES_PATH + FilePathsStrings.FILENAME_ACTIONS_TEMPLATE);
        NumberFormat numberFormat = new DecimalFormat(XmlQueryStrings.TECH_DECI_FORMAT);

        //create rootNode
        configReader.createEmptyRootNodeFromTemplate();
        ConfigNode rootNode = configReader.getRootNode();

        //create action
        for (Map.Entry<String, ActionDetails> entry : actionDetailsMap.entrySet()) {
            ActionDetails aDetails = entry.getValue();
            ConfigNode actionNode = configReader.createNodeFromTemplate
                    (configReader.getConfigTemplate().getNodes(1).get(XmlQueryStrings.WORD_ACTION_U));
            actionNode.addAttribute(XmlQueryStrings.WORD_NAME_L, aDetails.getName());
            actionNode.addAttribute(XmlQueryStrings.WORD_NEIGHBORHOOD_L, numberFormat.format(aDetails.getNeighborhood()));
            actionNode.addAttribute(XmlQueryStrings.WORD_SIGNAL_TYPE_L, aDetails.getSignalType().toString());
            actionNode.addAttribute(XmlQueryStrings.WORD_IN_OUT_L, aDetails.getSignalingTopologicalType().toString());
            actionNode.addAttribute(XmlQueryStrings.WORD_FUNCTIONAL_TYPE_L, aDetails.getActionFunctionalType().toString());
            actionNode.addAttribute(XmlQueryStrings.WORD_DURATION_L, numberFormat.format(aDetails.getDuration()));
            actionNode.addAttribute(XmlQueryStrings.WORD_INITIAL_L, aDetails.isInitial() ? "1" : "0");
            actionNode.addAttribute(XmlQueryStrings.WORD_REPEATABLE_L, aDetails.isRepeatable() ? "1" : "0");

            rootNode.addNode(actionNode);
        }
        configReader.writeConfig(newConfigFilename);
    }

    public void addAction(String actionRow) {
        String neighborhood = "0";
        String signalType = String.valueOf(ActionSignalType.Discrete);
        String signalingTopologicalType = String.valueOf(SignalingTopologicalType.InnerCell);
        String triggerType = String.valueOf(ActionFunctionalType.Do);
        String duration = "1";
        String initial = "0";
        String repeatable = "0";

        ActionDetails actionDetails = new ActionDetails(actionRow, neighborhood, signalType, signalingTopologicalType,
                triggerType, duration, initial, repeatable);
        actionDetailsMap.put(actionRow, actionDetails);
    }

    public void removeAction(String actionToRemove) {
        actionDetailsMap.remove(actionToRemove);
    }

    public Set<String> getAllActions() {
        return actionDetailsMap.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toCollection(TreeSet::new));
    }

    public Collection<ActionDetails> getAllActionDetails() {
        return actionDetailsMap.values();
    }

    public Set<String> getAllFactors() {
        Set<String> result = new HashSet<>();
        actionDetailsMap.values().stream().forEach(actionDetails -> {
            if (actionDetails.isFactor())
                result.add(actionDetails.getName());
        });
        return result;
    }

    public void printIndividualActionDurations(List<UUID> ids) {
        ids.stream().forEach(id -> {
            PriorityTraceWriter.println("Individual num: " + id, 0);
            actionDetailsMap.values().stream()
                    .forEach(actionDetails ->
                            PriorityTraceWriter.println(
                                    actionDetails.getName() + " := " + actionDetails.getDurationByGenes(id), 0));
            PriorityTraceWriter.println("", 0);
        });
    }
    //endregion

    //region Private Methods
    //endregion
}
