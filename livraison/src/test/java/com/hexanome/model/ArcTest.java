///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.hexanome.model;
//
//import java.awt.Point;
//import java.util.ArrayList;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author guillaume
// */
//public class ArcTest {
//    
//    public ArcTest() {
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
//    /**
//     * Test of getDest method, of class Arc.
//     */
//    @Test
//    public void testGetDest() {
//        System.out.println("getDest");
//        
//        Map map = new Map();
//        Node src = map.createNode(1, new Point(20,30));
//        Node dest = map.createNode(2, new Point(30,30));      
//        Arc arc = map.createArc("hollywood", 12, 31, 1, 2);
//        Node expResult = dest;
//        Node result = arc.getDest();
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of getSrc method, of class Arc.
//     */
//    @Test
//    public void testGetSrc() {
//        System.out.println("getSrc");
//        Map map = new Map();
//        Node src = map.createNode(1, new Point(20,30));
//        Node dest = map.createNode(2, new Point(30,30));
//        Arc arc = map.createArc("hollywood", 12, 31, 1, 2);
//        Node expResult = src;
//        Node result = arc.getSrc();
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of getAssociatedTimeSlot method, of class Arc.
//     */
//    @Test
//    public void testGetAssociatedTimeSlot() {
//        System.out.println("getAssociatedTimeSlot");
//        
//        Map map = new Map();
//        
//        //Node simple
//        Node node1 = map.createNode(1, new Point(10,30));
//        Node node2 = map.createNode(2, new Point(10,20));
//        Arc arc1 = map.createArc("route1",12,31,1,2);
//              
//        TimeSlot result1 = arc1.getAssociatedTimeSlot();
//        assertEquals(result1,null);
//        
//        //Node with delivery and TimeSlot
//        Node warehouse = map.createNode(3, new Point(20,10));
//        Node node4 = map.createNode(4, new Point(20,20));
//        Node node5 = map.createNode(5, new Point(20,30));
//        Arc arc2 = map.createArc("route2",12,31,3,4);
//        final Arc arc3 = map.createArc("route3",12,31,4,5);
//        
//        Delivery delivery1 = new Delivery(1,node4);
//        ArrayList<Delivery> deliveries1 = new ArrayList<>();
//        deliveries1.add(delivery1);
//        final TimeSlot timeSlot = new TimeSlot(8,9,deliveries1);
//        delivery1.attachTimeSlot(timeSlot);
//        
//        ArrayList<TimeSlot> timeSlotList1 = new ArrayList<>();
//        timeSlotList1.add(timeSlot);
//        
//        Planning planning1 = new Planning(map,warehouse,timeSlotList1);
//        
//        try { 
//            planning1.computeRoute();
//        } catch (Exception ex) {
//            Logger.getLogger(ArcTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        TimeSlot result2 = arc3.getAssociatedTimeSlot();
//        assertEquals(timeSlot, result2);
//
//         //Node with delivery and without TimeSlot
//        Node node6 = new Node(6, new Point(30,30));
//        Node node7 = new Node(7, new Point(30,20));
//        final Arc arc4 = new Arc("route4",12,31,node6,node7);
//
//        Delivery delivery2 = new Delivery(1,node6);
//        ArrayList<Delivery> deliveries2 = new ArrayList<>();
//        deliveries2.add(delivery2);
//        
//        ArrayList<TimeSlot> timeSlotList2 = new ArrayList<>();
//        
//        Planning planning2 = new Planning(map,warehouse,timeSlotList2);
//        try {
//            planning2.computeRoute();
//        } catch (Exception ex) {
//            Logger.getLogger(ArcTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        TimeSlot result3 = arc4.getAssociatedTimeSlot();
//        assertEquals(result3, null);
//    }
//
//    /**
//     * Test of setAssociatedTimeSlot method, of class Arc.
//     */
//    @Test
//    public void testSetAssociatedTimeSlot() {
//        System.out.println("setAssociatedTimeSlot");
//        
//        Node node1 = new Node(1, new Point(10,30));
//        Node node2 = new Node(2, new Point(10,20));
//        Arc arc1 = new Arc("hollywood",12,31,node1,node2);
//        
//        Delivery delivery1 = new Delivery(1,node1);
//        ArrayList<Delivery> deliveries1 = new ArrayList<>();
//        deliveries1.add(delivery1);
//        TimeSlot expResult = new TimeSlot(8,9,deliveries1);
//        
//        arc1.setAssociatedTimeSlot(expResult);
//        TimeSlot result = arc1.getAssociatedTimeSlot();
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of getDuration method, of class Arc.
//     */
//    @Test
//    public void testGetDuration() {
//        System.out.println("getDuration");
//        
//        Node node1 = new Node(1, new Point(10,30));
//        Node node2 = new Node(2, new Point(10,20));
//        Arc arc = new Arc("hollywood",12,31,node1,node2);
//        
//        float expResult = 12*31;
//        float result = arc.getDuration();
//        assertEquals(expResult, result, 0.0);
//    }
//   
//}
