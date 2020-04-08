package com.synstorm.common.Utils.Details;

import com.synstorm.common.Utils.EnumTypes.ActionFunctionalType;
import com.synstorm.common.Utils.EnumTypes.ActionSignalType;
import com.synstorm.common.Utils.EnumTypes.SignalingTopologicalType;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by human-research on 12/05/16.
 */
public class ActionDetailsTest {
    ActionDetails actionDetails = new ActionDetails("TestAction", "25", "Continuous", "InnerCell", "Do", "25", "0", "0");

    @Test
    public void getName() throws Exception {
        assertEquals("TestAction", actionDetails.getName());
    }

    @Test
    public void getNeighborhood() throws Exception {
        assertTrue(25 == actionDetails.getNeighborhood());
    }

    @Test
    public void getSignalType() throws Exception {
        assertEquals(ActionSignalType.Continuous, actionDetails.getSignalType());
    }

    @Test
    public void getSignalingTopologicalType() throws Exception {
        assertEquals(SignalingTopologicalType.InnerCell, actionDetails.getSignalingTopologicalType());
    }

    @Test
    public void getActionFunctionalType() throws Exception {
        assertEquals(ActionFunctionalType.Do, actionDetails.getActionFunctionalType());
    }

    @Test
    public void getDuration() throws Exception {
        assertTrue(25 == actionDetails.getDuration());
    }

    @Test
    public void getDurationByGenes() throws Exception {

    }

    @Test
    public void isRepeatable() throws Exception {
        assertFalse(actionDetails.isRepeatable());
    }

    @Test
    public void isInitial() throws Exception {
        assertFalse(actionDetails.isInitial());
    }

    @Test
    public void setNeighborhood() throws Exception {
        actionDetails.setNeighborhood(30);
        assertTrue(30 == actionDetails.getNeighborhood());
    }

    @Test
    public void setSignalType() throws Exception {
        actionDetails.setSignalType(ActionSignalType.Discrete);
        assertEquals(ActionSignalType.Discrete, actionDetails.getSignalType());
    }

    @Test
    public void setSignalingTopologicalType() throws Exception {
        actionDetails.setSignalingTopologicalType(SignalingTopologicalType.InnerOuterCell);
        assertEquals(SignalingTopologicalType.InnerOuterCell, actionDetails.getSignalingTopologicalType());
    }

    @Test
    public void setActionFunctionalType() throws Exception {
        actionDetails.setActionFunctionalType(ActionFunctionalType.Create);
        assertEquals(ActionFunctionalType.Create, actionDetails.getActionFunctionalType());
    }

    @Test
    public void setDuration() throws Exception {
        actionDetails.setDuration(100);
        assertTrue(100 == actionDetails.getDuration());
    }

    @Test
    public void setRepeatable() throws Exception {
        actionDetails.setRepeatable(true);
        assertTrue(actionDetails.isRepeatable());
    }

    @Test
    public void setInitial() throws Exception {
        actionDetails.setInitial(true);
        assertTrue(actionDetails.isInitial());
    }

    @Test
    public void clearDurationByGenes() throws Exception {

    }

    @Test
    public void calculateDurationByGenes() throws Exception {

    }

}