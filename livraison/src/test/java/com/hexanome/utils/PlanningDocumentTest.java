/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.utils;

import com.hexanome.model.Map;
import com.hexanome.model.Node;
import com.hexanome.model.TimeSlot;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author paul
 */
public class PlanningDocumentTest {
    
    public PlanningDocumentTest() {
    }

    /**
     * Test of getWarehouse method, of class PlanningDocument.
     */
    @Test
    public void testGetWarehouse() {
        try {
            System.out.println("getWarehouse");
            // Create Map
            SAXBuilder builder = new SAXBuilder();
            Map map = new Map();
            File mapFile = new File("src/test/java/com/hexanome/utils/plan10x10.xml");
            MapDocument mapDoc = new MapDocument(builder.build(mapFile));
            mapDoc.fillMap(map);
            // Create planning document
            File planningFile = new File("src/test/java/com/hexanome/utils/livraison10x10.xml");
            PlanningDocument instance = new PlanningDocument(builder.build(planningFile));
            // Check result
            Node expResult = map.getNodeById(2);
            Node result = instance.getWarehouse(map);
            assertEquals(expResult, result);
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(PlanningDocumentTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of getTimeSlots method, of class PlanningDocument.
     */
    @Test
    public void testGetTimeSlots() {
        try {
            System.out.println("getTimeSlots");
            // Create Map
            SAXBuilder builder = new SAXBuilder();
            Map map = new Map();
            File mapFile = new File("src/test/java/com/hexanome/utils/plan10x10.xml");
            MapDocument mapDoc = new MapDocument(builder.build(mapFile));
            mapDoc.fillMap(map);
            // Create Planning
            File planningFile = new File("src/test/java/com/hexanome/utils/livraison10x10.xml");
            PlanningDocument instance = new PlanningDocument(builder.build(planningFile));
            // Check results
            ArrayList<TimeSlot> result = instance.getTimeSlots(map);
            assertEquals(result.size(), 1); // Check number of timeslots
            assertEquals(result.get(0).getDeliveries().size(), 2); // Check number of deliveries
            assertEquals(result.get(0).getDeliveries().get(0).getNode(), map.getNodeById(0)); 
            assertEquals(result.get(0).getDeliveries().get(1).getNode(), map.getNodeById(1));
            assertEquals(result.get(0).getStartTime(), 8*3600);
            assertEquals(result.get(0).getEndTime(), 12*3600);
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(PlanningDocumentTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of checkIntegrity method, of class PlanningDocument.
     */
    @Test
    public void testCheckIntegrity() {
        try {
            System.out.println("checkIntegrity");
            // Create Map
            SAXBuilder builder = new SAXBuilder();
            Map map = new Map();
            File mapFile = new File("src/test/java/com/hexanome/utils/plan10x10.xml");
            MapDocument mapDoc = new MapDocument(builder.build(mapFile));
            mapDoc.fillMap(map);
            // Create Planning
            File planningFile = new File("src/test/java/com/hexanome/utils/livraison10x10.xml");
            PlanningDocument instance = new PlanningDocument(builder.build(planningFile));
            // Check result
            boolean result = instance.checkIntegrity(map);
            assertEquals(true, result);
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(PlanningDocumentTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
