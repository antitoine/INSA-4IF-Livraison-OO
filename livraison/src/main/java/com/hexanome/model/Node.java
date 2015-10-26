package com.hexanome.model;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author paul
 */
public class Node {
    private int id;
    private Point location;
    ArrayList<Arc> outgoings;
    
    /**
     * 
     * @param id
     * @param location
     */
    public Node(int id, Point location) {
        this.id = id;
        this.location = location;
    }
    /**
     * 
     * @param arc 
     */
    public void AttachOutgoingArc(Arc arc) {
        // \todo implement here
    }

    public int getId() {
        return id;
    }

    public Point getLocation() {
        return location;
    }
    
}
