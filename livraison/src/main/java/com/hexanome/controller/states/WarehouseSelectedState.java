/**
 * 
 */
package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.UIManager;
import com.hexanome.model.Delivery;
import com.hexanome.model.Node;

/**
 * This class represents the logic state when warehouse is selected 
 * on the map
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class WarehouseSelectedState extends SelectionsStates {

    private static WarehouseSelectedState warehouseSelectedState = null;

    private WarehouseSelectedState(){
        // Nothing to do here
    }

    /**
     * Returns the instance of the SwapDeliveriesState,
     * it is a singleton
     * @return The instance of SwapDeliveriesState
     */
    public static WarehouseSelectedState getInstance() {
        if(warehouseSelectedState == null)
        {
            warehouseSelectedState = new WarehouseSelectedState();
        }
        return warehouseSelectedState;
    }


    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void clickOnDelivery(Delivery delivery) {
        // Hide current pop over
        UIManager.getInstance().getMainWindow().getMapView().hidePopOver();
        // Show new popover
        UIManager.getInstance().getMainWindow().getMapView().showPopOver(delivery.getNode());
        // Jump to DeliverySelectedState
        ContextManager.getInstance().setCurrentState(DeliverySelectedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnEmptyNode()
     */
    @Override
    public void clickOnEmptyNode(Node node) {
        // Hide current pop over
        UIManager.getInstance().getMainWindow().getMapView().hidePopOver();
        // Show new popover
        UIManager.getInstance().getMainWindow().getMapView().showPopOver(node);
        // Jump to EmptyNodeSelectedState
        ContextManager.getInstance().setCurrentState(EmptyNodeSelectedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickSomewhereElse()
     */
    @Override
    public void clickSomewhereElse() {
        // Hide current pop over
        UIManager.getInstance().getMainWindow().getMapView().hidePopOver();
        // Jump to NothingSelectedState
        ContextManager.getInstance().setCurrentState(NothingSelectedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#closePopOver()
     */
    @Override
    public void closePopOver() {
        // \todo TODO
    }

    /**
     * Returns the string describing the state, used for debug only
     * @return a string describing the state
     */
    @Override
    public String toString() {
        return "WarehouseSelectedState"; //To change body of generated methods, choose Tools | Templates.
    }

}
