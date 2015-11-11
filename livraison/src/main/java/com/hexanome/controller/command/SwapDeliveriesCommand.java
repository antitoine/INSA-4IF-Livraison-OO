package com.hexanome.controller.command;

import com.hexanome.controller.ModelManager;
import com.hexanome.model.Delivery;

/**
 * This class represents the action of swaping two deliveries in the planning.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 * @see ICommand
 */
public class SwapDeliveriesCommand implements ICommand {

    /**
     * The first delivery to swap with another delivery.
     */
    private Delivery firstDelivery;

    /**
     * The second delivery to swap with another delivery.
     */
    private Delivery secondDelivery;

    /**
     * Constructs a new instance of SwapDeliveriesCommand.
     *
     * @param firstDelivery The first delivery to swap.
     * @param secondDelivery The second delivery to swap.
     */
    public SwapDeliveriesCommand(Delivery firstDelivery, Delivery secondDelivery) {
        this.firstDelivery = firstDelivery;
        this.secondDelivery = secondDelivery;
    }

    /**
     * Executes the command swaping first delivery with the second.
     *
     * @see ICommand
     */
    @Override
    public void execute() {
        if (ModelManager.getInstance().getPlanning() != null) {
            ModelManager.getInstance().getPlanning().swapDeliveries(
                    firstDelivery,
                    secondDelivery
            );
        }
    }

    /**
     * Reverses execution of a command swaping again the two deliveries.
     *
     * @return
     * @see ICommand
     */
    @Override
    public void reverse() {
        if (ModelManager.getInstance().getPlanning() != null) {
            ModelManager.getInstance().getPlanning().swapDeliveries(
                    secondDelivery,
                    firstDelivery
            );
        }
    }

}
