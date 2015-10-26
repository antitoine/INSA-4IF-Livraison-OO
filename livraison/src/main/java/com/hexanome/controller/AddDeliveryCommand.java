/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.controller;

import com.hexanome.model.Delivery;
import com.hexanome.model.TimeSlot;

/**
 *
 * @author paul
 */
public class AddDeliveryCommand implements ICommand {
    private Delivery delivery;
    private Delivery prevDelivery;
    private TimeSlot timeSlot;
    /**
     * 
     * @param delivery
     * @param prevDelivery
     * @param timeSlot
     */
    public AddDeliveryCommand(Delivery delivery, Delivery prevDelivery, TimeSlot timeSlot) {
        this.delivery = delivery;
        this.prevDelivery = prevDelivery;
        this.timeSlot = timeSlot;
    }
    
    @Override
    public ICommand execute() {
        ModelManager.getInstance().getPlanning().addDelivery(delivery, prevDelivery, timeSlot);
        return this;
    }
    
    @Override
    public ICommand reverse() {
        ModelManager.getInstance().getPlanning().removeDelivery(delivery);
        return this;
    }
    
}
