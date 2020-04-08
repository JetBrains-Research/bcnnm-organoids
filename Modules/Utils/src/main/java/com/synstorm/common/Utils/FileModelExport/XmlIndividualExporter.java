package com.synstorm.common.Utils.FileModelExport;

import com.synstorm.common.Utils.PlatformLoaders.XMLTools.Individual.Individual;
import com.synstorm.common.Utils.PlatformLoaders.XMLTools.Individual.State;
import com.synstorm.common.Utils.TraceMessageWriter.PriorityTraceWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;

/**
 * Created by human-research on 2019-03-14.
 */
public class XmlIndividualExporter extends XmlFileExporter {
    //region Fields
    private JAXBContext jaxbContext;
    private Marshaller marshaller;
    private boolean initialized;
    //endregion


    //region Constructors
    public XmlIndividualExporter(String uuidAsString) {
        super(uuidAsString + "_Individual.xml");
        this.initialized = initXmlTools();
    }
    //endregion


    //region Getters and Setters

    //endregion

    //region Public Methods
    @Override
    public void writeHeader(String header) {
        if (initialized) {
            try {
                bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
                bufferedWriter.write("<Individual id=\"" + header + "\">\n");
            } catch (IOException e) {
                PriorityTraceWriter.println("IOException caught in XmlIndividualExporter:writeHeader. Individual will not be saved.", 0);
                this.initialized = false;
            }
        }
    }

    public void writeObjectsHeader() {
        if (initialized) {
            try {
                bufferedWriter.write("<Objects>\n");
            } catch (IOException e) {
                PriorityTraceWriter.println("IOException caught in XmlIndividualExporter:writeObjectsHeader. Individual will not be saved.", 0);
                this.initialized = false;
            }
        }
    }

    public void writeLogicObject(com.synstorm.common.Utils.PlatformLoaders.XMLTools.Individual.Object object)  {
        if (initialized) {
            try {
                marshaller.marshal(object, bufferedWriter);
                bufferedWriter.write("\n");
            } catch (JAXBException e) {
                PriorityTraceWriter.println("JAXBException caught in XmlIndividualExporter:writeLogicObject. Individual will not be saved.", 0);
                this.initialized = false;
            } catch (IOException e1) {
                PriorityTraceWriter.println("IOException caught in XmlIndividualExporter:writeLogicObject. Individual will not be saved.", 0);
                this.initialized = false;
            }
        }
    }

    public void writeObjectsFooter() {
        if (initialized) {
            try {
                bufferedWriter.write("</Objects>\n");
            } catch (IOException e) {
                PriorityTraceWriter.println("IOException caught in XmlIndividualExporter:writeObjectsFooter. Individual will not be saved.", 0);
                this.initialized = false;
            }
        }
    }

    public void writeSpaceStateHeader() {
        if (initialized) {
            try {
                bufferedWriter.write("<SpaceState>\n");
            } catch (IOException e) {
                PriorityTraceWriter.println("IOException caught in XmlIndividualExporter:writeSpaceStateHeader. Individual will not be saved.", 0);
                this.initialized = false;
            }
        }
    }

    public void writeSpaceState(State state)  {
        if (initialized) {
            try {
                marshaller.marshal(state, bufferedWriter);
                bufferedWriter.write("\n");
            } catch (JAXBException e) {
                PriorityTraceWriter.println("JAXBException caught in XmlIndividualExporter:writeSpaceState. Individual will not be saved.", 0);
                this.initialized = false;
            } catch (IOException e1) {
                PriorityTraceWriter.println("IOException caught in XmlIndividualExporter:writeSpaceState. Individual will not be saved.", 0);
                this.initialized = false;
            }
        }
    }

    public void writeSpaceStateFooter() {
        if (initialized) {
            try {
                bufferedWriter.write("</SpaceState>\n");
            } catch (IOException e) {
                PriorityTraceWriter.println("IOException caught in XmlIndividualExporter:writeSpaceStateFooter. Individual will not be saved.", 0);
                this.initialized = false;
            }
        }
    }

    public void writeFooter() {
        if (initialized) {
            try {
                bufferedWriter.write("</Individual>");
            } catch (IOException e) {
                PriorityTraceWriter.println("IOException caught in XmlIndividualExporter:writeFooter. Individual will not be saved.", 0);
                this.initialized = false;
            }
        }
    }

    @Override
    protected void initializeAllowedEvents() {

    }
    //endregion


    //region Private Methods
    private boolean initXmlTools() {
        final Individual jaxbIndividual = new Individual();
        try {
            this.jaxbContext = JAXBContext.newInstance(Individual.class);
            this.marshaller = jaxbContext.createMarshaller();
            this.marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            this.marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        } catch (JAXBException e) {
            PriorityTraceWriter.println("JAXBException caught in XmlIndividualExporter. Individual will not be saved.", 0);
            return false;
        }
        return true;
    }
    //endregion

}
