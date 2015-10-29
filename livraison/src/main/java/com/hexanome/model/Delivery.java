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
    
    private TimeSlot timeSlot;
    
    /**
     * 
     * @param id
     * @param node 
     * @param timeSlot 
     */
    public Delivery(int id, Node node) {
        this.id = id;
        this.node = node;
        this.node.attachDelivery(this);
    }

    public int getId(){
        return id;
    }

    public void setDelivery(float deliveryTime){
        this.deliveryTime = deliveryTime;
    }
    /**
     * 
     */
    public Node getNode() {
        return node;
    }
    
    void attachTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
    
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    @Override
    public String toString() {
        return String.format(""
                + "{\n"
                + "id:%s,\n"
                + "deliveryTime:%s,\n"
                + "node:%s\n"
                + "}", id, deliveryTime, node.toString());
    }
    
}
