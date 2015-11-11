package com.hexanome.controller.command;

import com.hexanome.controller.ModelManager;
import com.hexanome.model.Delivery;
import com.hexanome.model.Node;
import com.hexanome.model.TimeSlot;

/**
 * This class represents the action of adding a delivery to the planning.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 * @see ICommand
 */
public class AddDeliveryCommand implements ICommand {

    /** The node representing the location of the new delivery to add. */
    private Node node;
    
    /**
     * The node representing the location of the delivery done before the
     * delivery to add.
     */
    private Node nodePreviousDelivery;
    
    /** The time slot of the new delivery to add. */
    private TimeSlot timeSlot;
    
    /** The delivery to add in the planning. */
    private Delivery delivery;

    /**
     * Constructs a new AddDeliveryCommand, in order to add a new delivery 
     * to a planning
     *
     * @param node The location on the map where a new delivery will be added.
     * @param nodePreviousDelivery The node with the delivery preceding the 
     * delivery to add
     * @param timeSlot The time slot of the new delivery to add.
     */
    public AddDeliveryCommand(Node node, Node nodePreviousDelivery, TimeSlot timeSlot) {
        this.node = node;
        this.nodePreviousDelivery = nodePreviousDelivery;
        this.timeSlot = timeSlot;
        this.delivery = null;
    }

    /**
     * Executes the command by adding a delivery to the planning.
     * @see ICommand
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
     * Reverses execution of the command by removing the added delivery from the
     * planning.
     *
     * @see ICommand
     */
    @Override
    public void reverse() {
        if (ModelManager.getInstance().getPlanning() != null && delivery != null) {
            ModelManager.getInstance().getPlanning().removeDelivery(delivery);
        }
    }
}
