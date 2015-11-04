/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.model;

import java.awt.Point;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author guillaume
 */
public class PathTest {
    
    public PathTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getArcs method, of class Path.
     */
    @Test
    public void testGetArcs() {
        System.out.println("getArcs");
        
        Node node1 = new Node(1, new Point(10,30));
        Node node2 = new Node(2, new Point(10,20));
        Arc arc1 = new Arc("hollywood",12,31,node1,node2);
        Arc arc2 = new Arc("doowylloh",12,31,node2,node1);      
        ArrayList<Arc> expResult = new ArrayList<>();
        expResult.add(arc1);
        expResult.add(arc2);    
        Path instance = new Path(expResult);
        ArrayList<Arc> result = instance.getArcs();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFirstNode method, of class Path.
     */
    @Test
    public void testGetFirstNode() {
        System.out.println("getFirstNode");
        Node first = new Node(1, new Point(10,10));
        Node second = new Node(2, new Point(10,20));
        Node third = new Node(2, new Point(10,30));
        Arc arc1 = new Arc("hollywood",12,31,first,second);
        Arc arc2 = new Arc("doowylloh",12,31,second,third);      
        ArrayList<Arc> expResult = new ArrayList<>();
        expResult.add(arc1);
        expResult.add(arc2);    
        Path instance = new Path(expResult);
        Node result = instance.getFirstNode();
        assertEquals(first, result);
    }

    /**
     * Test of getLastNode method, of class Path.
     */
    @Test
    public void testGetLastNode() {
        System.out.println("getLastNode");
        Node first = new Node(1, new Point(10,10));
        Node second = new Node(2, new Point(10,20));
        Node third = new Node(2, new Point(10,30));
        Arc arc1 = new Arc("hollywood",12,31,first,second);
        Arc arc2 = new Arc("doowylloh",12,31,second,third);      
        ArrayList<Arc> expResult = new ArrayList<>();
        expResult.add(arc1);
        expResult.add(arc2);    
        Path instance = new Path(expResult);
        Node result = instance.getLastNode();
        assertEquals(third, result);
    }

    /**
     * Test of containsNode method, of class Path.
     */
    @Test
    public void testContainsNode() {
        System.out.println("containsNode");
        
        //Node is in the path
        Node first = new Node(1, new Point(10,10));
        Node second = new Node(2, new Point(10,20));
        Node test = new Node(2, new Point(10,30));
        Arc arc1 = new Arc("hollywood",12,31,first,second);
        Arc arc2 = new Arc("doowylloh",12,31,second,test);      
        ArrayList<Arc> arcsListWithNodeTest = new ArrayList<>();
        arcsListWithNodeTest.add(arc1);
        arcsListWithNodeTest.add(arc2);
        
        Path instance1 = new Path(arcsListWithNodeTest);
        boolean result1 = instance1.containsNode(test);
        assertEquals(true, result1);
        
        //Node is not in the path
        ArrayList<Arc> arcsListWithoutNodeTest = new ArrayList<>();
        arcsListWithoutNodeTest.add(arc1);
        Path instance2 = new Path(arcsListWithoutNodeTest);
        boolean result2 = instance2.containsNode(test);
        assertEquals(false, result2);
    }

    /**
     * Test of getPathDuration method, of class Path.
     */
    @Test
    public void testGetPathDuration() {
        System.out.println("getPathDuration");
        
        //Node is in the path
        Node first = new Node(1, new Point(10,10));
        Node second = new Node(2, new Point(10,20));
        Node test = new Node(2, new Point(10,30));
        Arc arc1 = new Arc("hollywood",12,10,first,second);
        Arc arc2 = new Arc("doowylloh",12,10,second,test);      
        ArrayList<Arc> arcsList = new ArrayList<>();
        arcsList.add(arc1);
        arcsList.add(arc2);
        Path instance = new Path(arcsList);

        float expResult = 240.0F;
        float result = instance.getPathDuration();
        assertEquals(expResult, result, 0.0);
    }    
}
