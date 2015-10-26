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
    // removeMeLater : delivery must have its own id cause swaping deliveries is not equivalent to swaping nodes
    private int id; 
    
    /**
     * 
     * @param node 
     */
    public Delivery(int id, Node node) {
        this.node = node;
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void SetDelivery(float deliveryTime){
        this.deliveryTime = deliveryTime;
    }
    
}
