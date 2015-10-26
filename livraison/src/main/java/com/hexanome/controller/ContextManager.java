package com.hexanome.controller;

/**
 *
 * @author paul
 */
public class ContextManager {

    private static ContextManager contextManager = null;
    private IState currentState;
    
    /**
     * 
     */
    private ContextManager() {
        currentState = new InitState();
    }

    /**
     * 
     * @return 
     */
    public static ContextManager getInstance() {
        if(contextManager == null)
        {
            contextManager = new ContextManager();
        }
        return contextManager;
    }
}
