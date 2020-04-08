package com.synstorm.SimulationModel.Model;

import com.synstorm.DES.EventsDispatcher;
import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.SimulationModel.LogicObjectR.LogicObjectFactory;
import com.synstorm.SimulationModel.SpaceShell.Shell;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathway;
import com.synstorm.common.Utils.Mechanisms.MechanismResponse.EmptyResponse;
import com.synstorm.common.Utils.ModelConfiguration.ModelConfig;
import com.synstorm.common.Utils.ModelExport.IModelDataExporter;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;
import gnu.trove.set.TIntSet;
import org.uncommons.maths.random.MersenneTwisterRNG;

import java.nio.ByteBuffer;
import java.util.*;

@Model_v1
public enum Model {
    INSTANCE;

    public static final IEventExecutionResult emptyResponse = new EmptyResponse();

    private IndividualR individual;
    private Shell spaceShell;
    private EventsDispatcher eventsDispatcher;
    private List<IModelDataExporter> simExporters;
    private Random random;
    private long randomCount;
    private int lObjectIdCounter;

    private long currentTick;
    private long nextTick;

    public void initModel(List<IModelDataExporter> exporters, int seed) {
        final UUID id = UUID.randomUUID();
        LogicObjectFactory.INSTANCE.init();
        spaceShell = new Shell(ModelConfig.INSTANCE.getCapacity(), ModelConfig.INSTANCE.getSignalsRadius());
        eventsDispatcher = new EventsDispatcher();
        final byte[] seedByte = ByteBuffer.allocate(16).putInt(seed).array();
        random = new MersenneTwisterRNG(seedByte);
        simExporters = exporters;
        randomCount = 0;
        lObjectIdCounter = 0;

        individual = new IndividualR(id);
    }

    public IndividualR getIndividual() {
        return individual;
    }

    public Shell getSpaceShell() {
        return spaceShell;
    }

    public EventsDispatcher getEventsDispatcher() {
        return eventsDispatcher;
    }

    public Random getRandom() {
        return random;
    }

    public int nextLObjectId() {
        return lObjectIdCounter++;
    }

    public double nextDouble() {
        randomCount++;
        return random.nextDouble();
    }

    public int nextInt() {
        randomCount++;
        return random.nextInt();
    }

    public int nextInt(int bound) {
        randomCount++;
        return random.nextInt(bound);
    }

    public long getCurrentTick() {
        return currentTick;
    }

    public long getNextTick() {
        return nextTick;
    }

    public void updateCurrentToNext() {
        currentTick = nextTick;
    }

    public void updateNextTick(long tick) {
        nextTick = tick;
    }

    public void proceedExportAndStatistic(ISimulationEvent event) {
        simExporters.forEach(exporter -> exporter.write(event));
//        statistics.proceedEvent(event);
    }

    public void traumatizeIndividual(double ratio, Set<String> cellTypes, ISignalingPathway customPathway) {
        final TIntSet validCellIds = individual.getObjectIdsByTypes(cellTypes);
        ArrayList<Integer> cells = spaceShell.selectCellsForTrauma(ratio, validCellIds);
        individual.instantTraumatizing(cells, customPathway);
    }
}
