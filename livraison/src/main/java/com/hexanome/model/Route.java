package com.hexanome.model;

import java.util.LinkedList;

/**
 * This class represents a route, with the entire path to follow to execute every delivery.
 * @author pierre
 */
public class Route {
    
    /** Collection of path representing the route. */
    private LinkedList<Path> paths;
    
    /**
     * Construct the route with the paths passed by parameter and update the
     * deliveries times.
     * @param paths The paths representing the route. 
     */
    public Route(LinkedList<Path> paths) {
        this.paths = paths;
        updateDeliveriesTime();
    }
    
    /**
     * Update the delivery time of each delivery contained in the path collection.
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
     * Adds a delivery to the route.
     * @param delivery the delivery to add.
     * @param prevDelivery the delivery that will be executed before the one to add.
     * @param timeSlot the time slot to which the new delivery will belong.
     */
    public void addDelivery(Delivery delivery, Delivery prevDelivery, TimeSlot timeSlot) {
        // \todo implement
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
        // \todo implement
    }

    @Override
    public String toString() {
        String strpaths = "{";
        for (Path path : paths) {
            strpaths += path.toString() + ",";
        }
        strpaths = strpaths.substring(0, strpaths.length()-1) + "}";
        return String.format("{ \"Route\" : { \"paths\":%s } }", strpaths);
    }
    
}
