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
    private float _startTime;
    private float _endTime;
    private ArrayList<Delivery> _deliveries;
    
    public TimeSlot(float startTime, float endTime, ArrayList<Delivery> deliveries) {
        _startTime = startTime;
        _endTime = endTime;
        _deliveries = deliveries;
    }
    
    public boolean containsTime(float time) {
        // \todo implement here
        return false;
    }
}
