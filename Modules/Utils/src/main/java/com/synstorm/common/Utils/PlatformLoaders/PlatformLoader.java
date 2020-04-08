/*
 * Copyright (c) 2018. This file was made as a part of BCNNM@JetBrains_Research work.
 */

package com.synstorm.common.Utils.PlatformLoaders;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.PlatformLoaders.Configuration.PlatformConfiguration;
import com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM.BCNNM;
import com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM.ModelCell;
import com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM.SP;
import com.synstorm.common.Utils.PlatformLoaders.XMLTools.Individual.Individual;
import com.synstorm.common.Utils.TraceMessageWriter.PriorityTraceWriter;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Purpose of this class:
 * 1. To hold the JAXB objects of configuration
 * 2. To hold the configuration as objects for Model
 * 3. To load XML configs
 * 4. To validate XML over XSD and another checks
 *
 * This class should be used in SimulationModel.Loader.
 */

@Model_v1
public class PlatformLoader {
    //region Fields
    private BCNNM model;
    private Individual individual;
    private String configPath;

    private PlatformConfiguration platformConfiguration;

    //endregion


    //region Constructors

    public PlatformLoader(String configPath) {
        this.configPath = configPath;
        this.platformConfiguration = new PlatformConfiguration();
    }


    //endregion


    //region Getters and Setters
    public PlatformConfiguration getPlatformConfiguration() {
        return platformConfiguration;
    }

    //endregion


    //region Public Methods
    public boolean load() {
        return verify() && transformConfiguration();

    }

//    public void saveIndividualConfiguration(IndividualConfiguration individualConfiguration, String pathPrefix, String customIndividualFilename) {
//
//        Individual jaxbIndividual = new Individual();
//        jaxbIndividual.setId(ConstantValues.DEFAULT_START_UUID.toString());
//
//        Individual.Objects objects = new Individual.Objects();
//        individualConfiguration.getSpaceObjects().forEach(spaceObject -> {
//            Object object = new Object();
//            object.setId(BigInteger.valueOf(spaceObject.getId()));
//            object.setGroup(spaceObject.getGroup());
//            object.setType(spaceObject.getILogicObjectDescription().getId());
//            object.setX(spaceObject.getCoordinate()[0]);
//            object.setY(spaceObject.getCoordinate()[1]);
//            object.setZ(spaceObject.getCoordinate()[2]);
//
//            if (spaceObject.getAxonConnections() != null)
//                object.setAxonConnections(spaceObject.getAxonConnections());
//
//            object.setAxonPoints(spaceObject.getAxonPoints());
//            objects.getObject().add(object);
//        });
//        jaxbIndividual.setObjects(objects);
//
//        Individual.SpaceState spaceState = new Individual.SpaceState();
//        individualConfiguration.getStateMap().forEach((key, value) -> {
//            for (Pair<Integer, Integer> pair : value) {
//                State state = new State();
//                state.setId(BigInteger.valueOf(key));
//                state.setLigand(BigInteger.valueOf(pair.getFirst()));
//                state.setRadius(BigInteger.valueOf(pair.getSecond()));
//                spaceState.getState().add(state);
//            }
//        });
//        jaxbIndividual.setSpaceState(spaceState);
//        JAXBContext context;
//        Marshaller marshaller;
//
//        try {
//            context = JAXBContext.newInstance(Individual.class);
//            marshaller = context.createMarshaller();
//            //marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            marshaller.marshal(jaxbIndividual, FileStreamsFactory.INSTANCE.getXMLFileOutputStream(pathPrefix, "individual", customIndividualFilename));
//        } catch (IOException e1) {
//            PriorityTraceWriter.println("Unable to write XML of experiment results. Individual.xml will not be saved.", 0);
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
//    }



    private boolean verify()  {
        if (configPath == null) {
            PriorityTraceWriter.println("ERROR: configPath not set.", 0);
            return false;
        }

        return verifyConfig("model", BCNNM.class)
                && verifyConfig("individual", Individual.class)
                && verifyObjects(model, individual)
                ;
    }

