package com.hexanome.controller.command;

import com.hexanome.controller.ModelManager;
import com.hexanome.model.Delivery;
import com.hexanome.model.Node;
import com.hexanome.model.TimeSlot;

/**
 * This class represent the action of adding a delivery to the planning
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 * @see ICommand
 */
public class AddDeliveryCommand implements ICommand {

    private Node node;
    private Node nodePreviousDelivery;
    private TimeSlot timeSlot;
    private Delivery delivery;

    /**
     * Construct a new AddDeliveryCommand to add a new delivery to the planning
     *
     * @param node Delivery to add
     * @param nodePreviousDelivery The node with the delivery preceding the 
     * delivery to add
     * @param timeSlot The time slot of the new delivery to add
     */
    public AddDeliveryCommand(Node node, Node nodePreviousDelivery, TimeSlot timeSlot) {
        this.node = node;
        this.nodePreviousDelivery = nodePreviousDelivery;
        this.timeSlot = timeSlot;        
        this.delivery = null;
    }

    /**
     * Execute the command by adding a delivery to the planning
     *
     * @see ICommand
     * @return
     */
    @Override
    public void execute() {
        if (ModelManager.getInstance().getPlanning() != null) {

            delivery = ModelManager.getInstance()
                    .getPlanning()
                    .addDelivery(node, nodePreviousDelivery, timeSlot);
        }
    }

    /**
     * Reverse execution of the command by removing the delivery from the
     * planning
     *
     * @see ICommand
     * @return
     */
    @Override
    public void reverse() {
        if (ModelManager.getInstance().getPlanning() != null && delivery != null) {
            ModelManager.getInstance().getPlanning().removeDelivery(delivery);
        }
    }

}
