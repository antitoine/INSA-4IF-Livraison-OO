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
public class Node {
    private int id;
    private float x;
    private float y;
    ArrayList<Arc> outgoings;
    
    /**
     * 
     * @param id
     * @param x
     * @param y 
     */
    public Node(int id, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
    /**
     * 
     * @param arc 
     */
    public void AttachOutgoingArc(Arc arc) {
        // \todo implement here
    }

    public int getId() {
        return _id;
    }
    
}
