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
public class ArcTest {
    
    public ArcTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getDest method, of class Arc.
     */
    @Test
    public void testGetDest() {
        System.out.println("getDest");
        
        Map map = new Map();
        Node src = map.createNode(1, new Point(20,30));
        Node dest = map.createNode(2, new Point(30,30));      
        Arc arc = map.createArc("hollywood", 12, 31, 1, 2);
        Node expResult = dest;
        Node result = arc.getDest();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSrc method, of class Arc.
     */
    @Test
    public void testGetSrc() {
        System.out.println("getSrc");
        Map map = new Map();
        Node src = map.createNode(1, new Point(20,30));
        Node dest = map.createNode(2, new Point(30,30));
        Arc arc = map.createArc("hollywood", 12, 31, 1, 2);
        Node expResult = src;
        Node result = arc.getSrc();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAssociatedTimeSlot method, of class Arc.
     */
    @Test
    public void testGetAssociatedTimeSlot() {
        System.out.println("getAssociatedTimeSlot");
        
        //Node simple
        Node node1 = new Node(1, new Point(10,30));
        Node node2 = new Node(2, new Point(10,20));
        Arc arc1 = new Arc("hollywood",12,31,node1,node2);
              
        TimeSlot result1 = arc1.getAssociatedTimeSlot();
        assertEquals(result1,null);
        
        //Node with delivery and TimeSlot
        Node node3 = new Node(3, new Point(20,30));
        Node node4 = new Node(4, new Point(20,20));
        Arc arc2 = new Arc("hollywood",12,31,node3,node4);
        
        Delivery delivery1 = new Delivery(1,node3);
        ArrayList<Delivery> deliveries1 = new ArrayList<>();
        deliveries1.add(delivery1);
        TimeSlot expResult1 = new TimeSlot(8,9,deliveries1);
        delivery1.attachTimeSlot(expResult1);
        TimeSlot result2 = arc2.getAssociatedTimeSlot();
        assertEquals(expResult1, result2);
        
        //Node with delivery and without TimeSlot
        Node node5 = new Node(5, new Point(30,30));
        Node node6 = new Node(6, new Point(30,20));
        Arc arc3 = new Arc("hollywood",12,31,node5,node6);
        
        Delivery delivery2 = new Delivery(1,node5);
        ArrayList<Delivery> deliveries2 = new ArrayList<>();
        deliveries2.add(delivery2);
        TimeSlot result3 = arc3.getAssociatedTimeSlot();
        assertEquals(result3, null);
        
    }

    /**
     * Test of setAssociatedTimeSlot method, of class Arc.
     */
    @Test
    public void testSetAssociatedTimeSlot() {
        System.out.println("setAssociatedTimeSlot");
        
        Node node1 = new Node(1, new Point(10,30));
        Node node2 = new Node(2, new Point(10,20));
        Arc arc1 = new Arc("hollywood",12,31,node1,node2);
        
        Delivery delivery1 = new Delivery(1,node1);
        ArrayList<Delivery> deliveries1 = new ArrayList<>();
        deliveries1.add(delivery1);
        TimeSlot expResult = new TimeSlot(8,9,deliveries1);
        
        arc1.setAssociatedTimeSlot(expResult);
        TimeSlot result = arc1.getAssociatedTimeSlot();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDuration method, of class Arc.
     */
    @Test
    public void testGetDuration() {
        System.out.println("getDuration");
        
        Node node1 = new Node(1, new Point(10,30));
        Node node2 = new Node(2, new Point(10,20));
        Arc arc = new Arc("hollywood",12,31,node1,node2);
        
        float expResult = 12*31;
        float result = arc.getDuration();
        assertEquals(expResult, result, 0.0);
    }
   
}
