package com.synstorm.SimulationModel.ModelProcessors;

import com.synstorm.SimulationModel.CellLineage.R.Neuron;
import com.synstorm.SimulationModel.Loader;
import com.synstorm.SimulationModel.LogicObjectR.LogicObject;
import com.synstorm.SimulationModel.Model.IndividualR;
import com.synstorm.SimulationModel.Model.Model;
import com.synstorm.SimulationModel.ModelAgent.IIndividualAgentListener;
import com.synstorm.SimulationModel.ModelAgent.IndividualAgent;
import com.synstorm.SimulationModel.SpaceShell.Shell;
import com.synstorm.SimulationModel.Utils.ModelStrings;
import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
import com.synstorm.common.Utils.ConsoleProgressBar.AdditionalInfo;
import com.synstorm.common.Utils.ConsoleProgressBar.IProgressBar;
import com.synstorm.common.Utils.Details.IndividualDetails;
import com.synstorm.common.Utils.EnumTypes.StatisticType;
import com.synstorm.common.Utils.EvolutionUtils.Gene.Gene;
import com.synstorm.common.Utils.FileModelExport.*;
import com.synstorm.common.Utils.ModelExport.EmptyExporter;
import com.synstorm.common.Utils.ModelExport.IModelDataExporter;
import com.synstorm.common.Utils.ModelStatistic.IndividualStatistics;
import com.synstorm.common.Utils.SimArgs.SimulationArguments;
import com.synstorm.common.Utils.SimulationEvents.IndividualEvents.StatisticSingleFormEvent;
import com.synstorm.common.Utils.TraceMessageWriter.PriorityTraceWriter;
import gnu.trove.map.TIntObjectMap;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

/**
 * Base abstract class for common behaviour of individual processor.
 * Created by dvbozhko on 06/07/16.
 */
public abstract class BaseModelProcessor implements IIndividualAgentListener {
    //region Fields
    private Runtime runtime;
    private final int mb;
    private long maxMemory;
    protected final int availableProcessors;
    private final ExecutorService executorService;
    protected final String configName;
    protected final String experimentName;
    protected String fullExperimentPrefix;
    protected final int modelSeedNum;
//    protected final byte[] individualSeed;
    protected final long tStart;
    protected final String simulationStartTime;
    protected final JsonStatisticsExporter statisticsExporter;
    protected final List<IModelDataExporter> simulationExporters;
    protected final CsvSpaceSnapshotExporter spaceSnapshotExporter;
    protected final CsvAxonLengthExporter axonLengthExporter;
    protected XmlIndividualExporter xmlIndividualExporter;
    protected IProgressBar progressBar;

    protected Map<UUID, Future<IndividualStatistics>> calculationResults;
    protected BlockingDeque<UUID> calculatedAgents;
    protected int calculatedAgentsCount;
    protected int populationVolume;

    //endregion

    //region Constructors
    public BaseModelProcessor() {
        tStart = System.currentTimeMillis();
        simulationStartTime = String.valueOf(tStart);
        configName = SimulationArguments.INSTANCE.getConfigPath();
        experimentName = configName.replace("/", ".");
        modelSeedNum = SimulationArguments.INSTANCE.getIndividualSeedNum();
        runtime = Runtime.getRuntime();
//        ModelLoader.load(ModelStrings.CONFIG_PATH + configName, modelSeedNum);
        mb = 1024 * 1024;
        maxMemory = 0;
        calculatedAgentsCount = 0;
        populationVolume = calculatePopulationVolume();
        statisticsExporter = new JsonStatisticsExporter();
        simulationExporters = new ArrayList<>();
        spaceSnapshotExporter = new CsvSpaceSnapshotExporter();
        axonLengthExporter = new CsvAxonLengthExporter();
        calculationResults = new ConcurrentHashMap<>();
        calculatedAgents = new LinkedBlockingDeque<>();
        availableProcessors = calculateAvailableProcessors();
        executorService = Executors.newFixedThreadPool(availableProcessors);
        final int loggingLevel = SimulationArguments.INSTANCE.getLogLevel();
        PriorityTraceWriter.setLevel(loggingLevel);
        PriorityTraceWriter.println("Logging level set to: " + loggingLevel, 0);
        PriorityTraceWriter.println("Available amount of cores for threading: " + runtime.availableProcessors(), 0);
        PriorityTraceWriter.println("Required amount of cores for threading: " + availableProcessors, 0);
//        individualSeed = ModelLoader.getSeed(modelSeedNum);
    }
    //endregion

