package com.synstorm.common.Utils.EvolutionUtils.Score;

import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
import com.synstorm.common.Utils.EnumTypes.ConsoleColors;
import com.synstorm.common.Utils.EnumTypes.ScoreEnum;
import com.synstorm.common.Utils.EnumTypes.Sorting;
import com.synstorm.common.Utils.TraceMessageWriter.PriorityTraceWriter;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by human-research on 11/01/16.
 */
public class ScoreTable {
    //region Fields
    static Map<ScoreEnum, Sorting> scoreRules;
    private static Map<ScoreEnum, Double> scoreWeight;
    private static boolean includesRecombinationCounter;
    private Map<UUID, Score> scoreTable;
    //endregion

    //region Constructors
    static {
        scoreRules = new LinkedHashMap<>();
        scoreWeight = new LinkedHashMap<>();
        includesRecombinationCounter = false;
    }

    public ScoreTable() {
        scoreTable = new LinkedHashMap<>();
    }
    //endregion

    //region Getters and Setters
    public Collection<Score> getScoreTable() {
        formRating();
        return scoreTable.values();
    }

    public UUID getLeader() {
        formRating();
        Comparator<Score> comparator = Comparator.comparing(Score::getRating);
        return scoreTable.values().stream().max(comparator).get().getIndividualID();
    }

    public List<UUID> getTopTen() {
        formRating();
        Comparator<Score> comparator = Comparator.comparing(Score::getRating).reversed();

        return scoreTable.values().stream()
                .sorted(comparator)
                .limit(10)
                .map(Score::getIndividualID)
                .collect(toList());
    }

    public Score getScore(UUID uuid) {
        return scoreTable.get(uuid);
    }
    //endregion

    //region Public Methods
    public static void addScoreRule(ScoreEnum scoreEnum, Sorting sortingType, double weight) {
        scoreRules.put(scoreEnum, sortingType);
        scoreWeight.put(scoreEnum, weight);
        if (scoreEnum == ScoreEnum.RecombinationCounter)
            includesRecombinationCounter = true;
    }

    public void removeScoreRule(ScoreEnum scoreEnum) {
        scoreRules.remove(scoreEnum);
        scoreWeight.remove(scoreEnum);
    }

    public int getScoreTableSize() {
        return scoreTable.size();
    }

    private void formRating() {
        if (scoreTable.size() == 0)
            return;

        Set<ScoreEnum> scoreEnumSet = ModelLoader.getScoreRulesSet();
        scoreTable.values().forEach(score -> score.setRating(0.0));
        Map<ScoreEnum, List<Double>> scoreEnumsMinsMaxs = new HashMap<>();
        scoreEnumSet.forEach(scoreEnum -> {
            List<Double> minAndMax = new ArrayList<>();
            minAndMax.add(getMinValueOfScoreEnum(scoreEnum));
            minAndMax.add(getMaxValueOfScoreEnum(scoreEnum));
            scoreEnumsMinsMaxs.put(scoreEnum, minAndMax);
        });

        scoreTable.values().forEach(score -> {
            List<Double> criterionResultsWithWeights = new ArrayList<>();
            scoreEnumSet.forEach(scoreEnum -> {
                        double criterionMin = scoreEnumsMinsMaxs.get(scoreEnum).get(0);
                        double criterionMax = scoreEnumsMinsMaxs.get(scoreEnum).get(1);
                        if (scoreRules.get(scoreEnum).equals(Sorting.DESC))
                            criterionResultsWithWeights.add(getCriterionRating(score, scoreEnum, criterionMin, criterionMax) * scoreWeight.get(scoreEnum));
                        else
                            criterionResultsWithWeights.add( (1 / (getCriterionRating(score, scoreEnum, criterionMin, criterionMax) + 1)) * scoreWeight.get(scoreEnum));
                    });
            score.setRating(getIntegralRating(criterionResultsWithWeights));
        });
    }

    private double getIntegralRating(List<Double> criterionResultsWithWeight) {
        return /*Math.sqrt(*/criterionResultsWithWeight.stream()
                            .mapToDouble(x -> Math.pow(x, 2))
                            .reduce(0d, (a, b) -> a + b)/*)*/;
    }

    public void addIndividualScore(Score individualScore) {
        scoreTable.put(individualScore.getIndividualID(), individualScore);
    }

    public void removeIndividualScore(Score individualScore) { scoreTable.remove(individualScore.getIndividualID()); }

    public void incrementRecombinationCounter(UUID uuid) {
        if (includesRecombinationCounter) {
            Score targetScore = getScore(uuid);
            targetScore.setScoreParameter(ScoreEnum.RecombinationCounter, targetScore.getScoreParameter(ScoreEnum.RecombinationCounter) + 1.0);
        }
    }

