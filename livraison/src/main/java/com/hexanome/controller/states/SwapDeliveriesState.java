/**
 * 
 */
package com.hexanome.controller.states;

import com.hexanome.model.Delivery;

/**
 * @author antitoine
 * \todo TODO
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
    public void leftClickReleased(Delivery delivery) {
        // \todo TODO
    }

}
