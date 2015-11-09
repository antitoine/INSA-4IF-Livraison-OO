/**
 *
 */
package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.UIManager;
import com.hexanome.view.ConstView;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * This class represents the logic state when a planning has been loaded
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class PlanningLoadedState extends DefaultState implements EventHandler {

    private static PlanningLoadedState planningLoadedState = null;

    private PlanningLoadedState() {
        // Nothing to do here
    }

    /**
     * Returns the instance of the PlanningLoadedState, it is a singleton
     *
     * @return The instance of PlanningLoadedState
     */
    public static PlanningLoadedState getInstance() {
        if (planningLoadedState == null) {
            planningLoadedState = new PlanningLoadedState();
        }
        UIManager.getInstance().getMainWindow().enableButton(ConstView.Button.COMPUTE_ROUTE);
        UIManager.getInstance().getMainWindow().enableButton(ConstView.Button.CLEAR_PLANNING);
        UIManager.getInstance().getMainWindow().disableButton(ConstView.Button.ROAD_MAP);
        return planningLoadedState;
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadMap()
     */
    @Override
    public void btnLoadMap() {
        // WARNING : calls order matters
        if (UIManager.getInstance().askConfirmation("Current map and planning will be lost.")) {
            // Full clear model
            ModelManager.getInstance().clearModel();
            // Jump to MapSelectState
            ContextManager.getInstance().setCurrentState(MapSelectState.getInstance());
            // Ask user for the map to load
            UIManager.getInstance().getMainWindow().askFile();
        }
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadPlanning()
     */
    @Override
    public void btnLoadPlanning() {
        // WARNING : calls order matters
        // Clear current model's planning
        if (UIManager.getInstance().askConfirmation("Current planning will be lost.")) {
            ModelManager.getInstance().clearPlanning();
            // Jump to PlanningSelectState
            ContextManager.getInstance().setCurrentState(PlanningSelectState.getInstance());
            // Ask user for planning file to load
            UIManager.getInstance().getMainWindow().askFile();
        }
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCloseMap()
     */
    @Override
    public void btnCloseMap() {
        if (UIManager.getInstance().askConfirmation("Current map and planning will be lost.")) {
            // Full clear model
            ModelManager.getInstance().clearModel();
            // Jump to InitState
            ContextManager.getInstance().setCurrentState(InitState.getInstance());
        }
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnClearPlanning()
     */
    @Override
    public void btnClearPlanning() {
        if (UIManager.getInstance().askConfirmation("Current planning will be lost.")) {
            // Clear model's current planning
            ModelManager.getInstance().clearPlanning();
            // Jump to MapLoadedState
            ContextManager.getInstance().setCurrentState(MapLoadedState.getInstance());
        }
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnGenerateRoute()
     */
    @Override
    public void btnGenerateRoute() {
        // Jump to ComputingRouteState
        ContextManager.getInstance().setCurrentState(ComputingRouteState.getInstance());

        // Launch asynchronous Route computation algorithm
        UIManager.getInstance().beginComputingRoute();
        ModelManager.getInstance().getPlanning().computeRoute(this);
    }

    /**
     * Returns the string describing the state, used for debug only
     *
     * @return a string describing the state
     */
    @Override
    public String toString() {
        return "PlanningLoadedState"; //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Handler for the end of the route computing.
     * @param event 
     */
    @Override
    public void handle(Event event) {
        // Change current state to nothing selected state
        ContextManager.getInstance().setCurrentState(NothingSelectedState.getInstance());
        
        // Enable ROAD_MAP button
        UIManager.getInstance().getMainWindow().enableButton(ConstView.Button.ROAD_MAP);
        
        UIManager.getInstance().getMainWindow().disableButton(ConstView.Button.COMPUTE_ROUTE);

        // Add MapView as a subscriber of route
        UIManager.getInstance().endRouteComputation();
    }

}
