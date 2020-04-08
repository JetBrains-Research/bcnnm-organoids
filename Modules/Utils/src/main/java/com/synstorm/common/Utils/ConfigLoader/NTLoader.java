package com.synstorm.common.Utils.ConfigLoader;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.ConfigWorker.ConfigNode;
import com.synstorm.common.Utils.ConfigWorker.ConfigReader;
import com.synstorm.common.Utils.ConfigurationStrings.FilePathsStrings;
import com.synstorm.common.Utils.ConfigurationStrings.XmlQueryStrings;
import com.synstorm.common.Utils.Details.DendriteReceptorTypeDetails;
import com.synstorm.common.Utils.Details.IDetails;
import com.synstorm.common.Utils.Details.NeurotransmitterDetails;
import com.synstorm.common.Utils.TraceMessageWriter.PriorityTraceWriter;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Dmitry.Bozhko on 3/25/2015.
 */

@Model_v0
@NonProductionLegacy

public class NTLoader extends BaseLoader implements ILoader {
    //region Fields
    private Map<String, NeurotransmitterDetails> neurotransmitterDetailsMap;
    //endregion

    //region Constructors
    public NTLoader() {
        neurotransmitterDetailsMap = new HashMap<>();
    }
    //endregion

    //region Getters and Setters
    @Override
    @Nullable
    public IDetails getDetails(String key) {
        return neurotransmitterDetailsMap.getOrDefault(key, null);
    }

