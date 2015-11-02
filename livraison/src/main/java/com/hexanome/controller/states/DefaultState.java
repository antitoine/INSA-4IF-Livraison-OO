/**
 * 
 */
package com.hexanome.controller.states;

import java.io.File;

import com.hexanome.model.Delivery;
import com.hexanome.model.Node;

/**
 * @author antitoine
 * \todo TODO
 */
public abstract class DefaultState implements IState {

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadMap()
     */
    @Override
    public void btnLoadMap() {
        System.out.println("btnLoadMap In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadPlanning()
     */
    @Override
    public void btnLoadPlanning() {
        System.out.println("btnLoadPlanning In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnGenerateRoute()
     */
    @Override
    public void btnGenerateRoute() {
        System.out.println("btnGenerateRoute In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCancel()
     */
    @Override
    public void btnCancel() {
        System.out.println("btnCancel In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnValidateFile(java.io.File)
     */
    @Override
    public void btnValidateFile(File file) {
        System.out.println("btnValidateFile In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#leftClickPressedOnDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void leftClickPressedOnDelivery(Delivery delivery) {
        System.out.println("leftClickPressedOnDelivery In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#leftClickReleased(com.hexanome.model.Delivery)
     */
    @Override
    public void leftClickReleased(Delivery delivery) {
        System.out.println("leftClickReleased In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void clickOnDelivery(Delivery delivery) {
        System.out.println("clickOnDelivery In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickSomewhereElse()
     */
    @Override
    public void clickSomewhereElse() {
        System.out.println("clickSomewhereElse In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#closePopOver()
     */
    @Override
    public void closePopOver() {
        System.out.println("closePopOver In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnEmptyNode()
     */
    @Override
    public void clickOnEmptyNode(Node node) {
        System.out.println("clickOnEmptyNode In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnAddDelivery(com.hexanome.model.Node)
     */
    @Override
    public void btnAddDelivery(Node node, Delivery previousDelivery) {
        System.out.println("btnAddDelivery In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnRemoveDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void btnRemoveDelivery(Delivery delivery) {
        System.out.println("btnRemoveDelivery In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnWarehouse(com.hexanome.model.Node)
     */
    @Override
    public void clickOnWarehouse(Node warehouse) {
        System.out.println("clickOnWarehouse In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCloseMap()
     */
    @Override
    public void btnCloseMap() {
        System.out.println("btnCloseMap In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnClearPlanning()
     */
    @Override
    public void btnClearPlanning() {
        System.out.println("btnClearPlanning In DefaultState");
        // Nothing to do here
    }

    @Override
    public String toString() {
        return "DefaultState";
    }

}
