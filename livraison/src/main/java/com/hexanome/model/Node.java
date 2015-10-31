package com.hexanome.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author paul
 */
public class Node {

    private int id;
    private ArrayList<Arc> outgoings;
    private Point location;
    
    private Delivery delivery;

    /**
     *
     * @param id
     * @param location
     */
    public Node(int id, Point location) {
        this.id = id;
        this.location = location;
        this.outgoings = new ArrayList<>();
        this.delivery = null;
    }

    /**
     *
     * @param arc
     */
    public void attachOutgoingArc(Arc arc) {
        outgoings.add(arc);
    }
    
    public List<Arc> getOutgoingArcs() {
        return Collections.unmodifiableList(outgoings);
    }
    
    Arc getOutgoingArc(Node dest) {
        for (Arc arc : outgoings) {
            if (arc.getDest().equals(dest)) {
                return arc;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public Point getLocation() {
        return location;
    }
    
    void attachDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
    
    public Delivery getDelivery() {
        return delivery;
    }

    @Override
    public String toString() {
        String arcs = "";
        if (outgoings.size() > 0) {
            for (Arc outgoing : outgoings) {
                arcs += outgoing.toString() + ",";
            }
            arcs = arcs.substring(0, arcs.length() - 1);
        }

        return String.format(""
                + "{\n"
                + "\"id\":%s, "
                + "\"location\":\"%s\","
                + "\"outgoings\":"
                + "["
                + "%s\n"
                + "]\n"
                + "}", id, location.toString(), arcs);
    }

}
