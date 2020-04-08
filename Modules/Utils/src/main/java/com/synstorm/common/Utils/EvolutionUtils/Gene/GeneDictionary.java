package com.synstorm.common.Utils.EvolutionUtils.Gene;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Created by human-research on 21/09/15.
 */
public class GeneDictionary {
    //region Fields
    private final Map<String, Gene> genes = new HashMap<>();
    private final Map<Integer, ArrayList<String>> durationGenes = new TreeMap<>();
    //endregion

    //region Constructors
    public GeneDictionary(String fileName) {
        getInitialGeneData(fileName);
    }
    //endregion

    //region Getters and Setters
    public Map<String, Gene> getGenes() {
        return genes;
    }

    public Map<Integer, ArrayList<String>> getDurationGenes() {
        return durationGenes;
    }
    //endregion

    //region Public Methods
    private void getInitialGeneData(String fileName) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert scanner != null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            String geneName = parts[0];
            int geneDuration = Integer.parseInt(parts[1]);
            Gene gene = new Gene(geneName, geneDuration);
            genes.put(geneName, gene);
            if (!durationGenes.containsKey(geneDuration)) {
                durationGenes.put(geneDuration, new ArrayList<String>() {{
                    add(geneName);
                }});
            }
            else {
                ArrayList<String> temp = durationGenes.get(geneDuration);
                temp.add(geneName);
            }
        }
    }
    //endregion

    //region Private Methods
    //endregion
}