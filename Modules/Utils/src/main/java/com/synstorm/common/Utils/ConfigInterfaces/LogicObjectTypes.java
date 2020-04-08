package com.synstorm.common.Utils.ConfigInterfaces;


import com.synstorm.common.Utils.Annotations.Classes.Model_v1;

@Model_v1
public enum LogicObjectTypes {
    // Cells
    StemCell,
    NeuronCell,
    GlialCell,

    // Compartments
    Axon,
    AxonalTerminal,
    DendriteTree,
    DendriticSpine,

    // Connections
    Synapse,
    GapJunction,

    // SignalPoints
    SignalPoint;

    private CreateExecutor createExecutor;

    public ILogicObject create(int parentId, ILogicObjectDescription description) {
        return createExecutor.execute(parentId, description);
    }
    
    public void setCreateExecutor(CreateExecutor executor) {
        createExecutor = executor;
    }

    @FunctionalInterface
    public interface CreateExecutor {
        ILogicObject execute(int parentId, ILogicObjectDescription description);
    }
}
