package insa.h4401.controller.states;

import insa.h4401.controller.ContextManager;
import insa.h4401.controller.UIManager;
import insa.h4401.model.Delivery;
import insa.h4401.model.Node;

/**
 * This class represents the logic state when a map and a planning
 * are loaded but no node is selected.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class NothingSelectedState extends SelectionsStates {

    /**
     * The unique instance of this class.
     */
    private static NothingSelectedState nothingSelectedState = null;

    private NothingSelectedState() {
        // Nothing to do here
    }

    /**
     * Returns the instance of the NothingSelectedState, which is a singleton.
     *
     * @return The instance of NothingSelectedState
     */
    public static NothingSelectedState getInstance() {
        if (nothingSelectedState == null) {
            nothingSelectedState = new NothingSelectedState();
        }
        return nothingSelectedState;
    }

    /* (non-Javadoc)
     * @see IState#clickOnDelivery(Delivery)
     */
    @Override
    public void clickOnDelivery(Delivery delivery) {
        // Show new pop over assuming none is currently open (current state assumption)
        UIManager.getInstance().getMainWindow().getMapView().showPopOver(delivery.getNode());
        ContextManager.getInstance().setCurrentState(DeliverySelectedState.getInstance());
    }

    /* (non-Javadoc)
     * @see IState#clickOnEmptyNode()
     */
    @Override
    public void clickOnEmptyNode(Node node) {
        // Show new pop over assuming none is currently open (current state assumption)
        UIManager.getInstance().getMainWindow().getMapView().showPopOver(node);
        ContextManager.getInstance().setCurrentState(EmptyNodeSelectedState.getInstance());
    }

    /* (non-Javadoc)
     * @see IState#clickOnWarehouse(Node)
     */
    @Override
    public void clickOnWarehouse(Node warehouse) {
        // Show new pop over assuming none is currently open (current state assumption)
        UIManager.getInstance().getMainWindow().getMapView().showPopOver(warehouse);
        ContextManager.getInstance().setCurrentState(WarehouseSelectedState.getInstance());
    }

    /**
     * Returns the string describing the state, used for debug only
     *
     * @return a string describing the state
     */
    @Override
    public String toString() {
        return "NothingSelectedState";
    }
}
