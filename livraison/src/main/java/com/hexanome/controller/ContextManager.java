package com.hexanome.controller;

import java.io.File;
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
        // \todo updateUndoStateMachine();
        // \todo updateRedoStateMachine();
    }
    /**
     * 
     */
    void redo() {
        // \todo (security) check if redo possible
        done.push(undone.pop().execute());
        // \todo updateUndoStateMachine();
        // \todo updateRedoStateMachine();
    }

    void loadMap(File file) {
        // \todo (security) check if load map is possible
        ModelManager.getInstance().initModelMap(IOManager.getInstance().getMapDocument(file));
        // \todo update application state
    }

    void loadPlanning(File file) {
        // \todo (security) check if load planning is possible
        ModelManager.getInstance().initModelPlanning(IOManager.getInstance().getPlanningDocument(file));
        // \todo update application state
    }

    void exit() {
        // \todo Check if Route should be saved
        // EXIT application
        System.exit(0);
    }
}
