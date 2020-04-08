package com.synstorm.common.Utils.PlatformLoaders;

import com.synstorm.DES.AllowedEvent;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathway;
import com.synstorm.common.Utils.Mechanisms.MechanismParameters.ObjectCreateParameters;
import com.synstorm.common.Utils.PlatformLoaders.Configuration.CellsConfiguration;
import com.synstorm.common.Utils.PlatformLoaders.Configuration.MechanismsConfiguration;
import com.synstorm.common.Utils.PlatformLoaders.Configuration.PlatformConfiguration;
import com.synstorm.common.Utils.PlatformLoaders.Objects.CellObjects.Cell;
import com.synstorm.common.Utils.TraceMessageWriter.PriorityTraceWriter;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by human-research on 2019-03-07.
 */
public enum Verifier {
    INSTANCE;

    Verifier() {
    }

    //region Public Methods
    public boolean verify(@NotNull PlatformConfiguration platformConfiguration) {
        PriorityTraceWriter.println("===Logical verification===", 0);
        boolean verified = true;
        final MechanismsConfiguration mechanismsConfiguration = platformConfiguration.getMechanismsConfiguration();
        final CellsConfiguration cellsConfiguration = platformConfiguration.getCellsConfiguration();


        for (Cell cell : platformConfiguration.getCellsConfiguration().getAllCells()) {
            final Set<ISignalingPathway> cellSPs = new HashSet<>(); //be careful with existing collections!
            cellSPs.addAll(cell.getSignalingPathways());
            cell.getAllObjectDescriptions().values().forEach(ilod -> cellSPs.addAll(ilod.getSignalingPathways()));

            for (ISignalingPathway sp : cellSPs) {
                Set<AllowedEvent> allCascades = new HashSet<>();
                allCascades.addAll(sp.getExecutingDefaultSignalingPathways());
                allCascades.addAll(sp.getDisruptingDefaultSignalingPathways());
                allCascades.addAll(sp.getExecutingOnConditionTrueSignalingPathways());
                allCascades.addAll(sp.getExecutingOnConditionFalseSignalingPathways());
                allCascades.addAll(sp.getDisruptingOnConditionTrueSignalingPathways());
                allCascades.addAll(sp.getDisruptingOnConditionFalseSignalingPathways());
                for (AllowedEvent allowedEvent : allCascades) {
                    //1. check if SP in cascade exist in SPs of cell
                    if (cellSPs.stream().noneMatch(s -> s.getPathway().getName().equals(allowedEvent.getName()))) {
                        PriorityTraceWriter.printf(0, "CELL: %s -> SP: %s -> Cascade pathway %s not presented in configuration of cell.", cell.getId(), sp.getPathway().getName(), allowedEvent.getName());
                        verified = false;
                    }

                    //2. Check if object create parameters links to allowed logic object
                    ISignalingPathway signalingPathway = Objects.requireNonNull(
                            mechanismsConfiguration.getSignalingPathway(allowedEvent.getName()));
                    if (signalingPathway.getExecutingParameters() != null) {
                        if (signalingPathway.getExecutingParameters() instanceof ObjectCreateParameters) {
                            ObjectCreateParameters ocp = (ObjectCreateParameters) signalingPathway.getExecutingParameters();
                            switch (Objects.requireNonNull(
                                    mechanismsConfiguration.getMechanismBySignalingPathway(
                                            allowedEvent.getName()).getId())) {
                                case DivideAndDifferentiate: {
                                    if (cellsConfiguration.getCell(ocp.getDiffType()) == null) {
                                        PriorityTraceWriter.printf(0, "CELL: %s -> SP: %s tries to asymmetrically divide to unknown type of cell %s\n", cell.getId(), allowedEvent.getName(), ocp.getDiffType());
                                        verified = false;
                                    }
                                    break;
                                }
                                case Differentiate: {
                                    if (cellsConfiguration.getCell(ocp.getDiffType()) == null) {
                                        PriorityTraceWriter.printf(0, "CELL: %s -> SP: %s tries to differentiate to unknown type of cell %s\n", cell.getId(), allowedEvent.getName(), ocp.getDiffType());
                                        verified = false;
                                    }
                                    break;
                                }
                                case CompartmentForm: {
                                    if (cell.getAllObjectDescriptions().values().stream().noneMatch(o -> o.getId().equals(ocp.getDiffType()))) {
                                        PriorityTraceWriter.printf(0, "CELL: %s -> SP: %s tries to create compartment %s not presented in configuration of cell.\n", cell.getId(), allowedEvent.getName(), ocp.getDiffType());
                                        verified = false;
                                    }
                                    break;
                                }
                                default:
                                    break;

                            }
                        }
                    }
                }
            }
        }
        return verified;
    }


    public void printErrorAndExit(String format, Object... args) {
        final String header = "ERROR: CONFIGURATION: ";
        PriorityTraceWriter.printf(0, header + format, args);
        System.exit(-1);
    }
    //endregion


    //region Private Methods

    //endregion


}