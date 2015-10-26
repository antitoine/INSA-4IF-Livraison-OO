/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.controller;

/**
 *
 * @author paul
 */
public class SwapDeliveriesCommand implements ICommand {
    private int firstDeliveryId;
    private int secondDeliveryId;
    /**
     * 
     */
    public SwapDeliveriesCommand(int firstDeliveryId, int secondDeliveryId) {
        this.firstDeliveryId = firstDeliveryId;
        this.secondDeliveryId = secondDeliveryId;
    }
    
    @Override
    public ICommand execute() {
        if(ModelManager.getInstance().getRoute() != null)
        {
            ModelManager.getInstance().getRoute().swapDeliveries(
                    ModelManager.getInstance().getPlanning().getDeliveryById(firstDeliveryId),
                    ModelManager.getInstance().getPlanning().getDeliveryById(secondDeliveryId));
        }
        else
        {
            // \todo treat error case
        }
        return this;
    }
    
    @Override
    public ICommand reverse() {
        if(ModelManager.getInstance().getRoute() != null)
        {
            ModelManager.getInstance().getRoute().swapDeliveries(
                    ModelManager.getInstance().getPlanning().getDeliveryById(secondDeliveryId),
                    ModelManager.getInstance().getPlanning().getDeliveryById(firstDeliveryId));
        }
        else
        {
            // \todo treat error case
        }
        return this;
    }
    
}
