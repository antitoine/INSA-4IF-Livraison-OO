package insa.h4401.controller.states;

import insa.h4401.controller.ContextManager;
import insa.h4401.controller.ModelManager;
import insa.h4401.controller.UIManager;

/**
 * This class represents the logic state in which the application
 * is computing the best route to reach all delivery points.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class ComputingRouteState extends DefaultState {

    /** The unique instance of this class. */
    private static ComputingRouteState computingRouteState = null;

    private ComputingRouteState() {
        // Nothing to do here
    }

    /**
     * Returns the instance of the ComputingRouteState, which is a singleton.
     *
     * @return The instance of ComputingRouteState.
     */
    public static ComputingRouteState getInstance() {
        if (computingRouteState == null) {
            computingRouteState = new ComputingRouteState();
        }
        return computingRouteState;
    }

    /* 
     * (non-Javadoc)
     * @see IState#btnCancel()
     */
    @Override
    public void btnCancel() {
        ContextManager.getInstance().setCurrentState(PlanningLoadedState.getInstance());
        ModelManager.getInstance().getPlanning().abortComputeRoute();
        UIManager.getInstance().getMainWindow().endLoadingState();
    }

    /**
     * Returns the string describing the state, used for debug only.
     *
     * @return A string describing the state
     */
    @Override
    public String toString() {
        return "ComputingRouteState";
    }
}
