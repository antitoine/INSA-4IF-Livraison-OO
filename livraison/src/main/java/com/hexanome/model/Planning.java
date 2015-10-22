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
public class Planning {
    private Map _map;
    private Node _warehouse;
    private Route _route;
    private ArrayList<TimeSlot> _timeSlots;
    
    public Planning(Map map, Node warehouse, ArrayList<TimeSlot> timeSlots) {
        _map = map;
        _warehouse = warehouse;
        _route = null; // On initialise à null pour l'instant
        _timeSlots = timeSlots;
    }
    
    // \todo add methods here
}
