package insa.h4401.controller.states;

import insa.h4401.controller.ContextManager;
import insa.h4401.controller.UIManager;
import insa.h4401.model.Delivery;
import insa.h4401.model.Node;

/**
 * This class represents the logic state when warehouse is selected
 * on the map.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class WarehouseSelectedState extends SelectionsStates {

    /**
     * The unique instance of this class.
     */
    private static WarehouseSelectedState warehouseSelectedState = null;

    private WarehouseSelectedState() {
        // Nothing to do here
    }

    /**
     * Returns the instance of the SwapDeliveriesState, which is a singleton.
     *
     * @return The instance of SwapDeliveriesState
     */
    public static WarehouseSelectedState getInstance() {
        if (warehouseSelectedState == null) {
            warehouseSelectedState = new WarehouseSelectedState();
        }
        return warehouseSelectedState;
    }

    /* (non-Javadoc)
     * @see IState#clickOnDelivery(Delivery)
     */
    @Override
    public void clickOnDelivery(Delivery delivery) {
        UIManager.getInstance().getMainWindow().getMapView().hidePopOver();
        UIManager.getInstance().getMainWindow().getMapView().showPopOver(delivery.getNode());
        ContextManager.getInstance().setCurrentState(DeliverySelectedState.getInstance());
    }

    /* (non-Javadoc)
     * @see IState#clickSomewhereElse()
     */
    @Override
    public void clickSomewhereElse() {
        UIManager.getInstance().getMainWindow().getMapView().hidePopOver();
        ContextManager.getInstance().setCurrentState(NothingSelectedState.getInstance());
    }

    /* (non-Javadoc)
     * @see IState#clickOnEmptyNode()
     */
    @Override
    public void clickOnEmptyNode(Node node) {
        UIManager.getInstance().getMainWindow().getMapView().hidePopOver();
        UIManager.getInstance().getMainWindow().getMapView().showPopOver(node);
        ContextManager.getInstance().setCurrentState(EmptyNodeSelectedState.getInstance());
    }

    /**
     * Returns the string describing the state, used for debug only
     *
     * @return a string describing the state
     */
    @Override
    public String toString() {
        return "WarehouseSelectedState";
    }
}
