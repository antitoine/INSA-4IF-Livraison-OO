package com.hexanome.model;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author paul
 */
public class Node {

    private int id;
    ArrayList<Arc> outgoings;
    private Point location;

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
        String arcs = "";
        if (outgoings.size() > 0) {
            for (Arc outgoing : outgoings) {
                arcs += outgoing.toString() + ",";
            }
            arcs += arcs.substring(0, arcs.length() - 1);
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
