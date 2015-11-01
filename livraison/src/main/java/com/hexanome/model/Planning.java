package com.hexanome.model;

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
        //planningComputeRouteWorker.interrupt();
    }

    /**
     * Start the route computing. The observers will be notified when the route
     * is set. Update the deliveries time as well.
     */
    public void computeRoute() {
        planningComputeRouteWorker = new PlanningComputeRouteWorker(this);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return planningComputeRouteWorker;
            }
        };
        service.stateProperty()
                .addListener(new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State oldValue,
                            Worker.State newValue) {
                        switch (newValue) {
                            case FAILED:
                            case CANCELLED:
                            case SUCCEEDED:
                                ModelManager.getInstance().getPlanning().getRoute().addSubscriber(UIManager.getInstance().getMainWindow().getMapView());
                                break;
                        }
                    }
                });
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
