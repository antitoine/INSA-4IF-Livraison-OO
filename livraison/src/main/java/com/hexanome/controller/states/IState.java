package com.hexanome.controller.states;

import com.hexanome.model.Delivery;
import com.hexanome.model.Node;

/**
 * @author antoine
 * \todo TODO
 */
public interface IState {

    /**
     * Click on button to load a new map from file
     */
    public void btnLoadMap();

    /**
     * Click on button to load a new planning from file
     */
    public void btnLoadPlanning();

    /**
     * Click on button to compute the best route to make deliveries
     */
    public void btnGenerateRoute();

    /**
     * Click on button to cancel an loading action
     */
    public void btnCancel();

    /**
     * Left click pressed on a delivery
     * @param delivery The delivery clicked
     */
    public void leftClickPressedOnDelivery(Delivery delivery);

    /**
     * Left click released on another delivery
     * @param delivery another delivery
     */
    public void leftClickReleased(Delivery delivery);

    /**
     * Simple click on a delivery
     * @param delivery the delivery clicked
     */
    public void clickOnDelivery(Delivery delivery);

    /**
     * Simple click somewhere else (not a interactive point)
     */
    public void clickSomewhereElse();

    /**
     * Click on button to close the pop over
     */
    public void closePopOver();

    /**
     * Click on an empty node (not a delivery or warehouse node)
     * @param node The empty node clicked
     */
    public void clickOnEmptyNode(Node node);

    /**
     * Click on button to add a new delivery
     * @param node The node to deliver
     * @param previousDelivery the delivery witch is before this one
     */
    public void btnAddDelivery(Node node, Delivery previousDelivery);

    /**
     * Click on button to remove a delivery
     */
    public void btnRemoveDelivery(Delivery delivery);

    /**
     * Click on a specific node : the warehouse
     */
    public void clickOnWarehouse(Node warehouse);

    /**
     * Click on button to close the current map already loaded
     */
    public void btnCloseMap();

    /**
     * Click on button to clear the current planning already loaded
     */
    public void btnClearPlanning();
}
