package com.synstorm.common.Utils.ConfigLoader;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.ConfigWorker.ConfigNode;
import com.synstorm.common.Utils.ConfigWorker.ConfigReader;
import com.synstorm.common.Utils.ConfigurationStrings.FilePathsStrings;
import com.synstorm.common.Utils.ConfigurationStrings.XmlQueryStrings;
import com.synstorm.common.Utils.Details.GPDetails;
import com.synstorm.common.Utils.Details.IDetails;
import com.synstorm.common.Utils.SpaceUtils.CoordinateUtils;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;
import com.synstorm.common.Utils.SpaceUtils.IllegalDimensionalSizeException;
import com.synstorm.common.Utils.TraceMessageWriter.PriorityTraceWriter;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by human-research on 21/07/15.
 */

@Model_v0
@NonProductionLegacy

public class SpaceLoader extends BaseLoader implements ILoader {
    //region Fields
    private int spaceVolume;
    private Map<Integer, GPDetails> gpDetailsMap;
    private int counter;
    //endregion

    //region Constructors
    public SpaceLoader() {
        this.counter = 0;
        this.gpDetailsMap = new HashMap<>();
    }
    //endregion

    //region Getters and Setters
    @Override
    @Nullable
    public IDetails getDetails(String key) {
        return null;
    }

    public long getSpaceVolume() {
        return spaceVolume;
    }

    public void setSpaceVolume(int spaceVolume) throws IllegalDimensionalSizeException {
        CoordinateUtils.INSTANCE.initializeUtils(spaceVolume);
        this.spaceVolume = spaceVolume;
    }
    //endregion

    //region Public Methods
    @Override
    public void load(String configFileName, String templateFileName) {
        initialize(configFileName, templateFileName);
        spaceVolume = Integer.parseInt(configReader.getAttributeValue(XmlQueryStrings.SPACE_VOLUME));
        try {
            CoordinateUtils.INSTANCE.initializeUtils(spaceVolume);
        } catch (IllegalDimensionalSizeException e) {
            e.printStackTrace();
        }
        List<String> gpsList = configReader.getAttributesValueList(XmlQueryStrings.SPACE_GPS_ALL);
        for (String gp : gpsList) {
            Integer xCoord = Integer.parseInt(configReader.getAttributeValue(XmlQueryStrings.SPACE_GP_BY_ID + gp + XmlQueryStrings.SPACE_ATTR_X));
            Integer yCoord = Integer.parseInt(configReader.getAttributeValue(XmlQueryStrings.SPACE_GP_BY_ID + gp + XmlQueryStrings.SPACE_ATTR_Y));
            Integer zCoord = Integer.parseInt(configReader.getAttributeValue(XmlQueryStrings.SPACE_GP_BY_ID + gp + XmlQueryStrings.SPACE_ATTR_Z));
            ICoordinate coordinate = CoordinateUtils.INSTANCE.createCoordinate(xCoord, yCoord, zCoord);
            Integer radius = Integer.parseInt(configReader.getAttributeValue(XmlQueryStrings.SPACE_GP_BY_ID + gp + XmlQueryStrings.SPACE_ATTR_RADIUS));
            boolean calculateKp = Boolean.parseBoolean(configReader.getAttributeValue(XmlQueryStrings.SPACE_GP_BY_ID + gp + XmlQueryStrings.SPACE_ATTR_CALCULATE_KP));
            String group = configReader.getAttributeValue(XmlQueryStrings.SPACE_GP_BY_ID + gp + XmlQueryStrings.SPACE_ATTR_GROUP);
            String factorName = configReader.getAttributeValue(XmlQueryStrings.SPACE_GP_BY_ID + gp + XmlQueryStrings.SPACE_ATTR_FACTOR);
            gpDetailsMap.put(counter, new GPDetails(counter, coordinate, radius, calculateKp, factorName, group));
            counter++;
        }
    }

    @Override
    public void save(String newConfigFilename) throws Exception {
        configReader = new ConfigReader();
        configReader.loadTemplate(FilePathsStrings.FILENAME_TEMPLATES_PATH + FilePathsStrings.FILENAME_SPACE_TEMPLATE);

        configReader.createEmptyRootNodeFromTemplate();
        ConfigNode rootNode = configReader.getRootNode();

        rootNode.addNode(createSpaceGeometryNode());
        rootNode.addNode(createGPContainerNode());

        configReader.writeConfig(newConfigFilename);
    }

