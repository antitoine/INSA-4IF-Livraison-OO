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
public class Path {
    private ArrayList<Arc> _arcs;
    private float _pathDuration;
    /**
     * 
     * @param arcs 
     */
    public Path(ArrayList<Arc> arcs) {
        _arcs = arcs;
        _pathDuration = 0;
        for (Arc arc : arcs) {
            _pathDuration += arc.getDuration();
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
}
