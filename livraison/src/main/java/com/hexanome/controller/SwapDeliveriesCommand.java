package com.hexanome.controller;

/**
 * This class represent the action of swaping two deliveries in the planning
 * @author paul
 * @see ICommand
 */
public class SwapDeliveriesCommand implements ICommand {
    private int firstDeliveryId;
    private int secondDeliveryId;
    /**
     * Construct a new instance of SwapDeliveriesCommand
     * @param firstDeliveryId
     *      Id of the first delivery to swap
     * @param secondDeliveryId 
     *      Id of the second delivery to swap
     */
    public SwapDeliveriesCommand(int firstDeliveryId, int secondDeliveryId) {
        this.firstDeliveryId = firstDeliveryId;
        this.secondDeliveryId = secondDeliveryId;
    }
    
    /**
     * Execute the command swaping first delivery with the second
     * @return 
     * @see ICommand
     */
    @Override
    public ICommand execute() {
        if(ModelManager.getInstance().getPlanning()!= null)
        {
            ModelManager.getInstance().getPlanning().swapDeliveries(
                    ModelManager.getInstance().getPlanning().getDeliveryById(firstDeliveryId),
                    ModelManager.getInstance().getPlanning().getDeliveryById(secondDeliveryId)
            );
        }
        else
        {
            // \todo treat error case
        }
        return this;
    }
    /**
     * Reverse execution of a command swaping again the two deliveries 
     * @return 
     * @see ICommand
     */
    @Override
    public ICommand reverse() {
        if(ModelManager.getInstance().getPlanning()!= null)
        {
            ModelManager.getInstance().getPlanning().swapDeliveries(
                    ModelManager.getInstance().getPlanning().getDeliveryById(secondDeliveryId),
                    ModelManager.getInstance().getPlanning().getDeliveryById(firstDeliveryId)
            );
        }
        else
        {
            // \todo treat error case
        }
        return this;
    }
    
}
