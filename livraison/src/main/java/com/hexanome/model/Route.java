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
        updateArcTimeSlots();
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
    
    public void addDelivery(Delivery delivery, Delivery prevDelivery, TimeSlot timeSlot) {
        
        
        
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