    public void printScoreTable(boolean withRating, int topN) {
        formRating();

        ArrayList<Score> tempScore = new ArrayList<>();
        tempScore.addAll(scoreTable.values());
        sortScoreTableByRating(tempScore);
//        sortScoreTableByScoreEnum(tempScore, ScoreEnum.FinalSynapseCount);
        Map<ScoreEnum, Integer> scoreMaxLengths = new HashMap<>();
        List<String> rows = new ArrayList<>();
        scoreRules.keySet().forEach(scoreEnum -> scoreMaxLengths.put(scoreEnum, scoreEnum.toString().length()));
        tempScore.forEach(score -> scoreRules.keySet()
                        .forEach(scoreEnum -> {
                            String sScore = String.valueOf(score.getScoreParameter(scoreEnum));
                            int length = sScore.length();
                            if (length > scoreMaxLengths.get(scoreEnum))
                                scoreMaxLengths.put(scoreEnum, length);
                        }));

        int scoreTableTopSize = tempScore.size() <= topN ? tempScore.size() : topN;
        for (int i = 0; i < scoreTableTopSize; i++) {
            Score score = tempScore.get(i);
            String row = "| " + score.getIndividualID() + " |";
            if (withRating)
                row = row + " " + String.format("%1$-2.4f", score.getRating()) + " |";
            for (ScoreEnum scoreEnum : scoreRules.keySet()) {
                row = row + " " + String.format("%1$-" + scoreMaxLengths.get(scoreEnum) + "s",
                        score.getScoreParameter(scoreEnum)) + " |";
            }
            rows.add(row);
        }

        String horizontalBorder = "";
        String columnsCaption = "|                 UUID                 |";
        if (withRating)
            columnsCaption = columnsCaption + " Rating |";
        for (Map.Entry<ScoreEnum, Sorting> scoreEnum : scoreRules.entrySet())
            columnsCaption = columnsCaption + " " + String.format("%1$-" + scoreMaxLengths.get(scoreEnum.getKey()) + "s",
                    scoreEnum.getKey().toString()) + " |";

        for (int i = 0; i < columnsCaption.length(); i++)
            horizontalBorder = horizontalBorder + "=";

        PriorityTraceWriter.println("", 0);
        PriorityTraceWriter.println(ConsoleColors.ANSI_BLUE.value(), 0);
        PriorityTraceWriter.println(horizontalBorder, 0);
        PriorityTraceWriter.println(columnsCaption, 0);
        PriorityTraceWriter.println(horizontalBorder, 0);

        rows.forEach(row -> PriorityTraceWriter.println(row, 0));
        PriorityTraceWriter.println(horizontalBorder, 0);
        PriorityTraceWriter.println("Top " + topN + " scores from " + scoreTable.size(), 0);
        PriorityTraceWriter.print(ConsoleColors.ANSI_RESET.value(), 0);
    }
    //endregion

    //region Private Methods
    private void sortScoreTableByScoreEnum(List<Score> tempScore, ScoreEnum scoreEnum) {
        tempScore.sort((s1, s2) -> {
            if (s1.getScoreParameter(scoreEnum) > s2.getScoreParameter(scoreEnum))
                return -1;
            else if (s1.getScoreParameter(scoreEnum) < s2.getScoreParameter(scoreEnum))
                return 1;
            else
                return 0;
        });
    }

    private void sortScoreTableByRating(List<Score> tempScore) {
        tempScore.sort((s1, s2) -> Double.compare(s2.getRating(), s1.getRating()));
    }

    private Map<Double, Integer> getRatingByScoreEnum(ScoreEnum scoreEnum, Sorting sortingRule) {
        Map<Double, Integer> result = new LinkedHashMap<>();
        List<Double> temp = new ArrayList<>();
        scoreTable.values().forEach(i -> temp.add(i.getScoreParameter(scoreEnum)));
        if (sortingRule.equals(Sorting.DESC))
            Collections.sort(temp);
        else
            Collections.sort(temp, Collections.reverseOrder());

        int rate = 1;
        Set<Double> keys = new LinkedHashSet<>(temp);

        for (Double key : keys)
            if (key == 0.0)
                result.put(key, 0);
            else
                result.put(key, rate++);

        return result;
    }

    private double getMaxValueOfScoreEnum(ScoreEnum scoreEnum) {
        return scoreTable.values().stream()
                .mapToDouble(score -> score.getScoreParameter(scoreEnum))
                .max()
                .getAsDouble();
    }

    private double getMinValueOfScoreEnum(ScoreEnum scoreEnum) {
        return scoreTable.values().stream()
                .mapToDouble(score -> score.getScoreParameter(scoreEnum))
                .min()
                .getAsDouble();
    }

    private double getCriterionRating(Score score, ScoreEnum criterion, double criterionMin, double criterionMax) {
        if (criterionMax == criterionMin)
            return 1;
        else
            return (score.getScoreParameter(criterion) - criterionMin) / (criterionMax - criterionMin);
    }
    //endregion
}