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
import java.util.List;
import static org.junit.Assert.*;

/**
 *
 * @author guillaume
 */
public class NodeTest {
    
    public NodeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of attachOutgoingArc method, of class Node.
     */
    @Test
    public void testAttachOutgoingArc() {
        System.out.println("attachOutgoingArc");
        Node node1 = new Node(1, new Point(10,10));
        Node node2 = new Node(2, new Point(10,20));
        Node node3 = new Node(3, new Point(10,30));
        Arc arc = new Arc("hollywood",12,31,node1,node2);
        
        node3.attachOutgoingArc(arc); //Function tested
        
        ArrayList<Arc> expResult = new ArrayList<>();
        expResult.add(arc);
        ArrayList<Arc> result = (ArrayList<Arc>) node3.getOutgoingArcs();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOutgoingArcs method, of class Node.
     */
    @Test
    public void testGetOutgoingArcs() {
        System.out.println("getOutgoingArcs");
        Node node1 = new Node(1, new Point(10,10));
        Node node2 = new Node(2, new Point(10,20));
        Node node3 = new Node(3, new Point(10,30));
        Arc arc = new Arc("hollywood",12,31,node1,node2);
        
        node3.attachOutgoingArc(arc); //Function tested
        
        ArrayList<Arc> expResult = new ArrayList<>();
        expResult.add(arc);
        ArrayList<Arc> result = (ArrayList<Arc>) node3.getOutgoingArcs(); //Function tested
        assertEquals(expResult, result);
    }

    /**
     * Test of getOutgoingArc method, of class Node.
     */
    @Test
    public void testGetOutgoingArc() {
        System.out.println("getOutgoingArc");
        
        Node dest = new Node(1, new Point(10,10));
        Node src = new Node(2, new Point(10,20));
        Arc expResult = new Arc("hollywood",12,31,src,dest);
        
        //first senario with result return arc
        Arc result = dest.getOutgoingArc(dest);
        assertEquals(expResult, result);
        
        //second senario with result is null
        Arc result1 = dest.getOutgoingArc(src);
        assertEquals(result1, null);
    }

    /**
     * Test of getId method, of class Node.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        int id = 2;
        Node node = new Node(id, new Point(10,10));
        int result = node.getId();
        assertEquals(id, result);
    }

    /**
     * Test of getLocation method, of class Node.
     */
    @Test
    public void testGetLocation() {
        System.out.println("getLocation");
        int id = 2;
        Point expResult = new Point(10,10);
        Node node = new Node(id, expResult);
        Point result = node.getLocation();
        assertEquals(expResult, result);
    }

    /**
     * Test of attachDelivery method, of class Node.
     */
    @Test
    public void testAttachDelivery() {
        System.out.println("attachDelivery");
        Node node = new Node(1, new Point(10,10));
        Delivery expResult = new Delivery(1,node);
        node.attachDelivery(expResult); //Function tested
        Delivery result = node.getDelivery();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDelivery method, of class Node.
     */
    @Test
    public void testGetDelivery() {
        System.out.println("getDelivery");
        Node node = new Node(1, new Point(10,10));
        Delivery expResult = new Delivery(1,node);
        node.attachDelivery(expResult);
        Delivery result = node.getDelivery(); //Function tested
        assertEquals(expResult, result);
    }    
}