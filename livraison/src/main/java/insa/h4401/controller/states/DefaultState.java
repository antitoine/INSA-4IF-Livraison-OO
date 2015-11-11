package insa.h4401.controller.states;

import insa.h4401.controller.UIManager;
import insa.h4401.model.Delivery;
import insa.h4401.model.Node;
import insa.h4401.model.TimeSlot;
import insa.h4401.view.ConstView;
import insa.h4401.view.MainWindow;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents the default logic state extended by all
 * other concretes states.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public abstract class DefaultState implements IState {

    /* (non-Javadoc)
     * @see IState#btnLoadMap()
     */
    @Override
    public void btnLoadMap() {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "btnLoadMap In DefaultState");
    }

    /* (non-Javadoc)
     * @see IState#btnLoadPlanning()
     */
    @Override
    public void btnLoadPlanning() {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "btnLoadPlanning In DefaultState");
    }

    /* (non-Javadoc)
     * @see IState#btnGenerateRoute()
     */
    @Override
    public void btnGenerateRoute() {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "btnGenerateRoute In DefaultState");
    }

    /* (non-Javadoc)
     * @see IState#btnCancel()
     */
    @Override
    public void btnCancel() {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "btnCancel In DefaultState");
    }

    /* (non-Javadoc)
     * @see IState#btnValidateFile(java.io.File)
     */
    @Override
    public void btnValidateFile(File file) {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "btnValidateFile In DefaultState");
    }

    /* (non-Javadoc)
     * @see IState#leftClickPressedOnDelivery(Delivery)
     */
    @Override
    public void leftClickPressedOnDelivery() {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "leftClickPressedOnDelivery In DefaultState");
    }

    /* (non-Javadoc)
     * @see IState#leftClickReleased(Delivery)
     */
    @Override
    public void leftClickReleased(Delivery sourceDelivery, Delivery targetDelivery) {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "leftClickReleased In DefaultState");
    }

    /* (non-Javadoc)
     * @see IState#clickOnDelivery(Delivery)
     */
    @Override
    public void clickOnDelivery(Delivery delivery) {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "clickOnDelivery In DefaultState");
    }

    /* (non-Javadoc)
     * @see IState#clickSomewhereElse()
     */
    @Override
    public void clickSomewhereElse() {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "clickSomewhereElse In DefaultState");
    }

    /* (non-Javadoc)
     * @see IState#clickOnEmptyNode()
     */
    @Override
    public void clickOnEmptyNode(Node node) {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "clickOnEmptyNode In DefaultState");
    }

    /* (non-Javadoc)
     * @see IState#btnAddDelivery(Node)
     */
    @Override
    public void btnAddDelivery(Node node, Node previousDeliveryNode, TimeSlot timeSlot) {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "btnAddDelivery In DefaultState");
    }

    /* (non-Javadoc)
     * @see IState#btnRemoveDelivery(Delivery)
     */
    @Override
    public void btnRemoveDelivery(Delivery delivery) {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "btnRemoveDelivery In DefaultState");
    }

    /* (non-Javadoc)
     * @see IState#clickOnWarehouse(Node)
     */
    @Override
    public void clickOnWarehouse(Node warehouse) {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "clickOnWarehouse In DefaultState");
    }

    /* (non-Javadoc)
     * @see IState#btnCloseMap()
     */
    @Override
    public void btnCloseMap() {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "btnCloseMap In DefaultState");
    }

    /* (non-Javadoc)
     * @see IState#btnClearPlanning()
     */
    @Override
    public void btnClearPlanning() {
        Logger.getLogger(DefaultState.class.getName()).log(Level.INFO, "btnClearPlanning In DefaultState");
    }

    /* (non-Javadoc)
     * @see IState#initView()
     */
    @Override
    public void initView() {
        Logger.getLogger(DefaultState.class.getName()).log(Level.FINEST, "Disable all buttons with initView in DefaultState");
        MainWindow mainWindow = UIManager.getInstance().getMainWindow();
        if (mainWindow != null) {
            for (ConstView.Button button : ConstView.Button.values()) {
                if (!button.equals(ConstView.Button.UNDO) && !button.equals(ConstView.Button.REDO)) {
                    mainWindow.setEnableButton(button, false);
                }
            }
            mainWindow.getDeliveryTreeView().enableDragAndDrop(false);
        }
    }

    /**
     * Returns the string describing the state, used for debug only.
     *
     * @return a string describing the state
     */
    @Override
    public String toString() {
        return "DefaultState";
    }
}
