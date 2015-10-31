package com.hexanome.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author paul
 */
public class Path {
    private ArrayList<Arc> arcs;
    private float pathDuration;
    /**
     * 
     * @param arcs 
     */
    public Path(ArrayList<Arc> arcs) {
        this.arcs = arcs;
        this.pathDuration = 0;
        for (Arc arc : arcs) {
            this.pathDuration += arc.getDuration();
        }
    }
    /**
     * 
     * @return 
     */
    public Node getFirstNode() {
        return arcs.get(0).getSrc();
    }
    /**
     * 
     * @return 
     */
    public Node getLastNode() {
        return arcs.get(arcs.size() - 1).getDest();
    }
    
    public boolean constainsNode(Node node)
    {
        // \todo implement here
        return false;
    }
    
    public float getPathDuration() {
        return pathDuration;
    }
    
    public List<Arc> getArcs() {
        return Collections.unmodifiableList(arcs);
    }

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
