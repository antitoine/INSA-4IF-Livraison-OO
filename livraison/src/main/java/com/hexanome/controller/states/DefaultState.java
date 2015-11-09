/**
 * 
 */
package com.hexanome.controller.states;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hexanome.controller.UIManager;
import com.hexanome.model.Delivery;
import com.hexanome.model.Node;
import com.hexanome.model.TimeSlot;
import com.hexanome.view.ConstView;
import com.hexanome.view.MainWindow;

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
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "btnLoadMap In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadPlanning()
     */
    @Override
    public void btnLoadPlanning() {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "btnLoadPlanning In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnGenerateRoute()
     */
    @Override
    public void btnGenerateRoute() {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "btnGenerateRoute In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCancel()
     */
    @Override
    public void btnCancel() {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "btnCancel In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnValidateFile(java.io.File)
     */
    @Override
    public void btnValidateFile(File file) {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "btnValidateFile In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#leftClickPressedOnDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void leftClickPressedOnDelivery() {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "leftClickPressedOnDelivery In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#leftClickReleased(com.hexanome.model.Delivery)
     */
    @Override
    public void leftClickReleased(Delivery sourceDelivery, Delivery targetDelivery) {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "leftClickReleased In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void clickOnDelivery(Delivery delivery) {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "clickOnDelivery In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickSomewhereElse()
     */
    @Override
    public void clickSomewhereElse() {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "clickSomewhereElse In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#closePopOver()
     */
    @Override
    public void closePopOver() {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "closePopOver In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnEmptyNode()
     */
    @Override
    public void clickOnEmptyNode(Node node) {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "clickOnEmptyNode In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnAddDelivery(com.hexanome.model.Node)
     */
    @Override
    public void btnAddDelivery(Node node, Node previousDeliveryNode, TimeSlot timeSlot) {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "btnAddDelivery In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnRemoveDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void btnRemoveDelivery(Delivery delivery) {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "btnRemoveDelivery In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnWarehouse(com.hexanome.model.Node)
     */
    @Override
    public void clickOnWarehouse(Node warehouse) {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "clickOnWarehouse In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCloseMap()
     */
    @Override
    public void btnCloseMap() {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "btnCloseMap In DefaultState");
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnClearPlanning()
     */
    @Override
    public void btnClearPlanning() {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "btnClearPlanning In DefaultState");
        // Nothing to do here
    }

    @Override
    public void initView() {
        Logger.getLogger(DefaultState.class.getName()).log(Level.FINEST, "Disable all buttons with initView in DefaultState");
        // By default disable all buttons
        MainWindow mainWindow = UIManager.getInstance().getMainWindow();
        if (mainWindow != null) {
            for (ConstView.Button button : ConstView.Button.values()) {
                if (!button.equals(ConstView.Button.UNDO) && !button.equals(ConstView.Button.REDO)) {
                    mainWindow.disableButton(button);
                }
            }
            // Disable Drag-and-drop
            mainWindow.getDeliveryTreeView().enableDragAndDrop(false);
        }
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
