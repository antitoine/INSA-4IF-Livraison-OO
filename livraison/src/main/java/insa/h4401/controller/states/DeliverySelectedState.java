package insa.h4401.controller.states;

import insa.h4401.controller.ContextManager;
import insa.h4401.controller.UIManager;
import insa.h4401.controller.command.RemoveDeliveryCommand;
import insa.h4401.model.Delivery;
import insa.h4401.model.Node;

/**
 * This state represents the logic state when a delivery is selected
 * on the map.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class DeliverySelectedState extends SelectionsStates {

    /** The unique instance of this class. */
    private static DeliverySelectedState deliverySelectedState = null;

    private DeliverySelectedState() {
        // Nothing to do here
    }

    /**
     * Returns the instance of the DeliverySelectedState, which is a singleton.
     *
     * @return The instance of DeliverySelectedState.
     */
    public static DeliverySelectedState getInstance() {
        if (deliverySelectedState == null) {
            deliverySelectedState = new DeliverySelectedState();
        }
        return deliverySelectedState;
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

    /* (non-Javadoc)
     * @see IState#btnRemoveDelivery(Delivery)
     */
    @Override
    public void btnRemoveDelivery(Delivery delivery) {
        UIManager.getInstance().getMainWindow().getMapView().hidePopOver();
        RemoveDeliveryCommand rmDeliveryCmd = new RemoveDeliveryCommand(delivery);
        ContextManager.getInstance().executeCommand(rmDeliveryCmd);
    }

    /* (non-Javadoc)
     * @see IState#clickOnWarehouse(Node)
     */
    @Override
    public void clickOnWarehouse(Node warehouse) {
        UIManager.getInstance().getMainWindow().getMapView().hidePopOver();
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
        return "DeliverySelectedState";
    }
}
