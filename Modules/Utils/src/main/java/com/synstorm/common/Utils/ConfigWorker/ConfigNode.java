package com.synstorm.common.Utils.ConfigWorker;


import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dmitry.Bozhko on 7/2/2014.
 */

@Model_v0
@NonProductionLegacy

public class ConfigNode extends TemplateNode {
    private String value;
    private List<ConfigNode> childNodes;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<ConfigNode> getChildNodes() {
        return childNodes;
    }

    public void addNode(ConfigNode node) {
        childNodes.add(node);
    }

    public void removeNode(ConfigNode node) {
        childNodes.remove(node);
    }

    public void addAttribute(String name, String value) {
        nodeAttributes.put(name, new ConfigAttribute(name, value));
    }

    public ConfigNode(String nodeName, int nodeLevel) {
        super(nodeName, nodeLevel);
        childNodes = new ArrayList<>();
    }

    public ConfigAttribute getAttribute(String name) {
        return (ConfigAttribute)nodeAttributes.get(name);
    }

    @Nullable
    public ConfigNode getNode(String attrName, String attrValue) {
        for (ConfigNode node : childNodes) {
            if(node.getAttribute(attrName).getValue().equals(attrValue))
                return node;
        }

        return null;
    }

    public List<String> getChildNodesAttributeValues(String attrName) {
        return childNodes.stream().map(node -> node.getAttribute(attrName).getValue()).collect(Collectors.toList());
    }
}
