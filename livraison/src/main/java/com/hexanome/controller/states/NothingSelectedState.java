/**
 * 
 */
package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.UIManager;
import com.hexanome.model.Delivery;
import com.hexanome.model.Node;

/**
 * This class represents the logic state when a map and a planning
 * are loaded but no node is selected
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
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
        // Show new pop over assuming none is currently open (current state assumption)
        UIManager.getInstance().getMainWindow().getMapView().showPopOver(delivery.getNode());
        // Jump to DeliverySelectedState
        ContextManager.getInstance().setCurrentState(DeliverySelectedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnEmptyNode()
     */
    @Override
    public void clickOnEmptyNode(Node node) {
        // Show new pop over assuming none is currently open (current state assumption)
        UIManager.getInstance().getMainWindow().getMapView().showPopOver(node);
        // Jump to EmptyNodeSelectedState
        ContextManager.getInstance().setCurrentState(EmptyNodeSelectedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnWarehouse(com.hexanome.model.Node)
     */
    @Override
    public void clickOnWarehouse(Node warehouse) {
        // Show new pop over assuming none is currently open (current state assumption)
        UIManager.getInstance().getMainWindow().getMapView().showPopOver(warehouse);        
        // Jump to WarehouseSelectedState
        ContextManager.getInstance().setCurrentState(WarehouseSelectedState.getInstance());
    }

    @Override
    public String toString() {
        return "NothingSelectedState"; //To change body of generated methods, choose Tools | Templates.
    }
    
}
