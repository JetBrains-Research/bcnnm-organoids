/*
 * Copyright (c) 2018. This file was made as a part of BCNNM@JetBrains_Research work.
 */

package com.synstorm.common.Utils.PlatformLoaders.Configuration;


import com.synstorm.common.Utils.ConfigInterfaces.CodeGeneration.ILogicalExpression;
import com.synstorm.common.Utils.ConfigInterfaces.CodeGeneration.LogicalExpressionFactory;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathway;
import com.synstorm.common.Utils.ConfigInterfaces.LogicObjectTypes;
import com.synstorm.common.Utils.ModelConfiguration.ModelConfig;
import com.synstorm.common.Utils.PlatformLoaders.Objects.CellObjects.Cell;
import com.synstorm.common.Utils.PlatformLoaders.Objects.CellObjects.CellCompartment;
import com.synstorm.common.Utils.PlatformLoaders.Objects.ChemicalObjects.Ligand;
import com.synstorm.common.Utils.PlatformLoaders.Objects.ChemicalObjects.ReceptorDescription;
import com.synstorm.common.Utils.PlatformLoaders.Objects.MechanismsObjects.*;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SignalPointsObjects.ModelSignalPoint;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SpaceObjects.SpaceObject;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Dataset;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.DatasetType;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.ModelStageAction;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.StageAttribute;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.StageParametersFactory;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Stage;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Training;
import com.synstorm.common.Utils.PlatformLoaders.Verifier;
import com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM.BCNNM;
import com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM.Predicate;
import com.synstorm.common.Utils.PlatformLoaders.XMLTools.Individual.Individual;
import com.synstorm.common.Utils.ProbabilitiesEvaluation.DistributionFactory;
import com.synstorm.common.Utils.ProbabilitiesEvaluation.IDistribution;
import com.synstorm.common.Utils.TraceMessageWriter.PriorityTraceWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PlatformConfiguration {
    //region Fields
    private final int SIZE = 100;

    private SystemConfiguration systemConfiguration;
    private MechanismsConfiguration mechanismsConfiguration;
    private CellsConfiguration cellsConfiguration;
    private ReceptorsDescriptionConfiguration receptorsDescriptionConfiguration;
    private LigandsConfiguration ligandsConfiguration;
    private ModelSignalPointsConfiguration modelSignalPointsConfiguration;

    private IndividualConfiguration individualConfiguration;
    //endregion


    //region Constructors

    public PlatformConfiguration() {
    }

    //endregion


    //region Getters and Setters

    public CellsConfiguration getCellsConfiguration() {
        return cellsConfiguration;
    }

    public IndividualConfiguration getIndividualConfiguration() {
        return individualConfiguration;
    }

    public SystemConfiguration getSystemConfiguration() {
        return systemConfiguration;
    }

    public ModelSignalPointsConfiguration getModelSignalPointsConfiguration() {
        return modelSignalPointsConfiguration;
    }

    public LigandsConfiguration getLigandsConfiguration() {
        return ligandsConfiguration;
    }

    //endregion


    //region Public Methods
    public boolean loadConfiguration(BCNNM bcnnmXmlObjectModel, Individual individualXmlObjectModel) {
        try {
            loadLigandsConfiguration(bcnnmXmlObjectModel.getLigands());
            loadReceptorsConfiguration(bcnnmXmlObjectModel.getReceptorsDescription());

            makeSpAndCells(bcnnmXmlObjectModel.getMechanisms(), bcnnmXmlObjectModel.getModelCells());
            setSpAndCellsParameters(bcnnmXmlObjectModel.getMechanisms(), bcnnmXmlObjectModel.getModelCells());

            loadSignalPointsConfiguration(bcnnmXmlObjectModel.getSignalPoints());
            loadSystemConfiguration(bcnnmXmlObjectModel.getSystem());
            loadIndividualConfiguration(individualXmlObjectModel);
        } catch (Exception e) {
            PriorityTraceWriter.println(e.getMessage(), 0);
            return false;
        }
        //final verification
        return Verifier.INSTANCE.verify(this);
    }

    public int getFlatCoordinate(int x, int y, int z) {
        return SIZE * (x * SIZE + y) + z;
    }

    @Nullable
    public ILogicObjectDescription getILogicObjectDescription(@NotNull String type) {
        for (Cell cell : cellsConfiguration.getAllCells()) {
            if (cell.getId().equals(type))
                return cell;
        }

        for (ModelSignalPoint signalPoint : modelSignalPointsConfiguration.getAvailableModelSignalPoints()) {
            if (signalPoint.getId().equals(type))
                return signalPoint;
        }
        return null;
    }

    public MechanismsConfiguration getMechanismsConfiguration() {
        return mechanismsConfiguration;
    }
    //endregion


    //region Private Methods
    private void loadSignalPointsConfiguration(BCNNM.SignalPoints signalPoints) {
        if (signalPoints == null) return;
        PriorityTraceWriter.println("===Loading Signal Points configuration===", 0);
        this.modelSignalPointsConfiguration = new ModelSignalPointsConfiguration();
        signalPoints.getSignalPoint()
                .forEach(sPoint -> {
                    ModelSignalPoint modelSignalPoint = new ModelSignalPoint(sPoint.getType());

                    sPoint.getSPs().getSP().forEach(
                            xSp -> {
                                ISignalingPathway signalingPathway = mechanismsConfiguration.getSignalingPathway(xSp.getType());
                                if (signalingPathway == null)
                                    Verifier.INSTANCE.printErrorAndExit("Signal Point %s uses unknown pathway %s",sPoint.getType(), xSp.getType());

                                modelSignalPoint.addSP(signalingPathway);
                            });

                    modelSignalPointsConfiguration.addModelSignalPoint(modelSignalPoint);
                });
    }

    private void loadSystemConfiguration(@NotNull BCNNM.System system) {
        PriorityTraceWriter.println("===Loading System configuration===", 0);
        this.systemConfiguration = new SystemConfiguration(system.getSpace().getLimit().intValue());
        ModelConfig.INSTANCE.setCapacity(systemConfiguration.getLimit());

        //make pipeline
        system.getPipeline().getStage().forEach(xStage -> {
            Stage stage = makeStage(xStage);

            systemConfiguration.getPipeline().addStage(
                    stage.getStageAttribute(StageAttribute.id).getIntValue(),
                    stage);
        });

        //make training
        if (system.getTraining() != null) {
            Training training = new Training(
                    system.getTraining().getLqWindowSize(),
                    system.getTraining().getClusteringSeedCount()
            );
            system.getTraining().getDatasets().getDataset()
                    .forEach(dSet -> {
                        Dataset dataset = new Dataset(
                                dSet.getId().intValue(),
                                DatasetType.valueOf(dSet.getType()),
                                dSet.getClassCount().intValue(),
                                dSet.getFilename(),
                                dSet.getSize());
                        training.addDataset(dataset);
                    });
            systemConfiguration.setTraining(training);
        }

    }

    private Stage makeStage(@NotNull com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM.Stage action) {
        PriorityTraceWriter.printf(0, "---parsing Stage %s\n", action.getId());
        Stage stage = new Stage();

        //required fields
        stage.setStageAttribute(
                StageAttribute.id,
                StageParametersFactory.INSTANCE.createStageParameter(
                        StageAttribute.id,
                        action.getId())
        );

        stage.setStageAttribute(
                StageAttribute.mode,
                StageParametersFactory.INSTANCE.createStageParameter(
                        StageAttribute.mode,
                        ModelStageAction.valueOf(action.getMode().value()))
        );

        //optional fields
        if (action.getType() != null) {
            stage.setStageAttribute(
                    StageAttribute.type,
                    StageParametersFactory.INSTANCE.createStageParameter(
                            StageAttribute.type,
                            action.getType()));
        }

        if (action.getDatasetType() != null) {
            stage.setStageAttribute(
                    StageAttribute.datasetType,
                    StageParametersFactory.INSTANCE.createStageParameter(
                            StageAttribute.datasetType,
                            DatasetType.valueOf(action.getDatasetType().value())));
        }

        if (action.getSize() != null) {
            stage.setStageAttribute(
                    StageAttribute.size,
                    StageParametersFactory.INSTANCE.createStageParameter(
                            StageAttribute.size,
                            action.getSize().longValue()));
        }

        if (action.getTarget() != null) {
            stage.setStageAttribute(
                    StageAttribute.target,
                    StageParametersFactory.INSTANCE.createStageParameter(
                            StageAttribute.target,
                            action.getTarget()));
        }

        if (action.getScoring() != null) {
            stage.setStageAttribute(
                    StageAttribute.scoring,
                    StageParametersFactory.INSTANCE.createStageParameter(
                            StageAttribute.scoring,
                            action.getScoring()));
        }

        if (action.getCellType() != null) {
            stage.setStageAttribute(
                    StageAttribute.cellType,
                    StageParametersFactory.INSTANCE.createStageParameter(
                            StageAttribute.cellType,
                            cellsConfiguration.getCell(action.getCellType())));
        }

        if (action.getRatio() != null) {
            stage.setStageAttribute(
                    StageAttribute.ratio,
                    StageParametersFactory.INSTANCE.createStageParameter(
                            StageAttribute.ratio,
                            action.getRatio()));
        }

        if (action.getPharma() != null) {
            if (ligandsConfiguration.getLigand(action.getPharma()) == null)
                Verifier.INSTANCE.printErrorAndExit("SYSTEM: Stage %s configuration has unknown ligand as pharma value\n", action.getId());
            stage.setStageAttribute(
                    StageAttribute.pharma,
                    StageParametersFactory.INSTANCE.createStageParameter(
                            StageAttribute.pharma,
                            action.getPharma()));
        }

        if (action.getTime() != null) {
            stage.setStageAttribute(
                    StageAttribute.time,
                    StageParametersFactory.INSTANCE.createStageParameter(
                            StageAttribute.time,
                            action.getTime()));
        }

        if (action.getCustomSP() != null) {
            stage.setStageAttribute(
                    StageAttribute.customSp,
                    StageParametersFactory.INSTANCE.createStageParameter(
                            StageAttribute.customSp,
                            mechanismsConfiguration.getSignalingPathway(action.getCustomSP())));
        }

        return stage;
    }

    private void makeSpAndCells(BCNNM.Mechanisms mechanisms, BCNNM.ModelCells modelCells) {
        this.mechanismsConfiguration = new MechanismsConfiguration();
        this.cellsConfiguration = new CellsConfiguration();
        makeSignalingPathways(mechanisms);
        makeModelCells(modelCells);
    }

    private void setSpAndCellsParameters(BCNNM.Mechanisms mechanisms, BCNNM.ModelCells modelCells) {
        setSignalingPathwaysParameters(mechanisms);
        setCellsParameters(modelCells);
    }

    private void setCellsParameters(@NotNull BCNNM.ModelCells modelCells) {
        PriorityTraceWriter.println("===Cells parameters update===", 0);
        modelCells.getModelCell().forEach(xCell -> {
            Cell cell = cellsConfiguration.getCell(xCell.getId());
            PriorityTraceWriter.printf(0, "Cell %s selected\n", cell.getId());

            if (xCell.getSPs() != null)
                xCell.getSPs().getSP().forEach(xSp -> {
                    if (mechanismsConfiguration.getSignalingPathway(xSp.getType()) == null )
                        Verifier.INSTANCE.printErrorAndExit(
                                "Cell %s has unknown SP %s",
                                xCell.getId(),
                                xSp.getType()
                        );

                    cell.addSp(mechanismsConfiguration.getSignalingPathway(xSp.getType()));
                });

            if (xCell.getCompartments() != null) {
                xCell.getCompartments().getCompartment().forEach(compartment -> {
                    boolean unique = compartment.isUnique() != null ? compartment.isUnique() : false;
                    CellCompartment cellCompartment = new CellCompartment(
                            compartment.getId(),
                            LogicObjectTypes.valueOf(compartment.getBaseType().value()),
                            unique
                    );

                    //future receptors
//                    compartment.getReceptors().getReceptor().forEach(receptor -> {
//                        ReceptorDescription receptorDescription = receptorsDescriptionConfiguration.getReceptor(receptor.getId());
//                        ISignalingPathway iSignalingPathway = mechanismsConfiguration.getSignalingPathway(receptor.getSp());
//
//                        if (receptorDescription == null || iSignalingPathway == null)
//                            printErrorAndExit("Cell %s compartment %s has wrong parameters Receptor: %s, SP: %s\n",
//                                    xCell.getId(),
//                                    compartment.getId(),
//                                    receptor.getId(),
//                                    receptor.getSp()
//                            );
//                        cellCompartment.addReceptor(receptorDescription, iSignalingPathway);
//                    });

                    if (compartment.getSPs() != null)
                        compartment.getSPs().getSP().forEach(sp -> {
                            if (mechanismsConfiguration.getSignalingPathway(sp.getType()) == null)
                                Verifier.INSTANCE.printErrorAndExit(
                                        "Cell's %s compartment %s has unknown SP %s",
                                        xCell.getId(),
                                        compartment.getId(),
                                        sp.getType()
                                );
                            cellCompartment.addSp(mechanismsConfiguration.getSignalingPathway(sp.getType()));
                        });

                    cell.addCompartment(cellCompartment);
                });

            }
        });
    }

    private int[] ligandToId(@NotNull String[] ligands) {
        int[] ids = new int[ligands.length];
        for (int i = 0; i < ligands.length; i++) {
            try {
                ids[i] = ligandsConfiguration.getLigand(ligands[i]).getNum();
            } catch (NullPointerException npe) {
                Verifier.INSTANCE.printErrorAndExit("Unknown ligand %s", ligands[i]);
            }
        }
        return ids;
    }

    private Condition parseCondition(@NotNull com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM.SignalingPathway xmlSignalingPathway) {
        int[] signals = null;
        ILogicalExpression rule = null;
        IDistribution distribution = null;
        int distributionVariable = 0;
        String[] predicateArguments = null;
        final com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM.SignalingPathway.Condition xmlCondition = xmlSignalingPathway.getCondition();
        //parse Predicate
        if (xmlCondition.getPredicate() != null) {
            predicateArguments = xmlCondition.getPredicate().getArgs().split(",");
            signals = ligandToId(predicateArguments);
            rule = null;

            try {
                rule = parseRule(xmlSignalingPathway.getId(), xmlCondition.getPredicate());
            } catch (Exception e) {
                Verifier.INSTANCE.printErrorAndExit("Signaling pathway %s exception while parsingPredicate. Check predicate and its arguments. \n", xmlSignalingPathway.getId());
            }

            //parse Distribution
            if (xmlCondition.getPredicate().getDistribution() != null) {
                if (xmlCondition.getPredicate().getDistributionParameters() == null
                        || xmlCondition.getPredicate().getDistributionVariable() == null)
                    Verifier.INSTANCE.printErrorAndExit("Configuration error: SP %s has distribution with incorrect parameters. If you use distribution, parameters and variable should be set.", xmlSignalingPathway.getId());

                String[] stringArgs = xmlCondition.getPredicate().getDistributionParameters().split(",");
                if (stringArgs.length != 2)
                    Verifier.INSTANCE.printErrorAndExit("Configuration error: SP %s wrong distribution parameters: %s.",
                            xmlSignalingPathway.getId(),
                            xmlCondition.getPredicate().getDistributionParameters());

                distributionVariable = ligandsConfiguration.getLigand(xmlCondition.getPredicate().getDistributionVariable()).getNum();
                final int tempLambdaVar = distributionVariable; //variable in lambda should be effectively final.
                if (!Arrays.stream(signals).filter(obj -> obj == tempLambdaVar).findAny().isPresent())
                    Verifier.INSTANCE.printErrorAndExit("Configuration error: SP %s condition distributionVariable value not presented in Predicate's args.", xmlSignalingPathway.getId());

                double arg0 = Double.parseDouble(stringArgs[0]);
                double arg1 = Double.parseDouble(stringArgs[1]);
                double[] args = new double[]{arg0, arg1};
                distribution = DistributionFactory.INSTANCE.generateDistribution(
                        xmlCondition.getPredicate().getDistribution().value(), args);
            }
        }

        //parse ConditionParameter
        ConditionParameter conditionParameter = null;
        if (xmlCondition.getConditionParameter() != null) {
            final com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM.ConditionParameter xmlConditionParameter = xmlCondition.getConditionParameter();

            if (predicateArguments != null && !Arrays.asList(predicateArguments).contains(xmlConditionParameter.getSelectionLigand()))
                Verifier.INSTANCE.printErrorAndExit("ConditionParameter ligand %s is incorrect in SP %s",
                        xmlConditionParameter.getSelectionLigand(),
                        xmlSignalingPathway.getId());



            final String selectionType = xmlConditionParameter.getSelectionType();
            final int ligand = ligandsConfiguration.getLigand(xmlConditionParameter.getSelectionLigand()).getNum();

            conditionParameter = new ConditionParameter(ligand, selectionType);
        }

        return new Condition(conditionParameter, rule, signals, distribution, distributionVariable);
    }

    private void setSignalingPathwaysParameters(@NotNull BCNNM.Mechanisms mechanisms) {
        PriorityTraceWriter.println("===Mechanisms parameters update===", 0);
        mechanisms.getMechanism().forEach(
                xmlMechanism -> {
                    PriorityTraceWriter.printf(0, "Mechanism %s selected \n", xmlMechanism.getType());

                    xmlMechanism.getSignalingPathway().forEach(
                            xmlSignalingPathway -> {
                                PriorityTraceWriter.printf(0, "----Set parameters to %s\n", xmlSignalingPathway.getId());
//                                Mechanism mechanism = mechanismsConfiguration.getMechanism(xmlMechanism.getType());
//                                SignalingPathway signalingPathway = mechanism.getSignalingPathway(xmlSignalingPathway.getId());
                                SignalingPathway signalingPathway = mechanismsConfiguration.getMechanism(xmlMechanism.getType())
                                        .getSignalingPathway(xmlSignalingPathway.getId());


                                final com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM.SignalingPathway.Condition xmlCondition = xmlSignalingPathway.getCondition();
                                //parse condition
                                if (xmlCondition != null)
                                    signalingPathway.setCondition(parseCondition(xmlSignalingPathway));

                                //parse ExecuteParameter
                                if (xmlSignalingPathway.getExecuteParameter() != null) {
                                    if (xmlSignalingPathway.getExecuteParameter().getCreationType() != null) {

//                                        final Cell targetCell = cellsConfiguration.getCell(xmlSignalingPathway.getExecuteParameter().getCreationType());
//                                        if (targetCell == null)
//                                            printErrorAndExit("Signaling pathway %s contains unknown ObjectCreateParameters: %s. Check SP and Cells configuration.",
//                                                    xmlSignalingPathway.getId(),
//                                                    xmlSignalingPathway.getExecuteParameter().getCreationType()
//                                            );
                                        signalingPathway.setExecuteParameter("Object",
                                                new String[]{xmlSignalingPathway.getExecuteParameter().getCreationType()});
                                    } else if (xmlSignalingPathway.getExecuteParameter().getProbability() != null) {
                                        signalingPathway.setExecuteParameter("Probability",
                                                new Double[]{xmlSignalingPathway.getExecuteParameter().getProbability()});
                                    } else if (xmlSignalingPathway.getExecuteParameter().getLigand() != null) {
                                        final Ligand ligand = ligandsConfiguration.getLigand(xmlSignalingPathway.getExecuteParameter().getLigand());
                                        if (ligand == null) {
                                            Verifier.INSTANCE.printErrorAndExit("SP %s uses non-existent ligand %s.",
                                                    xmlSignalingPathway.getId(),
                                                    xmlSignalingPathway.getExecuteParameter().getLigand());
                                        }
                                        signalingPathway.setExecuteParameter("Chemical",
                                                new Integer[]{ligand.getNum(), ligand.getRadius()});
                                    }
                                }

                                //parse Cascades
                                if (xmlSignalingPathway.getCascades() != null) {
                                    if (xmlSignalingPathway.getCascades().getCascade().size() != 0) {
                                        xmlSignalingPathway.getCascades().getCascade()
                                                .forEach(cascade -> {
                                                    if (mechanismsConfiguration.getSignalingPathway(cascade.getPathway()) == null )
                                                        Verifier.INSTANCE.printErrorAndExit("Cascade pathway %s for SP %s does not exist.",
                                                                cascade.getPathway(),
                                                                xmlSignalingPathway.getId()
                                                        );
                                                    final ConditionType conditionType = ConditionType.valueOf(cascade.getCondition());
                                                    if (conditionType != ConditionType.Default && xmlCondition == null)
                                                        Verifier.INSTANCE.printErrorAndExit("Null condition for cascade in %s. You can not use OnConditionTrue/False with empty predicate.", xmlSignalingPathway.getId());
                                                    signalingPathway.addSignalingPathway(
                                                            conditionType,
                                                            CascadeAction.valueOf(cascade.getAction().value()),
                                                            Objects.requireNonNull(mechanismsConfiguration.getSignalingPathway(cascade.getPathway())));
                                                });
                                    }
                                }
                            }
                    );
                });
    }




    private void makeSignalingPathways(BCNNM.Mechanisms mechanisms) {
        PriorityTraceWriter.println("===Loading Signaling Pathways configuration===", 0);
        mechanisms.getMechanism().forEach(xMechanism -> {
            String id = xMechanism.getType();
            System.out.printf("Parsing mechanism %s\n", id);
            Mechanism mechanism = new Mechanism(id);
            xMechanism.getSignalingPathway().forEach(sp -> {
                String spId = sp.getId();
                System.out.printf("---Parsing SP %s\n", spId);
                if (mechanismsConfiguration.getSignalingPathway(spId) != null)
                    Verifier.INSTANCE.printErrorAndExit("SP %s in mechanism %s is non-unique. There is another pathway with the same name.", spId, xMechanism.getType());

                int duration = sp.getDuration().intValue();
                boolean initial = sp.isInitial();

                SignalingPathway signalingPathway = new SignalingPathway(mechanism.getId(), spId, duration, initial);
                mechanism.addSignalingPathway(signalingPathway);
            });
            mechanismsConfiguration.addMechanism(mechanism);
        });
    }

    private ILogicalExpression parseRule(String spName, Predicate predicate) throws Exception {
        return LogicalExpressionFactory.INSTANCE.generateFunction(spName, predicate.getRule(), predicate.getArgs());
    }

    private void makeModelCells(@NotNull BCNNM.ModelCells modelCells) {
        PriorityTraceWriter.println("===Loading Cells configuration===", 0);
        modelCells.getModelCell().forEach(xCell -> {
            boolean immovable = xCell.isImmovable() != null ? xCell.isImmovable() : false;
            Cell cell = new Cell(
                    xCell.getId(),
                    LogicObjectTypes.valueOf(xCell.getBaseType().value()),
                    immovable
            );
            cellsConfiguration.addCell(cell);
        });
    }

    private void loadReceptorsConfiguration(BCNNM.ReceptorsDescription receptors) {
        if (receptors == null) return;

        PriorityTraceWriter.println("===Loading Receptors configuration===", 0);
        this.receptorsDescriptionConfiguration = new ReceptorsDescriptionConfiguration();
        receptors.getReceptorDescription().forEach(receptor -> {
            if (ligandsConfiguration.getLigand(receptor.getLigand()) == null)
                Verifier.INSTANCE.printErrorAndExit("Receptor %s has unknown ligand %s", receptor.getId(), receptor.getLigand());

            receptorsDescriptionConfiguration.addReceptor(
                    new ReceptorDescription(
                            receptor.getId(),
                            ligandsConfiguration.getLigand(receptor.getLigand())));
        });
    }

    private void loadLigandsConfiguration(@NotNull BCNNM.Ligands ligands) {
        PriorityTraceWriter.println("===Loading Ligands configuration===", 0);
        this.ligandsConfiguration = new LigandsConfiguration();
        for (com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM.Ligand xLigand : ligands.getLigand()) {
            ligandsConfiguration.addLigand(
                    new Ligand(
                            ligands.getLigand().indexOf(xLigand),
                            xLigand.getId(),
                            xLigand.getRadius().intValue()
                    )
            );
        }

        ModelConfig.INSTANCE.setSignalsRadius(ligandsConfiguration.getLigandsRadius());
    }

    private void loadIndividualConfiguration(@NotNull Individual individual) {
        PriorityTraceWriter.println("===Loading Individual configuration===", 0);
        this.individualConfiguration = new IndividualConfiguration();

        final Set<Integer> controlSet = new HashSet<>();
        individual.getObjects().getObject()
                .forEach(object -> {
                    String dendriteConnections = object.getDendriteConnections();
                    String axonConnections = object.getAxonConnections();
                    final int[] coordinate = {object.getX(), object.getY(), object.getZ()};
                    if (cellsConfiguration.getCell(object.getType()) != null
                            && !controlSet.add(getFlatCoordinate(coordinate[0], coordinate[1], coordinate[2]))) {
                        PriorityTraceWriter.println("Cell #" + object.getId()
                                +  " with coordinates "
                                + Arrays.toString(coordinate)
                                + " have duplicate coordinates with previous one.", 0);
                        System.exit(-1);
                    }
                    individualConfiguration.addSpaceObject(
                            new SpaceObject(
                                    object.getId().longValue(),
                                    object.getGroup(),
                                    coordinate,
                                    getILogicObjectDescription(object.getType()),
                                    axonConnections,
                                    dendriteConnections)
                    );
                });

        if (individual.getSpaceState() != null && individual.getSpaceState().getState() != null)
            individual.getSpaceState().getState()
                    .forEach(state -> individualConfiguration.addSpaceState(
                            state.getId().longValue(),
                            state.getLigand().intValue(),
                            state.getRadius().intValue()));
    }


    //endregion

}
