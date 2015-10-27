package com.hexanome.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author paul
 */
public class Map {
    private HashMap<Integer, Node> nodes;
    private ArrayList<Arc> arcs;
    
    /**
     * 
     * @param nodes
     * @param arcs 
     */
    public Map() { 
        nodes = new HashMap<>();
        arcs = new ArrayList<>();
    }
    /**
     * Factory method to build nodes in map
     * @param id
     * @param location
     * @return 
     */
    public Node createNode(int id, Point location) {
        // Create a new node 
        Node n = new Node(id, location);
        // Add the new node to the local collection
        nodes.put(id, n);
        // Return the new node
        return n;
    }
    /**
     * Factory method to build arcs in the map
     * @param streetName
     * @param length
     * @param avgSpeed
     * @param srcNodeId
     * @param destNodeId
     * @return 
     */
    public Arc createArc(String streetName, float length, float avgSpeed, int srcNodeId, int destNodeId) {
        // Retreive source node
        Node srcNode = getNodeById(srcNodeId);
        // Creating a new arc
        Arc a = new Arc(streetName, length, avgSpeed, srcNode, getNodeById(destNodeId));
        // Attaching the arc to its source node
        srcNode.AttachOutgoingArc(a);
        // Adding arc to the internal collection
        arcs.add(a);
        // Return the new arc
        return a;
    }
    /**
     * 
     * @param location
     * @return 
     */
    public Node getNodeByLocation(Point location) {
        // \todo implement here
        return null; 
    }
    /**
     * 
     * @param id
     * @return 
     */
    public Node getNodeById(int id) {
        return nodes.get(id);
    }
    /**
     * 
     * @return 
     */
    public ArrayList<Arc> getArcs() {
        return arcs;
    }
    /**
     * 
     * @return 
     */
    public ArrayList<Node> getNodes() {
        return null;
    }
    /**
     * 
     * @param start
     * @param end 
     * @return
     */
    public Path getFastestPath(Node start, Node end) {
        // \todo implement here
        return null;
    }
    
    public void clear() {
        // \todo implement here
    }

    @Override
    public String toString() {
        String strarcs = "";
        for (Arc arc : arcs) {
            strarcs += arc.toString() + ",";
        }
        strarcs = strarcs.substring(0, strarcs.length()-1);
        String strnodes = "";
        for(java.util.Map.Entry<Integer, Node> n : nodes.entrySet()) {
            strnodes += n.getValue().toString() + ",";
        }
        strnodes = strnodes.substring(0, strnodes.length()-1) + "";
        return String.format(""
                + "{"
                + "\"nodes\":[\n"
                + "%s\n"
                + "],\n"
                + "\"arcs\":[\n"
                + "%s\n"
                + "]\n"
                + "}", strnodes, strarcs);
    }
    
}
