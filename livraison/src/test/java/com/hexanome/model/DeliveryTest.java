/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.model;

import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author guillaume
 */
public class DeliveryTest {
    
    public DeliveryTest() {
    }

    /**
     * Test of getId method, of class Delivery.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        int id = 1;
        Node node = new Node(id,new Point(10,30));
        Delivery delivery = new Delivery(id,node);
        int result = delivery.getId();
        assertEquals(result, id);
    }
    /**
     * Test of getDeliveryTime method, of class Delivery.
     */
    @Test
    public void testGetDeliveryTime() {
        System.out.println("getDeliveryTime");        
        int id = 1;
        Node node = new Node(id,new Point(10,30));
        Delivery delivery = new Delivery(id,node);
        float expResult = 0.0F;
        float result = delivery.getDeliveryTime();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setDeliveryTime method, of class Delivery.
     */
    @Test
    public void testSetDeliveryTime() {
        System.out.println("setDeliveryTime");
        float deliveryTime = 3.5F;
        int id = 1;
        Node node = new Node(id,new Point(10,30));
        Delivery delivery = new Delivery(id,node);
        delivery.setDeliveryTime(deliveryTime);
        float result = delivery.getDeliveryTime();
        assertEquals(deliveryTime, result, 0.0);
    }

    /**
     * Test of getNode method, of class Delivery.
     */
    @Test
    public void testGetNode() {
        System.out.println("getNode");
        int id = 1;
        Node node = new Node(id,new Point(10,30));
        Delivery delivery = new Delivery(id,node);
        Node result = delivery.getNode();
        assertEquals(node, result);
    }

    /**
     * Test of attachTimeSlot method, of class Delivery.
     */
    @Test
    public void testAttachTimeSlot() {
        System.out.println("attachTimeSlot");
        int id = 1;
        Node node = new Node(id,new Point(10,30));
        Delivery delivery = new Delivery(id,node);    
        ArrayList<Delivery> deliveries = new ArrayList<>();
        deliveries.add(delivery);
        TimeSlot timeSlot = new TimeSlot(8,9,deliveries);
        delivery.attachTimeSlot(timeSlot);   //Function tested    
        TimeSlot result = delivery.getTimeSlot();
        assertEquals(timeSlot, result);
    }

    /**
     * Test of getTimeSlot method, of class Delivery.
     */
    @Test
    public void testGetTimeSlot() {
        System.out.println("getTimeSlot");
        int id = 1;
        Node node = new Node(id,new Point(10,30));
        Delivery delivery = new Delivery(id,node);    
        ArrayList<Delivery> deliveries = new ArrayList<>();
        deliveries.add(delivery);
        TimeSlot timeSlot = new TimeSlot(8,9,deliveries);
        delivery.attachTimeSlot(timeSlot);      
        TimeSlot result = delivery.getTimeSlot(); //Function tested 
        assertEquals(timeSlot, result);
    }
}
