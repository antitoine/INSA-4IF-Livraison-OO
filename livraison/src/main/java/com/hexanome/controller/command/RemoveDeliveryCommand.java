package com.hexanome.controller.command;

import com.hexanome.controller.ModelManager;
import com.hexanome.model.Delivery;
import com.hexanome.model.Node;
import com.hexanome.model.TimeSlot;

/**
 * This class represent the action of removing a delivery from the planning
 *
 * @author paul
 * @see ICommand
 */


public class RemoveDeliveryCommand implements ICommand {

    private Delivery delivery;
    private Node nodePreviousDelivery;
    private TimeSlot timeSlot;
    private Node node;

    /**
     * Constructs a new instance of a RemoveDeliveryCommand
     *
     * @param delivery Delivery to be removed
     */
    public RemoveDeliveryCommand(Delivery delivery) {
        this.delivery = delivery;
        this.timeSlot = delivery.getTimeSlot();
        this.node = delivery.getNode();
    }

    /**
     * Execute the command by removing the delivery
     *
     * @return
     * @see ICommand
     */
    @Override
    public ICommand execute() {
        if (ModelManager.getInstance().getPlanning() != null) {
            nodePreviousDelivery = ModelManager.getInstance().getPlanning().getNodePreviousDelivery(delivery);
            ModelManager.getInstance().getPlanning().removeDelivery(delivery);
        } else {
            // \todo treat error case
        }
        return this;
    }

    /**
     * Reverse command execution by bringing back the removed delivery
     *
     * @return
     * @see ICommand
     */
    @Override
    public ICommand reverse() {
        if (ModelManager.getInstance().getPlanning() != null) {
            delivery = ModelManager.getInstance().getPlanning().addDelivery(node, nodePreviousDelivery, timeSlot);
        } else {
            // \todo treat error case
        }
        return this;
    }
}
