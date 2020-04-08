package com.synstorm.common.Utils.ConfigLoader;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.ConfigWorker.ConfigNode;
import com.synstorm.common.Utils.ConfigWorker.ConfigReader;
import com.synstorm.common.Utils.ConfigurationStrings.FilePathsStrings;
import com.synstorm.common.Utils.ConfigurationStrings.XmlQueryStrings;
import com.synstorm.common.Utils.Details.*;
import com.synstorm.common.Utils.EnumTypes.CellFunctionalType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Dmitry.Bozhko on 3/25/2015.
 */
@Model_v0
@NonProductionLegacy
public class CellsLoader extends BaseLoader implements ILoader {
    //region Fields
    private Map<String, Integer> cellTypes;
    private Map<String, CellDetails> cellsDetailsMap;
    private int counter = 0;
    //endregion

    //region Constructors
    public CellsLoader() {
        cellTypes = new TreeMap<>();
        cellsDetailsMap = new TreeMap<>();
    }
    //endregion

    //region Getters and Setters
    @Override
    @Nullable
    public IDetails getDetails(String key) {
        return cellsDetailsMap.getOrDefault(key, null);
    }
    //endregion

    //region Public Methods
    @Override
    public void load(String configFileName, String templateFileName) {
        initialize(configFileName, templateFileName);

        List<String> cells = configReader.getAttributesValueList(XmlQueryStrings.ATTR_TYPE);
        for (String cellType : cells) {
            String cft = configReader.getAttributeValue(
                    XmlQueryStrings.CELL_BY_TYPE + cellType + XmlQueryStrings.CELL_ATTR_CFT);
            CellFunctionalType cellFunctionalType = CellFunctionalType.valueOf(cft);
            CellDetails cellDetails = new CellDetails(cellType, cellFunctionalType);

            loadFactors(cellType, cellDetails);
            loadProliferationInfo(cellType, cellDetails);
            loadActions(cellType, cellDetails);
            loadAxon(cellType, cellDetails);
            loadDendrite(cellType, cellDetails);

            cellsDetailsMap.put(cellType, cellDetails);
            cellTypes.put(cellType, counter++);
        }
    }

    @Override
    public void save(String newConfigFilename) throws Exception {
        configReader = new ConfigReader();
        configReader.loadTemplate(FilePathsStrings.FILENAME_TEMPLATES_PATH + FilePathsStrings.FILENAME_CELLS_TEMPLATE);

        //create rootNode
        configReader.createEmptyRootNodeFromTemplate();
        ConfigNode rootNode = configReader.getRootNode();

        //create action
        for (Map.Entry<String, CellDetails> entry : cellsDetailsMap.entrySet()) {
            CellDetails cDetails = entry.getValue();
            ConfigNode cellNode = configReader.createNodeFromTemplate
                    (configReader.getConfigTemplate().getNodes(1).get(XmlQueryStrings.WORD_CELL_U));
            cellNode.addAttribute(XmlQueryStrings.WORD_TYPE_L, cDetails.getType());
            cellNode.addAttribute(XmlQueryStrings.WORD_CELL_FUNCTIONAL_TYPE_L, cDetails.getCellFunctionalType().toString());

            cellNode.addNode(createFactorsContainerNode(cDetails));
            cellNode.addNode(createProliferationContainerNode(cDetails));
            cellNode.addNode(createActionsContainerNode(cDetails));
            cellNode.addNode(createConnectionContainerNode(cDetails));
            //add cellNode to rootNode
            rootNode.addNode(cellNode);
        }
        configReader.writeConfig(newConfigFilename);
    }

    public Map<String, Integer> getCellTypes() {
        return cellTypes; //throws exception deleted
    }

    public void addCell(String cellType, CellFunctionalType cellFunctionalType) {
        CellDetails newCell = new CellDetails(cellType, cellFunctionalType);
        cellsDetailsMap.put(cellType, newCell);
        cellTypes.put(cellType, counter++);
    }

    public void removeCell(String cellType) {
        cellsDetailsMap.remove(cellType);
        cellTypes.remove(cellType);
    }
    //endregion