    //region Getters and Setters
    public long getMaxMemoryUsed() {
        return maxMemory / mb;
    }
    //endregion

    //region Public Methods
    public void calculateModel() throws Exception {
        fullExperimentPrefix = ModelStrings.RESULTS_PATH + simulationStartTime + File.separator + experimentName;
        PriorityTraceWriter.printf(0, "Results will be saved to %s", fullExperimentPrefix);
        initializeExporters();
        statisticsExporter.open(fullExperimentPrefix);
        spaceSnapshotExporter.open(fullExperimentPrefix);
        axonLengthExporter.open(fullExperimentPrefix);
        //xmlIndividualExporter opened in SingleIndividualProcessor
        if (startCalculations())
            proceedResults();
        else
            progressBar.addAdditionalInfo(new AdditionalInfo("Error starting calculation", "Invalid initial configuration", 0));
        finishModeling();
    }

    @Override
    public synchronized void finishingSuccessfulAgentCalculation(IndividualAgent agent) throws Exception {
        agent.endCalculating();
        calculatedAgents.add(agent.getId());
    }

    @Override
    public synchronized void finishingFailedAgentCalculation(IndividualAgent agent) throws Exception {
        calculatedAgents.add(agent.getId());
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    protected int calculatePopulationVolume() {
        return 1;
    }

    protected int calculateAvailableProcessors() {
        final int coreCount = runtime.availableProcessors();
        return coreCount < populationVolume ? coreCount : populationVolume;
    }

    protected void finishModeling() {
        exportSpaceSnapshot();
        exportAxonLength();
        proceedAdditionalActionsBeforeFinish();
        formFinalStatistic();
        printAdditionalInfo();
        closeExporters();
        shutdownExecutorService();
    }

    private void exportAxonLength() {
        axonLengthExporter.writeHeader("");
        for (LogicObject logicObject: Model.INSTANCE.getIndividual().getIndLObjects().valueCollection()) {
            if (logicObject instanceof Neuron && ((Neuron) logicObject).hasAxon()) {
                Neuron neuron = (Neuron) logicObject;
                axonLengthExporter.exportObject(neuron.getObjectId(), neuron.getAxonCoordinates().size());
            }
        }
        axonLengthExporter.close();
    }

    private void exportSpaceSnapshot() {
        //export csv
        spaceSnapshotExporter.writeHeader("");
        Shell shell = Model.INSTANCE.getSpaceShell();
        TIntObjectMap<LogicObject> logicObjects = Model.INSTANCE.getIndividual().getIndLObjects();
        Arrays.stream(logicObjects.keys()).forEach(
                id -> spaceSnapshotExporter.exportObject(
                        id,
                        logicObjects.get(id).getType(),
                        shell.getCoordinate(id))
        );
        spaceSnapshotExporter.close();
    }

    protected void submitIndividualAgent(IndividualAgent individualAgent) {
        individualAgent.setMemCalc(this::updateMaxMemory);
        Future<IndividualStatistics> result = executorService.submit(individualAgent);
        calculationResults.put(individualAgent.getIndividual().getId(), result);
    }

    protected void updateMaxMemory() {
        long checkMem = runtime.totalMemory();
        if (checkMem > maxMemory)
            maxMemory = checkMem;
    }

    protected void proceedResults() throws Exception {
        while (!isAllAgentsCalculated()) {
            UUID calcId = calculatedAgents.take();
            try {
                IndividualStatistics statistics = calculationResults.get(calcId).get();
                proceedIndividualResult(statistics);
            } catch (Exception e) {
                finishModeling();
                throw new Exception(e);
            }
        }
    }

    protected void closeExporters() {
        simulationExporters.forEach(IModelDataExporter::close);
        statisticsExporter.close();
        xmlIndividualExporter.close();
    }

    protected final void recalculateActions(IndividualDetails individual) {
        UUID uuid = individual.getUuid();
        List<Gene> genes = individual.getIndividualGenes();
        ModelLoader.getAllActions().stream()
                .map(ModelLoader::getActionDetails)
                .forEach(ad -> {
                    ad.calculateDurationByGenes(uuid, genes);
                    individual.addActionDuration(ad.getName(), ad.getDurationByGenes(uuid));
                });
    }

    protected abstract boolean startCalculations() throws Exception;

    protected abstract void proceedIndividualResult(IndividualStatistics statistics) throws Exception;

    protected abstract void proceedAdditionalActionsBeforeFinish();

    protected abstract @Nullable IndividualDetails getNextIndividualToCompute() throws Exception;

//    protected abstract Individual formIndividualObject(IndividualDetails individualDetails) throws Exception;

    protected abstract IndividualR formIndividualObject();
    //endregion

    //region Private Methods
    private void initializeExporters() {
        String exporters = SimulationArguments.INSTANCE.getStatisticExporters();

        if(exporters.equals("default")) {
            IModelDataExporter individualExporter = new EmptyExporter();
            simulationExporters.add(individualExporter);
        }
        else {
            IModelDataExporter[] iModelDataExporters = ExporterFactory.INSTANCE.parseExporters(exporters, Loader.INSTANCE.getPlatformConfiguration());
            for (IModelDataExporter modelDataExporter : iModelDataExporters) {
                modelDataExporter.open(fullExperimentPrefix);
                modelDataExporter.writeHeader("");
                simulationExporters.add(modelDataExporter);
            }
        }
    }

    private boolean isAllAgentsCalculated() {
        return calculatedAgentsCount >= populationVolume && calculatedAgents.size() == 0;
    }

    private void shutdownExecutorService() {
        executorService.shutdown();
    }

    private void printAdditionalInfo() {
        progressBar.printAllAdditionalInfo();
    }

    private void formFinalStatistic() {
        String modelingTime = progressBar.getModelingTime();
        String modelingStartTime = progressBar.getStartTime();
//        int cachedCoordinates = CoordinateUtils.INSTANCE.getCachedCount();
//        long getsFromCachedCoordinates = CoordinateUtils.INSTANCE.getGetFromCacheCount();
//        String seedValue = "\"" + new String(ModelLoader.getSeed(modelSeedNum)) + "\"";
        long maximumMemory = getMaxMemoryUsed();

        statisticsExporter.write(new StatisticSingleFormEvent<>(StatisticType.ModelingTime, "\"" + modelingTime + "\""));
        statisticsExporter.write(new StatisticSingleFormEvent<>(StatisticType.ModelingStartTime, "\"" + modelingStartTime + "\""));
//        statisticsExporter.write(new StatisticSingleFormEvent<>(StatisticType.TotalCachedCoordinates, cachedCoordinates));
//        statisticsExporter.write(new StatisticSingleFormEvent<>(StatisticType.TotalGetsFromCachedCoordinates, getsFromCachedCoordinates));
        statisticsExporter.write(new StatisticSingleFormEvent<>(StatisticType.ExperimentName, "\"" + experimentName + "\""));
//        statisticsExporter.write(new StatisticSingleFormEvent<>(StatisticType.SeedValue, seedValue));

        progressBar.addAdditionalInfoList(new ArrayList<AdditionalInfo>() {{
            add(new AdditionalInfo("Total modeling time", modelingTime, 0));
//            add(new AdditionalInfo("Total cached coordinates", cachedCoordinates, 0));
//            add(new AdditionalInfo("Total gets from cached coordinates", getsFromCachedCoordinates, 0));
            add(new AdditionalInfo("Maximum memory over time (MB)", maximumMemory, 0));
        }});
    }
    //endregion
}