    public List<GPDetails> getGPsList() {
        return new ArrayList<>(gpDetailsMap.values());
    }

    public Set<Integer> getGPIDs() {
        return gpDetailsMap.keySet();
    }

    public int addGP(ICoordinate coordinate, int radius, boolean calculateKp, String factorName, String group) {
        GPDetails newGP = new GPDetails(counter, coordinate, radius, calculateKp, factorName, group);
        gpDetailsMap.put(counter, newGP);
        counter++;
        return counter - 1;
    }

    public int copyGP(int gpNum) {
        GPDetails currentGP = gpDetailsMap.get(gpNum);
        GPDetails newGP = new GPDetails(counter,
                                        currentGP.getCoordinate(),
                                        currentGP.getRadius(),
                                        currentGP.isCalculateKp(),
                                        currentGP.getFactorName(),
                                        currentGP.getGroup());
        gpDetailsMap.put(counter, newGP);
        counter++;
        return counter - 1;
    }

    public void removeGP(List<Integer> gpsList) {
        for (Integer gp : gpsList) {
            gpDetailsMap.remove(gp);
            PriorityTraceWriter.println("GP #" + gp + " deleted.", 3);
        }
    }

    public GPDetails getGP(int num) {
        return gpDetailsMap.get(num);
    }

    public Set<Integer> getGPsByGroup(String group) {
        Set<Integer> result = new TreeSet<>();
        gpDetailsMap.values().stream().forEach((gp) -> {
            if (gp.getGroup().equals(group))
                result.add(gp.getId());
        });
        return result;
    }

    public Set<String> getAvailableGPGroups() {
        Set<String> groups = new TreeSet<>();
        gpDetailsMap.values().stream().forEach((gp) -> groups.add(gp.getGroup()));
        return groups;
    }

    public boolean isFactorUsedByGPs(String factorName) {
        for (GPDetails currentGP : gpDetailsMap.values()) {
            if (currentGP.getFactorName().equals(factorName))
                return true;
        }
        return false;
    }
    //endregion

    //region Private Methods
    private ConfigNode createGPContainerNode() throws Exception {
        ConfigNode gpContainer = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(1).get(XmlQueryStrings.WORD_CONTAINER_U));
        gpContainer.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.WORD_GP_S_L);
        for (Map.Entry<Integer, GPDetails> gpEntry : gpDetailsMap.entrySet()) {
            GPDetails gpDetails = gpEntry.getValue();
            ICoordinate gpCoordinate = gpDetails.getCoordinate();
            ConfigNode gpNode = configReader.createNodeFromTemplate
                    (configReader.getConfigTemplate().getNodes(2).get(XmlQueryStrings.WORD_GLOWING_POINT_U));
            gpNode.addAttribute(XmlQueryStrings.WORD_NUM_L, gpEntry.getKey().toString());
            gpNode.addAttribute(XmlQueryStrings.WORD_FACTOR_NAME_L, gpDetails.getFactorName());
            gpNode.addAttribute(XmlQueryStrings.WORD_RADIUS_L, String.valueOf(gpDetails.getRadius()));
            gpNode.addAttribute(XmlQueryStrings.WORD_GROUP_L, String.valueOf(gpDetails.getGroup()));
            gpNode.addAttribute(XmlQueryStrings.SYMBOL_X_L, String.valueOf(gpCoordinate.getX()));
            gpNode.addAttribute(XmlQueryStrings.SYMBOL_Y_L, String.valueOf(gpCoordinate.getY()));
            gpNode.addAttribute(XmlQueryStrings.SYMBOL_Z_L, String.valueOf(gpCoordinate.getZ()));
            gpNode.addAttribute(XmlQueryStrings.WORD_CALCULATE_KP_L, String.valueOf(gpDetails.isCalculateKp()));
            gpContainer.addNode(gpNode);
        }
        return gpContainer;
    }

    private ConfigNode createSpaceGeometryNode() throws Exception {
        ConfigNode geometryNode = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(1).get(XmlQueryStrings.WORD_GEOMETRY_U));
        geometryNode.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.WORD_CUBE_L);
        geometryNode.addAttribute(XmlQueryStrings.WORD_VOLUME_L, String.valueOf(spaceVolume));
        return geometryNode;
    }
    //endregion
}