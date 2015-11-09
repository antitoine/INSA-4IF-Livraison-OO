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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author lisa
 */
public class RouteTest {
    
    public RouteTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getNextDelivery method, of class Route.
     */
    @Test
    public void testGetNextDelivery() {
        System.out.println("getNextDelivery");
        
        Map map = new Map();
        
        Node node1 = map.createNode(1, new Point(20, 30));
        Node node2 = map.createNode(2, new Point(20, 20));
        Node node3 = map.createNode(3, new Point(20, 10));
        Node node4 = map.createNode(4, new Point(10, 10));
        Node node5 = map.createNode(5, new Point(10, 20));
        Node node6 = map.createNode(6, new Point(10, 30));
        Node warehouse = map.createNode(7, new Point(10,40));
        
        Arc streetWHto1 = map.createArc("WHto1", 10, 3, 7, 1);
        Arc street1to2 = map.createArc("1to2", 10, 1, 1, 2);
        Arc street2to3 = map.createArc("2to3", 10, 2, 2, 3);
        Arc street2to4 = map.createArc("2to4", 10, 4, 2, 4);
        Arc street2to5 = map.createArc("2to5", 10, 3, 2, 5);
        Arc street3to5 = map.createArc("3to5", 10, 3, 3, 5);
        Arc street4to5 = map.createArc("4to5", 10, 1, 4, 5);
        Arc street4to6 = map.createArc("4to6", 10, 5, 4, 6);
        Arc street5to6 = map.createArc("5to6", 10, 2, 5, 6);
        Arc street6toWH = map.createArc("6toWH", 10, 5, 6, 7);
        
        
        Delivery delivery1 = new Delivery(1, node1);
        Delivery delivery2 = new Delivery(2, node2);
        Delivery delivery3 = new Delivery(3, node4);
        Delivery delivery4 = new Delivery(4, node6);
        
        ArrayList<Delivery> deliveries = new ArrayList<>();
        deliveries.add(delivery1);
        deliveries.add(delivery2);
        deliveries.add(delivery3);
        deliveries.add(delivery4);

        ArrayList<TimeSlot> timeSlotList = new ArrayList<>();
        TimeSlot time = new TimeSlot(8, 11, deliveries);
        timeSlotList.add(time);
        
        Path path1 = map.getFastestPath(warehouse, node1);
        Path path2 = map.getFastestPath(node1, node2);
        Path path3 = map.getFastestPath(node2, node4);
        Path path4 = map.getFastestPath(node4, node6);
        Path path5 = map.getFastestPath(node6, warehouse);
        
        Planning plan = new Planning(map, warehouse, timeSlotList);
        
        try {
            plan.computeRoute();
        } catch (Exception ex) {
            Logger.getLogger(RouteTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Route route = plan.getRoute();
        
        Delivery result = route.getNextDelivery(delivery2);

        assertEquals(delivery3, result);
        
        System.out.println("------------TestGetNextDeliveryOK");
    }
    
    /**
     * Test of addDelivery method, of class Route.
     */
    @Test
    public void testAddDelivery() {
        System.out.println("addDelivery");
        
        Map map = new Map();
        
        Node node1 = map.createNode(1, new Point(20, 30));
        Node node2 = map.createNode(2, new Point(20, 20));
        Node node3 = map.createNode(3, new Point(20, 10));
        Node node4 = map.createNode(4, new Point(10, 10));
        Node node5 = map.createNode(5, new Point(10, 20));
        Node node6 = map.createNode(6, new Point(10, 30));
        Node warehouse = map.createNode(7, new Point(10,40));
        
        Arc streetWHto1 = map.createArc("WHto1", 10, 3, 7, 1);
        Arc street1to2 = map.createArc("1to2", 10, 1, 1, 2);
        Arc street2to3 = map.createArc("2to3", 10, 2, 2, 3);
        Arc street2to4 = map.createArc("2to4", 10, 4, 2, 4);
        Arc street2to5 = map.createArc("2to5", 10, 3, 2, 5);
        Arc street3to5 = map.createArc("3to5", 10, 3, 3, 5);
        Arc street4to5 = map.createArc("4to5", 10, 1, 4, 5);
        Arc street4to6 = map.createArc("4to6", 10, 5, 4, 6);
        Arc street5to6 = map.createArc("5to6", 10, 2, 5, 6);
        Arc street6toWH = map.createArc("6toWH", 10, 5, 6, 7);        
        
        Delivery delivery1 = new Delivery(1, node1);
        Delivery delivery2 = new Delivery(2, node2);
        Delivery delivery6 = new Delivery(3, node6);
        
        ArrayList<Delivery> deliveries = new ArrayList<>();
        deliveries.add(delivery1);
        deliveries.add(delivery2);
        deliveries.add(delivery6);
        
        ArrayList<TimeSlot> timeSlotList = new ArrayList<>();
        TimeSlot time = new TimeSlot(8, 11, deliveries);
        timeSlotList.add(time);
        
        Path path1 = map.getFastestPath(warehouse, node1);
        Path path2 = map.getFastestPath(node1, node2);
        Path path3 = map.getFastestPath(node2, node5);
        Path path4 = map.getFastestPath(node5, node6);
        Path path5 = map.getFastestPath(node6, warehouse);
        
        Planning plan = new Planning(map, warehouse, timeSlotList);
        
        try {
            plan.computeRoute();
        } catch (Exception ex) {
            Logger.getLogger(Route.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        Route route = plan.getRoute();
                
        Delivery deliveryToAdd = new Delivery(5, node5);
        // deliveryToAdd should be added between node2 and node6
        plan.addDelivery(deliveryToAdd.getNode(), node2, time); 
        
        List<Path> result = route.getPaths();
        List<Path> expResult = new ArrayList<>();
        expResult.add(path1);
        expResult.add(path2);
        expResult.add(path3);
        expResult.add(path4);
        expResult.add(path5);
        
        assertEquals(expResult.size(), result.size());
        for(int i = 0; i < expResult.size(); i++)
        {
            assertEquals(expResult.get(i), result.get(i));
        }
        
        System.out.println("------------TestAddDeliveryOK");
    }

    /**
     * Test of removeDelivery method, of class Route.
     */
    @Test
    public void testRemoveDelivery() {
        System.out.println("removeDelivery");
        
        Map map = new Map();
        
        Node node1 = map.createNode(1, new Point(20, 30));
        Node node2 = map.createNode(2, new Point(20, 20));
        Node node3 = map.createNode(3, new Point(20, 10));
        Node node4 = map.createNode(4, new Point(10, 10));
        Node node5 = map.createNode(5, new Point(10, 20));
        Node node6 = map.createNode(6, new Point(10, 30));
        Node warehouse = map.createNode(7, new Point(10,40));
        
        Arc streetWHto1 = map.createArc("WHto1", 10, 3, 7, 1);
        Arc street1to2 = map.createArc("1to2", 10, 1, 1, 2);
        Arc street2to3 = map.createArc("2to3", 10, 2, 2, 3);
        Arc street2to4 = map.createArc("2to4", 10, 4, 2, 4);
        Arc street2to5 = map.createArc("2to5", 10, 3, 2, 5);
        Arc street3to5 = map.createArc("3to5", 10, 3, 3, 5);
        Arc street4to5 = map.createArc("4to5", 10, 1, 4, 5);
        Arc street4to6 = map.createArc("4to6", 10, 5, 4, 6);
        Arc street5to6 = map.createArc("5to6", 10, 2, 5, 6);
        Arc street6toWH = map.createArc("6toWH", 10, 5, 6, 7);
        
        
        Delivery delivery1 = new Delivery(1, node1);
        Delivery delivery2 = new Delivery(2, node2);
        Delivery delivery3 = new Delivery(3, node4);
        Delivery delivery4 = new Delivery(4, node6);
        
        ArrayList<Delivery> deliveries = new ArrayList<>();
        deliveries.add(delivery1);
        deliveries.add(delivery2);
        deliveries.add(delivery3);
        deliveries.add(delivery4);

        ArrayList<TimeSlot> timeSlotList = new ArrayList<>();
        TimeSlot time = new TimeSlot(8, 11, deliveries);
        timeSlotList.add(time);
        
        Path path1 = map.getFastestPath(warehouse, node1);
        Path path2 = map.getFastestPath(node1, node2);
        Path path3 = map.getFastestPath(node2, node4);
        Path path4 = map.getFastestPath(node4, node6);
        Path path5 = map.getFastestPath(node6, warehouse);
        
        Planning plan = new Planning(map, warehouse, timeSlotList);
        
        try {
            plan.computeRoute();
        } catch (Exception ex) {
            Logger.getLogger(RouteTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Route route = plan.getRoute();
        try {
            route.removeDelivery(delivery3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Path> result = route.getPaths();
        List<Path> expResult = new ArrayList<>();
        expResult.add(path1);
        expResult.add(path2);
        expResult.add(map.getFastestPath(node2, node6));
        expResult.add(path5);
        
        assertEquals(expResult.size(), result.size());
        for(int i = 0; i<expResult.size(); i++)
        {
            assertEquals(expResult.get(i), result.get(i));
        }
        
        System.out.println("------------TestRemoveDeliveryOK");
    }

    /**
     * Test of swapDeliveries method, of class Route.
     */
    @Test
    public void testSwapDeliveries() {
        System.out.println("testSwapDeliveries");
        
        Map map = new Map();
        
        Node node1 = map.createNode(1, new Point(20, 30));
        Node node2 = map.createNode(2, new Point(20, 20));
        Node node3 = map.createNode(3, new Point(20, 10));
        Node node4 = map.createNode(4, new Point(10, 10));
        Node node5 = map.createNode(5, new Point(10, 20));
        Node node6 = map.createNode(6, new Point(10, 30));
        Node warehouse = map.createNode(7, new Point(10,40));
        
        Arc streetWHto1 = map.createArc("WHto1", 10, 3, 7, 1);
        Arc street1to2 = map.createArc("1to2", 10, 1, 1, 2);
        Arc street2to3 = map.createArc("2to3", 10, 2, 2, 3);
        Arc street2to4 = map.createArc("2to4", 10, 4, 2, 4);
        Arc street2to5 = map.createArc("2to5", 10, 3, 2, 5);
        Arc street3to5 = map.createArc("3to5", 10, 3, 3, 5);
        Arc street4to5 = map.createArc("4to5", 10, 1, 4, 5);
        Arc street4to6 = map.createArc("4to6", 10, 5, 4, 6);
        Arc street5to6 = map.createArc("5to6", 10, 2, 5, 6);
        Arc street6toWH = map.createArc("6toWH", 10, 5, 6, 7);        
        
        Delivery delivery1 = new Delivery(1, node1);
        Delivery delivery2 = new Delivery(2, node2);
        Delivery delivery4 = new Delivery(3, node4);
        Delivery delivery6 = new Delivery(4, node6);
        
        ArrayList<Delivery> deliveries = new ArrayList<>();
        deliveries.add(delivery1);
        deliveries.add(delivery2);
        deliveries.add(delivery4);
        deliveries.add(delivery6);
        
        ArrayList<TimeSlot> timeSlotList = new ArrayList<>();
        TimeSlot time = new TimeSlot(8, 11, deliveries);
        timeSlotList.add(time);
        
        Planning plan = new Planning(map, warehouse, timeSlotList);
        
        try {
            plan.computeRoute();
        } catch (Exception ex) {
            Logger.getLogger(RouteTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Route route = plan.getRoute();
        
        route.swapDeliveries(delivery2, delivery6); /* swap node2 and node6 */
        
        List<Path> result = route.getPaths();
        List<Path> expResult = new ArrayList<>();
        expResult.add(map.getFastestPath(warehouse, node1));
        expResult.add(map.getFastestPath(node1, node4));
        expResult.add(map.getFastestPath(node4, node6));
        expResult.add(map.getFastestPath(node6, node2));
        expResult.add(map.getFastestPath(node2, warehouse));
        
        assertEquals(expResult.size(), result.size());
        for(int i = 0; i<expResult.size(); i++)
        {
            assertEquals(expResult.get(i), result.get(i));
        }
        
        System.out.println("------------TestSwapDeliveriesOK");
    }

    /**
     * Test of getNodePreviousDelivery method, of class Route.
     */
    @Test
    public void testGetNodePreviousDelivery() {
        System.out.println("getNodePreviousDelivery");
        
        Map map = new Map();
        
        Node node1 = map.createNode(1, new Point(20, 30));
        Node node2 = map.createNode(2, new Point(20, 20));
        Node node3 = map.createNode(3, new Point(20, 10));
        Node node4 = map.createNode(4, new Point(10, 10));
        Node node5 = map.createNode(5, new Point(10, 20));
        Node node6 = map.createNode(6, new Point(10, 30));
        Node warehouse = map.createNode(7, new Point(10,40));
        
        Arc streetWHto1 = map.createArc("WHto1", 10, 3, 7, 1);
        Arc street1to2 = map.createArc("1to2", 10, 1, 1, 2);
        Arc street2to3 = map.createArc("2to3", 10, 2, 2, 3);
        Arc street2to4 = map.createArc("2to4", 10, 4, 2, 4);
        Arc street2to5 = map.createArc("2to5", 10, 3, 2, 5);
        Arc street3to5 = map.createArc("3to5", 10, 3, 3, 5);
        Arc street4to5 = map.createArc("4to5", 10, 1, 4, 5);
        Arc street4to6 = map.createArc("4to6", 10, 5, 4, 6);
        Arc street5to6 = map.createArc("5to6", 10, 2, 5, 6);
        Arc street6toWH = map.createArc("6toWH", 10, 5, 6, 7);
        
        
        Delivery delivery1 = new Delivery(1, node1);
        Delivery delivery2 = new Delivery(2, node2);
        Delivery delivery3 = new Delivery(3, node4);
        Delivery delivery4 = new Delivery(4, node6);
        
        ArrayList<Delivery> deliveries = new ArrayList<>();
        deliveries.add(delivery1);
        deliveries.add(delivery2);
        deliveries.add(delivery3);
        deliveries.add(delivery4);

        ArrayList<TimeSlot> timeSlotList = new ArrayList<>();
        TimeSlot time = new TimeSlot(8, 11, deliveries);
        timeSlotList.add(time);
        
        Path path1 = map.getFastestPath(warehouse, node1);
        Path path2 = map.getFastestPath(node1, node2);
        Path path3 = map.getFastestPath(node2, node4);
        Path path4 = map.getFastestPath(node4, node6);
        Path path5 = map.getFastestPath(node6, warehouse);
        
        Planning plan = new Planning(map, warehouse, timeSlotList);
        
        try {
            plan.computeRoute();
        } catch (Exception ex) {
            Logger.getLogger(RouteTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Route route = plan.getRoute();
        
        Node result = route.getNodePreviousDelivery(delivery2);

        assertEquals(node1, result);
        System.out.println("------------TestGetNodePreviousDeliveryOK");
    }

    /**
     * Test of getPaths method, of class Route.
     */
    @Test
    public void testGetPaths() {
        System.out.println("getPaths");
        
        Map map = new Map();
        
        Node node1 = map.createNode(1, new Point(20, 30));
        Node node2 = map.createNode(2, new Point(20, 20));
        Node node3 = map.createNode(3, new Point(20, 10));
        Node node4 = map.createNode(4, new Point(10, 10));
        Node node5 = map.createNode(5, new Point(10, 20));
        Node node6 = map.createNode(6, new Point(10, 30));
        Node warehouse = map.createNode(7, new Point(10,40));
        
        Arc streetWHto1 = map.createArc("WHto1", 10, 3, 7, 1);
        Arc street1to2 = map.createArc("1to2", 10, 1, 1, 2);
        Arc street2to3 = map.createArc("2to3", 10, 2, 2, 3);
        Arc street2to4 = map.createArc("2to4", 10, 4, 2, 4);
        Arc street2to5 = map.createArc("2to5", 10, 3, 2, 5);
        Arc street3to5 = map.createArc("3to5", 10, 3, 3, 5);
        Arc street4to5 = map.createArc("4to5", 10, 1, 4, 5);
        Arc street4to6 = map.createArc("4to6", 10, 5, 4, 6);
        Arc street5to6 = map.createArc("5to6", 10, 2, 5, 6);
        Arc street6toWH = map.createArc("6toWH", 10, 5, 6, 7);
        
        
        Delivery delivery1 = new Delivery(1, node1);
        Delivery delivery2 = new Delivery(2, node2);
        Delivery delivery3 = new Delivery(3, node4);
        Delivery delivery4 = new Delivery(4, node6);
        
        ArrayList<Delivery> deliveries = new ArrayList<>();
        deliveries.add(delivery1);
        deliveries.add(delivery2);
        deliveries.add(delivery3);
        deliveries.add(delivery4);

        ArrayList<TimeSlot> timeSlotList = new ArrayList<>();
        TimeSlot time = new TimeSlot(8, 11, deliveries);
        timeSlotList.add(time);
        
        Path path1 = map.getFastestPath(warehouse, node1);
        Path path2 = map.getFastestPath(node1, node2);
        Path path3 = map.getFastestPath(node2, node4);
        Path path4 = map.getFastestPath(node4, node6);
        Path path5 = map.getFastestPath(node6, warehouse);
        
        Planning plan = new Planning(map, warehouse, timeSlotList);

        List<Path> expResult = new ArrayList<>();
        expResult.add(path1);
        expResult.add(path2);
        expResult.add(path3);
        expResult.add(path4);
        expResult.add(path5);
        
        try {
            plan.computeRoute();
        } catch (Exception ex) {
            Logger.getLogger(RouteTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Route route = plan.getRoute();
        List<Path> result = route.getPaths();
        
        assertEquals(expResult.size(), result.size());
        for(int i = 0; i<expResult.size(); i++)
        {
            assertEquals(expResult.get(i), result.get(i));
        }
        
        System.out.println("------------TestGetPathsOK");
    }
}
