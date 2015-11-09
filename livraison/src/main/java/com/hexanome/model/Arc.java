package com.hexanome.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
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
     * The time slots associated with the arc when the arc is in a route.
     */
    private Set<TimeSlot> associatedTimeSlot;

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
     * Returns arc's length
     * @return the length in meters
     */
    public float getLength() {
        return length;
    }
    
    /**
     * Returns street's name
     * @return a string containing street's name
     */
    public String getStreetName() {
        return streetName;
    }
    /**
     * Returns the duration (seconds) needed to go from the start to the end of the arc.
     * @return the attribute "duration".
     */
    public float getDuration() {
        return duration;
    }
    /**
     * Returns the time slots associated with the arc, if it exists.
     * @return The time slot if it exists, empty list otherwise.
     */
    public Set<TimeSlot> getAssociatedTimeSlots() {
        return Collections.unmodifiableSet(associatedTimeSlot);
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
        this.duration = length / avgSpeed; // Unit : s
        this.dest = dest;
        this.src = src;
        this.associatedTimeSlot = new TreeSet<>();
    }
    
    /**
     * Set a time slot to the current arc. 
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
