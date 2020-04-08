package com.synstorm.SimulationModel.ConfigurationVerifier;

import com.synstorm.SimulationModel.Annotations.SignalingPathway;
import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;
import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
import com.synstorm.common.Utils.ConfigurationStrings.ClassPathsStrings;
import com.synstorm.common.Utils.Details.ActionDetails;
import com.synstorm.common.Utils.Details.CellDetails;
import com.synstorm.common.Utils.Details.IndividualCellDetails;
import com.synstorm.common.Utils.EnumTypes.ActionFunctionalType;
import com.synstorm.common.Utils.EnumTypes.ConsoleColors;
import com.synstorm.common.Utils.TraceMessageWriter.PriorityTraceWriter;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of singleton for verification of modeling configuration
 * Created by Dmitry.Bozhko on 10/1/2015.
 */

@Model_v0
@ProductionLegacy
public enum CVerifier {
    INSTANCE;

    //region Fields
    private int cellTypeMaxLength = 0;
    private int actionMaxLength = 0;
    private int maxLength = 0;
    //endregion

    //region Constructors
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    public boolean isConfigConsistent() {
        boolean isMainConfigConsistent = isMainConfigConsistent();
        boolean isIndividualScenarioConsistent = isIndividualScenarioConsistent();
        checkUnusedPathwayMethods();


        return isMainConfigConsistent && isIndividualScenarioConsistent && checkActionsByGenes(ModelLoader.getIndividualDetails().getUuid()) != -1;
    }


    public boolean isMainConfigConsistent() {
        calculateMaxLengths();
        Set<String> cellTypes = ModelLoader.getCellTypes().keySet();
        PriorityTraceWriter.println("Checking main configuration...", 1);
        Map<String, Boolean> messages = new HashMap<>();
        cellTypes.stream()
                .forEach(cellType -> {
                    List<String> annotatedMethods = getAnnotatedMessages(cellType);
                    CellDetails cellDetails = ModelLoader.getCellDetails(cellType);
                    cellDetails.getActions().stream()
                            .forEach(action -> checkAction(messages, annotatedMethods, cellType, action));
                    cellDetails.getActionsWithReceivedFactors().stream()
                            .forEach(action -> checkAction(messages, annotatedMethods, cellType, action));
                    cellDetails.getEmittedFactors().stream()
                            .forEach(action -> checkAction(messages, annotatedMethods, cellType, action));
                });
        boolean hasFailedActions = messages.entrySet().stream()
                .map(Map.Entry::getValue).collect(Collectors.toList()).contains(false);

        printConfigResult(hasFailedActions);
        return !hasFailedActions;
    }

    public boolean isIndividualScenarioConsistent() {
        Set<String> cellTypes = ModelLoader.getCellTypes().keySet();
        Set<IndividualCellDetails> individualCellDetails = ModelLoader.getIndividualDetails()
                .getIndividualCellDetailsMap().entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());

        PriorityTraceWriter.println("Checking scenario configuration...", 1);
        Map<String, Boolean> messages = new HashMap<>();
        individualCellDetails.stream()
                .forEach(cellDetails -> {
                    String cellType = cellDetails.getCellType();
                    String message = String.format("%1$-" + cellTypeMaxLength + "s", "Cell id: " + cellDetails.getId())
                            + " -> " + String.format("%1$-" + cellTypeMaxLength + "s", cellType);
                    if (cellTypes.contains(cellType)) {
                        PriorityTraceWriter.println(message + " := " + ConsoleColors.ANSI_GREEN.value() +
                                "Type Passed" + ConsoleColors.ANSI_RESET.value(), 2);
                        messages.put(message, true);
                        List<String> annotatedMethods = getAnnotatedMessages(cellType);
                        cellDetails.getCellActions().entrySet().stream()
                                .forEach(actionEntry ->
                                        checkAction(messages, annotatedMethods, cellType, actionEntry.getKey()));
                    } else {
                        PriorityTraceWriter.println(message + " := " + ConsoleColors.ANSI_RED.value() +
                                "Type Failed" + ConsoleColors.ANSI_RESET.value(), 2);
                        messages.put(message, false);
                    }
                });

        boolean hasFailedActions = messages.entrySet().stream()
                .map(Map.Entry::getValue).collect(Collectors.toList()).contains(false);

