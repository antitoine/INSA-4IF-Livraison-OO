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
    private int _id;
    private float _x;
    private float _y;
    ArrayList<Arc> _outgoings;
    
    /**
     * 
     * @param id
     * @param x
     * @param y 
     */
    public Node(int id, float x, float y) {
        _id = id;
        _x = x;
        _y = y;
    }
    /**
     * 
     * @param arc 
     */
    public void AttachOutgoingArc(Arc arc) {
        // \todo implement here
    }
    
}
