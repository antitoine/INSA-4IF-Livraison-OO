package com.hexanome.model;

/**
 * This class represents a delivery point.
 * @author paul
 */

public class Delivery {
    /**
     * The time when the delivery will be executed.
     */
    private float deliveryTime ;
    /**
     * The node where this delivery will be executed.
     */
    private Node node;
    // removeMeLater : delivery must have its own id cause swaping deliveries is not equivalent to swaping nodes
    /**
     * The id of the delivery.
     */
    private int id; 
    /**
     * The time slot to which the delivery belongs.
     */
    private TimeSlot timeSlot;
    
    /**
     * Constructor.
     * @param id the id of the delivery
     * @param node the node where the delivery will be attached
     */
    public Delivery(int id, Node node) {
        this.id = id;
        this.node = node;
        this.node.attachDelivery(this);
    }

    /**
     * Returns the id of the delivery.
     * @return the id
     */
    public int getId(){
        return id;
    }

    /**
     * Set the delivery time.
     * @param deliveryTime when the delivery will be executed.
     */
    public void setDelivery(float deliveryTime){
        this.deliveryTime = deliveryTime;
    }
    
    /**
     * Returns the node associated with the delivery.
     * @return the node
     */
    public Node getNode() {
        return node;
    }
    
    /**
     * Associates the delivery with a given time slot.
     * @param timeSlot the time slot attached to the delivery
     */
    void attachTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
    
    /**
     * Returns the time slot attached to the delivery.
     * @return the time slot.
     */
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