        printConfigResult(hasFailedActions);
        return !hasFailedActions;
    }

    public boolean isIndividualScenarioConsistent(UUID individualId) {
        Set<String> cellTypes = ModelLoader.getCellTypes().keySet();
        Set<IndividualCellDetails> individualCellDetails = ModelLoader.getIndividualDetails()
                .getIndividualCellDetailsMap().entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());

        PriorityTraceWriter.println("Checking scenario configuration...", 1);
        Map<String, Boolean> messages = new HashMap<>();
        individualCellDetails.stream()
                .forEach(cellDetails -> {
                    String cellType = cellDetails.getCellType();
                    String message = String.format("%1$-" + cellTypeMaxLength + "s", "Cell id: " + cellDetails.getId())
                            + " -> " + String.format("%1$-" + cellTypeMaxLength + "s", cellType);
                    if (cellTypes.contains(cellType)) {
                        PriorityTraceWriter.println(message + " := " + ConsoleColors.ANSI_GREEN.value() +
                                "Type Passed" + ConsoleColors.ANSI_RESET.value(), 2);
                        messages.put(message, true);
                        List<String> annotatedMethods = getAnnotatedMessages(cellType);
                        cellDetails.getCellActions().entrySet().stream()
                                .forEach(actionEntry ->
                                        checkAction(individualId, messages, annotatedMethods, cellType, actionEntry.getKey()));
                    } else {
                        PriorityTraceWriter.println(message + " := " + ConsoleColors.ANSI_RED.value() +
                                "Type Failed" + ConsoleColors.ANSI_RESET.value(), 2);
                        messages.put(message, false);
                    }
                });

        boolean hasFailedActions = messages.entrySet().stream()
                .map(Map.Entry::getValue).collect(Collectors.toList()).contains(false);

        printConfigResult(hasFailedActions);
        return !hasFailedActions;
    }

    public int checkActionsByGenes(UUID individualId) {
        //form a map <actionName, presented in genes> and check it.
        Map<String, Boolean> checkMap = ModelLoader.getAllActionDetails().stream()
                .collect(Collectors.toMap(ActionDetails::getName, actionDetails -> actionDetails.isActionPresentedByGenes(individualId)));

        // if all actions presented in genes, return 1
        // if all actions not presented in genes, return 0
        // if partially presented, return -1
        if (!checkMap.values().contains(false))
            return 1;
        else if (!checkMap.values().contains(true))
            return 0;
        else
            return -1;
    }
    //endregion

    //region Private Methods
    private void calculateMaxLengths() {
        Set<String> cellTypes = ModelLoader.getCellTypes().keySet();
        cellTypes.stream()
                .forEach(cellType -> {
                    cellTypeMaxLength = checkLength(cellType, cellTypeMaxLength);
                    CellDetails cellDetails = ModelLoader.getCellDetails(cellType);
                    cellDetails.getActions().stream()
                            .forEach(action -> actionMaxLength = checkLength(action, actionMaxLength));
                    cellDetails.getActionsWithReceivedFactors().stream()
                            .forEach(action -> actionMaxLength = checkLength(action, actionMaxLength));
                    cellDetails.getEmittedFactors().stream()
                            .forEach(action -> actionMaxLength = checkLength(action, actionMaxLength));
                });

        cellTypeMaxLength = Math.max(cellTypeMaxLength, actionMaxLength);
        actionMaxLength = Math.max(cellTypeMaxLength, actionMaxLength);
        maxLength = cellTypeMaxLength + actionMaxLength + 4;
    }

    private void checkUnusedPathwayMethods() {
        Set<String> cellTypes = ModelLoader.getCellTypes().keySet();
        PriorityTraceWriter.println("Checking unused implemented signaling pathways...", 1);
        Map<String, Integer> annotatedMethodsUsage = new HashMap<>();
        cellTypes.stream()
                .forEach(cellType -> {
                    List<String> annotatedMethods = getAnnotatedMessages(cellType);
                    CellDetails cellDetails = ModelLoader.getCellDetails(cellType);
                    annotatedMethods.stream().forEach(method -> {
                        if (!annotatedMethodsUsage.containsKey(method))
                            annotatedMethodsUsage.put(method, 0);

                        boolean contains = cellDetails.getActions().contains(method) ||
                                cellDetails.getActionsWithReceivedFactors().contains(method) ||
                                cellDetails.getEmittedFactors().contains(method);

                        if (contains)
                            annotatedMethodsUsage.put(method, annotatedMethodsUsage.get(method) + 1);
                    });
                });

        int unusedCount = (int) annotatedMethodsUsage.values().stream().filter(val -> val == 0).count();
        annotatedMethodsUsage.entrySet().stream().forEach(aMethod -> {
            if (aMethod.getValue() == 0) {
                PriorityTraceWriter.println(String.format("%1$-" + maxLength + "s", aMethod.getKey()) + " := " +
                        ConsoleColors.ANSI_YELLOW.value() + "Not used" + ConsoleColors.ANSI_RESET.value(), 2);
            }
        });

        PriorityTraceWriter.println("", 2);
        PriorityTraceWriter.println("Unused implemented signaling pathways count: " + ConsoleColors.ANSI_YELLOW.value() +
                unusedCount + ConsoleColors.ANSI_RESET.value(), 1);
        PriorityTraceWriter.println("", 0);
    }

    private void printConfigResult(boolean hasFailed) {
        if (hasFailed) {
            PriorityTraceWriter.println("", 2);
            PriorityTraceWriter.println("Main configuration: " + ConsoleColors.ANSI_RED.value() + "Done!" +
                    ConsoleColors.ANSI_RESET.value(), 1);
            PriorityTraceWriter.println("Main configuration status: " + ConsoleColors.ANSI_RED.value() + "False" +
                    ConsoleColors.ANSI_RESET.value() + "\n", 1);
        } else {
            PriorityTraceWriter.println("", 2);
            PriorityTraceWriter.println("Main configuration: " + ConsoleColors.ANSI_GREEN.value() + "Done!" +
                    ConsoleColors.ANSI_RESET.value(), 1);
            PriorityTraceWriter.println("Main configuration status: " + ConsoleColors.ANSI_GREEN.value() + "True" +
                    ConsoleColors.ANSI_RESET.value() + "\n", 1);
        }
    }

    private void checkAction(Map<String, Boolean> messages, List<String> annotatedMethods, String cellType, String action) {
        String message = String.format("%1$-" + cellTypeMaxLength + "s", cellType) + " -> " +
                String.format("%1$-" + actionMaxLength + "s", action);
        ActionDetails ad = ModelLoader.getActionDetails(action);
        if (ad != null && ((annotatedMethods.contains(action)) || isReserved(cellType, action)) && ad.getDuration() > 0) {
            PriorityTraceWriter.println(message + " := " + ConsoleColors.ANSI_GREEN.value() + "Action Passed" +
                    ConsoleColors.ANSI_RESET.value(), 2);
            messages.put(message, true);
        } else {
            PriorityTraceWriter.println(message + " := " + ConsoleColors.ANSI_RED.value() + "Action Failed" +
                    ConsoleColors.ANSI_RESET.value(), 2);
            messages.put(message, false);
        }
    }

    private void checkAction(UUID individualId, Map<String, Boolean> messages, List<String> annotatedMethods, String cellType, String action) {
        String message = String.format("%1$-" + cellTypeMaxLength + "s", cellType) + " -> " +
                String.format("%1$-" + actionMaxLength + "s", action);
        ActionDetails ad = ModelLoader.getActionDetails(action);
        if (ad != null && ((annotatedMethods.contains(action)) || isReserved(cellType, action)) && ad.getDurationByGenes(individualId) > 0) {
            PriorityTraceWriter.println(message + " := " + ConsoleColors.ANSI_GREEN.value() + "Action Passed" +
                    ConsoleColors.ANSI_RESET.value(), 2);
            messages.put(message, true);
        } else {
            PriorityTraceWriter.println(message + " := " + ConsoleColors.ANSI_RED.value() + "Action Failed" +
                    ConsoleColors.ANSI_RESET.value(), 2);
            messages.put(message, false);
        }
    }

    private List<String> getAnnotatedMessages(String cellType) {
        List<String> annotatedMethods = new ArrayList<>();
        try {
            Class cl = Class.forName(ClassPathsStrings.CELL_LINEAGE_CLASSPATH + cellType);
            List<Method> method = getInheritedMethods(cl);

            annotatedMethods.addAll(method.stream()
                    .filter(md -> md.isAnnotationPresent(SignalingPathway.class))
                    .map(Method::getName)
                    .collect(Collectors.toList()));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return annotatedMethods;
    }

    private boolean isReserved(String cellType, String action) {
        return action.startsWith(cellType) && (
                action.endsWith(ActionFunctionalType.Create.toString()) ||
                action.endsWith(ActionFunctionalType.Delete.toString()) ||
                action.endsWith(ActionFunctionalType.Proliferate.toString()));
    }

    private int checkLength(String toCheck, int maxLength) {
        if (toCheck.length() > maxLength)
            return toCheck.length();
        else
            return maxLength;
    }

    private List<Method> getInheritedMethods(Class cl) {
        List<Method> result = new ArrayList<>(Arrays.asList(cl.getDeclaredMethods()));
        while (cl.getSuperclass() != null) {
            cl = cl.getSuperclass();
            result.addAll(Arrays.asList(cl.getDeclaredMethods()));
        }

        return result;
    }
    //endregion
}
