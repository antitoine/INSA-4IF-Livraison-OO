package insa.h4401.controller.states;

import insa.h4401.controller.ContextManager;
import insa.h4401.controller.UIManager;
import insa.h4401.controller.command.AddDeliveryCommand;
import insa.h4401.model.Delivery;
import insa.h4401.model.Node;
import insa.h4401.model.TimeSlot;

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
     * @see IState#btnAddDelivery(Node)
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
     * @see IState#clickOnWarehouse(Node)
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
