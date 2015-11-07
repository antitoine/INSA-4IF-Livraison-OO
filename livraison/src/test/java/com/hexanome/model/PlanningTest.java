/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.model;

import com.hexanome.utils.Subscriber;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author guillaume
 */
public class PlanningTest {
    
    public PlanningTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of abortComputeRoute method, of class Planning.
     */
    @Test
    public void testAbortComputeRoute() {
        System.out.println("abortComputeRoute");
        Planning instance = null;
        instance.abortComputeRoute();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeRoute method, of class Planning.
     */
    @Test
    public void testComputeRoute_ChangeListener() {
        System.out.println("computeRoute");
        ChangeListener<Worker.State> listenerComputeRoute = null;
        Planning instance = null;
        instance.computeRoute(listenerComputeRoute);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeRoute method, of class Planning.
     */
    @Test
    public void testComputeRoute_0args() throws Exception {
        System.out.println("computeRoute");
        Planning instance = null;
        instance.computeRoute();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFastestRoute method, of class Planning.
     */
    @Test
    public void testGetFastestRoute() {
        System.out.println("getFastestRoute");
        Planning instance = null;
        Route expResult = null;
        Route result = instance.getFastestRoute();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addDelivery method, of class Planning.
     */
    @Test
    public void testAddDelivery() {
        System.out.println("addDelivery");
        
        Map map = new Map();   
        Node warehouse = map.createNode(1, new Point(20,10));
        Node node1 = map.createNode(2, new Point(20,20));
        Node node2 = map.createNode(3, new Point(20,30));
        Node node3 = map.createNode(4, new Point(20,40));
        Arc arc2 = map.createArc("route1",12,31,1,2);
        Arc arc3 = map.createArc("route2",12,31,2,3);
        Arc arc4 = map.createArc("route3",12,31,3,4);
        Delivery delivery1 = new Delivery(1,node1);
        Delivery delivery2 = new Delivery(2,node2);
        ArrayList<Delivery> arrayList1 = new ArrayList<>();
        ArrayList<Delivery> arrayList2 = new ArrayList<>();
        arrayList1.add(delivery1);
        arrayList2.add(delivery2);
        TimeSlot timeSlot1 = new TimeSlot(8,9,arrayList1);
        TimeSlot timeSlot2 = new TimeSlot(9,10,arrayList2);
        ArrayList<TimeSlot> arrayTimeSlot = new ArrayList<>();
        arrayTimeSlot.add(timeSlot1);
        
        
        Planning planning = new Planning(map,warehouse,arrayTimeSlot);
        planning.addDelivery(node2, node1, timeSlot2);
        
        ArrayList<Delivery> expResult = new ArrayList<>();
        expResult.add(delivery1);
        expResult.add(delivery2);
       
        List<Delivery> result = new ArrayList<>();
        result = planning.getDeliveries();
        
         for(int i=0;i<expResult.size();i++)
        {
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
        Node warehouse = map.createNode(1, new Point(20,10));
        Node node1 = map.createNode(2, new Point(20,20));
        Node node2 = map.createNode(3, new Point(20,30));
        Node node3 = map.createNode(4, new Point(20,40));
        Arc arc2 = map.createArc("route1",12,31,1,2);
        Arc arc3 = map.createArc("route2",12,31,2,3);
        Arc arc4 = map.createArc("route3",12,31,3,4);
        Delivery delivery1 = new Delivery(1,node1);
        Delivery delivery2 = new Delivery(2,node2);
        ArrayList<Delivery> arrayList1 = new ArrayList<>();
        ArrayList<Delivery> arrayList2 = new ArrayList<>();
        arrayList1.add(delivery1);
        arrayList2.add(delivery2);
        TimeSlot timeSlot1 = new TimeSlot(8,9,arrayList1);
        TimeSlot timeSlot2 = new TimeSlot(9,10,arrayList2);
        ArrayList<TimeSlot> arrayTimeSlot = new ArrayList<>();
        arrayTimeSlot.add(timeSlot1);
        arrayTimeSlot.add(timeSlot2);
          
        Planning planning = new Planning(map,warehouse,arrayTimeSlot);
        planning.removeDelivery(delivery2);
        
        ArrayList<Delivery> expResult = new ArrayList<>();
        expResult.add(delivery1);
       
        List<Delivery> result = new ArrayList<>();
        result = planning.getDeliveries();
        
         for(int i=0;i<expResult.size();i++)
        {
            assertEquals(expResult.get(i), result.get(i));
        } 
    }

    /**
     * Test of getNodePreviousDelivery method, of class Planning.
     */
    @Test
    public void testGetNodePreviousDelivery() {
        System.out.println("getNodePreviousDelivery");
        Delivery delivery = null;
        Planning instance = null;
        Node expResult = null;
        Node result = instance.getNodePreviousDelivery(delivery);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of swapDeliveries method, of class Planning.
     */
    @Test
    public void testSwapDeliveries() {
        System.out.println("swapDeliveries");
        Delivery delivery1 = null;
        Delivery delivery2 = null;
        Planning instance = null;
        instance.swapDeliveries(delivery1, delivery2);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTimeSlots method, of class Planning.
     */
    @Test
    public void testGetTimeSlots() {
        System.out.println("getTimeSlots");
        Planning instance = null;
        List<TimeSlot> expResult = null;
        List<TimeSlot> result = instance.getTimeSlots();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFirstTimeSlot method, of class Planning.
     */
    @Test
    public void testGetFirstTimeSlot() {
        System.out.println("getFirstTimeSlot");
        Planning instance = null;
        TimeSlot expResult = null;
        TimeSlot result = instance.getFirstTimeSlot();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMap method, of class Planning.
     */
    @Test
    public void testGetMap() {
        System.out.println("getMap");
        Planning instance = null;
        Map expResult = null;
        Map result = instance.getMap();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWarehouse method, of class Planning.
     */
    @Test
    public void testGetWarehouse() {
        System.out.println("getWarehouse");
        Planning instance = null;
        Node expResult = null;
        Node result = instance.getWarehouse();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRoute method, of class Planning.
     */
    @Test
    public void testGetRoute() {
        System.out.println("getRoute");
        Planning instance = null;
        Route expResult = null;
        Route result = instance.getRoute();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRoute method, of class Planning.
     */
    @Test
    public void testSetRoute() {
        System.out.println("setRoute");
        Route route = null;
        Planning instance = null;
        instance.setRoute(route);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    /**
     * Test of getDeliveries method, of class Planning.
     */
    @Test
    public void testGetDeliveries() {
        System.out.println("getDeliveries");
        
        Map map = new Map();   
        Node warehouse = map.createNode(1, new Point(20,10));
        Node node1 = map.createNode(2, new Point(20,20));
        Node node2 = map.createNode(3, new Point(20,30));
        Node node3 = map.createNode(4, new Point(20,40));
        Arc arc2 = map.createArc("route1",12,31,1,2);
        Arc arc3 = map.createArc("route2",12,31,2,3);
        Arc arc4 = map.createArc("route3",12,31,3,4);
        Delivery delivery1 = new Delivery(1,node1);
        Delivery delivery2 = new Delivery(2,node2);
        ArrayList<Delivery> arrayList1 = new ArrayList<>();
        ArrayList<Delivery> arrayList2 = new ArrayList<>();
        arrayList1.add(delivery1);
        arrayList2.add(delivery2);
        TimeSlot timeSlot1 = new TimeSlot(8,9,arrayList1);
        TimeSlot timeSlot2 = new TimeSlot(9,10,arrayList2);
        ArrayList<TimeSlot> arrayTimeSlot = new ArrayList<>();
        arrayTimeSlot.add(timeSlot1);
        
        
        Planning planning = new Planning(map,warehouse,arrayTimeSlot);
        planning.addDelivery(node2, node1, timeSlot2);
        
        ArrayList<Delivery> expResult = new ArrayList<>();
        expResult.add(delivery1);
        expResult.add(delivery2);
       
        List<Delivery> result = new ArrayList<>();
        result = planning.getDeliveries();              //Function tested
        
         for(int i=0;i<expResult.size();i++)
        {
            assertEquals(expResult.get(i), result.get(i));
        }
    }
    
}
