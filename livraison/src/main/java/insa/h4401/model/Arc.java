package insa.h4401.model;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class represents a road, that goes from a node to another one.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class Arc {
    
    /**
     * Name of the street.
     */
    private String streetName;
    
    /**
     * Length of the street, in meters.
     */
    private float length;
    
    /**
     * Average speed of the street, in meters/seconds.
     */
    private float avgSpeed;
    
    /**
     * The time (seconds) needed to go from the start to the end of the arc.
     */
    private float duration;
    
    /**
     * The node at the end of the arc.
     */
    private Node dest;
    
    /**
     * The node at the beginning of the arc.
     */
    private Node src;
    
    /**
     * The time slots associated with the arc when the arc is in a route.
     */
    private Set<TimeSlot> associatedTimeSlot;

    /**
     * Constructs a new arc.
     *
     * @param streetName The name of the street.
     * @param length     The length of the street, in meters.
     * @param avgSpeed   The average speed on this street, in meters/seconds.
     * @param src        The node from where the street starts.
     * @param dest       The node where the street ends.
     */
    public Arc(String streetName, float length, float avgSpeed, Node src, Node dest) {
        this.streetName = streetName;
        this.length = length;
        this.avgSpeed = avgSpeed;
        this.duration = length / avgSpeed;
        this.dest = dest;
        this.src = src;
        this.associatedTimeSlot = new TreeSet<>();
    }

    /**
     * Returns the node that ends the arc.
     *
     * @return the node "dest".
     */
    public Node getDest() {
        return dest;
    }

    /**
     * Returns the node that starts the arc.
     *
     * @return the node "src".
     */
    public Node getSrc() {
        return src;
    }

    /**
     * Returns arc's length.
     *
     * @return The length in meters.
     */
    public float getLength() {
        return length;
    }

    /**
     * Returns street's name.
     *
     * @return a string containing street's name
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Returns the duration (seconds) needed to go from the start to the end 
     * of the arc.
     *
     * @return The duration in seconds.
     */
    public float getDuration() {
        return duration;
    }

    /**
     * Returns the time slots associated with the arc.
     *
     * @return The time slots if it exists, empty list otherwise.
     */
    public Set<TimeSlot> getAssociatedTimeSlots() {
        return Collections.unmodifiableSet(associatedTimeSlot);
    }

    /**
     * Add a time slot to the current arc, or ignore it if it's already contained
     * in the list.
     *
     * @param timeSlot The time slot to attach.
     */
    void addAssociatedTimeSlot(TimeSlot timeSlot) {
        if (associatedTimeSlot != null) {
            associatedTimeSlot.add(timeSlot);
        }
    }

    /**
     * Remove all the associated time slots of the current arc.
     */
    void clearAssociatedTimeSlot() {
        if (associatedTimeSlot != null) {
            associatedTimeSlot.clear();
        }
    }

    /**
     * Returns the string describing the objet, used for debug only
     *
     * @return a string describing the object
     */
    @Override
    public String toString() {
        return String.format(""
                + "{\n"
                + "\"streetName\":\"%s\",\n"
                + "\"length\":%s,\n"
                + "\"avgSpeed\":%s,\n"
                + "\"duration\":%s,\n"
                + "\"destNodeId\":%s,\n"
                + "\"srcNodeId\":%s\n"
                + "}", streetName, length, avgSpeed, duration, dest.getId(), src.getId());
    }
}
