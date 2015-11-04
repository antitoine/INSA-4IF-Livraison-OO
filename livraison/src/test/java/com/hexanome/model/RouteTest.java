/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.model;

import com.hexanome.utils.MapDocument;
import com.hexanome.utils.Subscriber;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
        
        ArrayList<TimeSlot> timeSlotList = new ArrayList<TimeSlot>();
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
        Route route = plan.getFastestRoute();
        
        Delivery result = route.getNextDelivery(delivery2);
        Delivery expResult = delivery3;
        
        assertEquals(expResult, result);
        System.out.println("------------TestGetNextDeliveryOK");
    }

    /**
     * Test of addDelivery method, of class Route.
     */
    @Test
    public void testAddDelivery() {
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
        
        ArrayList<TimeSlot> timeSlotList = new ArrayList<TimeSlot>();
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
        Route route = plan.getFastestRoute();
        
        Delivery deliveryToAdd = new Delivery(5, node5);
        route.addDelivery(deliveryToAdd, node4, time); /* deliveryToAdd should be added between node4 and node6 */
        
        List<Path> result = route.getPaths();
        List<Path> expResult = new ArrayList<Path>();
        expResult.add(path1);
        expResult.add(path2);
        expResult.add(path3);
        //expResult.add(le path entre node4 et deliveryToAdd)
        expResult.add(path4);
        expResult.add(path5);
        
        assertEquals(expResult.size(), result.size());
        for(int i = 0; i<expResult.size(); i++)
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
        
        ArrayList<TimeSlot> timeSlotList = new ArrayList<TimeSlot>();
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
        Route route = plan.getFastestRoute();
        route.removeDelivery(delivery3);
        
        List<Path> result = route.getPaths();
        List<Path> expResult = new ArrayList<Path>();
        // pouet pouet pouet je sais pas comment faire !
        // il faudrait créer des nouveaux paths et redéfinir le equals de Path
        // pour faire la comparaison..
        
    }

    /**
     * Test of swapDeliveries method, of class Route.
     */
    @Test
    public void testSwapDeliveries() {
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
        
        ArrayList<TimeSlot> timeSlotList = new ArrayList<TimeSlot>();
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
        Route route = plan.getFastestRoute();
        
        route.swapDeliveries(delivery2, delivery4);
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
        
        ArrayList<TimeSlot> timeSlotList = new ArrayList<TimeSlot>();
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
        Route route = plan.getFastestRoute();
        
        Node result = route.getNodePreviousDelivery(delivery2);
        Node expResult = node1;
        
        assertEquals(expResult, result);
        System.out.println("TestOK");
    }

    /**
     * Test of addSubscriber method, of class Route.
     */
    @Test
    public void testAddSubscriber() {
        System.out.println("addSubscriber");
        Subscriber s = null;
        Route instance = null;
        instance.addSubscriber(s);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeSubscriber method, of class Route.
     */
    @Test
    public void testRemoveSubscriber() {
        System.out.println("removeSubscriber");
        Subscriber s = null;
        Route instance = null;
        instance.removeSubscriber(s);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of notifySubscribers method, of class Route.
     */
    @Test
    public void testNotifySubscribers() {
        System.out.println("notifySubscribers");
        Route instance = null;
        instance.notifySubscribers();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clearSubscribers method, of class Route.
     */
    @Test
    public void testClearSubscribers() {
        System.out.println("clearSubscribers");
        Route instance = null;
        instance.clearSubscribers();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        
        ArrayList<TimeSlot> timeSlotList = new ArrayList<TimeSlot>();
        TimeSlot time = new TimeSlot(8, 11, deliveries);
        timeSlotList.add(time);
        
        Path path1 = map.getFastestPath(warehouse, node1);
        Path path2 = map.getFastestPath(node1, node2);
        Path path3 = map.getFastestPath(node2, node4);
        Path path4 = map.getFastestPath(node4, node6);
        Path path5 = map.getFastestPath(node6, warehouse);
        
        Planning plan = new Planning(map, warehouse, timeSlotList);
        
        List<Path> expResult = new ArrayList<Path>();
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
        Route route = plan.getFastestRoute();
        List<Path> result = route.getPaths();
        
        assertEquals(expResult.size(), result.size());
        for(int i = 0; i<expResult.size(); i++)
        {
            assertEquals(expResult.get(i), result.get(i));
        }
        System.out.println("TestGetPathsOK");
    }
}
