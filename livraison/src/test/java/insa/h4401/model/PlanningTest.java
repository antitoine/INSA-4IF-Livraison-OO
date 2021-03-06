/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insa.h4401.model;

import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/**
 * @author guillaume
 */
public class PlanningTest {

    /**
     * Test of addDelivery method, of class Planning.
     */
    @Test
    public void testAddDelivery() {
        System.out.println("addDelivery");

        Map map = new Map();

        Node node1 = map.createNode(1, new Point(20, 30));
        Node node2 = map.createNode(2, new Point(20, 20));
        map.createNode(3, new Point(20, 10));
        map.createNode(4, new Point(10, 10));
        Node node5 = map.createNode(5, new Point(10, 20));
        Node node6 = map.createNode(6, new Point(10, 30));
        Node warehouse = map.createNode(7, new Point(10, 40));

        map.createArc("WHto1", 10, 3, 7, 1);
        map.createArc("1to2", 10, 1, 1, 2);
        map.createArc("2to3", 10, 2, 2, 3);
        map.createArc("2to4", 10, 4, 2, 4);
        map.createArc("2to5", 10, 3, 2, 5);
        map.createArc("3to5", 10, 3, 3, 5);
        map.createArc("4to5", 10, 1, 4, 5);
        map.createArc("4to6", 10, 5, 4, 6);
        map.createArc("5to6", 10, 2, 5, 6);
        map.createArc("6toWH", 10, 5, 6, 7);

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
        for (int i = 0; i < expResult.size(); i++) {
            assertEquals(expResult.get(i), result.get(i));
        }
    }

