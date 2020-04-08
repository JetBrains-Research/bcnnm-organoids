package com.synstorm.common.Utils.ConfigLoader;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.ConfigWorker.ConfigNode;
import com.synstorm.common.Utils.ConfigWorker.ConfigReader;
import com.synstorm.common.Utils.ConfigurationStrings.FilePathsStrings;
import com.synstorm.common.Utils.ConfigurationStrings.XmlQueryStrings;
import com.synstorm.common.Utils.Details.IDetails;
import com.synstorm.common.Utils.Details.IndividualCellDetails;
import com.synstorm.common.Utils.Details.IndividualDetails;
import com.synstorm.common.Utils.EnumTypes.CellFunctionalType;
import com.synstorm.common.Utils.EvolutionUtils.Gene.Gene;
import com.synstorm.common.Utils.SpaceUtils.CoordinateUtils;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by human-research on 28/05/15.
 */

@Model_v0
@NonProductionLegacy

public class IndividualLoader extends BaseLoader implements ILoader {
    //region Fields
    private IndividualDetails individualDetails;
    //endregion

    //region Constructors
    public IndividualLoader() {
        individualDetails = null;
    }
    //endregion

    //region Getters and Setters
    @Override
    @Nullable
    public IDetails getDetails(String key) {
        return individualDetails;
    }

    public void setIndividualDetails(IndividualDetails individualDetails) {
        this.individualDetails = individualDetails;
    }
    //endregion

