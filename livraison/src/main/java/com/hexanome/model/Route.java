package com.hexanome.model;

import com.hexanome.utils.Publisher;
import com.hexanome.utils.Subscriber;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a route, with the entire path to follow to execute every delivery.
 * @author pierre
 */
public class Route implements Publisher {

    /**
     * Collection of path representing the route.
     */
    private LinkedList<Path> paths;

    /**
     * List of the subscribers.
     */
    private ArrayList<Subscriber> subscribers;
    
    /**
     * Planning associated with this route.
     */
    private Planning planning;

    /**
     * Construct the route with the paths passed by parameter and update the
     * deliveries times.
     *
     * @param planning
     * @param paths The paths representing the route.
     */
    public Route(Planning planning, LinkedList<Path> paths) {
        this.planning = planning;
        this.paths = paths;
        subscribers = new ArrayList<>();

        updateDeliveriesTime();
        updateArcTimeSlots();
    }

    /**
     * Update the delivery time of each delivery contained in the path
     * collection.
     */
    private void updateDeliveriesTime() {
        // Get for each path the delivery object to update
        for (int i = 0, iMax = paths.size() - 1; i <= iMax; ++i) {
            Path path = paths.get(i);
            Path previousPath = (i == 0) ? null : paths.get(i - 1);

            Delivery delivery = path.getFirstNode().getDelivery();

            if (delivery != null) {
                float deliveryTime = path.getPathDuration();

                if (previousPath != null) {
                    Delivery previousDelivery = previousPath.getFirstNode().getDelivery();
                    if (previousDelivery != null) {
                        deliveryTime += previousDelivery.getDeliveryTime();
                    }
                }

                if (deliveryTime < delivery.getTimeSlot().getStartTime()) {
                    delivery.setDeliveryTime(delivery.getTimeSlot().getStartTime());
                } else {
                    delivery.setDeliveryTime(deliveryTime);
                }
            }
        }
    }
    
    /**
     * Update the associated time slots of the arcs contained in the path.
     */
    private void updateArcTimeSlots() {
        for (Path p : paths) {
            Delivery delivery = p.getLastNode().getDelivery();
            
            if (delivery != null) {
                for (Arc arc : p.getArcs()) {
                    arc.setAssociatedTimeSlot(delivery.getTimeSlot());
                }
            }
        }
    }
    
    /**
     * Find the delivery passed by parameter and return the next delivery if
     * exists.
     * @param delivery The delivery to find in the route.
     * @return The next delivery, or null if it doesn't exist.
     */
    public Delivery getNextDelivery(Delivery delivery) {
        for (Path p : paths) {
            Delivery currentDelivery = p.getFirstNode().getDelivery();
            
            if (currentDelivery != null) {
                if (currentDelivery.equals(delivery)) {
                    return p.getLastNode().getDelivery();
                }
            }
        }
        
        return null;
    }
    
    /*
     * Adds a delivery to the route.
     * @param delivery the delivery to add.
     * @param prevDelivery the delivery that will be executed before the one to add.
     * @param timeSlot the time slot to which the new delivery will belong.
     */
    public void addDelivery(Delivery delivery, Delivery prevDelivery, TimeSlot timeSlot) {
        // Find the previous delivery in the list of paths
        
    }
    
    /**
     * Removes a delivery from the route.
     * @param delivery the delivery to remove.
     */
    public void removeDelivery(Delivery delivery) {
        // retrieving the index of the paths where delivery is the source node or the end node 
        int deliveryIsSourcePath = -1;
        int deliveryIsDestPath = -1;
        int i = 0;
        while(i < paths.size() || (deliveryIsSourcePath != -1 && deliveryIsDestPath != -1))
        {
            Path path = paths.get(i);
            if(path.getLastNode() == delivery.getNode())
            {
                deliveryIsDestPath = i;
            }
            else if(path.getFirstNode() == delivery.getNode())
            {
                deliveryIsSourcePath = i;
            }
            i++;
        }
        
        // creating the new path
        Path newPath = null;
        Node prevNode = paths.get(deliveryIsDestPath).getFirstNode();
        Node nextNode = paths.get(deliveryIsSourcePath).getLastNode();
        newPath = planning.getMap().getFastestPath(prevNode, nextNode);
        
        // removing the old paths
        paths.remove(deliveryIsDestPath);
        paths.remove(deliveryIsSourcePath);
        
        // adding the new path
        paths.add(deliveryIsDestPath, newPath);
        
        updateDeliveriesTime();
        updateArcTimeSlots();
    }
    
    /**
     * Swaps two deliveries.
     * @param delivery1 the first delivery to swap.
     * @param delivery2 the second delivery to swap.
     */
    public void swapDeliveries(Delivery delivery1, Delivery delivery2) {
        // \todo implement
    }

    @Override
    public String toString() {
        String strpaths = "{";
        for (Path path : paths) {
            strpaths += path.toString() + ",";
        }
        strpaths = strpaths.substring(0, strpaths.length() - 1) + "}";
        return String.format("{ \"Route\" : { \"paths\":%s } }", strpaths);
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
    
    public List<Path> getPaths() {
        return Collections.unmodifiableList(paths);
    }

}
