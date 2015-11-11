package com.hexanome.controller.command;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.UIManager;
import com.hexanome.controller.states.EmptyNodeSelectedState;
import com.hexanome.model.Delivery;
import com.hexanome.model.Node;
import com.hexanome.model.TimeSlot;

/**
 * This class represents the action of removing a delivery from the planning.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 * @see ICommand
 */
public class RemoveDeliveryCommand implements ICommand {

    /** The delivery to remove. */
    private Delivery delivery;
    
    /** The node that contains the delivery preceding the delivery to remove. */
    private Node nodePreviousDelivery;
    
    /** The time slot of the delivery to remove. */
    private TimeSlot timeSlot;
    
    /** The location of the delivery to remove. */
    private Node node;

    /**
     * Constructs a new instance of a RemoveDeliveryCommand.
     *
     * @param delivery Delivery to be removed.
     */
    public RemoveDeliveryCommand(Delivery delivery) {
        this.delivery = delivery;
        this.timeSlot = delivery.getTimeSlot();
        this.node = delivery.getNode();
    }

    /**
     * Executes the command by removing the delivery.
     * @see ICommand
     */
    @Override
    public void execute() {
        if (ModelManager.getInstance().getPlanning() != null) {
            nodePreviousDelivery = ModelManager.getInstance()
                    .getPlanning().getNodePreviousDelivery(delivery);

            ModelManager.getInstance().getPlanning().removeDelivery(delivery);

            ModelManager.getInstance().getPlanning().notifySubscribers();
            ModelManager.getInstance().getPlanning().getRoute().notifySubscribers();

            ContextManager.getInstance().setCurrentState(
                    EmptyNodeSelectedState.getInstance()
            );
            
            UIManager.getInstance().getMainWindow().repositionToLatestPosition();
            UIManager.getInstance().getMainWindow().getMapView().showPopOver(
                    delivery.getNode()
            );
        }
    }

    /**
     * Reverses command execution by bringing back the removed delivery.
     * @see ICommand
     */
    @Override
    public void reverse() {
        if (ModelManager.getInstance().getPlanning() != null) {
            delivery = ModelManager.getInstance().getPlanning().addDelivery(node, nodePreviousDelivery, timeSlot);
        }
    }
}
