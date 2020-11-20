package com.synstorm.common.Utils.FileModelExport;

import com.synstorm.common.Utils.ModelExport.IModelDataExporter;
import com.synstorm.common.Utils.PlatformLoaders.Configuration.PlatformConfiguration;
import com.synstorm.common.Utils.PlatformLoaders.Objects.ChemicalObjects.Ligand;
import com.synstorm.common.Utils.TraceMessageWriter.PriorityTraceWriter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

public enum ExporterFactory {
    INSTANCE;

    private final Map<String, Function<String, IModelDataExporter>> simpleExporters;
    private final Map<String, Function<String[], IModelDataExporter>> parametrizedExporters;

    ExporterFactory() {
        simpleExporters = new HashMap<>();
        parametrizedExporters = new HashMap<>();
        initializeSimpleMap();
        initializeParametrizedMap();
    }

    public IModelDataExporter createSimpleExporter(String name, String prefix) {
        return simpleExporters.get(name).apply(prefix);
    }

    public IModelDataExporter createParametrizedExporter(String name, String[] args) {
        return parametrizedExporters.get(name).apply(args);
    }


    /**
     * Method parses exporters string from SimulationArguments.
     * Exporter's names must be divided by ";"
     * @param exporters
     * @param platformConfiguration
     * @return IModelDataExporter[]
     */

    @NotNull
    public IModelDataExporter[] parseExporters(@NotNull String exporters, PlatformConfiguration platformConfiguration) {
        //parse exporters strings
        String[] exportersList = exporters.split(Pattern.quote(";"));
        IModelDataExporter[] modelExporters = new IModelDataExporter[exportersList.length];
        for (int i = 0; i < exportersList.length; i++) {
            IModelDataExporter modelDataExporter = parseExporter(exportersList[i], platformConfiguration);
            modelExporters[i] = modelDataExporter;
        }
        return modelExporters;

    }

    /**
     * Method parses exporter string.
     * @param exporterString @NotNull String
     * @param platformConfiguration PlatformConfiguration
     * @return IModelDataExporter
     */
    private IModelDataExporter parseExporter(@NotNull String exporterString, PlatformConfiguration platformConfiguration) {
        IModelDataExporter modelDataExporter;
        if (matchExporterString(exporterString)) {
            String exporterName = exporterString.substring(0, exporterString.indexOf("{"));
            //find first { and first }. Args splitted by commas
            String[] args = exporterString.substring(
                    exporterString.indexOf("{") + 1,
                    exporterString.indexOf("}"))
                    .split(",");
            if (platformConfiguration != null)
                try {
                    args = argumentsPostProcessing(exporterName, args, platformConfiguration);
                } catch (IllegalArgumentException exception) {
                    PriorityTraceWriter.printf(0, "Wrong arguments %s in exporter string %s", Arrays.toString(args), exporterString);
                    System.exit(-1);
                }
            modelDataExporter = createParametrizedExporter(exporterName, args);
        } else {
            modelDataExporter = createSimpleExporter(exporterString, "");
        }
        return modelDataExporter;
    }

    /**
     * Regular expression checks
     * Regex expression for parametrized exporters is: ^[A-Za-z]+[{](?![{])(\w+)(,\s*\w+)*[}](?!})$
     * https://regex101.com/r/fVTQjy/63
     * It will pass exporter{string,with,one,or,more,than,one,argument}
     * @param exporterString
     * @return
     */
    @Contract(pure = true)
    boolean matchExporterString(@NotNull String exporterString) {
        return exporterString.matches("^[A-Za-z]+[{](?![{])(\\w+)(,\\s*\\w+)*[}](?!})$");
    }

    @Contract("_, _, _ -> param2")
    private String[] argumentsPostProcessing(@NotNull String exporterName, String[] args, PlatformConfiguration platformConfiguration) throws IllegalArgumentException {
        if ("CsvChemicalEventExporter".equals(exporterName)) {
            if ("_".equals(args[0])) return args;
            for (int i = 0; i < args.length; i++) {
                Ligand ligand = platformConfiguration.getLigandsConfiguration().getLigand(args[i]);
                if (ligand != null)
                    args[i] = String.valueOf(ligand.getNum());
                else
                    throw new IllegalArgumentException("Bad argument for " + exporterName + ". Seems like ligand not exist in configuration.");
            }
            return args;
        }
        return args;
    }

    private void initializeSimpleMap() {
        simpleExporters.put("CellMovementExporter",                ExporterFactory::createCellMovementExporter);
        simpleExporters.put("CsvAnswerVectorExporter",             ExporterFactory::createCsvAnswerVectorExporter);
        simpleExporters.put("CsvGeneMappingExporter",              ExporterFactory::createCsvGeneMappingExporter);
        simpleExporters.put("CsvLqsExporter",                      ExporterFactory::createCsvLqsExporter);
        simpleExporters.put("CsvSynapticPowerStatisticExporter",   ExporterFactory::createCsvSynapticPowerStatisticExporter);
        simpleExporters.put("JsonAnswersExporter",                 ExporterFactory::createJsonAnswersExporter);
        simpleExporters.put("JsonStatisticsExporter",              ExporterFactory::createJsonStatisticsExporter);
        simpleExporters.put("LearningScoreTableExporter",          ExporterFactory::createLearningScoreTableExporter);
        simpleExporters.put("ScoreTableExporter",                  ExporterFactory::createScoreTableExporter);
        simpleExporters.put("XmlIndividualExporter",               ExporterFactory::createXmlIndividualExporter);
        simpleExporters.put("ConcentrationsExporter",              ExporterFactory::createConcentrationsExporter);
    }

    private void initializeParametrizedMap() {
        parametrizedExporters.put("CsvChemicalEventExporter",      ExporterFactory::createCsvEventExporter);
    }

    static private IModelDataExporter createCellMovementExporter(String prefix)                 { return new CellMovementExporter();}
    static private IModelDataExporter createCsvGeneMappingExporter(String prefix)               { return new CsvGeneMappingExporter();}
    static private IModelDataExporter createCsvSynapticPowerStatisticExporter(String prefix)    { return new CsvSynapticPowerStatisticExporter();}
    static private IModelDataExporter createJsonStatisticsExporter(String prefix)               { return new JsonStatisticsExporter();}
    static private IModelDataExporter createLearningScoreTableExporter(String prefix)           { return new LearningScoreTableExporter();}
    static private IModelDataExporter createScoreTableExporter(String prefix)                   { return new ScoreTableExporter();}
    static private IModelDataExporter createConcentrationsExporter(String prefix)               { return new ConcentrationsExporter();}

    // with prefix
    static private IModelDataExporter createJsonAnswersExporter(String prefix)      { return new JsonAnswersExporter(prefix);}
    static private IModelDataExporter createCsvLqsExporter(String prefix)           { return new CsvLqsExporter(prefix);}
    static private IModelDataExporter createCsvAnswerVectorExporter(String prefix)  { return new CsvAnswerVectorExporter(prefix);}
    static private IModelDataExporter createXmlIndividualExporter(String prefix)    { return new XmlIndividualExporter(prefix);}

    // parametrized
    static private IModelDataExporter createCsvEventExporter(String[] args)         { return new CsvChemicalEventExporter(args);}
}
