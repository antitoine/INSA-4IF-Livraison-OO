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
        ModelManager.getInstance().clearModel();
        ContextManager.getInstance().setCurrentState(MapSelectState.getInstance());
        UIManager.getInstance().getMainWindow().askFile();
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadPlanning()
     */
    @Override
    public void btnLoadPlanning() {
        ContextManager.getInstance().setCurrentState(PlanningSelectState.getInstance());
        UIManager.getInstance().getMainWindow().askFile();
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCloseMap()
     */
    @Override
    public void btnCloseMap() {
        ModelManager.getInstance().clearModel();
        ContextManager.getInstance().setCurrentState(InitState.getInstance());
    }
}
