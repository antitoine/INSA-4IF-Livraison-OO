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
    private float deliveryTime ;
    private Node node;
    
    /**
     * 
     * @param node 
     */
    public Delivery(Node node) {
        this.node = node;
    }

    public int getId(){
        return node.getId();
    }

    public void SetDelivery(float deliveryTime){
        this.deliveryTime = deliveryTime;
    }
    
}
