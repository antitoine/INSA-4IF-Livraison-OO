/**
 * 
 */
package com.hexanome.controller.states;

import com.hexanome.model.Delivery;

/**
 * This class represents the state when to deliveries are being 
 * swaped
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class SwapDeliveriesState extends DefaultState {

    private static SwapDeliveriesState swapDeliveriesState = null;

    private SwapDeliveriesState(){
        // Nothing to do here
    }

    /**
     * Returns the instance of the SwapDeliveriesState,
     * it is a singleton
     * @return The instance of SwapDeliveriesState
     */
    public static SwapDeliveriesState getInstance() {
        if(swapDeliveriesState == null)
        {
            swapDeliveriesState = new SwapDeliveriesState();
        }
        return swapDeliveriesState;
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#leftClickReleased(com.hexanome.model.Delivery)
     */
    @Override
    public void leftClickReleased(Delivery sourceDelivery, Delivery targetDelivery) {
        // \todo TODO
    }

    /**
     * Returns the string describing the state, used for debug only
     * @return a string describing the state
     */
    @Override
    public String toString() {
        return "SwapDeliveriesState"; //To change body of generated methods, choose Tools | Templates.
    }
    
}