    /**
     * Test of removeDelivery method, of class Planning.
     */
    @Test
    public void testRemoveDelivery() {
        System.out.println("removeDelivery");

        Map map = new Map();
        Node warehouse = map.createNode(1, new Point(20, 10));
        Node node1 = map.createNode(2, new Point(20, 20));
        Node node2 = map.createNode(3, new Point(20, 30));
        map.createNode(4, new Point(20, 40));
        map.createArc("route1", 12, 31, 1, 2);
        map.createArc("route2", 12, 31, 2, 3);
        map.createArc("route3", 12, 31, 3, 4);
        Delivery delivery1 = new Delivery(1, node1);
        Delivery delivery2 = new Delivery(2, node2);
        ArrayList<Delivery> arrayList1 = new ArrayList<>();
        ArrayList<Delivery> arrayList2 = new ArrayList<>();
        arrayList1.add(delivery1);
        arrayList2.add(delivery2);
        TimeSlot timeSlot1 = new TimeSlot(8, 9, arrayList1);
        TimeSlot timeSlot2 = new TimeSlot(9, 10, arrayList2);
        ArrayList<TimeSlot> arrayTimeSlot = new ArrayList<>();
        arrayTimeSlot.add(timeSlot1);
        arrayTimeSlot.add(timeSlot2);

        Planning planning = new Planning(map, warehouse, arrayTimeSlot);
        try {
            planning.removeDelivery(delivery2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Delivery> expResult = new ArrayList<>();
        expResult.add(delivery1);

        List<Delivery> result = planning.getDeliveries();

        for (int i = 0; i < expResult.size(); i++) {
            assertEquals(expResult.get(i), result.get(i));
        }
    }

    /**
     * Test of getNodePreviousDelivery method, of class Planning.
     */
    @Test
    public void testGetNodePreviousDelivery() {
        System.out.println("getNodePreviousDelivery");
        Map map = new Map();

        Node node1 = map.createNode(1, new Point(20, 30));
        Node node2 = map.createNode(2, new Point(20, 20));
        map.createNode(3, new Point(20, 10));
        Node node4 = map.createNode(4, new Point(10, 10));
        map.createNode(5, new Point(10, 20));
        Node node6 = map.createNode(6, new Point(10, 30));
        Node warehouse = map.createNode(7, new Point(10, 40));

        map.createArc("WHto1", 10, 3, 7, 1);
        map.createArc("1to2", 10, 1, 1, 2);
        map.createArc("2to3", 10, 2, 2, 3);
        map.createArc("2to4", 10, 4, 2, 4);
        map.createArc("2to5", 10, 3, 2, 5);
        map.createArc("3to5", 10, 3, 3, 5);
        map.createArc("4to5", 10, 1, 4, 5);
        map.createArc("4to6", 10, 5, 4, 6);
        map.createArc("5to6", 10, 2, 5, 6);
        map.createArc("6toWH", 10, 5, 6, 7);


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

        Planning plan = new Planning(map, warehouse, timeSlotList);

        try {
            plan.computeRoute();
        } catch (Exception ex) {
            Logger.getLogger(RouteTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Node result = plan.getNodePreviousDelivery(delivery2);

        assertEquals(node1, result);
    }

    /**
     * Test of swapDeliveries method, of class Planning.
     */
    @Test
    public void testSwapDeliveries() {
        System.out.println("swapDeliveries");

        Map map = new Map();

        Node node1 = map.createNode(1, new Point(20, 30));
        Node node2 = map.createNode(2, new Point(20, 20));
        map.createNode(3, new Point(20, 10));
        Node node4 = map.createNode(4, new Point(10, 10));
        map.createNode(5, new Point(10, 20));
        Node node6 = map.createNode(6, new Point(10, 30));
        Node warehouse = map.createNode(7, new Point(10, 40));

        map.createArc("WHto1", 10, 3, 7, 1);
        map.createArc("1to2", 10, 1, 1, 2);
        map.createArc("2to3", 10, 2, 2, 3);
        map.createArc("2to4", 10, 4, 2, 4);
        map.createArc("2to5", 10, 3, 2, 5);
        map.createArc("3to5", 10, 3, 3, 5);
        map.createArc("4to5", 10, 1, 4, 5);
        map.createArc("4to6", 10, 5, 4, 6);
        map.createArc("5to6", 10, 2, 5, 6);
        map.createArc("6toWH", 10, 5, 6, 7);

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

        plan.swapDeliveries(delivery2, delivery6); /* swap node2 and node6 */

        List<Path> result = route.getPaths();

        List<Path> expResult = new ArrayList<>();
        expResult.add(map.getFastestPath(warehouse, node1));
        expResult.add(map.getFastestPath(node1, node4));
        expResult.add(map.getFastestPath(node4, node6));
        expResult.add(map.getFastestPath(node6, node2));
        expResult.add(map.getFastestPath(node2, warehouse));

        assertEquals(expResult.size(), result.size());
        for (int i = 0; i < expResult.size(); i++) {
            assertEquals(expResult.get(i), result.get(i));
        }
    }

    /**
     * Test of getTimeSlots method, of class Planning.
     */
    @Test
    public void testGetTimeSlots() {
        System.out.println("getTimeSlots");

        Map map = new Map();

        Node node1 = map.createNode(1, new Point(20, 30));
        Node node2 = map.createNode(2, new Point(20, 20));
        map.createNode(3, new Point(20, 10));
        Node node4 = map.createNode(4, new Point(10, 10));
        map.createNode(5, new Point(10, 20));
        Node node6 = map.createNode(6, new Point(10, 30));
        Node warehouse = map.createNode(7, new Point(10, 40));

        map.createArc("WHto1", 10, 3, 7, 1);
        map.createArc("1to2", 10, 1, 1, 2);
        map.createArc("2to3", 10, 2, 2, 3);
        map.createArc("2to4", 10, 4, 2, 4);
        map.createArc("2to5", 10, 3, 2, 5);
        map.createArc("3to5", 10, 3, 3, 5);
        map.createArc("4to5", 10, 1, 4, 5);
        map.createArc("4to6", 10, 5, 4, 6);
        map.createArc("5to6", 10, 2, 5, 6);
        map.createArc("6toWH", 10, 5, 6, 7);

        Delivery delivery1 = new Delivery(1, node1);
        Delivery delivery2 = new Delivery(2, node2);
        Delivery delivery4 = new Delivery(3, node4);
        Delivery delivery6 = new Delivery(4, node6);

        ArrayList<Delivery> deliveries = new ArrayList<>();
        deliveries.add(delivery1);
        deliveries.add(delivery2);
        ArrayList<Delivery> deliveries2 = new ArrayList<>();
        deliveries2.add(delivery4);
        deliveries2.add(delivery6);

        ArrayList<TimeSlot> timeSlotList = new ArrayList<>();
        TimeSlot time = new TimeSlot(8, 11, deliveries);
        TimeSlot time2 = new TimeSlot(11, 13, deliveries2);
        timeSlotList.add(time);
        timeSlotList.add(time2);

        Planning plan = new Planning(map, warehouse, timeSlotList);

        List<TimeSlot> result = plan.getTimeSlots();

        assertEquals(timeSlotList, result);
    }

    /**
     * Test of getFirstTimeSlot method, of class Planning.
     */
    @Test
    public void testGetFirstTimeSlot() {
        System.out.println("getFirstTimeSlot");

        Map map = new Map();

        Node node1 = map.createNode(1, new Point(20, 30));
        Node node2 = map.createNode(2, new Point(20, 20));
        map.createNode(3, new Point(20, 10));
        Node node4 = map.createNode(4, new Point(10, 10));
        map.createNode(5, new Point(10, 20));
        Node node6 = map.createNode(6, new Point(10, 30));
        Node warehouse = map.createNode(7, new Point(10, 40));

        map.createArc("WHto1", 10, 3, 7, 1);
        map.createArc("1to2", 10, 1, 1, 2);
        map.createArc("2to3", 10, 2, 2, 3);
        map.createArc("2to4", 10, 4, 2, 4);
        map.createArc("2to5", 10, 3, 2, 5);
        map.createArc("3to5", 10, 3, 3, 5);
        map.createArc("4to5", 10, 1, 4, 5);
        map.createArc("4to6", 10, 5, 4, 6);
        map.createArc("5to6", 10, 2, 5, 6);
        map.createArc("6toWH", 10, 5, 6, 7);

        Delivery delivery1 = new Delivery(1, node1);
        Delivery delivery2 = new Delivery(2, node2);
        Delivery delivery4 = new Delivery(3, node4);
        Delivery delivery6 = new Delivery(4, node6);

        ArrayList<Delivery> deliveries = new ArrayList<>();
        deliveries.add(delivery1);
        deliveries.add(delivery2);
        ArrayList<Delivery> deliveries2 = new ArrayList<>();
        deliveries2.add(delivery4);
        deliveries2.add(delivery6);

        ArrayList<TimeSlot> timeSlotList = new ArrayList<>();
        TimeSlot time = new TimeSlot(8, 11, deliveries);
        TimeSlot time2 = new TimeSlot(11, 13, deliveries2);
        timeSlotList.add(time);
        timeSlotList.add(time2);

        Planning plan = new Planning(map, warehouse, timeSlotList);

        TimeSlot result = plan.getFirstTimeSlot();

        assertEquals(time, result);
    }

    /**
     * Test of getMap method, of class Planning.
     */
    @Test
    public void testGetMap() {
        System.out.println("getMap");

        Map map = new Map();

        Node node1 = map.createNode(1, new Point(20, 30));
        Node node2 = map.createNode(2, new Point(20, 20));
        map.createNode(3, new Point(20, 10));
        Node node4 = map.createNode(4, new Point(10, 10));
        map.createNode(5, new Point(10, 20));
        Node node6 = map.createNode(6, new Point(10, 30));
        Node warehouse = map.createNode(7, new Point(10, 40));

        map.createArc("WHto1", 10, 3, 7, 1);
        map.createArc("1to2", 10, 1, 1, 2);
        map.createArc("2to3", 10, 2, 2, 3);
        map.createArc("2to4", 10, 4, 2, 4);
        map.createArc("2to5", 10, 3, 2, 5);
        map.createArc("3to5", 10, 3, 3, 5);
        map.createArc("4to5", 10, 1, 4, 5);
        map.createArc("4to6", 10, 5, 4, 6);
        map.createArc("5to6", 10, 2, 5, 6);
        map.createArc("6toWH", 10, 5, 6, 7);

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

        Map result = plan.getMap();

        assertEquals(map, result);
    }

    /**
     * Test of getWarehouse method, of class Planning.
     */
    @Test
    public void testGetWarehouse() {
        System.out.println("getWarehouse");

        Map map = new Map();

        Node node1 = map.createNode(1, new Point(20, 30));
        Node node2 = map.createNode(2, new Point(20, 20));
        Node node3 = map.createNode(3, new Point(20, 10));
        Node node4 = map.createNode(4, new Point(10, 10));
        Node node5 = map.createNode(5, new Point(10, 20));
        Node node6 = map.createNode(6, new Point(10, 30));
        Node warehouse = map.createNode(7, new Point(10, 40));

        map.createArc("WHto1", 10, 3, 7, 1);
        map.createArc("1to2", 10, 1, 1, 2);
        map.createArc("2to3", 10, 2, 2, 3);
        map.createArc("2to4", 10, 4, 2, 4);
        map.createArc("2to5", 10, 3, 2, 5);
        map.createArc("3to5", 10, 3, 3, 5);
        map.createArc("4to5", 10, 1, 4, 5);
        map.createArc("4to6", 10, 5, 4, 6);
        map.createArc("5to6", 10, 2, 5, 6);
        map.createArc("6toWH", 10, 5, 6, 7);

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

        Node result = plan.getWarehouse();

        assertEquals(warehouse, result);
    }

    /**
     * Test of getRoute method, of class Planning.
     */
    @Test
    public void testGetRoute() {
        System.out.println("getFastestRoute");

        Map map = new Map();

        Node node1 = map.createNode(1, new Point(20, 30));
        Node node2 = map.createNode(2, new Point(20, 20));
        map.createNode(3, new Point(20, 10));
        Node node4 = map.createNode(4, new Point(10, 10));
        map.createNode(5, new Point(10, 20));
        Node node6 = map.createNode(6, new Point(10, 30));
        Node warehouse = map.createNode(7, new Point(10, 40));

        map.createArc("WHto1", 10, 3, 7, 1);
        map.createArc("1to2", 10, 1, 1, 2);
        map.createArc("2to3", 10, 2, 2, 3);
        map.createArc("2to4", 10, 4, 2, 4);
        map.createArc("2to5", 10, 3, 2, 5);
        map.createArc("3to5", 10, 3, 3, 5);
        map.createArc("4to5", 10, 1, 4, 5);
        map.createArc("4to6", 10, 5, 4, 6);
        map.createArc("5to6", 10, 2, 5, 6);
        map.createArc("6toWH", 10, 5, 6, 7);


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

        Planning planning = new Planning(map, warehouse, timeSlotList);

        try {
            planning.computeRoute();
        } catch (Exception ex) {
            Logger.getLogger(RouteTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Route result = planning.getRoute();

        LinkedList<Path> paths = new LinkedList<>();
        paths.add(path1);
        paths.add(path2);
        paths.add(path3);
        paths.add(path4);
        paths.add(path5);

        Route expResult = new Route(planning, paths);

        assertEquals(expResult, result);
    }

    /**
     * Test of setRoute method, of class Planning.
     */
    @Test
    public void testSetRoute() {
        System.out.println("setRoute");

        Map map = new Map();

        Node node1 = map.createNode(1, new Point(20, 30));
        Node node2 = map.createNode(2, new Point(20, 20));
        map.createNode(3, new Point(20, 10));
        Node node4 = map.createNode(4, new Point(10, 10));
        map.createNode(5, new Point(10, 20));
        Node node6 = map.createNode(6, new Point(10, 30));
        Node warehouse = map.createNode(7, new Point(10, 40));

        map.createArc("WHto1", 10, 3, 7, 1);
        map.createArc("1to2", 10, 1, 1, 2);
        map.createArc("2to3", 10, 2, 2, 3);
        map.createArc("2to4", 10, 4, 2, 4);
        map.createArc("2to5", 10, 3, 2, 5);
        map.createArc("3to5", 10, 3, 3, 5);
        map.createArc("4to5", 10, 1, 4, 5);
        map.createArc("4to6", 10, 5, 4, 6);
        map.createArc("5to6", 10, 2, 5, 6);
        map.createArc("6toWH", 10, 5, 6, 7);


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

        Planning planning = new Planning(map, warehouse, timeSlotList);

        try {
            planning.computeRoute();
        } catch (Exception ex) {
            Logger.getLogger(RouteTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        LinkedList<Path> paths = new LinkedList<>();
        paths.add(path1);
        paths.add(path2);
        paths.add(path3);
        paths.add(path4);
        paths.add(path5);

        Route route = new Route(planning, paths);
        planning.setRoute(route);

        Route result = planning.getRoute();


        assertEquals(route, result);
    }

    /**
     * Test of getDeliveries method, of class Planning.
     */
    @Test
    public void testGetDeliveries() {
        System.out.println("getDeliveries");

        Map map = new Map();

        Node node1 = map.createNode(1, new Point(20, 30));
        Node node2 = map.createNode(2, new Point(20, 20));
        map.createNode(3, new Point(20, 10));
        map.createNode(4, new Point(10, 10));
        Node node5 = map.createNode(5, new Point(10, 20));
        Node node6 = map.createNode(6, new Point(10, 30));
        Node warehouse = map.createNode(7, new Point(10, 40));

        map.createArc("WHto1", 10, 3, 7, 1);
        map.createArc("1to2", 10, 1, 1, 2);
        map.createArc("2to3", 10, 2, 2, 3);
        map.createArc("2to4", 10, 4, 2, 4);
        map.createArc("2to5", 10, 3, 2, 5);
        map.createArc("3to5", 10, 3, 3, 5);
        map.createArc("4to5", 10, 1, 4, 5);
        map.createArc("4to6", 10, 5, 4, 6);
        map.createArc("5to6", 10, 2, 5, 6);
        map.createArc("6toWH", 10, 5, 6, 7);

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

        map.getFastestPath(warehouse, node1);
        map.getFastestPath(node1, node2);
        map.getFastestPath(node2, node5);
        map.getFastestPath(node5, node6);
        map.getFastestPath(node6, warehouse);

        Planning plan = new Planning(map, warehouse, timeSlotList);

        List<Delivery> result = plan.getDeliveries();
        List<Delivery> expResult = new ArrayList<>();
        expResult.add(delivery1);
        expResult.add(delivery2);
        expResult.add(delivery6);

        assertEquals(expResult.size(), result.size());
        for (int i = 0; i < expResult.size(); i++) {
            assertEquals(expResult.get(i), result.get(i));
        }
    }
}
