package com.hexanome.controller.states;

import java.io.File;

import com.hexanome.model.Delivery;
import com.hexanome.model.Node;
import com.hexanome.model.TimeSlot;

/**
 * This interface defines all the methods that should be implemented
 * by logical states
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
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
     * Click on button to load file
     * @param file
     *      file to load as a map or a planning depending on the state
     */
    public void btnValidateFile(File file);

    /**
     * Left click pressed on a delivery
     */
    public void leftClickPressedOnDelivery();

    /**
     * Left click released on another delivery
     * @param sourceDelivery
     *      Delivery to switch with target delivery
     * @param targetDelivery 
     *      Delivery to switch with sourceDelivery
     */
    public void leftClickReleased(Delivery sourceDelivery, Delivery targetDelivery);

    /**
     * Simple click on a delivery
     * @param delivery 
     *      the delivery clicked
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
     * @param node 
     *      The empty node clicked
     */
    public void clickOnEmptyNode(Node node);

    /**
     * Click on button to add a new delivery
     * @param node 
     *      The node to deliver
     * @param previousDeliveryNode 
     *      The nodewith the previous delivery
     * @param timeSlot 
     *      The time slot of the new delivery
     */
    public void btnAddDelivery(Node node, Node previousDeliveryNode, TimeSlot timeSlot);

    /**
     * Click on button to remove a delivery
     * @param delivery
     *      The delivery to remove
     */
    public void btnRemoveDelivery(Delivery delivery);

    /**
     * Click on a specific node : the warehouse
     * @param warehouse
     *      The warehouse node clicked
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
