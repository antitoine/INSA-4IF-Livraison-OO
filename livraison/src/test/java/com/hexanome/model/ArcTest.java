/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.model;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
        Node result = arc.getDest();
        assertEquals(dest, result);
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
        Node result = arc.getSrc();
        assertEquals(src, result);
    }

    /**
     * Test of getAssociatedTimeSlot method, of class Arc.
     */
    @Test
    public void testGetAssociatedTimeSlots() {
        System.out.println("getAssociatedTimeSlots");
        
        // Create a map
        Map map = new Map();
        
        // Fill map
        Node warehouse = map.createNode(0, new Point(0,0));
        map.createNode(1, new Point(20,20));
        Node deliv0 = map.createNode(2, new Point(10,10));
        map.createNode(3, new Point(10,0));
        Node deliv1 = map.createNode(4, new Point(0,10));
        map.createNode(5, new Point(20,0));
        // Add arcs
        map.createArc("a", 1000, 10, 0, 1);
        //map.createArc("b", 1000, 10, 0, 2);
        map.createArc("c", 1000, 10, 0, 3);
        Arc A04 = map.createArc("d", 1000, 10, 0, 4);
        map.createArc("e", 1000, 10, 0, 5);
        map.createArc("f", 1000, 10, 1, 2);
        map.createArc("g", 1000, 10, 1, 3);
        map.createArc("h", 1000, 10, 1, 4);
        map.createArc("i", 1000, 10, 1, 5);
        map.createArc("j", 1000, 10, 2, 3);
        //map.createArc("k", 1000, 10, 2, 4);
        map.createArc("l", 1000, 10, 2, 5);
        map.createArc("m", 1000, 10, 3, 4);
        map.createArc("n", 1000, 10, 3, 5);
        map.createArc("o", 1000, 10, 4, 5);
        map.createArc("e", 1000, 10, 5, 0);
        map.createArc("i", 1000, 10, 5, 1);
        map.createArc("l", 1000, 10, 5, 2);
        map.createArc("n", 1000, 10, 5, 3);
        map.createArc("o", 1000, 10, 5, 4);
        //map.createArc("d", 1000, 10, 4, 0);
        map.createArc("h", 1000, 10, 4, 1);
        Arc A42 = map.createArc("k", 1000, 10, 4, 2);
        map.createArc("m", 1000, 10, 4, 3);
        map.createArc("j", 1000, 10, 3, 2);
        map.createArc("g", 1000, 10, 3, 1);
        map.createArc("c", 1000, 10, 3, 0);
        map.createArc("f", 1000, 10, 2, 1);
        Arc A20 = map.createArc("b", 1000, 10, 2, 0);
        map.createArc("a", 1000, 10, 1, 0);
        
        // Create deliveries
        ArrayList<Delivery> deliveries = new ArrayList<>();
        deliveries.add(new Delivery(0, deliv0));
        deliveries.add(new Delivery(1, deliv1));
        
        // Create a timeslot
        ArrayList<TimeSlot> timeslots = new ArrayList<>();
        timeslots.add(new TimeSlot(8*3600, 12*3600, deliveries));
        
        // Create a planning
        Planning planning = new Planning(map, warehouse, timeslots);
        
        try {
            // Compute route
            planning.computeRoute();
        } catch (Exception ex) {
            fail("planning.computeRoute() failed !");
        }
        
        assertEquals(A04.getAssociatedTimeSlots().size(), 1);
        assertEquals(A42.getAssociatedTimeSlots().size(), 1);
        assertEquals(A20.getAssociatedTimeSlots().size(), 1);
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
        TimeSlot ts = new TimeSlot(8,9,deliveries1);
        Set<TimeSlot> expResult = new HashSet<>();
        expResult.add(ts);
        
        arc1.addAssociatedTimeSlot(ts);
        Set<TimeSlot> result = arc1.getAssociatedTimeSlots();
        
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
        Arc arc = new Arc("hollywood", 12, 10, node1, node2);
        
        float expResult = 1.2F;
        float result = arc.getDuration();
        assertEquals(expResult, result, 0.0);
    }
   
}
