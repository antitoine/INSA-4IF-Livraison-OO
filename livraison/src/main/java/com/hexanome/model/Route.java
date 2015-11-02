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

    
    private ArrayList<Subscriber> subscribers;
    
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
        int indexPreviousPath = -1;
        Path pathToReplace = null;
        
        boolean previousPathFound = false;
        for (int indexPath = 0, maxIndexPath = paths.size() - 1; !previousPathFound && indexPath <= maxIndexPath; ++indexPreviousPath) {
            Path p = paths.get(indexPath);
            
            Delivery currentDelivery = p.getFirstNode().getDelivery();
            
            if (currentDelivery != null && currentDelivery.equals(prevDelivery)) {
                previousPathFound = true;
                indexPreviousPath = indexPath;
                pathToReplace = p;
            }            
        }
        
        // Previous path with the previous delivery found
        if (pathToReplace != null) {
            // Compute the path between the previous delivery and the new delivery
            Path previousPath = planning.getMap().getFastestPath(pathToReplace.getFirstNode(), delivery.getNode());
            
            // Compute the path between the next delivery and the new delivery
            Path nextPath = planning.getMap().getFastestPath(delivery.getNode(), pathToReplace.getLastNode());
            
            // Replace the paths in the list
            paths.remove(indexPreviousPath);
            paths.add(indexPreviousPath, nextPath);
            paths.add(indexPreviousPath, previousPath);
            
            // Set the time slot and update the deliveries times
            delivery.attachTimeSlot(timeSlot);
            
            updateDeliveriesTime();
            updateArcTimeSlots();
        }
    }
    
    /**
     * Removes a delivery from the route.
     * @param delivery the delivery to remove.
     */
    public void removeDelivery(Delivery delivery) {
        // \todo implement
    }
    
    /**
     * Swaps two deliveries.
     * @param delivery1 the first delivery to swap.
     * @param delivery2 the second delivery to swap.
     */
    public void swapDeliveries(Delivery delivery1, Delivery delivery2) {
        // \todo implementh
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
    public void notifySubsrcibers() {
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
