package com.synstorm.common.Utils.ConfigWorker;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dmitry.Bozhko on 7/1/2014.
 */

@Model_v0
@NonProductionLegacy

public class TemplateNode implements INode {
    private String nodeName;
    private int nodeLevel;
    protected Map<String, IAttribute> nodeAttributes;

    public String getNodeName() {
        return nodeName;
    }

    public int getNodeLevel() {
        return nodeLevel;
    }

    public List<IAttribute> getNodeAttributes() {
        return new ArrayList<>(nodeAttributes.values());
    }

    private TemplateNode() {
        nodeName = "";
        nodeLevel = -1;
        nodeAttributes = new HashMap<>();
    }

    public TemplateNode(String nodeName, int nodeLevel) {
        this();
        this.nodeName = nodeName;
        this.nodeLevel = nodeLevel;
    }

    public void addAttribute(String templateAttribute) {
        nodeAttributes.put(templateAttribute, new TemplateAttribute(templateAttribute));
    }
}
