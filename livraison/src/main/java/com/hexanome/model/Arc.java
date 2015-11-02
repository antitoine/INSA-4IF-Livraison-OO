package com.hexanome.model;

/**
 * This class represents a road, that goes from a node to another one.
 * @author paul
 */
public class Arc {
    /**
     * Name of the street.
     */
    private String streetName;
    /**
     * Length of the street.
     */
    private float length;
    /**
     * Average speed of the street.
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
     * The time slot associated with the arc when the arc is in a route.
     */
    private TimeSlot associatedTimeSlot;

    /**
     * Returns the node that ends the arc.
     * @return the node "dest".
     */
    public Node getDest() {
        return dest;
    }

    /**
     * Returns the node that starts the arc.
     * @return the node "src".
     */
    public Node getSrc() {
        return src;
    }    
    
    /**
     * Constructor.
     * @param streetName the name of the street
     * @param length the length of the street
     * @param avgSpeed the average speed on this street
     * @param src the node from where the street starts
     * @param dest the node where the street ends
     */
    public Arc(String streetName, float length, float avgSpeed, Node src, Node dest) {
        this.streetName = streetName;
        this.length = length;
        this.avgSpeed = avgSpeed;
        this.duration = length*avgSpeed; // Unit : s
        this.dest = dest;
        this.src = src;
        this.associatedTimeSlot = null;
    }
    
    /**
     * Returns the time slot associated with the arc, if it exists.
     * @return The time slot if it exists, null otherwise.
     */
    public TimeSlot getAssociatedTimeSlot() {
        return associatedTimeSlot;
    }
    
    /**
     * Set a time slot to the current arc. 
     * @param timeSlot The time slot to attach.
     */
    void setAssociatedTimeSlot(TimeSlot timeSlot) {
        this.associatedTimeSlot = timeSlot;
    }
    
    /**
     * Returns the duration (seconds) needed to go from the start to the end of the arc.
     * @return the attribute "duration".
     */
    public float getDuration() {
        return duration;
    }

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
