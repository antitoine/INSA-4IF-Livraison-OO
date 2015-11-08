/**
 *
 */
package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.UIManager;
import com.hexanome.view.ConstView;
import javafx.application.Platform;

/**
 * This class represents the logic state when a map has been loaded
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class MapLoadedState extends DefaultState {

    private static MapLoadedState mapLoadedState = null;

    private MapLoadedState() {
        // Nothing to do here
    }

    /**
     * Returns the instance of the MapLoadedState, it is a singleton
     *
     * @return The instance of MapLoadedState
     */
    public static MapLoadedState getInstance() {
        if (mapLoadedState == null) {
            mapLoadedState = new MapLoadedState();
        }
        UIManager.getInstance().getMainWindow().enableButton(ConstView.Button.LOAD_PLANNING);
        UIManager.getInstance().getMainWindow().disableButton(ConstView.Button.COMPUTE_ROUTE);
        return mapLoadedState;
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadMap()
     */
    @Override
    public void btnLoadMap() {
        // WARNING : calls order matters
        if (UIManager.getInstance().askConfirmation("Current map and planning will be lost.")) {
            // Full clear of the model
            ModelManager.getInstance().clearModel();
            // Jump to MapSelectState
            ContextManager.getInstance().setCurrentState(MapSelectState.getInstance());
            // Ask user for a file to load
            UIManager.getInstance().getMainWindow().askFile();
        } 
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadPlanning()
     */
    @Override
    public void btnLoadPlanning() {
        // WARNING : calls order matters
        // Jump to PlanningSelectState
        ContextManager.getInstance().setCurrentState(PlanningSelectState.getInstance());
        // Ask user for a file to load
        UIManager.getInstance().getMainWindow().askFile();
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCloseMap()
     */
    @Override
    public void btnCloseMap() {
        if (UIManager.getInstance().askConfirmation("Current map and planning will be lost.")) {
            // Full clear of the model
            ModelManager.getInstance().clearModel();
            // Jump to InitState
            ContextManager.getInstance().setCurrentState(InitState.getInstance());
        }
    }

    /**
     * Returns the string describing the state, used for debug only
     * @return a string describing the state
     */
    @Override
    public String toString() {
        return "MapLoadedState"; //To change body of generated methods, choose Tools | Templates.
    }

}
