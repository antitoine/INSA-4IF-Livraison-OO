package com.hexanome.controller;

import com.hexanome.model.Delivery;
import com.hexanome.model.TimeSlot;

/**
 * This class represent the action of adding a delivery to the planning
 * @author paul
 * @see ICommand
 */
public class AddDeliveryCommand implements ICommand {
    private Delivery delivery;
    private Delivery prevDelivery;
    private TimeSlot timeSlot;
    /**
     * Construct a new AddDeliveryCommand to add a new delivery to the planning
     * @param delivery
     *      Delivery to add
     * @param prevDelivery
     *      Delivery preceding the delivery to add
     * @param timeSlot
     *      timeSlot of the delivery to add
     */
    public AddDeliveryCommand(Delivery delivery, Delivery prevDelivery, TimeSlot timeSlot) {
        this.delivery = delivery;
        this.prevDelivery = prevDelivery;
        this.timeSlot = timeSlot;
    }
    /**
     * Execute the command by adding a delivery to the planning
     * @see ICommand
     * @return 
     */
    @Override
    public ICommand execute() {
        if(ModelManager.getInstance().getPlanning() != null) {
        ModelManager.getInstance().getPlanning().addDelivery(delivery, prevDelivery, timeSlot);
        } else {
            // \todo treat error case
        }
        return this;
    }
    
    /**
     * Reverse execution of the command by removing the delivery from the planning
     * @see ICommand
     * @return 
     */
    @Override
    public ICommand reverse() {
        if(ModelManager.getInstance().getPlanning() != null) {
        ModelManager.getInstance().getPlanning().removeDelivery(delivery);
        } else {
            // \todo treat error case
        }
        return this;
    }
    
}
