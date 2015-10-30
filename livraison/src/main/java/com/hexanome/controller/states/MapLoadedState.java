/**
 * 
 */
package com.hexanome.controller.states;

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
        // \todo TODO
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadPlanning()
     */
    @Override
    public void btnLoadPlanning() {
        // \todo TODO
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCloseMap()
     */
    @Override
    public void btnCloseMap() {
        // \todo TODO
    }
}
