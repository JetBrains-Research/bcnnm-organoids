package com.synstorm.common.Utils.ConfigLoader;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.ConfigWorker.ConfigNode;
import com.synstorm.common.Utils.ConfigWorker.ConfigReader;
import com.synstorm.common.Utils.ConfigurationStrings.FilePathsStrings;
import com.synstorm.common.Utils.ConfigurationStrings.XmlQueryStrings;
import com.synstorm.common.Utils.Details.IDetails;
import com.synstorm.common.Utils.Details.SeedDetails;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Dmitry.Bozhko on 5/28/2015.
 */

@Model_v0
@NonProductionLegacy

public class SeedsLoader extends BaseLoader implements ILoader{
    //region Fields
    private Map<Integer, SeedDetails> seedDetailsMap;
    private int defaultSeedNum;
    private int cnt = 0;
    //endregion

    //region Constructors
    public SeedsLoader() {
        this(0);
    }

    public SeedsLoader(int defaultSeedNum) {
        this.seedDetailsMap = new LinkedHashMap<>();
        this.defaultSeedNum = defaultSeedNum;
    }
    //endregion

    //region Getters and Setters
    @Override
    @Nullable
    public IDetails getDetails(String key) {
        int intKey = Integer.parseInt(key);
        return seedDetailsMap.getOrDefault(intKey, null);
    }

    public int getSeedsCount() {
        return seedDetailsMap.size();
    }

    public byte[] getDefaultSeed() {
        return seedDetailsMap.get(defaultSeedNum).getSeed();
    }

    public byte[] getSeed(int num) {
        return seedDetailsMap.get(num).getSeed();
    }

    public String getStringDefaultSeed() {
        return new String(seedDetailsMap.get(defaultSeedNum).getSeed());
    }

    public List<String> getSeedsList() {
        return seedDetailsMap.entrySet().stream()
                .map(entry -> new String(entry.getValue().getSeed())).collect(Collectors.toList());
    }
    //endregion

    //region Public Methods
    @Override
    public void load(String configFileName, String templateFileName) {
        initialize(configFileName, templateFileName);
        List<String> seeds = configReader.getAttributesValueList(XmlQueryStrings.ATTR_BYTES);
        for (String seed : seeds)
            seedDetailsMap.put(cnt++, new SeedDetails(seed));

        cnt = 0;
    }

    @Override
    public void save(String newConfigFilename) throws Exception {
        configReader = new ConfigReader();
        configReader.loadTemplate(FilePathsStrings.FILENAME_TEMPLATES_PATH + FilePathsStrings.FILENAME_SEEDS_TEMPLATE);

        configReader.createEmptyRootNodeFromTemplate();
        ConfigNode rootNode = configReader.getRootNode();

        for (SeedDetails seedDetails : seedDetailsMap.values()) {
            ConfigNode seedNode = configReader.createNodeFromTemplate
                    (configReader.getConfigTemplate().getNodes(1).get(XmlQueryStrings.WORD_SEED_U));
            seedNode.addAttribute(XmlQueryStrings.WORD_BYTES_L, new String(seedDetails.getSeed()));
            rootNode.addNode(seedNode);
        }
        configReader.writeConfig(newConfigFilename);
    }

    public void nextSeed() {
        if (defaultSeedNum < seedDetailsMap.size() - 1)
            defaultSeedNum++;
        else
            defaultSeedNum = 0;
    }

    public void addSeed(String newSeed) {
        seedDetailsMap.put(cnt++, new SeedDetails(newSeed));
    }

    public void removeSeed(String toRemove) {
        Iterator iterator = seedDetailsMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            byte[] seed = ((SeedDetails)entry.getValue()).getSeed();
            String seedString = new String(seed);
            if (seedString.equals(toRemove)) {
                iterator.remove();
                break;
            }
        }

    }
    //endregion

    //region Private Methods
    //endregion
}
