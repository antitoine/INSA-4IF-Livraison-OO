package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.UIManager;
import com.hexanome.controller.command.AddDeliveryCommand;
import com.hexanome.model.Delivery;
import com.hexanome.model.Node;
import com.hexanome.model.TimeSlot;

/**
 * This class represents the logic state when an emptyNode is
 * selected on the map.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class EmptyNodeSelectedState extends SelectionsStates {

    /** The unique instance of this class. */
    private static EmptyNodeSelectedState emptyNodeSelectedState = null;

    private EmptyNodeSelectedState() {
        // Nothing to do here
    }

    /**
     * Returns the instance of the EmptyNodeSelectedState, which is a singleton.
     *
     * @return The instance of EmptyNodeSelectedState.
     */
    public static EmptyNodeSelectedState getInstance() {
        if (emptyNodeSelectedState == null) {
            emptyNodeSelectedState = new EmptyNodeSelectedState();
        }
        return emptyNodeSelectedState;
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void clickOnDelivery(Delivery delivery) {
        UIManager.getInstance().getMainWindow().getMapView().hidePopOver();
        UIManager.getInstance().getMainWindow().getMapView().showPopOver(delivery.getNode());
        ContextManager.getInstance().setCurrentState(DeliverySelectedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickSomewhereElse()
     */
    @Override
    public void clickSomewhereElse() {
        UIManager.getInstance().getMainWindow().getMapView().hidePopOver();
        ContextManager.getInstance().setCurrentState(NothingSelectedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnAddDelivery(com.hexanome.model.Node)
     */
    @Override
    public void btnAddDelivery(Node node, Node previousDeliveryNode, TimeSlot timeSlot) {
        UIManager.getInstance().getMainWindow().getMapView().hidePopOver();
        AddDeliveryCommand adc = new AddDeliveryCommand(node, previousDeliveryNode, timeSlot);
        ContextManager.getInstance().executeCommand(adc);
        ContextManager.getInstance().setCurrentState(DeliverySelectedState.getInstance());
        UIManager.getInstance().getMainWindow().repositionToLatestPosition();
        UIManager.getInstance().getMainWindow().getMapView().showPopOver(node);
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnWarehouse(com.hexanome.model.Node)
     */
    @Override
    public void clickOnWarehouse(Node warehouse) {
        UIManager.getInstance().getMainWindow().getMapView().hidePopOver();
        ContextManager.getInstance().setCurrentState(WarehouseSelectedState.getInstance());
    }

    /**
     * Returns the string describing the state, used for debug only
     *
     * @return a string describing the state
     */
    @Override
    public String toString() {
        return "EmptyNodeSelectedState";
    }
}
