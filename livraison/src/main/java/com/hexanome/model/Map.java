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
    public Map(ArrayList<Node> nodes, ArrayList<Arc> arcs) {
        _nodes = nodes;
        _arcs = arcs;
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
     * @param start
     * @param end 
     * @return
     */
    public Path getFastestPath(Node start, Node end) {
        // \todo implement here
        return null;
    }
}
