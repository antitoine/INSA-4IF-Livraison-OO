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
        this.outgoings = new ArrayList<>();
    }
    /**
     * 
     * @param arc 
     */
    public void AttachOutgoingArc(Arc arc) {
        outgoings.add(arc);
    }

    public int getId() {
        return id;
    }

    public Point getLocation() {
        return location;
    }

    @Override
    public String toString() {
        String arcs = "{";
        for (Arc outgoing : outgoings) {
            arcs += outgoing.toString() + ",";
        }
        arcs += arcs.substring(0, arcs.length()-1) + "}";
        return String.format("\"Node\" : {\n"
                + "\"id\":%s, \"location\":\"%s\", \"outgoings\":\"%s\"\n"
                + "}", id, location.toString(), arcs);
    }
    
}
