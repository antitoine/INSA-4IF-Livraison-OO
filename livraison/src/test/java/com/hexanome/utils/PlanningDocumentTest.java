/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.utils;

import com.hexanome.model.Map;
import com.hexanome.model.Node;
import com.hexanome.model.TimeSlot;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author paul
 */
public class PlanningDocumentTest {
    
    public PlanningDocumentTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getWarehouse method, of class PlanningDocument.
     */
    @Test
    public void testGetWarehouse() {
        System.out.println("getWarehouse");
        Map map = null;
        PlanningDocument instance = null;
        Node expResult = null;
        Node result = instance.getWarehouse(map);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTimeSlots method, of class PlanningDocument.
     */
    @Test
    public void testGetTimeSlots() {
        System.out.println("getTimeSlots");
        Map map = null;
        PlanningDocument instance = null;
        ArrayList<TimeSlot> expResult = null;
        ArrayList<TimeSlot> result = instance.getTimeSlots(map);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkIntegrity method, of class PlanningDocument.
     */
    @Test
    public void testCheckIntegrity() {
        System.out.println("checkIntegrity");
        Map map = null;
        PlanningDocument instance = null;
        boolean expResult = false;
        boolean result = instance.checkIntegrity(map);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
