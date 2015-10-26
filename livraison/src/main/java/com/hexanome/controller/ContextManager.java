package com.hexanome.controller;

import java.util.Stack;
/**
 *
 * @author paul
 */
public class ContextManager {

    private static ContextManager contextManager = null;
    private IState currentState;
    private Stack<ICommand> done;
    private Stack<ICommand> undone;
    
    /**
     * 
     */
    private ContextManager() {
        this.currentState = new InitState();
        this.done = new Stack<>();
        this.undone = new Stack<>();
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
    /**
     * 
     * @param cmd 
     */
    void executeCommand(ICommand cmd) {
        // Executing command
        cmd.execute();
        // Add command to done commands history
        done.push(cmd);
    }
    /**
     * 
     */
    void clearCommandsHistory () {
        done.clear();
        undone.clear();
    }
    /**
     * 
     */
    void undo() {
        // \todo (security) check if undo possible
        undone.push(done.pop().execute());
        updateUndoStateMachine();
        updateRedoStateMachine();
    }
    /**
     * 
     */
    void redo() {
        // \todo (security) check if redo possible
        done.push(undone.pop().execute());
        updateUndoStateMachine();
        updateRedoStateMachine();
    }

    void loadMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void loadPlanning() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * This method updates undo statemachine state
     */
    private void updateUndoStateMachine() {
        if(done.isEmpty()) {
            // \todo go to undo impossible state
        } else {
            // \todo go to undo possible state
        }
    }
    /**
     * This method updates redo statemachine state
     */
    private void updateRedoStateMachine() {
        if(undone.isEmpty()) {
            // \todo go to redo impossible state
        } else {
            // \todo go to redo possible state
        }
    }
}
