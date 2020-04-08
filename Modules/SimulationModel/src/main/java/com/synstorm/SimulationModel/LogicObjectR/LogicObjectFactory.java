package com.synstorm.SimulationModel.LogicObjectR;

import com.synstorm.SimulationModel.CellLineage.R.GlialCell;
import com.synstorm.SimulationModel.CellLineage.R.Neuron;
import com.synstorm.SimulationModel.CellLineage.R.StemCell;
import com.synstorm.SimulationModel.Compartments.Axon;
import com.synstorm.SimulationModel.Compartments.AxonalTerminal;
import com.synstorm.SimulationModel.Compartments.DendriteTree;
import com.synstorm.SimulationModel.Compartments.DendriticSpine;
import com.synstorm.SimulationModel.Connections.Synapse;
import com.synstorm.SimulationModel.Model.Model;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;
import com.synstorm.common.Utils.ConfigInterfaces.LogicObjectTypes;
import org.jetbrains.annotations.NotNull;

@Model_v1
public enum LogicObjectFactory {
    INSTANCE;

    //region Static Constructors
    public void init() {
        LogicObjectTypes.StemCell.setCreateExecutor(LogicObjectFactory::createStemCell);
        LogicObjectTypes.NeuronCell.setCreateExecutor(LogicObjectFactory::createNeuronCell);
        LogicObjectTypes.GlialCell.setCreateExecutor(LogicObjectFactory::createGlialCell);
        LogicObjectTypes.Axon.setCreateExecutor(LogicObjectFactory::createAxon);
        LogicObjectTypes.AxonalTerminal.setCreateExecutor(LogicObjectFactory::createAxonalTerminal);
        LogicObjectTypes.DendriteTree.setCreateExecutor(LogicObjectFactory::createDendriteTree);
        LogicObjectTypes.DendriticSpine.setCreateExecutor(LogicObjectFactory::createDendriticSpine);
        LogicObjectTypes.Synapse.setCreateExecutor(LogicObjectFactory::createSynapse);
        LogicObjectTypes.SignalPoint.setCreateExecutor(LogicObjectFactory::createSignalPoint);
    }
    //endregion

    //region Private Methods
    @NotNull
    private static Cell createStemCell(int parentId, ILogicObjectDescription description) {
        final int lObjectId = Model.INSTANCE.nextLObjectId();
        return new StemCell(lObjectId, parentId, description);
    }

    @NotNull
    private static Neuron createNeuronCell(int parentId, ILogicObjectDescription description) {
        final int lObjectId = Model.INSTANCE.nextLObjectId();
        return new Neuron(lObjectId, parentId, description);
    }

    private static Cell createGlialCell(int parentId, ILogicObjectDescription description) {
        final int lObjectId = Model.INSTANCE.nextLObjectId();
        return new GlialCell(lObjectId, parentId, description);
    }

    @NotNull
    private static Compartment createAxon(int parentId, ILogicObjectDescription description) {
        final int lObjectId = Model.INSTANCE.nextLObjectId();
        return new Axon(lObjectId, parentId, description);
    }

    @NotNull
    private static Compartment createAxonalTerminal(int parentId, ILogicObjectDescription description) {
        final int lObjectId = Model.INSTANCE.nextLObjectId();
        return new AxonalTerminal(lObjectId, parentId, description);
    }

    @NotNull
    private static Compartment createDendriteTree(int parentId, ILogicObjectDescription description) {
        final int lObjectId = Model.INSTANCE.nextLObjectId();
        return new DendriteTree(lObjectId, parentId, description);
    }

    @NotNull
    private static Compartment createDendriticSpine(int parentId, ILogicObjectDescription description) {
        final int lObjectId = Model.INSTANCE.nextLObjectId();
        return new DendriticSpine(lObjectId, parentId, description);
    }

    @NotNull
    private static Synapse createSynapse(int parentId, ILogicObjectDescription description) {
        final int lObjectId = Model.INSTANCE.nextLObjectId();
        return new Synapse(lObjectId, parentId, description);
    }

    @NotNull
    private static SignalPoint createSignalPoint(int parentId, ILogicObjectDescription description) {
        final int lObjectId = Model.INSTANCE.nextLObjectId();
        return new SignalPoint(lObjectId, parentId, description);
    }
    //endregion
}
