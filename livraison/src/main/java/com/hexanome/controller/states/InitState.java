package com.hexanome.controller.states;

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
        // \todo TODO
    }
}