    public List<String> getNeurotransmittersList() {
        return neurotransmitterDetailsMap.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public String getReceptorType(Set<String> possibleReceptors, String sourceCellType) {
        for (Map.Entry<String, NeurotransmitterDetails> ndPair : neurotransmitterDetailsMap.entrySet()) {
            String result = ndPair.getValue().getReceptorByCellType(possibleReceptors, sourceCellType);
            if (result != null)
                return result;
        }

        return "";
    }

    public DendriteReceptorTypeDetails getReceptorInfo(String receptorType) {
        for (NeurotransmitterDetails currentNT : neurotransmitterDetailsMap.values()) {
            if (currentNT.getReceptorTypeDetailsMap().containsKey(receptorType)) {
                return currentNT.GetReceptor(receptorType);
            }
        }
        PriorityTraceWriter.println("NTLoader not contains " + receptorType + " information", 2);
        return null;
    }

    public Set<String> getUsedSourceCells() {
        Set<String> result = new TreeSet<>();
        for (NeurotransmitterDetails currentNT : neurotransmitterDetailsMap.values()) {
            for (DendriteReceptorTypeDetails currentReceptor : currentNT.getReceptorTypeDetailsMap().values()) {
                result.addAll(currentReceptor.getSourceCellTypes());
            }
        }
        return result;
    }
    //endregion

    //region Public Methods
    @Override
    public void load(String configFileName, String templateFileName) {
        initialize(configFileName, templateFileName);
        List<String> ntList = configReader.getAttributesValueList(XmlQueryStrings.ATTR_NAME);
        for (String nt : ntList) {
            String type = configReader.getAttributeValue(XmlQueryStrings.NEUROTRANSMITTER_BY_NAME + nt +
                    XmlQueryStrings.NEUROTRANSMITTER_ATTR_type);
            String releaseThreshold = configReader.getAttributeValue(XmlQueryStrings.NEUROTRANSMITTER_BY_NAME + nt +
                    XmlQueryStrings.NEUROTRANSMITTER_ATTR_RELEASE_THRESHOLD);
            NeurotransmitterDetails ntToAdd = new NeurotransmitterDetails(nt, type, releaseThreshold);
            List<String> receptorsNums = configReader.getAttributesValueList
                    (XmlQueryStrings.NEUROTRANSMITTER_BY_NAME + nt + XmlQueryStrings.NEUROTRANSMITTER_ATTR_num);
            Map<String, DendriteReceptorTypeDetails> receptorTypeDetailsMap = new HashMap<>();
            for (String receptorRow : receptorsNums) {
                String receptorType = configReader.getAttributeValue(XmlQueryStrings.NEUROTRANSMITTER_BY_NAME + nt
                        + XmlQueryStrings.NEUROTRANSMITTER_REC_NUM + receptorRow + XmlQueryStrings.NEUROTRANSMITTER_ATTR_recType);
                String sourceCell = configReader.getAttributeValue(XmlQueryStrings.NEUROTRANSMITTER_BY_NAME + nt
                        + XmlQueryStrings.NEUROTRANSMITTER_REC_NUM + receptorRow + XmlQueryStrings.NEUROTRANSMITTER_ATTR_scType);
                if (receptorTypeDetailsMap.containsKey(receptorType)) {
                    DendriteReceptorTypeDetails currentReceptor = receptorTypeDetailsMap.get(receptorType);
                    currentReceptor.addSourceCell(sourceCell);
                } else {
                    DendriteReceptorTypeDetails dendriteReceptorTypeToAdd =
                            new DendriteReceptorTypeDetails(receptorType, nt);
                    dendriteReceptorTypeToAdd.addSourceCell(sourceCell);
                    receptorTypeDetailsMap.put(receptorType, dendriteReceptorTypeToAdd);
                }
            }
            receptorTypeDetailsMap.values().stream().forEach(ntToAdd::addReceptor);
            neurotransmitterDetailsMap.put(nt, ntToAdd);
        }
    }

    @Override
    public void save(String newConfigFilename) throws Exception {
        configReader = new ConfigReader();
        configReader.loadTemplate(FilePathsStrings.FILENAME_TEMPLATES_PATH + FilePathsStrings.FILENAME_NEUROTRANSMITTER_TEMPLATE);

        configReader.createEmptyRootNodeFromTemplate();
        ConfigNode rootNode = configReader.getRootNode();

        for (NeurotransmitterDetails ntDetails : neurotransmitterDetailsMap.values()) {
            ConfigNode ntNode = configReader.createNodeFromTemplate //NT Block
                    (configReader.getConfigTemplate().getNodes(1).get(XmlQueryStrings.WORD_NEUROTRANSMITTER_U));
            ntNode.addAttribute(XmlQueryStrings.WORD_NAME_L, ntDetails.getName());
            ntNode.addAttribute(XmlQueryStrings.WORD_TYPE_L, ntDetails.getType().toString());
            ntNode.addAttribute(XmlQueryStrings.WORD_RELEASE_THRESHOLD_L, String.valueOf(ntDetails.getReleaseThreshold()));
            int receptorCounter = 0;
            for (DendriteReceptorTypeDetails currentReceptor : ntDetails.getReceptorTypeDetailsMap().values()) {
                for (String sourceCell : currentReceptor.getSourceCellTypes()) {
                    ConfigNode receptorNode = configReader.createNodeFromTemplate
                            (configReader.getConfigTemplate().getNodes(2).get(XmlQueryStrings.WORD_RECEPTOR_U));
                    receptorNode.addAttribute(XmlQueryStrings.WORD_NUM_L, String.valueOf(receptorCounter++));
                    receptorNode.addAttribute(XmlQueryStrings.WORD_RECEPTOR_TYPE_L, currentReceptor.getReceptorType());
                    receptorNode.addAttribute(XmlQueryStrings.WORD_SOURCE_CELL_TYPE_L, sourceCell);
                    ntNode.addNode(receptorNode);
                }
            }
            rootNode.addNode(ntNode);
        }
        configReader.writeConfig(newConfigFilename);
    }

    public boolean contains(String ntName) {
       return neurotransmitterDetailsMap.containsKey(ntName);
    }

    public void addNeurotransmitter(String ntName, String ntType, String threshold) {
        neurotransmitterDetailsMap.put(ntName, new NeurotransmitterDetails(ntName, ntType, threshold));
    }

    public void removeNeurotransmitter(String ntName) {
        neurotransmitterDetailsMap.remove(ntName);
    }
    //endregion

    //region Private Methods
    //endregion
}
