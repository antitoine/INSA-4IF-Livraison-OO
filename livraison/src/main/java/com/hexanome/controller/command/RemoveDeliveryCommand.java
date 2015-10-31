package com.hexanome.controller.command;

import com.hexanome.controller.ModelManager;
import com.hexanome.controller.command.ICommand;
import com.hexanome.model.Delivery;
import com.hexanome.model.TimeSlot;

/**
 *  This class represent the action of removing a delivery from the planning
 * @author paul
 * @see ICommand
 */
public class RemoveDeliveryCommand implements ICommand {
    private Delivery delivery;
    private Delivery prevDelivery;
    private TimeSlot timeSlot;
    /**
     * Constructs a new instance of a RemoveDeliveryCommand
     * @param delivery
     *      Delivery to be removed
     * @param prevDelivery
     *      Delivery preceding the delivery to be removed
     * @param timeSlot
     *      timeSlot of the delivery to be removed
     */
    public RemoveDeliveryCommand(Delivery delivery, Delivery prevDelivery) {
        this.delivery = delivery;
        this.prevDelivery = prevDelivery;
        this.timeSlot = delivery.getTimeSlot();
    }
    
    /**
     * Execute the command by removing the delivery
     * @return 
     * @see ICommand
     */
    @Override
    public ICommand execute() {
        if(ModelManager.getInstance().getPlanning() != null) {
            ModelManager.getInstance().getPlanning().removeDelivery(delivery);
        } else {
            // \todo treat error case
        }
        return this;
    }
    /**
     * Reverse command execution by bringing back the removed delivery
     * @return 
     * @see ICommand
     */
    @Override
    public ICommand reverse() {
        if(ModelManager.getInstance().getPlanning() != null) {
        ModelManager.getInstance().getPlanning().addDelivery(delivery, prevDelivery, timeSlot);
        } else {
            // \todo treat error case
        }
        return this;
    }
}