    //region Public Methods
    @Override
    public void load(String configFileName, String templateFileName) {
        initialize(configFileName, templateFileName);
        String individualId = configReader.getAttributeValue(XmlQueryStrings.INDIVIDUAL_BY_ID);
        individualDetails = new IndividualDetails(UUID.fromString(individualId), null, null);

        //Work with Cells
        List<String> cellRows = configReader.getAttributesValueList(XmlQueryStrings.INDIVIDUAL_PART_ID + individualId
                + XmlQueryStrings.INDIVIDUAL_PART_CELLS_BY_NUM);
        if (cellRows.size() != 0) {
            for (String cell : cellRows) {
                String cellType = configReader.getAttributeValue(XmlQueryStrings.INDIVIDUAL_PART_ID + individualId
                                + XmlQueryStrings.INDIVIDUAL_PART_CELLS_CONTAINER + cell + XmlQueryStrings.INDIVIDUAL_ATTR_TYPE);
                String tagName = configReader.getAttributeValue(XmlQueryStrings.INDIVIDUAL_PART_ID + individualId
                        + XmlQueryStrings.INDIVIDUAL_PART_CELLS_CONTAINER + cell + XmlQueryStrings.INDIVIDUAL_ATTR_TAG_NAME);
                CellFunctionalType cellFunctionalType = CellFunctionalType.valueOf(configReader.getAttributeValue(XmlQueryStrings.INDIVIDUAL_PART_ID + individualId
                        + XmlQueryStrings.INDIVIDUAL_PART_CELLS_CONTAINER + cell + XmlQueryStrings.INDIVIDUAL_ATTR_FUNC_TYPE));
                int x = Integer.parseInt(configReader.getAttributeValue(XmlQueryStrings.INDIVIDUAL_PART_ID + individualId
                        + XmlQueryStrings.INDIVIDUAL_PART_CELLS_CONTAINER + cell + XmlQueryStrings.SPACE_ATTR_X));
                int y = Integer.parseInt(configReader.getAttributeValue(XmlQueryStrings.INDIVIDUAL_PART_ID + individualId
                        + XmlQueryStrings.INDIVIDUAL_PART_CELLS_CONTAINER + cell + XmlQueryStrings.SPACE_ATTR_Y));
                int z = Integer.parseInt(configReader.getAttributeValue(XmlQueryStrings.INDIVIDUAL_PART_ID + individualId
                        + XmlQueryStrings.INDIVIDUAL_PART_CELLS_CONTAINER + cell + XmlQueryStrings.SPACE_ATTR_Z));
                List<String> cellActionsRows = configReader.getAttributesValueList(XmlQueryStrings.INDIVIDUAL_PART_ID + individualId
                        + XmlQueryStrings.INDIVIDUAL_PART_CELLS_CONTAINER + cell + XmlQueryStrings.INDIVIDUAL_PART_CELLS_ACTIONS_CONTAINER);
                Map<String, Boolean> cellActionsMap = new TreeMap<>();
                for (String cellAction : cellActionsRows) {
                    Boolean initial = (configReader.getAttributeValue(XmlQueryStrings.INDIVIDUAL_PART_ID + individualId
                            + XmlQueryStrings.INDIVIDUAL_PART_CELLS_CONTAINER + cell + XmlQueryStrings.INDIVIDUAL_PART_CELLACTION
                            + cellAction + XmlQueryStrings.INDIVIDUAL_ATTR_INITIAL)).equals("1");
                    cellActionsMap.put(cellAction, initial);
                }
                IndividualCellDetails cellToAdd = new IndividualCellDetails(cell, cellType, cellFunctionalType,
                        CoordinateUtils.INSTANCE.createCoordinate(x, y, z), cellActionsMap);
                cellToAdd.setTagName(tagName);
                individualDetails.addCell(cellToAdd);
                List<String> synapses = configReader.getAttributesValueList(XmlQueryStrings.INDIVIDUAL_PART_ID + individualId
                        + XmlQueryStrings.INDIVIDUAL_PART_CELLS_CONTAINER + cell + XmlQueryStrings.INDIVIDUAL_PART_SYNAPSES_BY_DESTINATION);
                for (String synapse : synapses) {
                    individualDetails.addSynapse(Integer.valueOf(cell), Integer.valueOf(synapse));
                }
            }
        }

        //Work with Genes
        List<String> geneRows = configReader.getAttributesValueList(XmlQueryStrings.INDIVIDUAL_PART_ID + individualId
                + XmlQueryStrings.INDIVIDUAL_PART_GENES_BY_NAME);
        if (geneRows.size() != 0) {
            for (String geneRow : geneRows) {
                String duration = configReader.getAttributeValue(XmlQueryStrings.INDIVIDUAL_PART_ID + individualId
                        + XmlQueryStrings.INDIVIDUAL_PART_GENES_CONTAINER + geneRow + XmlQueryStrings.ATTR_DURATION);
                String mutability = configReader.getAttributeValue(XmlQueryStrings.INDIVIDUAL_PART_ID + individualId
                        + XmlQueryStrings.INDIVIDUAL_PART_GENES_CONTAINER + geneRow + XmlQueryStrings.INDIVIDUAL_ATTR_MUTABILITY);
                Gene geneToAdd = new Gene(geneRow, Double.parseDouble(duration), Double.parseDouble(mutability));
                List<String> geneActions = configReader.getAttributesValueList(XmlQueryStrings.INDIVIDUAL_PART_ID + individualId +
                    XmlQueryStrings.INDIVIDUAL_PART_GENES_CONTAINER + geneRow + XmlQueryStrings.DEP_ACTION_ATTR_NAME);
                for (String action : geneActions) {
                    String geneActive = configReader.getAttributeValue(XmlQueryStrings.INDIVIDUAL_PART_ID + individualId +
                            XmlQueryStrings.INDIVIDUAL_PART_GENES_CONTAINER + geneRow + XmlQueryStrings.INDIVIDUAL_PART_GENE_ACTION +
                            action + XmlQueryStrings.GENE_ATTR_PRESENT);
                    int present = Integer.parseInt(geneActive);
                    geneToAdd.addAction(action, present);
                }
                individualDetails.addGene(geneToAdd);
            }
        }
    }

    public void load(IndividualDetails details) {
        individualDetails = details;
    }

    @Override
    public void save(String newConfigFilename) throws Exception {
        configReader = new ConfigReader();
        configReader.loadTemplate(FilePathsStrings.FILENAME_TEMPLATES_PATH + FilePathsStrings.FILENAME_INDIVIDUAL_TEMPLATE);

        //create rootNode
        configReader.createEmptyRootNodeFromTemplate();
        ConfigNode rootNode = configReader.getRootNode();

        //create individNode
        ConfigNode individNode = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(1).get(XmlQueryStrings.WORD_INDIVID_U));
        individNode.addAttribute(XmlQueryStrings.WORD_NUM_L, "0");
        individNode.addAttribute(XmlQueryStrings.WORD_ID_L, String.valueOf(individualDetails.getUuid()));

