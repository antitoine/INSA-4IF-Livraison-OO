package com.hexanome.model;

import java.util.ArrayList;

/**
 * This class represents the path to follow to execute all the deliveries.
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class Path {
    /**
     * All the arcs to take, in the right order.
     */
    private ArrayList<Arc> arcs;
    /**
     * The total duration of the path.
     */
    private float pathDuration;
    
    /**
     * Returns a collection of arcs present in the path
     * @return a collection of arcs
     */
    public ArrayList<Arc> getArcs() {
        return arcs;
    }
    /**
     * Returns the first node of the path.
     * @return the first node of the path.
     */
    public Node getFirstNode() {
        return arcs.get(0).getSrc();
    }
    /**
     * Returns the last node of the path.
     * @return the last node of the path.
     */
    public Node getLastNode() {
        return arcs.get(arcs.size() - 1).getDest();
    }
    /**
     * Returns the total duration of the path, ie the sum of the arc's durations
     * @return The duration of the path.
     */
    public float getPathDuration() {
        return pathDuration;
    }
    
    /**
     * Constructor.
     * @param arcs the list of arcs of the path.
     */
    public Path(ArrayList<Arc> arcs) {
        this.arcs = arcs;
        this.pathDuration = 0;
        for (Arc arc : arcs) {
            this.pathDuration += arc.getDuration();
        }
    }
    
    /**
     * Determines if the path contains the node given in parameter.
     * @param node the node to look for.
     * @return true if the node is contained in the path.
     */
    public boolean containsNode(Node node)
    {
        for (Arc arc : arcs) {
            if (arc.getSrc().equals(node) || arc.getDest().equals(node)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Comparison operator for equality
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Path)) {
            return false;
        }
        
        Path path = (Path) obj;
        if(this.pathDuration != path.pathDuration || this.arcs.size() != path.arcs.size()){
            return false;
        }
        
        for(int i = 0; i < this.arcs.size(); i++) {
            if(!this.arcs.get(i).equals(path.arcs.get(i))) {
                return false;
            }
        }
        return true; //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Returns the string describing the objet, used for debug only
     * @return a string describing the object
     */
    @Override
    public String toString() {
        String strarcs = "{";
        for (Arc arc : arcs) {
            strarcs += arc.toString() + ",";
        }
        strarcs += strarcs.substring(0, strarcs.length()-1) + "}";
        return String.format("\"Path\" : {\n"
                + "\"pathDuration\":%s, \"arcs\":\"%s\"\n"
                + "}", strarcs, pathDuration);
    }
}
