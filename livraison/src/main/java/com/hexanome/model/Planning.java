package com.hexanome.model;

import com.hexanome.utils.Publisher;
import com.hexanome.utils.Subscriber;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
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
     *
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
        planningComputeRouteWorker.cancel();
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
        service.stateProperty().addListener(listenerComputeRoute);
        service.start();
    }
    
    /**
     * Compute the route synchronously. Update the deliveries time.
     * @throws java.lang.Exception Ifthe route can't be computed.
     */
    public void computeRoute() throws Exception {
        planningComputeRouteWorker = new PlanningComputeRouteWorker(this);
        planningComputeRouteWorker.call();
    }

    /**
     * Adds a delivery to the planning, after another delivery.
     *
     * @param node The node where we want to add a delivery
     * @param nodePreviousDelivery The node where is the delivery to do before
     * the new one we want to add.
     * @param timeSlot The time slot in which we want the new delivery to be.
     * @return The delivery newly created.
     */
    public Delivery addDelivery(Node node, Node nodePreviousDelivery, TimeSlot timeSlot) {
        if (route != null) {
            Delivery newDelivery = new Delivery(timeSlot.getDeliveries().size() + 1, node);
            timeSlot.addDelivery(newDelivery);

            route.addDelivery(newDelivery, nodePreviousDelivery);
            notifySubscribers();
            return newDelivery;
        }

        return null;
    }

    /**
     * Removes a delivery from the planning.
     *
     * @param delivery the delivery to remove.
     */
    public void removeDelivery(Delivery delivery) {
        if (route != null) {
            route.removeDelivery(delivery);          
            notifySubscribers();
        }
    }

    /**
     * Finds the delivery passed by parameter and returns the node which
     * contains the delivery done before. A route must be computed when you
     * call this method. Otherwise, it will return null.
     *
     * @param delivery The delivery to find in the current planning.
     * @return The node of the previous delivery.
     */
    public Node getNodePreviousDelivery(Delivery delivery) {
        if (route != null) {
            return route.getNodePreviousDelivery(delivery);
        }
        return null;
    }

    /**
     * Swaps two deliveries.
     *
     * @param delivery1 the first delivery to swap.
     * @param delivery2 the second delivery to swap.
     */
    public void swapDeliveries(Delivery delivery1, Delivery delivery2) {
        if (route != null) {
            route.swapDeliveries(delivery1, delivery2);
            notifySubscribers();
        }
    }

    /**
     * Returns all the time slots.
     *
     * @return the list of all the time slots.
     */
    public List<TimeSlot> getTimeSlots() {
        return Collections.unmodifiableList(timeSlots);
    }

    /**
     * Returns the first time slot, with the lowest start date.
     *
     * @return The first time slot, or null if it doesn't exist.
     */
    public TimeSlot getFirstTimeSlot() {
        return (timeSlots.isEmpty()) ? null : timeSlots.get(0);
    }

    /**
     * Returns the map corresponding to this planning.
     *
     * @return the map.
     */
    public Map getMap() {
        return map;
    }

    /**
     * Returns the unique warehouse of this planning.
     *
     * @return the warehouse.
     */
    public Node getWarehouse() {
        return warehouse;
    }

    /**
     * Returns the route created from this planning.
     *
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
        notifySubscribers();
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
        subscribers.remove(s);
    }

    @Override
    public void notifySubscribers() {
        for (Subscriber s : subscribers) {
            s.update(this, null);
        }
    }

    @Override
    public void clearSubscribers() {
        subscribers.clear();
    }

    public List<Delivery> getDeliveries() {
        ArrayList<Delivery> deliveries = new ArrayList<>();
        for (TimeSlot ts : timeSlots) {
            deliveries.addAll(ts.getDeliveries());
        }
        return deliveries;
    }

}
