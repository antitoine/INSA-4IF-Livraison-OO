package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.UIManager;

/**
 * @author antitoine
 *
 */
public class InitState extends DefaultState {

    private static InitState initState = null;

    private InitState(){
        // Nothing to do here
    }

    /**
     * Returns the instance of the InitState,
     * it is a singleton
     * @return The instance of InitState
     */
    public static InitState getInstance() {
        if(initState == null)
        {
            initState = new InitState();
        }
        return initState;
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadMap()
     */
    @Override
    public void btnLoadMap() {
        // WARNING : calls order matters
        // Jump to MapSelectedState
        ContextManager.getInstance().setCurrentState(MapSelectState.getInstance());        
        // Ask user for the file to load
        UIManager.getInstance().getMainWindow().askFile();
    }

    @Override
    public String toString() {
        return "InitState"; //To change body of generated methods, choose Tools | Templates.
    }
    
}
