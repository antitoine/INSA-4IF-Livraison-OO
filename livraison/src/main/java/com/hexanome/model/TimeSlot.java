package com.hexanome.model;

import java.util.ArrayList;

/**
 * This class represents a time slot, with a start time and an end time.
 * @author paul
 */
public class TimeSlot {

    /**
     * Start time of the time slot.
     */
    private int startTime; // Unit : seconds
    /**
     * End time of the time slot.
     */
    private int endTime; // Unit : seconds 
    /**
     * Deliveries contained in the time slot.
     */
    private ArrayList<Delivery> deliveries;

    /**
     * Returns all the deliveries contained in the time slot.
     * @return a list of deliveries.
     */
    public ArrayList<Delivery> getDeliveries() {
        return deliveries;
    }
    
    /**
     * Add a delivery to the current time slot
     * @param delivery The delivery to add
     */
    void addDelivery(Delivery delivery) {
        deliveries.add(delivery);
        delivery.attachTimeSlot(this);
    }

    /**
     * Constructor.
     * @param startTime the start time of the time slot.
     * @param endTime the end time of the time slot.
     * @param deliveries the deliveries contained in the time slot.
     */
    public TimeSlot(int startTime, int endTime, ArrayList<Delivery> deliveries) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.deliveries = deliveries;
        
        for (Delivery d : deliveries) {
            d.attachTimeSlot(this);
        }
    }

    /**
     * Returns the start time of the time slot.
     * @return the start time.
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Returns the end time of the time slot.
     * @return the end time.
     */
    public int getEndTime() {
        return endTime;
    }

    /**
     * Checks if the time passed by parameter is betweend the start time and the
     * end time of the current time slot.
     * @param time The time to check.
     * @return True if the time is contained in the current time slot, false
     * otherwise.
     */
    public boolean containsTime(float time) {
        return (time >= startTime && time <= endTime);
    }

    @Override
    public String toString() {
        String strdeliveries = "";
        for (Delivery delivery : deliveries) {
            strdeliveries += delivery.toString() + ",";
        }
        strdeliveries += strdeliveries.substring(0, strdeliveries.length() - 1) + "}";
        return String.format(""
                + "{\n"
                + "\"startTime\":%s,\n"
                + "\"endTime\":%s,\n"
                + "\"deliveries\":%s\n"
                + "}", startTime, endTime, strdeliveries);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof TimeSlot)) {
            return false;
        }
        
        TimeSlot timeSlot = (TimeSlot)obj;
        if(this.startTime != timeSlot.startTime || this.endTime != timeSlot.endTime) {
            return false;
        }
        
        for(int i = 0; i < this.deliveries.size(); i++) {
            if(!this.deliveries.get(i).equals(timeSlot.deliveries.get(i))) {
                return false;
            }
        }
        
        return true;
    }
}
