package com.hexanome.controller;

import com.hexanome.controller.command.ICommand;
import java.util.Stack;

import com.hexanome.controller.states.IState;
import com.hexanome.controller.states.InitState;
/**
 * This class manages both commands and state machines of the application
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
        this.setCurrentState(InitState.getInstance());
        this.done = new Stack<>();
        this.undone = new Stack<>();
    }

    /**
     * Returns the instance of ContextManager in the application,
     * it is a Singleton
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
     * Execute the given command and add it to commands history
     * @param cmd 
     */
    public void executeCommand(ICommand cmd) {
        // Executing command
        cmd.execute();
        // Add command to done commands history
        done.push(cmd);
    }
    /**
     * Clears commands history
     */
    public void clearCommandsHistory () {
        done.clear();
        undone.clear();
    }
    /**
     * Undo the last command added to done commands stack
     */
    void undo() {
        // \todo (security) check if undo possible
        undone.push(done.pop().reverse());
        // \todo updateUndoStateMachine();
        // \todo updateRedoStateMachine();
    }
    /**
     * Redo the last command added to undone commands stack
     */
    void redo() {
        // \todo (security) check if redo possible
        done.push(undone.pop().execute());
        // \todo updateUndoStateMachine();
        // \todo updateRedoStateMachine();
    }

    /**
     * Clear the current id planning loaded in the model
     */
    void resetPlanning() {
        // \todo (security) check if current state allows reset
        // Clear the planning
        ModelManager.getInstance().clearPlanning();
        // \todo update application state
    }
    
    void resetModel() {
        // \todo (security) check if current state allows reset
        ModelManager.getInstance().clearModel();
        // \todo update application state
    }

    /**
     * Closes the application
     */
    void exit() {
        // \todo Check if Route should be saved
        // EXIT application
        System.exit(0);
    }

    /**
     * @return the currentState
     */
    public IState getCurrentState() {
        return currentState;
    }

    /**
     * @param currentState the currentState to set
     */
    public void setCurrentState(IState currentState) {
        this.currentState = currentState;
    }
}
