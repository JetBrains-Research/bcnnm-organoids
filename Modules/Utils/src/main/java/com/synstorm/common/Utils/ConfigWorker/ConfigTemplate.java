package com.synstorm.common.Utils.ConfigWorker;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.ConfigurationStrings.XmlQueryStrings;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Dmitry.Bozhko on 7/1/2014.
 */

@Model_v0
@NonProductionLegacy

public class ConfigTemplate {
    private Map<TemplateNode, Integer> templateNodes;

    public ConfigTemplate() {
        templateNodes = new LinkedHashMap<>();
    }


    public TemplateNode getZeroNode() {
        return templateNodes.entrySet().stream()
                .filter(val -> val.getValue() == 0)
                .findFirst().get().getKey();
    }

    public Map<String, TemplateNode> getNodes(int level) {
        return templateNodes.entrySet().stream()
                .filter(val -> val.getValue() == level)
                .map(Map.Entry::getKey)
                .collect(Collectors.toMap(TemplateNode::getNodeName, item -> item));
    }

    public void loadTemplate(String filename) {
        templateNodes = new HashMap<>();

        File configFile = new File(filename);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(configFile);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        assert doc != null;
        doc.getDocumentElement().normalize();

        Element levels = doc.getDocumentElement();
        NodeList levelsChildNodes = levels.getChildNodes();

        for (int i = 0; i < levelsChildNodes.getLength(); i++)
        {
            Node level = levelsChildNodes.item(i);
            if (!level.getNodeName().equals(XmlQueryStrings.CONF_TEMPLATE_LEVEL))
                continue;

            String nodeName = level.getAttributes().getNamedItem(XmlQueryStrings.CONF_TEMPLATE_NODENAME).getNodeValue();
            Integer nodeLevel = Integer.parseInt(level.getAttributes().getNamedItem(XmlQueryStrings.CONF_TEMPLATE_NUM).getNodeValue());

            TemplateNode toAdd = new TemplateNode(nodeName, nodeLevel);

            NodeList child = ((Element)level).getElementsByTagName(XmlQueryStrings.CONF_TEMPLATE_ATTRIBUTES);

            if (child.getLength() > 0) {
                NodeList attributes = child.item(0).getChildNodes();

                for (int j = 0; j < attributes.getLength(); j++) {
                    if (attributes.item(j).getNodeName().equals("#text"))
                        continue;
                    String attribute = attributes.item(j).getNodeName();
                    toAdd.addAttribute(attribute);
                }
            }
            templateNodes.put(toAdd, nodeLevel);
        }
    }
}
