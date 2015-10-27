package com.hexanome.model;

import java.util.ArrayList;

/**
 *
 * @author paul
 */
public class Route {
    private ArrayList<Path> paths;
    
    public Route(ArrayList<Path> paths) {
        this.paths = paths;
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