        //create Cell container
        ConfigNode cellContainer = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(2).get(XmlQueryStrings.WORD_CONTAINER_U));
        cellContainer.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.WORD_CELL_POOL_U);
        Set<Integer> cellIndexes = individualDetails.getIndividualCellDetailsIndexes();
        for (Integer cell : cellIndexes) {
            IndividualCellDetails icd = individualDetails.getCell(cell);
            ConfigNode cellToAdd = configReader.createNodeFromTemplate
                    (configReader.getConfigTemplate().getNodes(3).get(XmlQueryStrings.WORD_CELL_U));
            cellToAdd.addAttribute(XmlQueryStrings.WORD_NUM_L, String.valueOf(cell));
            cellToAdd.addAttribute(XmlQueryStrings.WORD_TYPE_L, icd.getCellType());
            cellToAdd.addAttribute(XmlQueryStrings.WORD_CELL_FUNCTIONAL_TYPE_L, icd.getCellFunctionalType().toString());
            cellToAdd.addAttribute(XmlQueryStrings.WORD_TAGNAME_L, icd.getTagName());
            cellToAdd.addAttribute(XmlQueryStrings.SYMBOL_X_L, String.valueOf(icd.getCoordinate().getX()));
            cellToAdd.addAttribute(XmlQueryStrings.SYMBOL_Y_L, String.valueOf(icd.getCoordinate().getY()));
            cellToAdd.addAttribute(XmlQueryStrings.SYMBOL_Z_L, String.valueOf(icd.getCoordinate().getZ()));

            cellToAdd.addNode(createCellActionsContainerNode(icd));
            cellToAdd.addNode(createSynapsesContainerNode(cell));

            cellContainer.addNode(cellToAdd);
        }
        individNode.addNode(cellContainer);
        individNode.addNode(createGenesContainerNode(individualDetails));

        rootNode.addNode(individNode);
        configReader.writeConfig(newConfigFilename);
    }

    public void save(String newConfigFilename, IndividualDetails details) throws Exception {
        configReader = new ConfigReader();
        configReader.loadTemplate(FilePathsStrings.FILENAME_TEMPLATES_PATH + FilePathsStrings.FILENAME_INDIVIDUAL_TEMPLATE);

        //create rootNode
        configReader.createEmptyRootNodeFromTemplate();
        ConfigNode rootNode = configReader.getRootNode();

        //create individNode
        ConfigNode individNode = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(1).get(XmlQueryStrings.WORD_INDIVID_U));
        individNode.addAttribute(XmlQueryStrings.WORD_NUM_L, "0");
        individNode.addAttribute(XmlQueryStrings.WORD_ID_L, String.valueOf(details.getUuid()));

        //create Cell container
        ConfigNode cellContainer = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(2).get(XmlQueryStrings.WORD_CONTAINER_U));
        cellContainer.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.WORD_CELL_POOL_U);
        Set<Integer> cellIndexes = details.getIndividualCellDetailsIndexes();
        for (Integer cell : cellIndexes) {
            IndividualCellDetails icd = details.getCell(cell);
            ConfigNode cellToAdd = configReader.createNodeFromTemplate
                    (configReader.getConfigTemplate().getNodes(3).get(XmlQueryStrings.WORD_CELL_U));
            cellToAdd.addAttribute(XmlQueryStrings.WORD_NUM_L, String.valueOf(cell));
            cellToAdd.addAttribute(XmlQueryStrings.WORD_TYPE_L, icd.getCellType());
            cellToAdd.addAttribute(XmlQueryStrings.WORD_CELL_FUNCTIONAL_TYPE_L, icd.getCellFunctionalType().toString());
            cellToAdd.addAttribute(XmlQueryStrings.WORD_TAGNAME_L, icd.getTagName());
            cellToAdd.addAttribute(XmlQueryStrings.SYMBOL_X_L, String.valueOf(icd.getCoordinate().getX()));
            cellToAdd.addAttribute(XmlQueryStrings.SYMBOL_Y_L, String.valueOf(icd.getCoordinate().getY()));
            cellToAdd.addAttribute(XmlQueryStrings.SYMBOL_Z_L, String.valueOf(icd.getCoordinate().getZ()));

            cellToAdd.addNode(createCellActionsContainerNode(icd));
            cellToAdd.addNode(createSynapsesContainerNode(cell));

            cellContainer.addNode(cellToAdd);
        }
        individNode.addNode(cellContainer);
        individNode.addNode(createGenesContainerNode(details));

        rootNode.addNode(individNode);
        configReader.writeConfig(newConfigFilename);
    }

    public void initiateEmptyConfiguration() {
        individualDetails = new IndividualDetails(UUID.randomUUID(), null, null);

    }
    //endregion

    //region Private Methods
    private ConfigNode createSynapsesContainerNode(Integer cell) throws Exception {
        ConfigNode synapsesContainer = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(4).get(XmlQueryStrings.WORD_CONTAINER_U));
        synapsesContainer.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.WORD_SYNAPSES_U);
        if (individualDetails.getNeuronSynapses(cell) != null) {
            for (Integer connection : individualDetails.getNeuronSynapses(cell)) {
                ConfigNode synapse = configReader.createNodeFromTemplate
                        (configReader.getConfigTemplate().getNodes(5).get(XmlQueryStrings.WORD_SYNAPSE));
                synapse.addAttribute(XmlQueryStrings.WORD_DESTINATION_L, String.valueOf(connection));
                synapsesContainer.addNode(synapse);
            }
        }
        return synapsesContainer;
    }

    private ConfigNode createCellActionsContainerNode(IndividualCellDetails icd) throws Exception {
        ConfigNode cellActionsContainer = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(4).get(XmlQueryStrings.WORD_CONTAINER_U));
        cellActionsContainer.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.WORD_CELL_ACTIONS_U);
        for (Map.Entry<String, Boolean> cellAction : icd.getCellActions().entrySet()) {
            ConfigNode cellActionNode = configReader.createNodeFromTemplate
                    (configReader.getConfigTemplate().getNodes(5).get(XmlQueryStrings.WORD_CELL_ACTION_U));
            cellActionNode.addAttribute(XmlQueryStrings.WORD_NAME_L, cellAction.getKey());
            cellActionNode.addAttribute(XmlQueryStrings.WORD_INITIAL_L, cellAction.getValue() ? "1" : "0");
            cellActionsContainer.addNode(cellActionNode);
        }
        return cellActionsContainer;
    }

    private ConfigNode createGenesContainerNode(IndividualDetails details) throws Exception {
        ConfigNode genesContainer = configReader.createNodeFromTemplate
                (configReader.getConfigTemplate().getNodes(2).get(XmlQueryStrings.WORD_CONTAINER_U));
        genesContainer.addAttribute(XmlQueryStrings.WORD_TYPE_L, XmlQueryStrings.WORD_GENESET_U);

        for (Gene gene : details.getIndividualGenes()) {
            ConfigNode geneToAdd = configReader.createNodeFromTemplate
                    (configReader.getConfigTemplate().getNodes(3).get(XmlQueryStrings.WORD_GENE_U));
            geneToAdd.addAttribute(XmlQueryStrings.WORD_NAME_L, gene.getName());
            geneToAdd.addAttribute(XmlQueryStrings.WORD_DURATION_L, numberFormat.format(gene.getDuration()));
            geneToAdd.addAttribute(XmlQueryStrings.WORD_MUTABILITY_L, numberFormat.format(gene.getMutability()));

            for (Map.Entry<String, Integer> entry : gene.getActionPresentationMap().entrySet()) {
                ConfigNode actionToAdd = configReader.createNodeFromTemplate
                        (configReader.getConfigTemplate().getNodes(4).get(XmlQueryStrings.WORD_ACTION_U));
                actionToAdd.addAttribute(XmlQueryStrings.WORD_NAME_L, entry.getKey());
                actionToAdd.addAttribute(XmlQueryStrings.WORD_PRESENT_L, entry.getValue().toString());
                geneToAdd.addNode(actionToAdd);
            }
            genesContainer.addNode(geneToAdd);
        }
        return genesContainer;
    }
    //endregion
}
