package com.synstorm.common.Utils.Details;

import com.synstorm.common.Utils.EnumTypes.CellFunctionalType;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by human-research on 26/05/16.
 */
public class CellDetailsTest {
    CellDetails stemCell = new CellDetails("StemCell", CellFunctionalType.StemCell);
    CellDetails interNeuron = new CellDetails("Neuron", CellFunctionalType.InterNeuron);

    @Before
    public void method() {
        stemCell.addEmittedFactor("EmittedFactor");
        stemCell.addAction("Create");
        stemCell.addAction("Mama_Myla_Ramu");
        stemCell.addProliferationInfo(new ProliferationDetails("Proliferate", "InterNeuron"));
        List<String> sRFList = new ArrayList<>();
        sRFList.add("MoveFactor");
        sRFList.add("Move2Factor");
        sRFList.add("Move3Factor");
        stemCell.addReceivedFactor("Move", sRFList);
        stemCell.addAxon(new AxonDetails()); //empty axon
        stemCell.addDendrite(new DendriteDetails()); //empty dendrite

        interNeuron.addAction("GrowAxon");
        interNeuron.addAction("Receive Signal");
        interNeuron.addEmittedFactor("AxonGrowingFactor");
        List<String> nRFList = new ArrayList<>();
        nRFList.add("AxonGrowingFactor");
        interNeuron.addReceivedFactor("GrowAxon", nRFList);
        interNeuron.addAxon(new AxonDetails("GABA", "0.8", "0.5", "0.3"));
        interNeuron.addDendrite(new DendriteDetails("0.5", "0.1"));
        interNeuron.getDendriteDetails().addDendriteReceptor("Gaba1R");
        interNeuron.getDendriteDetails().addDendriteReceptor("Gaba2R");
    }

    @Test
    public void getType() throws Exception {
        assertEquals(stemCell.getType(), "StemCell");
        assertEquals(interNeuron.getType(), "Neuron");
    }

    @Test
    public void getCellFunctionalType() throws Exception {
        assertEquals(stemCell.getCellFunctionalType(), CellFunctionalType.StemCell);
        assertEquals(interNeuron.getCellFunctionalType(), CellFunctionalType.InterNeuron);
    }

    @Test
    public void getReceivedFactors() throws Exception {
        Map<String, Set<String>> stemRcFactors = new TreeMap<>();
        Set<String> sRFSet = new TreeSet<>();
        sRFSet.add("MoveFactor");
        sRFSet.add("Move2Factor");
        sRFSet.add("Move3Factor");
        stemRcFactors.put("Move", sRFSet);
        assertEquals(stemCell.getReceivedFactors(), stemRcFactors);

        Map<String, Set<String>> neuronRcFactors = new TreeMap<>();
        Set<String> nRFSet = new TreeSet<>();
        nRFSet.add("AxonGrowingFactor");
        neuronRcFactors.put("GrowAxon", nRFSet);
        assertEquals(interNeuron.getReceivedFactors(), neuronRcFactors);

    }

    @Test
    public void setCellFunctionalType() throws Exception {
        interNeuron.setCellFunctionalType(CellFunctionalType.SensorNeuron);
        assertEquals(interNeuron.getCellFunctionalType(), CellFunctionalType.SensorNeuron);


    }

    @Test
    public void getAxonDetails() throws Exception {
        AxonDetails interNeuronAxon = interNeuron.getAxonDetails();
        assertEquals(interNeuronAxon.getNeurotransmitter(), "GABA");
        assertTrue(interNeuronAxon.getUptakeNTPercentage() == 0.3);
        assertTrue(interNeuronAxon.getReleasingNTPercentage() == 0.5);
        assertTrue(interNeuronAxon.getExpressingNTPercentage() == 0.8);
        assertTrue(interNeuronAxon.hasAxon());
    }

    @Test
    public void getDendriteDetails() throws Exception {
        DendriteDetails interNeuronDendrite = interNeuron.getDendriteDetails();
        assertTrue(interNeuronDendrite.hasDendrite());
        assertTrue(interNeuronDendrite.getConstantThreshold() == 0.1);
        assertTrue(interNeuronDendrite.getStimuliThreshold() == 0.5);
        Set<String> dendriteReceptors = new HashSet<>();
        dendriteReceptors.add("Gaba1R");
        dendriteReceptors.add("Gaba2R");
        assertEquals(interNeuronDendrite.getDendriteReceptors(), dendriteReceptors);

    }

    @Test
    public void getActions() throws Exception {
        Set<String> sActionsSet = new TreeSet<>();
        sActionsSet.add("Create");
        sActionsSet.add("Mama_Myla_Ramu");
        assertEquals(stemCell.getActions(), sActionsSet);

        Set<String> nActionsSet = new TreeSet<>();
        nActionsSet.add("GrowAxon");
        nActionsSet.add("Receive Signal");
        assertEquals(interNeuron.getActions(), nActionsSet);
    }

    @Test
    public void getProliferationInfoSet() throws Exception {
        Set<ProliferationDetails> stemProliferationSet = stemCell.getProliferationInfoSet();
        ProliferationDetails stemProl = (ProliferationDetails) stemProliferationSet.toArray()[0];
        assertEquals(stemProl.getCellType(), "InterNeuron");
        assertEquals(stemProl.getFactorName(), "Proliferate");
    }

    @Test
    public void checkActionExist() throws Exception {
        assertTrue(stemCell.checkActionExist("Create"));
        assertFalse(stemCell.checkActionExist("123123123123123"));
        assertTrue(interNeuron.checkActionExist("Receive Signal"));
        assertFalse(interNeuron.checkActionExist("PowerBall"));
    }

    @Test
    public void getEmittedFactors() throws Exception {
        Set<String> sEmFlist = new HashSet<>();
        sEmFlist.add("EmittedFactor");
        assertEquals(stemCell.getEmittedFactors(), sEmFlist);
    }


    @Test
    public void getReceivedFactorsByAction() throws Exception {
        Set<String> sRFSet = new HashSet<>();
        sRFSet.add("MoveFactor");
        sRFSet.add("Move2Factor");
        sRFSet.add("Move3Factor");
        assertEquals(stemCell.getReceivedFactorsByAction("Move"), sRFSet);
        assertNull(stemCell.getReceivedFactorsByAction("BlaBla"));
    }

    @Test
    public void getActionsByReceivedFactor() throws Exception {
        Set<String> rfSet = new HashSet<>();
        rfSet.add("Move");
        assertEquals(stemCell.getActionsByReceivedFactor("Move2Factor"), rfSet);
    }

    @Test
    public void getActionsWithReceivedFactors() throws Exception {
        Set<String> actions = new TreeSet<>();
        actions.add("Move");
        assertEquals(stemCell.getActionsWithReceivedFactors(), actions);
    }
}