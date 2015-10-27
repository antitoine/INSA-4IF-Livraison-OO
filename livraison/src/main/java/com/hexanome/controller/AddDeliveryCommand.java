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
     * Construct a new AddDeliveryCommand to add a new delivery to the planning
     * @param delivery
     * @param prevDelivery
     * @param timeSlot
     */
    public AddDeliveryCommand(Delivery delivery, Delivery prevDelivery, TimeSlot timeSlot) {
        this.delivery = delivery;
        this.prevDelivery = prevDelivery;
        this.timeSlot = timeSlot;
    }
    /**
     * Execute the command
     * @see ICommand
     * @return 
     */
    @Override
    public ICommand execute() {
        ModelManager.getInstance().getPlanning().addDelivery(delivery, prevDelivery, timeSlot);
        return this;
    }
    
    /**
     * Reverse execution of the command executing the exact opposite command
     * @see ICommand
     * @return 
     */
    @Override
    public ICommand reverse() {
        ModelManager.getInstance().getPlanning().removeDelivery(delivery);
        return this;
    }
    
}
