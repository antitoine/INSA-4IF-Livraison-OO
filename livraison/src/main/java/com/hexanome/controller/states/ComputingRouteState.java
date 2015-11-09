package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.UIManager;

/**
 * This class represent the logic state in which the application
 * is computing the best route to reach all delivery points
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class ComputingRouteState extends DefaultState {

    private static ComputingRouteState computingRouteState = null;

    private ComputingRouteState(){
        // Nothing to do here
    }

    /**
     * Returns the instance of the ComputingRouteState,
     * it is a singleton
     * @return The instance of ComputingRouteState
     */
    public static ComputingRouteState getInstance() {
        if(computingRouteState == null)
        {
            computingRouteState = new ComputingRouteState();
        }
        return computingRouteState;
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCancel()
     */
    @Override
    public void btnCancel() {
        ContextManager.getInstance().setCurrentState(PlanningLoadedState.getInstance());
        ModelManager.getInstance().getPlanning().abortComputeRoute();
        UIManager.getInstance().getMainWindow().endLoadingState();
    }

    /**
     * Returns the string describing the state, used for debug only
     * @return a string describing the state
     */
    @Override
    public String toString() {
        return "ComputingRouteState"; //To change body of generated methods, choose Tools | Templates.
    }
}
