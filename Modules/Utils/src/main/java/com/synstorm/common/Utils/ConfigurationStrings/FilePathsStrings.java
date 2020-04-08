package com.synstorm.common.Utils.ConfigurationStrings;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;

import java.io.File;

/**
 * Created by human-research on 16/07/15.
 */

@Model_v0
@ProductionLegacy
public class FilePathsStrings {
    /**
     * Filenames: TEMPLATES
     */
    public static final String VARIABLE_GENEDICT_WITH_DURATIONS_ADDR = "data/EtalonGeneSample1000.csv";
    public static final String VARIABLE_GENEDICT_ADDR = "data/GeneDictionary.csv";
    public static final String WORKING_DIRECTORY = System.getProperty("user.dir");
    public static final String PWD = WORKING_DIRECTORY + File.separator + "data";
    public static final String FILENAME_TEMPLATES_PATH = PWD + File.separator + "Templates";
    public static final String FILENAME_SYSTEM_TEMPLATE = File.separator + "TSystem.xml";
    public static final String FILENAME_ACTIONS_TEMPLATE = File.separator + "TActions.xml";
    public static final String FILENAME_CELLS_TEMPLATE = File.separator + "TCells.xml";
    public static final String FILENAME_FACTORS_TEMPLATE = File.separator + "TFactors.xml";
    public static final String FILENAME_SEEDS_TEMPLATE = File.separator + "TSeeds.xml";
    public static final String FILENAME_NEUROTRANSMITTER_TEMPLATE = File.separator + "TNeurotransmitter.xml";
    public static final String FILENAME_SPACE_TEMPLATE = File.separator + "TSpace.xml";
    public static final String FILENAME_INDIVIDUAL_TEMPLATE = File.separator + "TIndividual.xml";
    public static final String FILENAME_EVOLUTION_TEMPLATE = File.separator + "TEvolution.xml";

    /**
     * Filenames: FILES
     */
    public static final String FILENAME_MODEL_CONFIG = File.separator + "Model.xml";


    public static final String FILENAME_SYSTEM_CONFIG = File.separator + "System.xml";
    public static final String FILENAME_ACTIONS_CONFIG = File.separator + "Actions.xml";
    public static final String FILENAME_CELLS_CONFIG = File.separator + "Cells.xml";
    public static final String FILENAME_FACTORS_CONFIG = File.separator + "Factors.xml";
    public static final String FILENAME_SEEDS_CONFIG = File.separator + "Seeds.xml";
    public static final String FILENAME_NEUROTRANSMITTER_CONFIG = File.separator + "Neurotransmitter.xml";
    public static final String FILENAME_SPACE_CONFIG = File.separator + "Space.xml";
    public static final String FILENAME_INDIVIDUAL_CONFIG = File.separator + "Individual.xml";
    public static final String FILENAME_EVOLUTION_CONFIG = File.separator + "Evolution.xml";

    /**
     * Filenames: TRAINING
     */
    public static final String TRAINING_DIRECTORY = PWD + File.separator + "dbMNIST";

    /**
     * Filenames: VALIDATION
     */
    public static final String FILENAME_MODEL_XSL = File.separator + "Model.xsl";
    public static final String FILENAME_INDIVIDUAL_XSL = File.separator + "Individual.xsl";

    /**
     * Filenames: XSD
     */
    public static final String FILENAME_MODEL_SCHEMA = File.separator + "Model.xsd";
    public static final String FILENAME_INDIVIDUAL_SCHEMA = File.separator + "Individual.xsd";
}