    //region Private Methods
    private ConfigNode createFactorsContainerNode(CellDetails cDetails) throws Exception {
        ConfigNode factorsContainer = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(2).get(XmlQueryStrings.WORD_CONTAINER_U));
        factorsContainer.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.WORD_FACTORS_U);
        //create Received factors Container
        ConfigNode receivedFactorsContainer = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(3).get(XmlQueryStrings.WORD_CONTAINER_U));
        receivedFactorsContainer.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.WORD_RECEIVED_U);
        int rFactorCounter = 0;
        for (String action : cDetails.getActionsWithReceivedFactors()) {
            for (String factor : cDetails.getReceivedFactorsByAction(action)) {
                ConfigNode rFactorNode = configReader.createNodeFromTemplate
                        (configReader.getConfigTemplate().getNodes(4).get(XmlQueryStrings.WORD_FACTOR_U));
                rFactorNode.addAttribute(XmlQueryStrings.WORD_NUM_L, String.valueOf(rFactorCounter++));
                rFactorNode.addAttribute(XmlQueryStrings.WORD_FACTOR_NAME_L, factor);
                rFactorNode.addAttribute(XmlQueryStrings.WORD_ACTION_L, action);
                receivedFactorsContainer.addNode(rFactorNode);
            }
        }
        factorsContainer.addNode(receivedFactorsContainer);

        //create Emitted factors Container
        ConfigNode emittedFactorsContainer = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(3).get(XmlQueryStrings.WORD_CONTAINER_U));
        emittedFactorsContainer.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.WORD_EMITTED_U);
        int eFactorCounter = 0;
        for (String emittedFactor : cDetails.getEmittedFactors()) {
            ConfigNode eFactorNode = configReader.createNodeFromTemplate
                    (configReader.getConfigTemplate().getNodes(4).get(XmlQueryStrings.WORD_FACTOR_U));
            eFactorNode.addAttribute(XmlQueryStrings.WORD_NUM_L, String.valueOf(eFactorCounter++));
            eFactorNode.addAttribute(XmlQueryStrings.WORD_FACTOR_NAME_L, emittedFactor);
            eFactorNode.addAttribute(XmlQueryStrings.WORD_ACTION_L, emittedFactor);
            emittedFactorsContainer.addNode(eFactorNode);
        }
        factorsContainer.addNode(emittedFactorsContainer);
        return factorsContainer;
    }

    private ConfigNode createActionsContainerNode(CellDetails cDetails) throws Exception {
        ConfigNode actionsContainer = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(2).get(XmlQueryStrings.WORD_CONTAINER_U));
        actionsContainer.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.WORD_ACTIONS_U);
        int actionsCounter = 0;
        for (String action : cDetails.getActions()) {
            ConfigNode actionNode = configReader.createNodeFromTemplate
                    (configReader.getConfigTemplate().getNodes(3).get(XmlQueryStrings.WORD_ACTION_U));
            actionNode.addAttribute(XmlQueryStrings.WORD_NAME_L, action);
            actionNode.addAttribute(XmlQueryStrings.WORD_NUM_L, String.valueOf(actionsCounter++));
            actionsContainer.addNode(actionNode);
        }
        return actionsContainer;
    }

    private ConfigNode createProliferationContainerNode(CellDetails cDetails) throws Exception {
        ConfigNode proliferationContainer = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(2).get(XmlQueryStrings.WORD_CONTAINER_U));
        proliferationContainer.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.WORD_PROLIFERATION_U);
        for (ProliferationDetails proliferationDetails : cDetails.getProliferationInfoSet()) {
            ConfigNode proliferationNode = configReader.createNodeFromTemplate
                    (configReader.getConfigTemplate().getNodes(3).get(XmlQueryStrings.WORD_PROLIFERATION_INFO_U));
            proliferationNode.addAttribute(XmlQueryStrings.WORD_CELLTYPE_L, proliferationDetails.getCellType());
            proliferationNode.addAttribute(XmlQueryStrings.WORD_FACTOR_NAME_L, proliferationDetails.getFactorName());
            proliferationContainer.addNode(proliferationNode);
        }
        return proliferationContainer;
    }

    private ConfigNode createConnectionContainerNode(CellDetails cDetails) throws Exception {
        ConfigNode connectionContainer = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(2).get(XmlQueryStrings.WORD_CONTAINER_U));
        connectionContainer.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.WORD_CONNECTION_U);

        //create AxonConnection Node
        AxonDetails axonDetails = cDetails.getAxonDetails();
        ConfigNode axonConnectionNode = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(3).get(XmlQueryStrings.WORD_AXON_CONNECTION_U));
        axonConnectionNode.addAttribute(XmlQueryStrings.WORD_AEXIST_L, axonDetails.hasAxon() ? "1" : "0");
        axonConnectionNode.addAttribute(XmlQueryStrings.WORD_NTTYPE_L, axonDetails.getNeurotransmitter());
        axonConnectionNode.addAttribute(XmlQueryStrings.WORD_EXPRESSING_NT_PERCENTAGE_L, numberFormat.format(axonDetails.getExpressingNTPercentage()));
        axonConnectionNode.addAttribute(XmlQueryStrings.WORD_RELEASING_NT_PERCENTAGE_L, numberFormat.format(axonDetails.getReleasingNTPercentage()));
        axonConnectionNode.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.WORD_AXON_U);
        axonConnectionNode.addAttribute(XmlQueryStrings.WORD_UPTAKE_NT_PERCENTAGE_L, numberFormat.format(axonDetails.getUptakeNTPercentage()));
        //Add Axon node to Connection container
        connectionContainer.addNode(axonConnectionNode);

        //create DendriteConnection node
        DendriteDetails dendriteDetails = cDetails.getDendriteDetails();
        ConfigNode dendriteConnectionNode = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(3).get(XmlQueryStrings.WORD_DENDRITE_CONNECTION_U));
        dendriteConnectionNode.addAttribute(XmlQueryStrings.WORD_CONSTANT_THRESHOLD_L, numberFormat.format(dendriteDetails.getConstantThreshold()));
        dendriteConnectionNode.addAttribute(XmlQueryStrings.WORD_DEXIST_L, dendriteDetails.hasDendrite() ? "1" : "0");
        dendriteConnectionNode.addAttribute(XmlQueryStrings.WORD_STIMULI_THRESHOLD_L, numberFormat.format(dendriteDetails.getStimuliThreshold()));
        dendriteConnectionNode.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.WORD_DENDRITE_U);
        //create Receptors container
        ConfigNode receptorsContainer = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(4).get(XmlQueryStrings.WORD_CONTAINER_U));
        receptorsContainer.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.WORD_RECEPTORS_U);
        int receptorsCounter = 0;
        for (String receptor : dendriteDetails.getDendriteReceptors()) {
            ConfigNode receptorNode = configReader.createNodeFromTemplate
                    (configReader.getConfigTemplate().getNodes(5).get(XmlQueryStrings.WORD_RECEPTOR_U));
            receptorNode.addAttribute(XmlQueryStrings.WORD_NUM_L, String.valueOf(receptorsCounter++));
            receptorNode.addAttribute(XmlQueryStrings.WORD_RECEPTOR_TYPE_L, receptor);
            receptorsContainer.addNode(receptorNode);
        }
        dendriteConnectionNode.addNode(receptorsContainer);
        //add DendriteNode to Connection container
        connectionContainer.addNode(dendriteConnectionNode);
        return connectionContainer;
    }

    private void loadFactors(String cellType, CellDetails cellDetails) {
        List<String> receivedFactors = configReader.getAttributesValueList(
                XmlQueryStrings.CELL_BY_TYPE + cellType + XmlQueryStrings.RECEIVED_FACTORS_ALL);
        List<String> emittedFactors = configReader.getAttributesValueList(
                XmlQueryStrings.CELL_BY_TYPE + cellType + XmlQueryStrings.EMITTED_FACTORS_ALL);

        for (String fNum : receivedFactors) {
            String fName = configReader.getAttributeValue(XmlQueryStrings.CELL_BY_TYPE + cellType +
                    XmlQueryStrings.RECEIVED_FACTOR_BY_ID + fNum + XmlQueryStrings.FACTOR_ATTR_NAME);
            String fAction = configReader.getAttributeValue(XmlQueryStrings.CELL_BY_TYPE + cellType +
                    XmlQueryStrings.RECEIVED_FACTOR_BY_ID + fNum + XmlQueryStrings.ATTR_ACTION);
            ArrayList<String> factorsList = new ArrayList<>();
            factorsList.add(fName);
            cellDetails.addReceivedFactor(fAction, factorsList);
        }

        for (String fNum : emittedFactors) {
            String fAction = configReader.getAttributeValue(XmlQueryStrings.CELL_BY_TYPE + cellType +
                    XmlQueryStrings.EMITTED_FACTOR_BY_ID + fNum + XmlQueryStrings.ATTR_ACTION);

            cellDetails.addEmittedFactor(fAction);
        }
    }

    private void loadProliferationInfo(String cellType, CellDetails cellDetails) {
        List<String> proliferationCellTypes = configReader.getAttributesValueList(
                XmlQueryStrings.CELL_BY_TYPE + cellType + XmlQueryStrings.PROLIFERATION_CELLTYPES_ALL);

        for (String pcType : proliferationCellTypes) {
            String factorName = configReader.getAttributeValue(XmlQueryStrings.CELL_BY_TYPE + cellType +
                    XmlQueryStrings.PROLIFERATION_CELLTYPE_BY_ID + pcType + XmlQueryStrings.PROLIFERATION_ATTR_FACTORNAME);
            cellDetails.addProliferationInfo(new ProliferationDetails(factorName, pcType));
        }
    }

    private void loadActions(String cellType, CellDetails cellDetails) {
        List<String> actions = configReader.getAttributesValueList(
                XmlQueryStrings.CELL_BY_TYPE + cellType + XmlQueryStrings.ACTIONS_ALL);
        for (String tNum : actions) {
            String tAction = configReader.getAttributeValue(XmlQueryStrings.CELL_BY_TYPE + cellType +
                    XmlQueryStrings.ACTION_BY_ID + tNum + XmlQueryStrings.ACTIONS_ATTR_NAME);
            cellDetails.addAction(tAction);
        }
    }

    private void loadAxon(String cellType, CellDetails cellDetails) {
        String aExist = configReader.getAttributeValue(
                XmlQueryStrings.CELL_BY_TYPE + cellType + XmlQueryStrings.AXON_ATTR_EXIST);
        boolean hasAxon = aExist.equals("1");

        if (hasAxon) {
            String neurotransmitter = configReader.getAttributeValue(
                    XmlQueryStrings.CELL_BY_TYPE + cellType + XmlQueryStrings.AXON_ATTR_NTTYPE);
            String expressingNTPercentage = configReader.getAttributeValue(
                    XmlQueryStrings.CELL_BY_TYPE + cellType + XmlQueryStrings.AXON_ATTR_ENTP);
            String releasingNTPercentage = configReader.getAttributeValue(
                    XmlQueryStrings.CELL_BY_TYPE + cellType + XmlQueryStrings.AXON_ATTR_RNTP);
            String uptakeNTPercentage = configReader.getAttributeValue(
                    XmlQueryStrings.CELL_BY_TYPE + cellType + XmlQueryStrings.AXON_ATTR_UNTP);

            cellDetails.addAxon(new AxonDetails(neurotransmitter, expressingNTPercentage,
                    releasingNTPercentage, uptakeNTPercentage));
        } else
            cellDetails.addAxon(new AxonDetails());
    }

    private void loadDendrite(String cellType, CellDetails cellDetails) {
        String dExist = configReader.getAttributeValue(
                XmlQueryStrings.CELL_BY_TYPE + cellType + XmlQueryStrings.DENDRITE_ATTR_EXIST);
        boolean hasDendrite = dExist.equals("1");

        if (hasDendrite) {
            String stimuliThreshold = configReader.getAttributeValue(
                    XmlQueryStrings.CELL_BY_TYPE + cellType + XmlQueryStrings.DENDRITE_ATTR_ST);
            String constantThreshold = configReader.getAttributeValue(
                    XmlQueryStrings.CELL_BY_TYPE + cellType + XmlQueryStrings.DENDRITE_ATTR_CT);

            DendriteDetails dendriteDetails = new DendriteDetails(stimuliThreshold, constantThreshold);
            List<String> receptors = configReader.getAttributesValueList(
                    XmlQueryStrings.CELL_BY_TYPE + cellType + XmlQueryStrings.DENDRITE_NT_RECEPTORS_ALL);

            for (String receptorId : receptors) {
                String rType = configReader.getAttributeValue(XmlQueryStrings.CELL_BY_TYPE + cellType +
                        XmlQueryStrings.DENDRITE_NT_RECEPTORS_BY_ID + receptorId + XmlQueryStrings.DENDRITE_NT_ATTR_RECEPTOR_TYPE);
                dendriteDetails.addDendriteReceptor(rType);
            }

            cellDetails.addDendrite(dendriteDetails);
        } else
            cellDetails.addDendrite(new DendriteDetails());
    }

    public boolean isFactorUsedByCells(String factorName) {
        for (CellDetails currentCell : cellsDetailsMap.values()) {
            if (currentCell.isFactorUsedByCell(factorName))
                return true;
        }
        return false;
    }
    //endregion
}
