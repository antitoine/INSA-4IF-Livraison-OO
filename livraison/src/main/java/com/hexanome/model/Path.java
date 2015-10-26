package com.hexanome.model;

import java.util.ArrayList;

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
        // \todo implement here
        return null;
    }
    /**
     * 
     * @return 
     */
    public Node getLastNode() {
        // \todo implement here
        return null;
    }
    
    public boolean constainsNode(Node node)
    {
        // \todo implement here
        return false;
    }
}
