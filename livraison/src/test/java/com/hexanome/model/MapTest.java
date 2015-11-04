/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.model;

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
        Point location = new Point(20,30);
        Map map = new Map();     
        Node expResult = map.createNode(1, new Point(20,30));
        Node result = map.getNodeByLocation(location);
        assertEquals(expResult, result);
    }

    /**
     * Test of getNodeById method, of class Map.
     */
    @Test
    public void testGetNodeById() {
        System.out.println("getNodeById");
        Map map = new Map();
        Node expResult = map.createNode(1, new Point(20,30));
        Node result = map.getNodeById(1);
        assertEquals(expResult, result);
    }

    /**
     * Test of getArcs method, of class Map.
     */
    @Test
    public void testGetArcs() {
        System.out.println("getArcs");
        Map map = new Map();
        Node node1 = map.createNode(1, new Point(20,30));
        Node node2 = map.createNode(2, new Point(30,20));
        Node node3 = map.createNode(3, new Point(20,20));
        Arc arc1 = map.createArc("hollywood", 12, 31, 1, 2);       
        
        //For one arc 
        ArrayList<Arc> expResult = new ArrayList<>();
        expResult.add(arc1);
        
        ArrayList<Arc> result = map.getArcs();
        
        result.get(0).toString();
        assertEquals(result.get(0).toString(), arc1.toString());
        assertEquals(result, expResult);
        //For several arc
        Arc arc2 = map.createArc("doowylloh", 12, 31, 2, 1);
        Arc arc3 = map.createArc("Insa", 12, 31, 1, 3);
        expResult.add(arc2);
        expResult.add(arc3);
        assertEquals(result, expResult);
    }

    /**
     * Test of getNodes method, of class Map.
     */
    @Test
    public void testGetNodes() {
        System.out.println("getNodes");
        Map map = new Map();
        Node node1 = map.createNode(1, new Point(20,30));
        HashMap<Integer, Node> expResult = new HashMap<>();
        expResult.put(1, node1);
        HashMap<Integer, Node> result = map.getNodes();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFastestPath method, of class Map.
     */
    @Test
    public void testGetFastestPath() {
        System.out.println("getFastestPath");
        Map map = new Map();
        Node start = map.createNode(1, new Point(20,30));
        Node inter = map.createNode(3, new Point(20,20));
        Node end = map.createNode(2, new Point(10,20));    
        
        Arc arc1 = map.createArc("route1", 12, 31, 1, 3);
        Arc arc2 = map.createArc("route2", 6, 31, 1, 2);
        Arc arc3 = map.createArc("route3", 5, 31, 2, 3);
        
        ArrayList<Arc> arcs = new ArrayList<>();
        arcs.add(arc2);
        arcs.add(arc3);
        Path expResult = new Path(arcs);
        Path result = map.getFastestPath(start, end);
        
        ArrayList<Arc> expResultList = expResult.getArcs();
        ArrayList<Arc> resultList = result.getArcs();
        assertEquals(expResultList.size(), resultList.size()); //test number of arcs.
        
        for(int i=0;i<expResultList.size();i++)
        {
            assertEquals(expResultList.get(i), resultList.get(i));
        }                
    }  
}
