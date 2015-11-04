package com.hexanome.controller.command;

import com.hexanome.controller.ModelManager;
import com.hexanome.model.Delivery;

/**
 * This class represent the action of swaping two deliveries in the planning
 * @author paul
 * @see ICommand
 */
public class SwapDeliveriesCommand implements ICommand {
    private Delivery firstDelivery;
    private Delivery secondDelivery;
    /**
     * Construct a new instance of SwapDeliveriesCommand
     * @param firstDelivery The first delivery to swap.
     * @param secondDelivery The second delivery to swap
     */
    public SwapDeliveriesCommand(Delivery firstDelivery, Delivery secondDelivery) {
        this.firstDelivery = firstDelivery;
        this.secondDelivery = secondDelivery;
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
            ModelManager.getInstance().getPlanning().swapDeliveries(firstDelivery, secondDelivery);
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
            ModelManager.getInstance().getPlanning().swapDeliveries(secondDelivery, firstDelivery);
        }
        else
        {
            // \todo treat error case
        }
        return this;
    }
    
}
