package com.hexanome.model;

import java.util.LinkedList;

/**
 * 
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
    
    public void addDelivery(Delivery delivery, Delivery prevDelivery, TimeSlot timeSlot) {
        // \todo implement
    }
    
    public void removeDelivery(Delivery delivery) {
        // \todo implement
    }
    
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
