package com.synstorm.common.Utils.EvolutionUtils.Gene;

import com.synstorm.common.Utils.ConfigurationStrings.FilePathsStrings;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by human-research on 27/02/2017.
 */
public enum SimpleGeneDictionary {
    INSTANCE;
    private final List<String> dictionary = new ArrayList<>();
    private int dictionaryVolume;
    //region Fields

    //endregion


    //region Constructors

    SimpleGeneDictionary() {

        getInitialGeneData(FilePathsStrings.VARIABLE_GENEDICT_ADDR);
    }

    //endregion


    //region Getters and Setters

    //endregion


    //region Public Methods
    public String getGene(int number) {
        return dictionary.get(number);
    }

    public int getDictionaryVolume() {
        return dictionaryVolume;
    }

    //endregion


    //region Private Methods
    private void getInitialGeneData(String fileName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        reader.lines().forEach(dictionary::add);
        dictionaryVolume = dictionary.size();
    }
    //endregion

}
