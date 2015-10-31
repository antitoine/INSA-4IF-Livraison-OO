package com.hexanome.model;

import com.hexanome.utils.Publisher;
import com.hexanome.utils.Subscriber;
import java.util.ArrayList;

public class Planning implements Publisher {

    private Map map;
    private Node warehouse;
    private Route route;
    private ArrayList<TimeSlot> timeSlots;
    private ArrayList<Subscriber> subscribers;

    public Planning(Map map, Node warehouse, ArrayList<TimeSlot> timeSlots) {
        this.map = map;
        this.warehouse = warehouse;
        this.route = null; // On initialise à null pour l'instant
        this.timeSlots = timeSlots;
        
        subscribers = new ArrayList<>();
    }

    public void computeRoute() {
        // \todo implement here
    }

    public Route getFastestRoute(Planning planning) {
        // \todo implement here
        return null;
    }

    public Delivery getDeliveryById(int id) {
        // \todo implement here
        return null;
    }

    public void addDelivery(Delivery delivery, Delivery previousDelivery, TimeSlot timeSlot) {
        // \todo implement here
    }

    public void removeDelivery(Delivery delivery) {
        // \todo implement here
    }

    public void swapDeliveries(Delivery delivery1, Delivery delivery2) {
        // \todo implement here
    }

    public ArrayList<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public Map getMap() {
        return map;
    }

    public Node getWarehouse() {
        return warehouse;
    }

    public Route getRoute() {
        return route;
    }

    // \todo add methods here
    @Override
    public String toString() {
        String strts = "";
        for (TimeSlot ts : timeSlots) {
            strts += ts.toString() + ",";
        }
        strts = strts.substring(0, strts.length() - 1) + "}";
        return String.format(""
                + "{\n"
                + "\"warehouseId\":%s,\n"
                + "\"timeslots\":{\n"
                + "%s\n"
                + "}"
                + "}", warehouse.getId(), strts);
    }

    @Override
    public void addSubscriber(Subscriber s) {
        subscribers.add(s);
        s.update(this, null);
    }

    @Override
    public void removeSubscriber(Subscriber s) {
        // TODO
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notifySubsrcibers() {
        for (Subscriber s : subscribers) {
            s.update(this, null);
        }
    }

    @Override
    public void clearSubscribers() {
        // TODO
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Delivery getPreviousDelivery(Delivery delivery) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
