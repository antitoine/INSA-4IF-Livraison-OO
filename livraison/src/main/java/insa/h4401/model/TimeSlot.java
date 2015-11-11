package insa.h4401.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a time slot, with a start time and an end time.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class TimeSlot implements Comparable<TimeSlot> {

    /**
     * Start time of the time slot, in seconds.
     */
    private int startTime;
    
    /**
     * End time of the time slot, in seconds.
     */
    private int endTime;
    
    /**
     * Deliveries contained in the time slot.
     */
    private ArrayList<Delivery> deliveries;

    /**
     * Constructs a new time slot, and attach the time slot to the deliveries.
     *
     * @param startTime  the start time of the time slot.
     * @param endTime    the end time of the time slot.
     * @param deliveries the deliveries contained in the time slot.
     */
    public TimeSlot(int startTime, int endTime, ArrayList<Delivery> deliveries) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.deliveries = deliveries;

        deliveries.stream().forEach((d) -> d.attachTimeSlot(this));
    }

    /**
     * Returns all the deliveries contained in the time slot.
     *
     * @return a list of deliveries.
     */
    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    /**
     * Returns the start time of the time slot, in sum of seconds.
     *
     * @return the start time.
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Returns the end time of the time slot, in sum of seconds.
     *
     * @return the end time.
     */
    public int getEndTime() {
        return endTime;
    }

    /**
     * Adds a delivery to the current time slot and attaches it to the time slot.
     *
     * @param delivery The delivery to add
     */
    protected void addDelivery(Delivery delivery) {
        deliveries.add(delivery);
        delivery.attachTimeSlot(this);
    }

    /**
     * Removes the delivery passed by parameter in the list of deliveries.
     * Unattaches the time slot to the delivery.
     *
     * @param delivery The delivery to remove.
     */
    protected void removeDelivery(Delivery delivery) {
        deliveries.remove(delivery);
        delivery.attachTimeSlot(null);
    }

    /**
     * Checks if the time passed by parameter is between the start time and the
     * end time of the current time slot.
     *
     * @param time The time to check.
     * @return True if the time is contained in the current time slot, false
     * otherwise.
     */
    public boolean containsTime(float time) {
        return (time >= startTime && time <= endTime);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash += 71 * hash + this.startTime;
        hash += 71 * hash + this.endTime;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TimeSlot)) {
            return false;
        }

        TimeSlot timeSlot = (TimeSlot) obj;
        if (this.startTime != timeSlot.startTime || this.endTime != timeSlot.endTime || this.deliveries.size() != timeSlot.deliveries.size()) {
            return false;
        }

        for (int i = 0; i < this.deliveries.size(); i++) {
            if (!this.deliveries.get(i).equals(timeSlot.deliveries.get(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the string describing the objet, used for debug only
     *
     * @return a string describing the object
     */
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
    public int compareTo(TimeSlot o) {
        return this.startTime - o.startTime;
    }
}
