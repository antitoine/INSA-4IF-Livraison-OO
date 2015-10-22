/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.model;

import java.util.ArrayList;

/**
 *
 * @author paul
 */
public class Map {
    private ArrayList<Node> _nodes;
    private ArrayList<Arc> _arcs;
    
    /**
     * 
     * @param nodes
     * @param arcs 
     */
    public Map() { 
    }
    /**
     * Factory method to build nodes in map
     * @return 
     */
    public Node createNode(int id, float x, float y) {
        // Create a new node 
        Node n = new Node(id, x, y);
        // Add the new node to the local collection
        _nodes.add(n);
        // Return the new node
        return n;
    }
    /**
     * Factory method to build arcs in the map
     * @param streetName
     * @param length
     * @param avgSpeed
     * @param src
     * @param dest
     * @return 
     */
    public Arc createArc(String streetName, float length, float avgSpeed, Node src, Node dest) {
        // Creating a new arc
        Arc a = new Arc(streetName, length, avgSpeed, src, dest);
        // Attaching the arc to its source node
        src.AttachOutgoingArc(a);
        // Adding arc to the internal collection
        _arcs.add(a);
        // Return the new arc
        return a;
    }
    /**
     * 
     * @param x
     * @param y
     * @return 
     */
    public Node getNode(float x, float y) {
        // \todo implement here
        return null; 
    }
    /**
     * 
     * @param id
     * @return 
     */
    public Node getNode(int id) {
       // \todo implement here
        return null; 
    }
    /**
     * 
     * @return 
     */
    public ArrayList<Arc> getArcs() {
        return _arcs;
    }
    /**
     * 
     * @return 
     */
    public ArrayList<Node> getNodes() {
        return _nodes;
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
}