    private boolean verifyConfig(String tag, Class<?> clazz) {
        JAXBContext context;
        Unmarshaller unmarshaller;

        PriorityTraceWriter.println("Verification of " + tag + "...", 0);
        try {
            context = JAXBContext.newInstance(clazz);
            unmarshaller = context.createUnmarshaller();
            if (!validateXML(tag))
                throw new JAXBException(tag + " is invalid.");

            switch (tag) {
                case "model": {
                    this.model = (BCNNM) unmarshaller.unmarshal(FileStreamsFactory.INSTANCE.getXMLFileInputStream(configPath, tag));
                    break;
                }
                case "individual": {
                    this.individual = (Individual) unmarshaller.unmarshal(FileStreamsFactory.INSTANCE.getXMLFileInputStream(configPath, tag));
                    break;
                }
                default:
                    throw new FileNotFoundException("Wrong tag");
            }


        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        PriorityTraceWriter.println(tag + " verified.", 0);

        return true;
    }

    private boolean transformConfiguration() {
        return platformConfiguration.loadConfiguration(model, individual);
        //return false;
    }


    private boolean validateXML(String configTag) {
        try {
            final Source xmlSrc = new StreamSource(
                    FileStreamsFactory.INSTANCE.getXMLFileInputStream(configPath, configTag));

            final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            final Schema schema = schemaFactory.newSchema(new StreamSource(
                    FileStreamsFactory.INSTANCE.getXSDFileInputStream(configPath, configTag)));
            final Validator xsdValidator = schema.newValidator();
            xsdValidator.validate(xmlSrc);

/*            final Source xmlSrc1 = new StreamSource(
                    FileStreamsFactory.INSTANCE.getXMLFileInputStream(configPath, configTag));
            final Source xsl = new StreamSource(
                    FileStreamsFactory.INSTANCE.getXSLFileInputStream(configPath, configTag));
            final Result xmlDest = new StreamResult(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    //We only want to see if it fails test-wise, so output is sent nowhere.
                }
            });
            final TransformerFactory factory = new TransformerFactoryImpl();
            final Transformer transformer = factory.newTransformer(xsl);
            transformer.transform(xmlSrc1, xmlDest);*/

            return true;
        } catch (SAXParseException e) {
            PriorityTraceWriter.println("XML parse warning in \""+e.getSystemId()+"\", line "+e.getLineNumber()+", column "+e.getColumnNumber()+": "+e.getMessage(), 0);
            System.exit(-1);
        }

        catch (SAXException e) {
            e.getStackTrace();
            PriorityTraceWriter.println(e.getMessage(), 0);
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    //endregion


    //region Private Methods
    private boolean verifyObjects(BCNNM model, Individual individual) {
        Set<String> signalingPathways = new HashSet<>();
        Set<String> cells = new HashSet<>();
        Set<Integer> cellsFlatCoordinates = new HashSet<>();
        Set<String> signalPointsTypes = new HashSet<>();
        boolean checkState = true;

        model.getMechanisms().getMechanism().forEach(mechanism -> mechanism.getSignalingPathway().forEach(sp -> signalingPathways.add(sp.getId())));
        model.getModelCells().getModelCell().forEach(cell -> cells.add(cell.getId()));
        if (model.getSignalPoints() != null)
            model.getSignalPoints().getSignalPoint().forEach(signalPointType -> signalPointsTypes.add(signalPointType.getType()));


        //case 0: existance of cell's SP in signaling pathways
        for (ModelCell cell : model.getModelCells().getModelCell()) {
            for (SP sp : cell.getSPs().getSP()) {
                if (!signalingPathways.contains(sp.getType())) {
                    PriorityTraceWriter.println("Cell: " + cell.getId() + " has invalid SP: " + sp.getType(), 0);
                    checkState = false;
                }
            }
        }


        for (com.synstorm.common.Utils.PlatformLoaders.XMLTools.Individual.Object object : individual.getObjects().getObject()) {
            //case 1: instance of cell must have correct baseType
            if (!cells.contains(object.getType()) & !signalPointsTypes.contains(object.getType()) ) {
                PriorityTraceWriter.println("Object #" + object.getId().toString() + " has invalid type.", 0);
                checkState = false;
            }

            //case 2: cell instances must have unique coordinates
            int flatCoord = platformConfiguration.getFlatCoordinate(
                    object.getX(),
                    object.getY(),
                    object.getZ());
            if (cells.contains(object.getType()) && !cellsFlatCoordinates.add(flatCoord)) {
                PriorityTraceWriter.printf(0,"Cell #%s has non-unique coordinate. FLAT COORDINATE: %s\n", object.getId().toString(), flatCoord);
                checkState = false;
            }
        }

        //case 3: signal point type should use existent SPs
        if (model.getSignalPoints() != null) {
            for (com.synstorm.common.Utils.PlatformLoaders.XMLTools.BCNNM.SignalPoint signalPointType : model.getSignalPoints().getSignalPoint()) {
                for (SP sp : signalPointType.getSPs().getSP()) {
                    if (!signalingPathways.contains(sp.getType())) {
                        PriorityTraceWriter.println("Signal Point type: " + signalPointType.getType()
                                + " has unknown SP: " + sp.getType() + ".", 0);
                        checkState = false;
                    }
                }
            }
        }

        return checkState;
    }
    //endregion

}
