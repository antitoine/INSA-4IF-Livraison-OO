package com.hexanome.model;

import com.hexanome.utils.IGraph;
import java.util.HashMap;

/**
 * This class represents \todo Define here
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class PathGraph implements IGraph {
    
    /**
     * Graph represented by a map, with :
     *  - Key : The id of the initial vertex
     *  - Value : The terminal verticies, associated with their path.
     */
    private HashMap<Integer, HashMap<Integer, Path>> graph;
    
    /**
     * Map with the association of an edge number and its id.
     *  - Key : Edge number (0-based)
     *  - Value : Edge id
     */
    private HashMap<Integer, Integer> nodes;
    
    /** The number of nodes stored in the graph. */
    private int nodesSize;

    /**
     * Constructor of an empty Path Graph.
     */
    public PathGraph() {
        graph = new HashMap<>();
        nodes = new HashMap<>();  
        nodesSize = 0;
    }    

    @Override
    public int getNbArcs() {
        return graph.keySet().size();
    }

    @Override
    public float getCost(int i, int j) {
        if (!nodes.containsKey(i) || !nodes.containsKey(j)) {
            return -1;
        }
        
        // Convert the index to ids
        int srcId = nodes.get(i);
        int destId = nodes.get(j);              
        
        HashMap<Integer, Path> initialVertex = graph.get(srcId);
        
        if (initialVertex == null) {
            return -1;
        }
        
        Path path = initialVertex.get(destId);
        
        return (path == null) ? -1 : path.getPathDuration();
    }

    @Override
    public boolean isArc(int i, int j) {
        if (!nodes.containsKey(i) || !nodes.containsKey(j)) {
            return false;
        }
        
        // Convert the index to ids
        int srcId = nodes.get(i);
        int destId = nodes.get(j);        
        
        HashMap<Integer, Path> initialVertex = graph.get(srcId);
        
        return (initialVertex == null) ? false : initialVertex.containsKey(destId);
    }
    
    /**
     * Add a Path to the current graph. Ignore if path is null.
     * @param path The Path to add.
     */
    public void addPath(Path path) {
        if (path == null) {
            return;
        }
        
        Node firstNode = path.getFirstNode();
        
        if (!nodes.containsValue(firstNode.getId())) {
            nodes.put(nodesSize, firstNode.getId());
            nodesSize++;
        }
        
        Node lastNode = path.getLastNode();
        
        if (!nodes.containsValue(lastNode.getId())) {
            nodes.put(nodesSize, lastNode.getId());
            nodesSize++;
        }
        
        HashMap<Integer, Path> initialVertex = graph.get(firstNode.getId());
        
        if (initialVertex == null) {
            initialVertex = new HashMap<>();            
            graph.put(firstNode.getId(), initialVertex);
        }
        
        initialVertex.put(lastNode.getId(), path);
    }
    
    /**
     * Find the path associated with the first node having the i number and the
     * last node having the j number.
     * @param i The number of the first node of the path to find.
     * @param j The number of the last node of the path to find.
     * @return The path if it exists, null otherwise.
     */
    public Path indexAsPath(int i, int j) {
        if (!nodes.containsKey(i) || !nodes.containsKey(j)) {
            return null;
        }
        
        // Convert the index in ids
        int srcId = nodes.get(i);
        int destId = nodes.get(j);        
        
        HashMap<Integer, Path> initialVertex = graph.get(srcId);
        
        return initialVertex.get(destId);
    }
}
