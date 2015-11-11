/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insa.h4401.model;

import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author guillaume
 */
public class TimeSlotTest {

    public TimeSlotTest() {
    }

    /**
     * Test of getDeliveries method, of class TimeSlot.
     */
    @Test
    public void testGetDeliveries() {
        System.out.println("getDeliveries");

        Map map = new Map();
        map.createNode(1, new Point(20, 10));
        Node node1 = map.createNode(2, new Point(20, 20));
        map.createNode(3, new Point(20, 30));
        map.createArc("route1", 12, 31, 1, 2);
        map.createArc("route2", 12, 31, 2, 3);
        Delivery delivery1 = new Delivery(1, node1);
        ArrayList<Delivery> expResult1 = new ArrayList<>(); //for timeslot attached
        ArrayList<Delivery> expResult2 = new ArrayList<>(); //for timeslot not attached
        expResult1.add(delivery1);
        TimeSlot timeSlot = new TimeSlot(8, 9, expResult1);
        //without timeslot attached
        List<Delivery> result = timeSlot.getDeliveries();
        // array empty
        for (int i = 0; i < expResult2.size(); i++) {
            assertEquals(expResult2.get(i), result.get(i));
        }
        //With timeslot attached
        delivery1.attachTimeSlot(timeSlot);
        result = timeSlot.getDeliveries();
        // array with timeSlot
        for (int i = 0; i < expResult1.size(); i++) {
            assertEquals(expResult1.get(i), result.get(i));
        }
    }

    /**
     * Test of addDelivery method, of class TimeSlot.
     */
    @Test
    public void testAddDelivery() {
        System.out.println("addDelivery");

        Map map = new Map();
        map.createNode(1, new Point(20, 10));
        Node node1 = map.createNode(2, new Point(20, 20));
        Node node2 = map.createNode(3, new Point(20, 30));
        map.createArc("route1", 12, 31, 1, 2);
        map.createArc("route2", 12, 31, 2, 3);
        Delivery delivery1 = new Delivery(1, node1);
        Delivery delivery2 = new Delivery(2, node2);
        ArrayList<Delivery> expResult = new ArrayList<>();
        expResult.add(delivery1);
        TimeSlot timeSlot = new TimeSlot(8, 9, expResult);

        expResult.add(delivery2);
        timeSlot.addDelivery(delivery2);        //Function tested
        List<Delivery> result = timeSlot.getDeliveries();
        for (int i = 0; i < expResult.size(); i++) {
            assertEquals(expResult.get(i), result.get(i));
        }
    }

    /**
     * Test of getStartTime method, of class TimeSlot.
     */
    @Test
    public void testGetStartTime() {
        System.out.println("getStartTime");

        int start = 8;
        int end = 9;
        Map map = new Map();
        map.createNode(1, new Point(20, 10));
        Node node1 = map.createNode(2, new Point(20, 20));
        map.createNode(3, new Point(20, 30));
        map.createArc("route1", 12, 31, 1, 2);
        map.createArc("route2", 12, 31, 2, 3);
        Delivery delivery1 = new Delivery(1, node1);
        ArrayList<Delivery> expResult = new ArrayList<>();
        expResult.add(delivery1);
        TimeSlot timeSlot = new TimeSlot(start, end, expResult);

        int result = timeSlot.getStartTime();
        assertEquals(start, result);
    }

    /**
     * Test of getEndTime method, of class TimeSlot.
     */
    @Test
    public void testGetEndTime() {
        System.out.println("getEndTime");
        int start = 8;
        int end = 9;

        Map map = new Map();
        map.createNode(1, new Point(20, 10));
        Node node1 = map.createNode(2, new Point(20, 20));
        map.createNode(3, new Point(20, 30));
        map.createArc("route1", 12, 31, 1, 2);
        map.createArc("route2", 12, 31, 2, 3);
        Delivery delivery1 = new Delivery(1, node1);
        ArrayList<Delivery> expResult = new ArrayList<>();
        expResult.add(delivery1);
        TimeSlot timeSlot = new TimeSlot(start, end, expResult);

        int result = timeSlot.getEndTime();
        assertEquals(end, result);
    }

    /**
     * Test of containsTime method, of class TimeSlot.
     */
    @Test
    public void testContainsTime() {
        System.out.println("containsTime");
        float timeok = 8.5F;
        float timenotok = 10.0F;
        int start = 8;
        int end = 9;

        Map map = new Map();
        map.createNode(1, new Point(20, 10));
        Node node1 = map.createNode(2, new Point(20, 20));
        map.createNode(3, new Point(20, 30));
        map.createArc("route1", 12, 31, 1, 2);
        map.createArc("route2", 12, 31, 2, 3);
        Delivery delivery1 = new Delivery(1, node1);
        ArrayList<Delivery> arrayList = new ArrayList<>();
        arrayList.add(delivery1);
        TimeSlot timeSlot = new TimeSlot(start, end, arrayList);

        //time is contained in the current time slot
        boolean result = timeSlot.containsTime(timeok);
        assertEquals(true, result);

        //time isn't contained in the current time slot
        result = timeSlot.containsTime(timenotok);
        assertEquals(false, result);

    }

    /**
     * Test of equals method, of class TimeSlot.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");

        Map map = new Map();
        map.createNode(1, new Point(20, 10));
        Node node1 = map.createNode(2, new Point(20, 20));
        Node node2 = map.createNode(3, new Point(20, 30));
        map.createArc("route1", 12, 31, 1, 2);
        map.createArc("route2", 12, 31, 2, 3);
        Delivery delivery1 = new Delivery(1, node1);
        Delivery delivery2 = new Delivery(2, node2);
        ArrayList<Delivery> arrayList1 = new ArrayList<>();
        ArrayList<Delivery> arrayList2 = new ArrayList<>();
        arrayList1.add(delivery1);
        arrayList2.add(delivery2);
        TimeSlot timeSlot1 = new TimeSlot(8, 9, arrayList1);
        TimeSlot timeSlot2 = new TimeSlot(8, 9, arrayList1);//same that timeSlot1
        TimeSlot timeSlot3 = new TimeSlot(9, 9, arrayList1);//timeStart different
        TimeSlot timeSlot4 = new TimeSlot(8, 10, arrayList1); //timeEnd different
        TimeSlot timeSlot5 = new TimeSlot(8, 9, arrayList2); //arrayList different

        //timeSlot1 equals timeSlot2
        boolean result = timeSlot1.equals(timeSlot2);
        assertEquals(true, result);

        //timeStart different
        result = timeSlot1.equals(timeSlot3);
        assertEquals(false, result);

        //timeEnd different
        result = timeSlot1.equals(timeSlot4);
        assertEquals(false, result);

        //arrayList different
        result = timeSlot1.equals(timeSlot5);
        assertEquals(false, result);
    }

}
