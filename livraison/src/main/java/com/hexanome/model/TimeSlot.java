package com.hexanome.model;

import java.util.ArrayList;

/**
 *
 * @author paul
 */
public class TimeSlot {
    private int startTime; // Unit : seconds
    private int endTime; // Unit : seconds 
    private ArrayList<Delivery> deliveries;
    
    public TimeSlot(int startTime, int endTime, ArrayList<Delivery> deliveries) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.deliveries = deliveries;
    }
    
    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }
    
    public boolean containsTime(float time) {
        // \todo implement here
        return false;
    }


}
