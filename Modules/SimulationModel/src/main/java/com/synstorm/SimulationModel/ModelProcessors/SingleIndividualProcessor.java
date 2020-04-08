package com.synstorm.SimulationModel.ModelProcessors;

import com.synstorm.SimulationModel.CellLineage.R.Neuron;
import com.synstorm.SimulationModel.Connections.Synapse;
import com.synstorm.SimulationModel.Loader;
import com.synstorm.SimulationModel.LogicObjectR.Compartment;
import com.synstorm.SimulationModel.Model.IndividualR;
import com.synstorm.SimulationModel.Model.Model;
import com.synstorm.SimulationModel.Model.Pipeline.ModelPipeline;
import com.synstorm.SimulationModel.ModelAgent.SingleIndividualAgent;
import com.synstorm.SimulationModel.SpaceShell.Shell;
import com.synstorm.SimulationModel.Utils.ModelStrings;
import com.synstorm.common.Utils.ConfigLoader.ModelLoader;
import com.synstorm.common.Utils.ConsoleProgressBar.IndividualProgressBar;
import com.synstorm.common.Utils.Details.IndividualDetails;
import com.synstorm.common.Utils.FileModelExport.XmlIndividualExporter;
import com.synstorm.common.Utils.ModelStatistic.IndividualStatistics;
import com.synstorm.common.Utils.PlatformLoaders.XMLTools.Individual.Object;
import com.synstorm.common.Utils.PlatformLoaders.XMLTools.Individual.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.math.BigInteger;
import java.util.List;

/**
 * Class for processing fixed tick modeling of one individual without learning process
 * Created by dvbozhko on 14/06/16.
 */
public class SingleIndividualProcessor extends BaseModelProcessor {
    //region Fields
    //endregion

    //region Constructors
    public SingleIndividualProcessor() {
        super();
        progressBar = new IndividualProgressBar(experimentName, ModelPipeline.INSTANCE.getStagesLength(), tStart);
    }
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    @Override
    protected boolean startCalculations() {
        Model.INSTANCE.initModel(simulationExporters, modelSeedNum);
        SingleIndividualAgent singleIndividualAgent = new SingleIndividualAgent(formIndividualObject(), progressBar, this);
        xmlIndividualExporter = new XmlIndividualExporter(singleIndividualAgent.getId().toString());
        xmlIndividualExporter.open(ModelStrings.RESULTS_PATH + simulationStartTime + File.separator);
        xmlIndividualExporter.writeHeader(singleIndividualAgent.getId().toString());
        submitIndividualAgent(singleIndividualAgent);
        return true;
    }

    @Override
    protected void proceedIndividualResult(IndividualStatistics statistics) {
        calculatedAgentsCount++;
    }

    @Override
    protected void proceedAdditionalActionsBeforeFinish() {
        saveIndividual();
    }

    private void saveIndividual() {
        xmlIndividualExporter.writeObjectsHeader();

        Shell shell = Model.INSTANCE.getSpaceShell();
        Model.INSTANCE.getIndividual().getIndLObjects().forEachEntry((k, v) -> {
            if (v instanceof Compartment || v instanceof Synapse)
                return true;

            String axonConnections = null;
            String dendriteConnections = null;
            //if object is neuron, it has axon and we should export its coordinates and connections
            if (v instanceof Neuron && ((Neuron) v).hasAxon()) {
                Neuron neuron = (Neuron) v;
                // if axon have not connections we will not write xml string
                if (neuron.getAxonConnections().length != 0) {
                    axonConnections = makeAxonConnections(neuron);
                }
            }

            if (v instanceof Neuron && ((Neuron) v).hasDendriteTree()) {
                Neuron neuron = (Neuron) v;
                // if dendrite have not connections we will not write xml string
                if (neuron.getDendriteConnections().length != 0) {
                    dendriteConnections = makeDendriteConnections(neuron);
                }
            }
            //write entire object
            final int[] objectCoordinate = Model.INSTANCE.getSpaceShell().getCoordinate(k);
            Object object = new Object();
            object.setId(BigInteger.valueOf(k));
            object.setGroup(v.getType());
            object.setType(v.getDescription().getId());
            object.setX(objectCoordinate[0]);
            object.setY(objectCoordinate[1]);
            object.setZ(objectCoordinate[2]);
            if (axonConnections != null)
                object.setAxonConnections(axonConnections);
            if (dendriteConnections != null)
                object.setDendriteConnections(dendriteConnections);
            xmlIndividualExporter.writeLogicObject(object);
            return true;
        });
        xmlIndividualExporter.writeObjectsFooter();

        //export Space

        xmlIndividualExporter.writeSpaceStateHeader();
        final List<Integer> availableLigands = Loader.INSTANCE.getLigands();
        Model.INSTANCE.getIndividual().getIndLObjects().forEachEntry((k, v) -> {
            availableLigands.forEach(ligand -> {
                int ligandRadius = shell.getSignalRadiusById(k, ligand);
                if (shell.getSignalRadiusById(k, ligand) > 0) {
                    State state = new State();
                    state.setId(BigInteger.valueOf(k));
                    state.setLigand(BigInteger.valueOf(ligand));
                    state.setRadius(BigInteger.valueOf(ligandRadius));
                    xmlIndividualExporter.writeSpaceState(state);
                }
            });
            return true;
        });
        xmlIndividualExporter.writeSpaceStateFooter();
        xmlIndividualExporter.writeFooter();
    }

    @Override
    protected @Nullable IndividualDetails getNextIndividualToCompute() {
        final IndividualDetails nextIndividualDetails = ModelLoader.getIndividualDetails();

//        if(SimulationArguments.INSTANCE.isHasGenes()) {
//            recalculateActions(nextIndividualDetails);
//        }

        return nextIndividualDetails;
    }

    @Override
    protected IndividualR formIndividualObject() {
        return Model.INSTANCE.getIndividual();
    }
    //endregion

    //region Private Methods
    @NotNull
    private String makeAxonConnections(@NotNull Neuron neuron) {
        final StringBuilder axonConnectionsStringBuilder = new StringBuilder();
        for (int id : neuron.getAxonConnections())
            axonConnectionsStringBuilder.append(id).append(",");
        axonConnectionsStringBuilder.delete(axonConnectionsStringBuilder.length() - 1, axonConnectionsStringBuilder.length());
        return axonConnectionsStringBuilder.toString();
    }

    @NotNull
    private String makeDendriteConnections(@NotNull Neuron neuron) {
        final StringBuilder dendriteConnectionsStringBuilder = new StringBuilder();
        for (int id : neuron.getDendriteConnections())
            dendriteConnectionsStringBuilder.append(id).append(",");
        dendriteConnectionsStringBuilder.delete(dendriteConnectionsStringBuilder.length() - 1, dendriteConnectionsStringBuilder.length());
        return dendriteConnectionsStringBuilder.toString();
    }
    //endregion
}