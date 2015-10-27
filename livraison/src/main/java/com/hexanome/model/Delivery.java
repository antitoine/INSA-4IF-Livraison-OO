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
    /**
     * 
     */
    public Node getNode() {
        return node;
    }

    @Override
    public String toString() {
        return String.format("[Delivery]{\n"
                + "id:%s,\n"
                + "deliveryTime:%s,\n"
                + "node:%s\n"
                + "}", id, deliveryTime, node.toString());
    }
    
}
