package com.hexanome.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents an ordered collection of arcs to follow to reach a
 * delivery or a warehouse.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class Path {

    /**
     * All the arcs to take, in the right order.
     */
    private LinkedList<Arc> arcs;

    /**
     * The total duration of the path.
     */
    private float pathDuration;

    /**
     * The total distance of the path.
     */
    private float pathDistance;

    /**
     * Constructor.
     *
     * @param arcs the list of arcs of the path.
     */
    public Path(LinkedList<Arc> arcs) {
        this.arcs = arcs;
        this.pathDuration = 0;

        for (Arc arc : arcs) {
            this.pathDuration += arc.getDuration();
            this.pathDistance += arc.getLength();
        }
    }

    /**
     * Returns a collection of arcs present in the path
     *
     * @return a collection of arcs
     */
    public List<Arc> getArcs() {
        return Collections.unmodifiableList(arcs);
    }

    /**
     * Returns the first node of the path.
     *
     * @return the first node of the path.
     */
    public Node getFirstNode() {
        return arcs.get(0).getSrc();
    }

    /**
     * Returns the last node of the path.
     *
     * @return the last node of the path.
     */
    public Node getLastNode() {
        return arcs.get(arcs.size() - 1).getDest();
    }

    /**
     * Returns the total duration of the path, ie the sum of the arc's durations
     *
     * @return The duration of the path.
     */
    public float getPathDuration() {
        return pathDuration;
    }

    /**
     * Returns the total distance of the path, ie the sum of the arc's distances
     *
     * @return The distance of the path.
     */
    public float getPathDistance() {
        return pathDistance;
    }

    /**
     * Determines if the path contains the node given in parameter.
     *
     * @param node the node to look for.
     * @return true if the node is contained in the path.
     */
    public boolean containsNode(Node node) {
        return arcs.stream().anyMatch(
                (arc) -> (arc.getSrc().equals(node) || arc.getDest().equals(node))
        );
    }

    /**
     * Comparison operator for equality.
     *
     * @param obj The object to compare.
     * 
     * @return True if equals, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Path)) {
            return false;
        }

        Path path = (Path) obj;
        if (this.pathDuration != path.pathDuration || this.arcs.size() != path.arcs.size()) {
            return false;
        }

        for (int i = 0; i < this.arcs.size(); i++) {
            if (!this.arcs.get(i).equals(path.arcs.get(i))) {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash += 41 * hash + Objects.hashCode(this.arcs);
        hash += 41 * hash + Float.floatToIntBits(this.pathDuration);
        hash += 41 * hash + Float.floatToIntBits(this.pathDistance);
        return hash;
    }

    /**
     * Returns the string describing the objet, used for debug only
     *
     * @return a string describing the object
     */
    @Override
    public String toString() {
        String strarcs = "{";
        strarcs = arcs.stream()
                      .map((arc) -> arc.toString() + ",")
                      .reduce(strarcs, String::concat);
        strarcs += strarcs.substring(0, strarcs.length() - 1) + "}";
        return String.format("\"Path\" : {\n"
                + "\"pathDuration\":%s, \"arcs\":\"%s\"\n"
                + "}", pathDuration, strarcs);
    }
}
