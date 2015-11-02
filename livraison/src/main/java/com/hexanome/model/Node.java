package com.hexanome.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a node, which can be the beginning
 * point of one or many outgoings streets.
 * @author paul
 */
public class Node {

    /**
     * The id of the node.
     */
    private int id;
    /**
     * The arcs starting from this node.
     */
    private ArrayList<Arc> outgoings;
    /**
     * The location of this node on the map.
     */
    private Point location;
    /**
     * The delivery attached to this node (if there is one).
     */
    private Delivery delivery;

    /**
     * Constructor.
     * @param id the id of the node.
     * @param location the location of the node on the map.
     */
    public Node(int id, Point location) {
        this.id = id;
        this.location = location;
        this.outgoings = new ArrayList<>();
        this.delivery = null;
    }

    /**
     * This methods adds an arc to the outgoing arcs list.
     * @param arc the arc to attach.
     */
    public void attachOutgoingArc(Arc arc) {
        outgoings.add(arc);
    }
    
    /**
     * Returns all the outgoing arcs.
     * @return the list of outgoing arcs.
     */
    public List<Arc> getOutgoingArcs() {
        return Collections.unmodifiableList(outgoings);
    }
    
    /**
     * Returns an outgoing arc, knowing its ending node.
     * @param dest the ending node of the arc we are looking for.
     * @return the arc.
     */
    Arc getOutgoingArc(Node dest) {
        for (Arc arc : outgoings) {
            if (arc.getDest().equals(dest)) {
                return arc;
            }
        }
        return null;
    }

    /**
     * Returns the id of the node.
     * @return the id.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the location of the node.
     * @return the location point.
     */
    public Point getLocation() {
        return location;
    }
    
    /**
     * Attaches a delivery to this node.
     * @param delivery the delivery to attach.
     */
    void attachDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
    
    /**
     * Returns the delivery attached to this node.
     * @return the delivery attached to this node.
     */
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
