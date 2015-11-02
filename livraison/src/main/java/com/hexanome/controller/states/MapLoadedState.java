/**
 * 
 */
package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.UIManager;

/**
 * @author antitoine
 * \todo TODO
 */
public class MapLoadedState extends DefaultState {

    private static MapLoadedState mapLoadedState = null;

    private MapLoadedState(){
        // Nothing to do here
    }

    /**
     * Returns the instance of the MapLoadedState,
     * it is a singleton
     * @return The instance of MapLoadedState
     */
    public static MapLoadedState getInstance() {
        if(mapLoadedState == null)
        {
            mapLoadedState = new MapLoadedState();
        }
        return mapLoadedState;
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadMap()
     */
    @Override
    public void btnLoadMap() {
        // WARNING : calls order matters
        // \todo Afficher par la vue un message comme quoi tout est perdu avant de changer d'état
        // Full clear of the model
        ModelManager.getInstance().clearModel();
        // Jump to MapSelectState
        ContextManager.getInstance().setCurrentState(MapSelectState.getInstance());
        // Ask user for a file to load
        UIManager.getInstance().getMainWindow().askFile();
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
        // \todo Afficher par la vue un message comme quoi tout est perdu avant de changer d'état
        // Full clear of the model
        ModelManager.getInstance().clearModel();
        // Jump to InitState
        ContextManager.getInstance().setCurrentState(InitState.getInstance());
    }

    @Override
    public String toString() {
        return "MapLoadedState"; //To change body of generated methods, choose Tools | Templates.
    }
    
}
