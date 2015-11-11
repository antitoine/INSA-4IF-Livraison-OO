package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.UIManager;

/**
 * This class represents the logic state at the very beginning
 * of the application.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class InitState extends DefaultState {

    /** The unique instance of this class. */
    private static InitState initState = null;

    private InitState() {
        // Nothing to do here
    }

    /**
     * Returns the instance of the InitState, which is a singleton.
     *
     * @return The instance of InitState.
     */
    public static InitState getInstance() {
        if (initState == null) {
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
        ContextManager.getInstance().setCurrentState(MapSelectState.getInstance());
        UIManager.getInstance().getMainWindow().askFile();
    }

    /**
     * Returns the string describing the state, used for debug only
     *
     * @return a string describing the state
     */
    @Override
    public String toString() {
        return "InitState";
    }
}
