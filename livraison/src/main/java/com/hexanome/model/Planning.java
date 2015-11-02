package com.hexanome.model;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.UIManager;
import com.hexanome.utils.Publisher;
import com.hexanome.utils.Subscriber;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

/**
 * 
 * @author 
 */
public class Planning implements Publisher {

    /**
     * The map associated with this planning.
     */
    private Map map;
    /**
     * The unique warehouse of the map.
     */
    private Node warehouse;
    /**
     * The route created from this planning.
     */
    private Route route;
    /**
     * All the timeslots where a delivery can be executed.
     */
    private ArrayList<TimeSlot> timeSlots;
    /**
     * The subscribers of this class.
     */
    private ArrayList<Subscriber> subscribers;

    /**
     * Computes the route from this planning.
     */
    private PlanningComputeRouteWorker planningComputeRouteWorker;

    /**
     * Constructor.
     * @param map the map related to this planning.
     * @param warehouse the warehouse.
     * @param timeSlots all the timeslots where a delivery can be executed.
     */
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
        //planningComputeRouteWorker.interrupt();
    }

    /**
     * Start the route computing. The observers will be notified when the route
     * is set. Update the deliveries time as well.
     */
    public void computeRoute(ChangeListener<Worker.State> listenerComputeRoute) {
        planningComputeRouteWorker = new PlanningComputeRouteWorker(this);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return planningComputeRouteWorker;
            }
        };
        service.stateProperty()
                .addListener(listenerComputeRoute);
        service.start();
    }

    /**
     * Returns the route, or null if any route was calculated before.
     *
     * @return The fastest route.
     */
    public Route getFastestRoute() {
        return route;
    }

    /**
     * Returns the delivery thanks to its id.
     * @param id the id of the delivery.
     * @return the delivery corresponding to this id.
     */
    public Delivery getDeliveryById(int id) {
        // \todo implement here
        return null;
    }

    /**
     * Adds a delivery to the planning, after another delivery.
     * @param delivery the delivery we want to add.
     * @param previousDelivery the delivery that will be before the one we want to add.
     * @param timeSlot the time slot in which we want the new delivery to be.
     */
    public void addDelivery(Delivery delivery, Delivery previousDelivery, TimeSlot timeSlot) {
        if (route != null) {
            route.addDelivery(delivery, previousDelivery, timeSlot);
        }
    }

    /**
     * Removes a delivery from the planning.
     * @param delivery the delivery to remove.
     */
    public void removeDelivery(Delivery delivery) {
        // \todo implement here
    }

    /**
     * Swaps two deliveries.
     * @param delivery1 the first delivery to swap.
     * @param delivery2  the second delivery to swap.
     */
    public void swapDeliveries(Delivery delivery1, Delivery delivery2) {
        // \todo implement here
    }

    /**
     * Returns all the time slots.
     * @return the list of all the time slots.
     */
    public List<TimeSlot> getTimeSlots() {
        return Collections.unmodifiableList(timeSlots);
    }

    /**
     * Returns the map corresponding to this planning.
     * @return the map.
     */
    public Map getMap() {
        return map;
    }

    /**
     * Returns the unique warehouse of this planning.
     * @return the warehouse.
     */
    public Node getWarehouse() {
        return warehouse;
    }

    /**
     * Returns the route created from this planning.
     * @return the route.
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Update the route and notify the subscribers.
     *
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

    /**
     * Returns the delivery that is before the one in parameter.
     * @param delivery the delivery that is right after the one we are looking for.
     * @return the delivery before the one in parameter.
     */
    public Delivery getPreviousDelivery(Delivery delivery) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Collection<Delivery> getDeliveries() {
        ArrayList<Delivery> deliveries = new ArrayList();
        for(TimeSlot ts : timeSlots){
            deliveries.addAll(ts.getDeliveries());
        }
        return deliveries;
    }
}
