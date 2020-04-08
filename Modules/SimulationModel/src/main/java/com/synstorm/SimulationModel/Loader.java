package com.synstorm.SimulationModel;

import com.synstorm.SimulationModel.Model.Pipeline.ModelPipeline;
import com.synstorm.SimulationModel.Model.Pipeline.ModelStage;
import com.synstorm.SimulationModel.Model.Pipeline.StageFactory;
import com.synstorm.common.Utils.ModelConfiguration.ModelConfig;
import com.synstorm.common.Utils.PlatformLoaders.Configuration.PlatformConfiguration;
import com.synstorm.common.Utils.PlatformLoaders.Objects.ChemicalObjects.Ligand;
import com.synstorm.common.Utils.PlatformLoaders.Objects.SystemObjects.Parameters.StageAttribute;
import com.synstorm.common.Utils.PlatformLoaders.PlatformLoader;
import com.synstorm.common.Utils.TraceMessageWriter.PriorityTraceWriter;
import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.stream.Collectors;

public enum Loader {
    INSTANCE;

    private PlatformLoader platformLoader;
    private String configPath;

    @Contract(pure = true)
    public String getConfigPath() {
        return configPath;
    }

    Loader() {
    }

    public void load(String configPath) {
        platformLoader = new PlatformLoader(configPath);
        if (!platformLoader.load()) {
            PriorityTraceWriter.println("Configuration not loaded.", 0);
            System.exit(-1);
        }

        makeModelObjects();
        makeModelPipeline();
        PriorityTraceWriter.println("Configuration loaded.", 0);
    }

    public List<Integer> getLigands() {
        return platformLoader.getPlatformConfiguration()
                .getLigandsConfiguration()
                .getAllLigands()
                .stream()
                .map(Ligand::getNum)
                .collect(Collectors.toList());
    }

    public PlatformConfiguration getPlatformConfiguration() {
        return platformLoader.getPlatformConfiguration();
    }


    private void makeModelObjects() {
//        platformLoader.getPlatformConfiguration().getCellsConfiguration().getAllCells()
//                .forEach(cell -> ModelConfig.INSTANCE.addLogicObjectDescription(cell.getId(), cell));

        platformLoader.getPlatformConfiguration().getCellsConfiguration().getAllCells()
                .forEach(cell -> ModelConfig.INSTANCE.addCellObjectDescriptions(cell.getAllObjectDescriptions()));

        platformLoader.getPlatformConfiguration().getModelSignalPointsConfiguration().getAvailableModelSignalPoints()
                .forEach(modelSignalPoint -> ModelConfig.INSTANCE.addLogicObjectDescription(modelSignalPoint.getId(), modelSignalPoint));

        platformLoader.getPlatformConfiguration().getIndividualConfiguration().getSpaceObjects()
                .forEach(ModelConfig.INSTANCE::addObjectInstance);

    }

    private void makeModelPipeline() {
        /*List<Stage> stageList = platformLoader.getPlatformConfiguration().getSystemConfiguration().getPipeline().getStageList();
        for (int i = 0; i < stageList.size(); i++) {
            Stage stage = stageList.get(i);
            ModelStage modelStage = new ModelStage(
                    i,
                    stage.getStageAttribute(StageAttribute.mode).getModelStageAction(),
                    stage.getStageAttributes()
            );

            ModelPipeline.INSTANCE.addStage(i, modelStage);
        }*/

        platformLoader.getPlatformConfiguration().getSystemConfiguration().getPipeline().getStageList()
                .forEach(xStage -> {
                    ModelStage modelStage = StageFactory.INSTANCE.createStage(
                            xStage.getStageAttribute(StageAttribute.mode).getModelStageAction(),
                            xStage);
                    ModelPipeline.INSTANCE.addStage(modelStage.getId(), modelStage);
                });
    }

//    public void saveIndividual(IndividualConfiguration individualConfiguration, String pathPrefix, String individualXmlFilename) {
//        platformLoader.saveIndividualConfiguration(individualConfiguration, pathPrefix, individualXmlFilename);
//    }
}
