package insa.h4401.controller.states;

import insa.h4401.controller.ContextManager;
import insa.h4401.controller.UIManager;
import insa.h4401.controller.command.SwapDeliveriesCommand;
import insa.h4401.model.Delivery;

/**
 * This class represents the state when to deliveries are being
 * swaped.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class SwapDeliveriesState extends DefaultState {

    /**
     * The unique instance of this class.
     */
    private static SwapDeliveriesState swapDeliveriesState = null;

    private SwapDeliveriesState() {
        // Nothing to do here
    }

    /**
     * Returns the instance of the SwapDeliveriesState, which is a singleton.
     *
     * @return The instance of SwapDeliveriesState
     */
    public static SwapDeliveriesState getInstance() {
        if (swapDeliveriesState == null) {
            swapDeliveriesState = new SwapDeliveriesState();
        }
        return swapDeliveriesState;
    }

    /* (non-Javadoc)
     * @see IState#leftClickReleased(Delivery)
     */
    @Override
    public void leftClickReleased(Delivery sourceDelivery, Delivery targetDelivery) {
        if (sourceDelivery != null && targetDelivery != null) {
            SwapDeliveriesCommand cmd = new SwapDeliveriesCommand(sourceDelivery, targetDelivery);
            ContextManager.getInstance().executeCommand(cmd);
        }

        ContextManager.getInstance().setCurrentState(NothingSelectedState.getInstance());
        UIManager.getInstance().getMainWindow().repositionToLatestPosition();
    }

    @Override
    public void initView() {
        // Nothing to do here
        // Override to not disable all buttons
    }

    /**
     * Returns the string describing the state, used for debug only
     *
     * @return a string describing the state
     */
    @Override
    public String toString() {
        return "SwapDeliveriesState";
    }
}
