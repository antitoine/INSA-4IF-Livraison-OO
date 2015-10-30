package com.hexanome.controller;

import com.hexanome.controller.command.ICommand;
import java.io.File;
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
        this.currentState = new InitState();
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
    void executeCommand(ICommand cmd) {
        // Executing command
        cmd.execute();
        // Add command to done commands history
        done.push(cmd);
    }
    /**
     * Clears commands history
     */
    void clearCommandsHistory () {
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
     * Load the map calling the ModelManager and the IOManager
     * @param file 
     *      file containing the XML description of the Map
     */
    void loadMap(File file) {
        // \todo (security) check if load map is possible
        if( ! ModelManager.getInstance().initModelMap(IOManager.getInstance().getMapDocument(file)) ) {
            // \todo treat error case
        }
        // \todo update application state
    }
    /**
     * Load the planning calling the ModelManager and the IOManager
     * @param file 
     *      file containing the XML description of the Planning
     */
    void loadPlanning(File file) {
        // \todo (security) check if load planning is possible
        if( ! ModelManager.getInstance().initModelPlanning(IOManager.getInstance().getPlanningDocument(file)) ) {
            // \todo treat error case
        }
        // \todo update application state
    }
    
    /**
     * Clear the current model and replace the application in its initState
     */
    void reset() {
        // \todo (security) check if current state allows reset
        // Clear the model
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
}
