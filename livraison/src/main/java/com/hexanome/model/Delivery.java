package com.hexanome.model;

/**
 * This class represents a delivery point.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class Delivery implements Comparable<Delivery> {

    /**
     * The duration of a delivery.
     */
    private static final float DELIVERY_DURATION = 600f;

    /**
     * The time when the delivery will be executed.
     */
    private float deliveryTime;
    /**
     * The node where this delivery will be executed.
     */
    private Node node;
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
     *
     * @param id the id of the delivery
     * @param node the node where the delivery will be attached
     */
    public Delivery(int id, Node node) {
        this.id = id;
        this.node = node;
        deliveryTime = 0.0f;
        updateNode();
    }

    /**
     * Update the node associated with the current delivery.
     */
    final void updateNode() {
        node.attachDelivery(this);
    }

    /**
     * Returns the id of the delivery.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the effective delivery time
     *
     * @return a float that is the sum in seconds of a timestamp
     */
    public float getDeliveryTime() {
        return deliveryTime;
    }

    /**
     * Set the delivery time.
     *
     * @param deliveryTime when the delivery will be executed.
     */
    void setDeliveryTime(float deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    /**
     * Returns the time whend the delivery is done.
     * @return The end time of the delivery.
     */
    public float getDeliveryEndTime() {
        return deliveryTime + DELIVERY_DURATION;
    }

    /**
     * Returns the node associated with the delivery.
     *
     * @return the node
     */
    public Node getNode() {
        return node;
    }

    /**
     * Associates the delivery with a given time slot.
     *
     * @param timeSlot the time slot attached to the delivery
     */
    void attachTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    /**
     * Returns the time slot attached to the delivery.
     *
     * @return the time slot.
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * Returns the string describing the objet, used for debug only
     *
     * @return a string describing the object
     */
    @Override
    public String toString() {
        return String.format(""
                + "{\n"
                + "id:%s,\n"
                + "deliveryTime:%s,\n"
                + "node:%s\n"
                + "}", id, deliveryTime, node.toString());
    }

    @Override
    public int compareTo(Delivery o) {
        return (int) (this.deliveryTime - o.deliveryTime);
    }
}
