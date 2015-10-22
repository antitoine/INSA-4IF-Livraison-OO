/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.model;

/**
 *
 * @author paul
 */
public class Delivery {
    private float _deliveryTime ;
    private Node _node;
    
    /**
     * 
     * @param node 
     */
    public Delivery(Node node) {
        _node = node;
    }
    
}
