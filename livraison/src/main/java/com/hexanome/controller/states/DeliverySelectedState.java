/**
 *
 */
package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.UIManager;
import com.hexanome.controller.command.RemoveDeliveryCommand;
import com.hexanome.model.Delivery;
import com.hexanome.model.Node;

/**
 * This state represents the logic state when a delivery is selected
 * on the map
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class DeliverySelectedState extends SelectionsStates {

    private static DeliverySelectedState deliverySelectedState = null;

    private DeliverySelectedState() {
        // Nothing to do here
    }

    /**
     * Returns the instance of the DeliverySelectedState,
     * it is a singleton
     *
     * @return The instance of DeliverySelectedState
     */
    public static DeliverySelectedState getInstance() {
        if (deliverySelectedState == null) {
            deliverySelectedState = new DeliverySelectedState();
        }
        return deliverySelectedState;
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickSomewhereElse()
     */
    @Override
    public void clickSomewhereElse() {
        // Hide currently open pop over
        UIManager.getInstance().getMainWindow().getMapView().hidePopOver();
        // Jump to NothingSelectedState
        ContextManager.getInstance().setCurrentState(NothingSelectedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnEmptyNode()
     */
    @Override
    public void clickOnEmptyNode(Node node) {
        // Close the open pop over
        UIManager.getInstance().getMainWindow().getMapView().hidePopOver();
        // Open the new popover
        UIManager.getInstance().getMainWindow().getMapView().showPopOver(node);
        // Jump to EmptyNodeSelectedState
        ContextManager.getInstance().setCurrentState(EmptyNodeSelectedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnRemoveDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void btnRemoveDelivery(Delivery delivery) {
        // Close the open pop over
        UIManager.getInstance().getMainWindow().getMapView().hidePopOver();
        // Create a new instance of RemoveDeliveryCommand
        RemoveDeliveryCommand rmDeliveryCmd = new RemoveDeliveryCommand(delivery);
        // ContextManager is asked to execute the command
        ContextManager.getInstance().executeCommand(rmDeliveryCmd);
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnWarehouse(com.hexanome.model.Node)
     */
    @Override
    public void clickOnWarehouse(Node warehouse) {
        // Hide current pop over
        UIManager.getInstance().getMainWindow().getMapView().hidePopOver();
        // Show new pop over
        UIManager.getInstance().getMainWindow().getMapView().showPopOver(warehouse);
        // Jump to WarehouseSelectedState
        ContextManager.getInstance().setCurrentState(WarehouseSelectedState.getInstance());
    }

    /**
     * Returns the string describing the state, used for debug only
     *
     * @return a string describing the state
     */
    @Override
    public String toString() {
        return "DeliverySelectedState"; //To change body of generated methods, choose Tools | Templates.
    }

}
