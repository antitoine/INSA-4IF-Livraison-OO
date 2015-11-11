package com.hexanome.model;

import com.hexanome.utils.Publisher;
import com.hexanome.utils.Subscriber;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a planning, a collection of deliveries
 * contained in timeslots.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
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
     * Constructs a new planning.
     *
     * @param map       The map related to this planning.
     * @param warehouse The warehouse.
     * @param timeSlots All the timeslots where a delivery can be executed.
     */
    public Planning(Map map, Node warehouse, ArrayList<TimeSlot> timeSlots) {
        this.map = map;
        this.warehouse = warehouse;
        this.route = null; // Route is null at this time, not yet computed.
        this.timeSlots = timeSlots;

        subscribers = new ArrayList<>();

        initNodesTimeSlot();
    }

    /**
     * Initializes the nodes contained in the map associated with the current
     * planning.
     */
    private void initNodesTimeSlot() {
        map.resetNodes();
        timeSlots.stream().forEach((ts) -> {
            ts.getDeliveries().stream().forEach((delivery) -> {
                delivery.updateNode();
            });
        });
    }

    /**
     * Finds the delivery passed by parameter and returns the node that
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
     * Returns a collection of deliveries present in the planning whatever the
     * associated timeslot is.
     *
     * @return A list of deliveries
     */
    public List<Delivery> getDeliveries() {
        List<Delivery> deliveries = new ArrayList<>();
        timeSlots.stream().forEach((ts) -> {
            deliveries.addAll(ts.getDeliveries());
        });
        return deliveries;
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
     * Updates the route.
     *
     * @param route The new route tu use.
     */
    void setRoute(Route route) {
        this.route = route;
    }

    /**
     * Aborts the route computing if it is running.
     */
    public void abortComputeRoute() {
        planningComputeRouteWorker.cancel();
    }

    /**
     * Starts the route computing in a new thread. Update the deliveries time as
     * well. The handler will be notified when the route computing will be done.
     * An ArithmeticException can be thrown throught the thread, if any route
     * can't be found.
     * 
     * @param handler The event handler to notify and the route computing is
     * finished.
     */
    public void computeRoute(EventHandler handler) {
        planningComputeRouteWorker = new PlanningComputeRouteWorker(this, handler);
        new Thread(planningComputeRouteWorker).start();
    }

    /**
     * Computes the route synchronously. Updates the deliveries time.
     *
     * @throws java.lang.Exception If the route can't be computed.
     */
    public void computeRoute() throws Exception {
        planningComputeRouteWorker = new PlanningComputeRouteWorker(this);
        planningComputeRouteWorker.call();
    }

    /**
     * Adds a delivery to the planning, after another delivery. Notifies the
     * planning and route subscribers when it's done.
     *
     * @param node The node where we want to add a delivery
     * @param nodePreviousDelivery The node where is the delivery to do before
     * the new one we want to add.
     * @param timeSlot The time slot in which we want the new delivery to be.
     * 
     * @return The delivery newly created.
     */
    public Delivery addDelivery(Node node, Node nodePreviousDelivery, TimeSlot timeSlot) {
        if (route != null) {
            Delivery newDelivery = new Delivery(
                    timeSlot.getDeliveries().size() + 1, 
                    node
            );
            timeSlot.addDelivery(newDelivery);

            route.addDelivery(newDelivery, nodePreviousDelivery);
            
            notifySubscribers();
            
            return newDelivery;
        }

        return null;
    }

    /**
     * Removes a delivery from the planning.  Notifies the planning and route 
     * subscribers when it's done.
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
     * Swaps two deliveries. Notifies the planning and route subscribers when 
     * it's done.
     *
     * @param delivery1 The first delivery to swap.
     * @param delivery2 The second delivery to swap.
     */
    public void swapDeliveries(Delivery delivery1, Delivery delivery2) {
        if (route != null) {
            route.swapDeliveries(delivery1, delivery2);
            notifySubscribers();
        }
    }

    /**
     * Adds a subscriber and notifies it.
     *
     * @param subscriber Subscriber to add
     */
    @Override
    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
        subscriber.update(this, null);
    }

    /**
     * Remove one subscriber.
     *
     * @param subscriber Subscriber to remove
     */
    @Override
    public void removeSubscriber(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    /**
     * Notifies all subscribers.
     */
    @Override
    public void notifySubscribers() {
        subscribers.stream().forEach((subscriber) -> {
            subscriber.update(this, null);
        });
    }

    /**
     * Removes all subscribers.
     */
    @Override
    public void clearSubscribers() {
        subscribers.clear();
    }

    /**
     * Returns the string describing the objet, used for debug only.
     *
     * @return a string describing the object
     */
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
}
