/**
 * 
 */
package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.command.RemoveDeliveryCommand;
import com.hexanome.model.Delivery;
import com.hexanome.model.Node;

/**
 * @author antitoine
 * \todo TODO
 */
public class NothingSelectedState extends SelectionsStates {

    private static NothingSelectedState nothingSelectedState = null;

    private NothingSelectedState(){
        // Nothing to do here
    }

    /**
     * Returns the instance of the NothingSelectedState,
     * it is a singleton
     * @return The instance of NothingSelectedState
     */
    public static NothingSelectedState getInstance() {
        if(nothingSelectedState == null)
        {
            nothingSelectedState = new NothingSelectedState();
        }
        return nothingSelectedState;
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void clickOnDelivery(Delivery delivery) {
        // We jump to deliverySelected state
        ContextManager.getInstance().setCurrentState(DeliverySelectedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnEmptyNode()
     */
    @Override
    public void clickOnEmptyNode(Node node) {
        // We jump to emptyNodeSelected state
        ContextManager.getInstance().setCurrentState(EmptyNodeSelectedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnWarehouse(com.hexanome.model.Node)
     */
    @Override
    public void clickOnWarehouse(Node warehouse) {
        // We jump to warehouseSelected state
        ContextManager.getInstance().setCurrentState(WarehouseSelectedState.getInstance());
    }
    

}
