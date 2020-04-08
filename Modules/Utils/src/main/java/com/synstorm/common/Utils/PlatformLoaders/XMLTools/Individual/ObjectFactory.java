
package com.synstorm.common.Utils.PlatformLoaders.XMLTools.Individual;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.synstorm.common.Utils.PlatformLoaders.XMLTools.Individual package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.synstorm.common.Utils.PlatformLoaders.XMLTools.Individual
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Individual }
     * 
     */
    public Individual createIndividual() {
        return new Individual();
    }

    /**
     * Create an instance of {@link Individual.Objects }
     * 
     */
    public Individual.Objects createIndividualObjects() {
        return new Individual.Objects();
    }

    /**
     * Create an instance of {@link Individual.SpaceState }
     * 
     */
    public Individual.SpaceState createIndividualSpaceState() {
        return new Individual.SpaceState();
    }

    /**
     * Create an instance of {@link State }
     * 
     */
    public State createState() {
        return new State();
    }

    /**
     * Create an instance of {@link Object }
     * 
     */
    public Object createObject() {
        return new Object();
    }

}
