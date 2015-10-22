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
public class TimeSlot {
    private int _startTime; // Unit : seconds
    private int _endTime; // Unit : seconds 
    private ArrayList<Delivery> _deliveries;
    
    public TimeSlot(int startTime, int endTime, ArrayList<Delivery> deliveries) {
        _startTime = startTime;
        _endTime = endTime;
        _deliveries = deliveries;
    }
    
    public boolean containsTime(float time) {
        // \todo implement here
        return false;
    }
}
