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
            ModelManager.getInstance().getPlanning().swapDeliveries(
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
            ModelManager.getInstance().getPlanning().swapDeliveries(
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
