package com.hexanome.model;

/**
 *
 * @author paul
 */
public class Arc {
    private String streetName;
    private float length;
    private float avgSpeed;
    private float duration;
    private Node dest;
    private Node src;
    
    public Arc(String streetName, float length, float avgSpeed, Node src, Node dest) {
        this.streetName = streetName;
        this.length = length;
        this.avgSpeed = avgSpeed;
        this.duration = length*avgSpeed; // Unit : s
        this.dest = dest;
        this.src = src;
    }
    
    public float getDuration() {
        return duration;
    }
}
