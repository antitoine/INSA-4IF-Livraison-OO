/**
 * 
 */
package com.hexanome.controller.states;

import java.io.File;

import com.hexanome.model.Delivery;
import com.hexanome.model.Node;
import com.hexanome.model.TimeSlot;

/**
 * This class represents the default logic state extended by all
 * other states
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public abstract class DefaultState implements IState {

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadMap()
     */
    @Override
    public void btnLoadMap() {
        System.err.println("btnLoadMap In DefaultState"); //DEBUG
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadPlanning()
     */
    @Override
    public void btnLoadPlanning() {
        System.err.println("btnLoadPlanning In DefaultState"); //DEBUG
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnGenerateRoute()
     */
    @Override
    public void btnGenerateRoute() {
        System.err.println("btnGenerateRoute In DefaultState"); //DEBUG
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCancel()
     */
    @Override
    public void btnCancel() {
        System.err.println("btnCancel In DefaultState"); //DEBUG
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnValidateFile(java.io.File)
     */
    @Override
    public void btnValidateFile(File file) {
        System.err.println("btnValidateFile In DefaultState"); //DEBUG
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#leftClickPressedOnDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void leftClickPressedOnDelivery() {
        System.err.println("leftClickPressedOnDelivery In DefaultState"); //DEBUG
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#leftClickReleased(com.hexanome.model.Delivery)
     */
    @Override
    public void leftClickReleased(Delivery sourceDelivery, Delivery targetDelivery) {
        System.err.println("leftClickReleased In DefaultState"); //DEBUG
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void clickOnDelivery(Delivery delivery) {
        System.err.println("clickOnDelivery In DefaultState"); //DEBUG
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickSomewhereElse()
     */
    @Override
    public void clickSomewhereElse() {
        System.err.println("clickSomewhereElse In DefaultState"); //DEBUG
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#closePopOver()
     */
    @Override
    public void closePopOver() {
        System.err.println("closePopOver In DefaultState"); //DEBUG
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnEmptyNode()
     */
    @Override
    public void clickOnEmptyNode(Node node) {
        System.err.println("clickOnEmptyNode In DefaultState"); //DEBUG
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnAddDelivery(com.hexanome.model.Node)
     */
    @Override
    public void btnAddDelivery(Node node, Node previousDeliveryNode, TimeSlot timeSlot) {
        System.err.println("btnAddDelivery In DefaultState"); //DEBUG
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnRemoveDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void btnRemoveDelivery(Delivery delivery) {
        System.err.println("btnRemoveDelivery In DefaultState");  //DEBUG
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnWarehouse(com.hexanome.model.Node)
     */
    @Override
    public void clickOnWarehouse(Node warehouse) {
        System.err.println("clickOnWarehouse In DefaultState");  //DEBUG
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCloseMap()
     */
    @Override
    public void btnCloseMap() {
        System.err.println("btnCloseMap In DefaultState");  //DEBUG
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnClearPlanning()
     */
    @Override
    public void btnClearPlanning() {
        System.err.println("btnClearPlanning In DefaultState");  //DEBUG
        // Nothing to do here
    }
    
    /**
     * Returns the string describing the state, used for debug only
     * @return a string describing the state
     */
    @Override
    public String toString() {
        return "DefaultState"; 
    }

}
