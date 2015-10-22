/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        
    }
    
    public void removeDelivery(Delivery delivery) {
        
    }
    
    public void swapDeliveries(Delivery delivery1, Delivery delivery2) {
        
    }
    
}
