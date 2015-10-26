package com.hexanome.model;

import java.util.ArrayList;

/**
 *
 * @author paul
 */
public class Planning {
    private Map map;
    private Node warehouse;
    private Route route;
    private ArrayList<TimeSlot> timeSlots;
    
    public Planning(Map map, Node warehouse, ArrayList<TimeSlot> timeSlots) {
        this.map = map;
        this.warehouse = warehouse;
        this.route = null; // On initialise à null pour l'instant
        this.timeSlots = timeSlots;
    }
    
    public void computeRoute()
    {
        // \todo implement here
    }
    
    public Route getFastestRoute(Planning planning)
    {
        // \todo implement here
        return null;
    }
    
    public Delivery getDeliveryById(int id) {
        // \todo implement here
        return null;
    }
    
    public void addDelivery(Delivery delivery, Delivery previousDelivery, TimeSlot timeSlot)
    {
        // \todo implement here
    }
    
    public void removeDelivery(Delivery delivery)
    {
        // \todo implement here
    }
    
    public void swapDeliveries(Delivery delivery1, Delivery delivery2)
    {
        // \todo implement here
    }
    public ArrayList<TimeSlot> getTimeSlots()
    {
        // \todo implement here
        return null;
    }
    
    // \todo add methods here
}
