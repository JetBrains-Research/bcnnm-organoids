package com.synstorm.common.Utils.ConfigWorker;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dmitry.Bozhko on 7/1/2014.
 */

@Model_v0
@NonProductionLegacy

public class ConfigReader {
    private ConfigTemplate configTemplate;
    private ConfigNode rootNode;

    public ConfigNode getRootNode() throws Exception {
        return rootNode;
    }

    public ConfigReader() {
        configTemplate = new ConfigTemplate();
    }

    public ConfigTemplate getConfigTemplate() {
        return configTemplate;
    }

    public void loadTemplate(String filename) {
        configTemplate.loadTemplate(filename);
    }

    public void readConfig(String filename) {
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

        Element root = doc.getDocumentElement();
        int zeroLevel = 0;

        if (root.getNodeName().equals(configTemplate.getZeroNode().getNodeName())) {
            rootNode = new ConfigNode(configTemplate.getZeroNode().getNodeName(),
                    configTemplate.getZeroNode().getNodeLevel());

            rootNode = getInnerNodes(zeroLevel + 1, rootNode, root);
        }
    }

    public ConfigNode getNodeByAddress(String path) {
        String[] splitPaths = path.split("\\|");
        ConfigNode cursor = rootNode;

        try {
            for (String value : splitPaths) {
                int attrStartIndex = value.indexOf("(");
                String nodeAttrToSearch = value.substring(attrStartIndex + 1, value.length() - 1);
                String[] searchParams = nodeAttrToSearch.split("=");
                cursor = cursor.getNode(searchParams[0], searchParams[1]);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    public String getAttributeValue(String path) {
        String result = "";
        String[] splitPaths = path.split("\\|");
        ConfigNode cursor = rootNode;
        ConfigAttribute attribute;

        try {
            for (String value : splitPaths) {
                if (value.startsWith("(")) {
                    String attributeName = value.substring(1, value.length() - 1);
                    attribute = cursor.getAttribute(attributeName);
                    result = attribute.getValue();
                    break;
                } else {
                    int attrStartIndex = value.indexOf("(");
                    String nodeAttrToSearch = value.substring(attrStartIndex + 1, value.length() - 1);
                    String[] searchParams = nodeAttrToSearch.split("=");
                    cursor = cursor.getNode(searchParams[0], searchParams[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public Map<String, String> getAttributeValues(String path) {
        Map<String, String> result = new HashMap<>();
        String[] splitPaths = path.split("\\|");
        ConfigNode cursor = rootNode;

        try {
            for (String value : splitPaths) {
                if (value.startsWith("(")) {
                    for (IAttribute attr : cursor.getNodeAttributes())
                        result.put(attr.getAttributeName(), ((ConfigAttribute)attr).getValue());
                    break;
                } else {
                    int attrStartIndex = value.indexOf("(");
                    String nodeAttrToSearch = value.substring(attrStartIndex + 1, value.length() - 1);
                    String[] searchParams = nodeAttrToSearch.split("=");
                    cursor = cursor.getNode(searchParams[0], searchParams[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<String> getAttributesValueList(String path) {
        List<String> result = new ArrayList<>();
        String[] splitPaths = path.split("\\|");
        ConfigNode cursor = rootNode;

        for (String value : splitPaths) {
            if (value.startsWith("(")) {
                String attributeName = value.substring(1, value.length() - 1);
                result = cursor.getChildNodesAttributeValues(attributeName);
                break;
            } else {
                int attrStartIndex = value.indexOf("(");
                String nodeAttrToSearch = value.substring(attrStartIndex + 1, value.length() - 1);
                String[] searchParams = nodeAttrToSearch.split("=");
                cursor = cursor.getNode(searchParams[0], searchParams[1]);
                if (cursor == null)
                    return result;
            }
        }

        return result;
    }

    private ConfigNode getInnerNodes(int level, ConfigNode node, Node xmlNode) {
        Map<String, TemplateNode> currentLevelNodes = configTemplate.getNodes(level);

        for (int i = 0; i < xmlNode.getChildNodes().getLength(); i++) {
            Node child = xmlNode.getChildNodes().item(i);
            String nodeName = child.getNodeName();

            if (!currentLevelNodes.containsKey(nodeName))
                continue;

            ConfigNode toAdd = new ConfigNode(nodeName, level);
            for (int j = 0; j < currentLevelNodes.get(nodeName).getNodeAttributes().size(); j++) {
                String attrName = currentLevelNodes.get(nodeName).getNodeAttributes().get(j).getAttributeName();
                if (child.getAttributes() != null) {
                    if (child.getAttributes().getNamedItem(attrName) != null) {
                        String attrValue = child.getAttributes().getNamedItem(attrName).getNodeValue();
                        toAdd.addAttribute(attrName, attrValue);
                    }
                }
            }

            level++;
            node.addNode(getInnerNodes(level, toAdd, child));
            level--;
        }

        return node;
    }

    public void createEmptyRootNodeFromTemplate() throws Exception {
        rootNode = new ConfigNode(getConfigTemplate().getZeroNode().getNodeName(), 0);
    }

    //moved from ConfigWriter
    public ConfigNode createNodeFromTemplate(TemplateNode templateNode) {
        int NodeLevel = templateNode.getNodeLevel();
        String NodeName = templateNode.getNodeName();
        List<IAttribute> NodeAttributes = templateNode.getNodeAttributes();
        ConfigNode result = new ConfigNode(NodeName, NodeLevel);

        for (IAttribute element : NodeAttributes) {
            result.addAttribute(element.getAttributeName(), "");
        }
        return result;
    }

    public void writeConfig(String fileName) throws Exception {
        DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dFactory.newDocumentBuilder();

        // root element
        Document doc = dBuilder.newDocument();
        Element rootElement = doc.createElement(rootNode.getNodeName());
        doc.appendChild(rootElement);

        List<ConfigNode> childNodes = rootNode.getChildNodes();
        makeXMLInnerNode(doc, rootElement, childNodes);

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute("indent-number", 2);
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fileName));

        transformer.transform(source, result);
    }

    private void makeXMLInnerNode(Document doc, Element rootElement, List<ConfigNode> childNodes) throws Exception {
        for (ConfigNode entry : childNodes) {
            Element childNode = doc.createElement(entry.getNodeName());
            rootElement.appendChild(childNode);

            List<IAttribute> attributes = entry.getNodeAttributes();
            for (IAttribute attrElement : attributes) {
                ConfigAttribute configAttribute = (ConfigAttribute) attrElement;
                Attr attr = doc.createAttribute(configAttribute.getAttributeName());
                attr.setValue(configAttribute.getValue());
                childNode.setAttributeNode(attr);
            }
            makeXMLInnerNode(doc, childNode, entry.getChildNodes());
        }
    }
}
