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

    void undo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void redo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void loadMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void loadPlanning() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
