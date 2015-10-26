package com.hexanome.controller;

import com.hexanome.model.Delivery;
import com.hexanome.model.TimeSlot;

/**
 *
 * @author paul
 */
public class RemoveDeliveryCommand implements ICommand {
    private Delivery delivery;
    private Delivery prevDelivery;
    private TimeSlot timeSlot;
    /**
     * 
     * @param delivery
     * @param prevDelivery
     * @param timeSlot
     */
    public RemoveDeliveryCommand(Delivery delivery, Delivery prevDelivery, TimeSlot timeSlot) {
        this.delivery = delivery;
        this.prevDelivery = prevDelivery;
        this.timeSlot = timeSlot;
    }
    
    @Override
    public ICommand execute() {
        ModelManager.getInstance().getPlanning().removeDelivery(delivery);
        return this;
    }
    
    @Override
    public ICommand reverse() {
        ModelManager.getInstance().getPlanning().addDelivery(delivery, prevDelivery, timeSlot);
        return this;
    }
}