/**
 * 
 */
package com.hexanome.controller.states;

import java.io.File;

/**
 * @author antitoine
 * \todo TODO
 */
public class MapSelectState extends DefaultState {

    private static MapSelectState mapSelectState = null;

    private MapSelectState(){
        // Nothing to do here
    }

    /**
     * Returns the instance of the MapSelectState,
     * it is a singleton
     * @return The instance of MapSelectState
     */
    public static MapSelectState getInstance() {
        if(mapSelectState == null)
        {
            mapSelectState = new MapSelectState();
        }
        return mapSelectState;
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCancel()
     */
    @Override
    public void btnCancel() {
        // \todo TODO
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnValidateFile(java.io.File)
     */
    @Override
    public void btnValidateFile(File file) {
        // \todo TODO
    }

}
