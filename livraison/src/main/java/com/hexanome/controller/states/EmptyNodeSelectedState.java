/**
 * 
 */
package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.model.Delivery;
import com.hexanome.model.Node;

/**
 * @author antitoine
 * \todo TODO
 */
public class EmptyNodeSelectedState extends SelectionsStates {

    private static EmptyNodeSelectedState emptyNodeSelectedState = null;

    private EmptyNodeSelectedState(){
        // Nothing to do here
    }

    /**
     * Returns the instance of the EmptyNodeSelectedState,
     * it is a singleton
     * @return The instance of EmptyNodeSelectedState
     */
    public static EmptyNodeSelectedState getInstance() {
        if(emptyNodeSelectedState == null)
        {
            emptyNodeSelectedState = new EmptyNodeSelectedState();
        }
        return emptyNodeSelectedState;
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnRemoveDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void btnRemoveDelivery(Delivery delivery) {
        // \todo TODO
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void clickOnDelivery(Delivery delivery) {
        // \todo TODO
        // We jump to DeliverySelectedState
        ContextManager.getInstance().setCurrentState(DeliverySelectedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnEmptyNode()
     */
    @Override
    public void clickOnEmptyNode(Node node) {
        // \todo TODO
        //removeMeLater: Update selection model ?
        // Note : don't change state cause it's the same state but maybe with another node
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickSomewhereElse()
     */
    @Override
    public void clickSomewhereElse() {
        // \todo TODO
        // We jump to NothingSelectedState
        ContextManager.getInstance().setCurrentState(NothingSelectedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnWarehouse(com.hexanome.model.Node)
     */
    @Override
    public void clickOnWarehouse(Node warehouse) {
        // \todo TODO
        // We jump to WarehouseSelectedState
        ContextManager.getInstance().setCurrentState(WarehouseSelectedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#closePopOver()
     */
    @Override
    public void closePopOver() {
        // \todo TODO
    }

}
