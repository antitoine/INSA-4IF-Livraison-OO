package com.hexanome.controller;

import com.hexanome.controller.command.ICommand;
import java.util.Stack;

import com.hexanome.controller.states.IState;
import com.hexanome.controller.states.InitState;
import com.hexanome.view.ConstView;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class manages both commands and the main state machine of the application
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class ContextManager {

    private static ContextManager contextManager = null;
    private IState currentState;
    private final Stack<ICommand> done;
    private final Stack<ICommand> undone;

    /**
     * Builds a new ContextManager instance 
     */
    private ContextManager() {
        this.setCurrentState(InitState.getInstance());
        this.done = new Stack<>();
        this.undone = new Stack<>();
    }

    /**
     * Returns the instance of ContextManager in the application, it is a
     * Singleton
     *
     * @return
     */
    public static ContextManager getInstance() {
        if (contextManager == null) {
            contextManager = new ContextManager();
        }
        return contextManager;
    }

    /**
     * Execute the given command and add it to commands history
     *
     * @param cmd
     */
    public void executeCommand(final ICommand cmd) {
        cmd.execute();
        // Add command to done commands history
        done.push(cmd);
        // Enable undo button
        UIManager.getInstance().getMainWindow().enableButton(ConstView.Button.UNDO);
    }

    /**
     * Clears commands history
     */
    public void clearCommandsHistory() {
        done.clear();
        undone.clear();
        // Disable undo/redo buttons
        UIManager.getInstance().getMainWindow().disableButton(ConstView.Button.UNDO);
        UIManager.getInstance().getMainWindow().disableButton(ConstView.Button.REDO);
    }

    /**
     * Undo the last command added to done commands stack
     */
    public void undo() {
        // Take command from stack
        ICommand cmd = done.pop();
        // Reverse command
        cmd.reverse();
        // Push command on the undone stack
        undone.push(cmd);
        // Enable redo button
        UIManager.getInstance().getMainWindow().enableButton(ConstView.Button.REDO);
        if (done.empty()) {
            // Disable undo button if done stack is empty
            UIManager.getInstance().getMainWindow().disableButton(ConstView.Button.UNDO);
        }
    }

    /**
     * Redo the last command added to undone commands stack
     */
    public void redo() {
        // Take command from top of undone stack
        ICommand cmd = undone.pop();
        // Execute command
        cmd.execute();
        // Push command on top of done stack
        done.push(cmd);
        // Enable undo button
        UIManager.getInstance().getMainWindow().enableButton(ConstView.Button.UNDO);
        if (undone.empty()) {
            // Disable redo button if undone stack is empty
            UIManager.getInstance().getMainWindow().disableButton(ConstView.Button.REDO);
        }
    }

    /**
     * Closes the application
     */
    public void exit() {
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
        // removeMeLater DEBUG --------------
        Logger.getLogger(ContextManager.class.getName()).log(Level.INFO, currentState.toString());
        // ----------------------------------
    }
}
