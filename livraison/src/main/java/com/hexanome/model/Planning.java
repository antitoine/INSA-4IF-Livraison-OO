package com.hexanome.model;

import com.hexanome.utils.ITSP;
import com.hexanome.utils.Publisher;
import com.hexanome.utils.Subscriber;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Planning implements Publisher {

    private Map map;
    private Node warehouse;
    private Route route;
    private ArrayList<TimeSlot> timeSlots;
    private ArrayList<Subscriber> subscribers;
    
    private PlanningComputeRouteWorker planningComputeRouteWorker;

    public Planning(Map map, Node warehouse, ArrayList<TimeSlot> timeSlots) {
        this.map = map;
        this.warehouse = warehouse;
        this.route = null; // On initialise à null pour l'instant
        this.timeSlots = timeSlots;

        subscribers = new ArrayList<>();
    }
    
    /**
     * Abort the route computing if it is running.
     */
    public void abortComputeRoute() {
        planningComputeRouteWorker.interrupt();
    }

    /**
     * Start the route computing. The observers will be notified when the route
     * is set. Update the deliveries time as well.
     */
    public void computeRoute() {
        planningComputeRouteWorker = new PlanningComputeRouteWorker(this);
        planningComputeRouteWorker.start();
    }

    /**
     * Returns the route, or null if any route was calculated before.
     * @return The fastest route.
     */
    public Route getFastestRoute() {
        return route;
    }

    public Delivery getDeliveryById(int id) {
        // \todo implement here
        return null;
    }

    public void addDelivery(Delivery delivery, Delivery previousDelivery, TimeSlot timeSlot) {
        if (route != null) {
            route.addDelivery(delivery, previousDelivery, timeSlot);
        }
    }

    public void removeDelivery(Delivery delivery) {
        // \todo implement here
    }

    public void swapDeliveries(Delivery delivery1, Delivery delivery2) {
        // \todo implement here
    }

    public List<TimeSlot> getTimeSlots() {
        return Collections.unmodifiableList(timeSlots);
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
    
    /**
     * Update the route and notify the subscribers.
     * @param route The new route tu use.
     */
    void setRoute(Route route) {
        this.route = route;
        notifySubsrcibers();
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
        s.update(this, route);
    }

    @Override
    public void removeSubscriber(Subscriber s) {
        subscribers.remove(s);
    }

    @Override
    public void notifySubsrcibers() {
        for (Subscriber s : subscribers) {
            s.update(this, route);
        }
    }

    @Override
    public void clearSubscribers() {
        subscribers.clear();
    }

    public Delivery getPreviousDelivery(Delivery delivery) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
