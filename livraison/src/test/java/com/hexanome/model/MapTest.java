/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.model;

import com.hexanome.utils.Subscriber;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Estelle
 */
public class MapTest {
    
    public MapTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of createNode method, of class Map.
     */
    @Test
    public void testCreateNode() {
        System.out.println("createNode");
        // init objects
        int id = 20;
        Point location = new Point(15, 15);
        Map map = new Map();
        
        // use case
        Node expResultNode = new Node(id, location);
        Node resultNode = map.createNode(id, location);
        assertEquals(expResultNode.toString(), resultNode.toString());
        assertEquals(map.getNodeById(id).toString(), resultNode.toString());
    }

    /**
     * Test of createArc method, of class Map.
     */
    @Test
    public void testCreateArc() {
        System.out.println("createArc");
        // init objects
        String streetName = "holywood";
        float length = 125;
        float avgSpeed = 31;
        int srcNodeId = 15;
        int destNodeId = 20;
        Map map = new Map();
        map.createNode(15, new Point(20,30));
        map.createNode(20, new Point(50,42));
        
        // use case
        Arc expResult = new Arc(streetName, length, avgSpeed, map.getNodeById(15), map.getNodeById(20));
        Arc result = map.createArc(streetName, length, avgSpeed, srcNodeId, destNodeId);
        assertEquals(expResult.toString(), result.toString());
        assertEquals(map.getArcs().get(0).toString(), result.toString());
    }

    /**
     * Test of getNodeByLocation method, of class Map.
     */
    @Test
    public void testGetNodeByLocation() {
        System.out.println("getNodeByLocation");
        Point location = null;
        Map instance = new Map();
        Node expResult = null;
        Node result = instance.getNodeByLocation(location);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNodeById method, of class Map.
     */
    @Test
    public void testGetNodeById() {
        System.out.println("getNodeById");
        int id = 0;
        Map instance = new Map();
        Node expResult = null;
        Node result = instance.getNodeById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getArcs method, of class Map.
     */
    @Test
    public void testGetArcs() {
        System.out.println("getArcs");
        Map instance = new Map();
        ArrayList<Arc> expResult = null;
        ArrayList<Arc> result = instance.getArcs();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNodes method, of class Map.
     */
    @Test
    public void testGetNodes() {
        System.out.println("getNodes");
        Map instance = new Map();
        HashMap<Integer, Node> expResult = null;
        HashMap<Integer, Node> result = instance.getNodes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFastestPath method, of class Map.
     */
    @Test
    public void testGetFastestPath() {
        System.out.println("getFastestPath");
        Node start = null;
        Node end = null;
        Map instance = new Map();
        Path expResult = null;
        Path result = instance.getFastestPath(start, end);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clear method, of class Map.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        Map instance = new Map();
        instance.clear();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
