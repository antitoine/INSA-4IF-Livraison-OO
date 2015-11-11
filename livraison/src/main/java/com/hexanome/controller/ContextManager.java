package com.hexanome.controller;

import com.hexanome.controller.command.ICommand;
import com.hexanome.controller.states.IState;
import com.hexanome.controller.states.InitState;
import com.hexanome.view.ConstView;

import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class manages both commands and the main state machine of the application
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class ContextManager {

    private static ContextManager contextManager = null;
    private final Stack<ICommand> done;
    private final Stack<ICommand> undone;
    private IState currentState;

    /**
     * Builds a new ContextManager instance
     */
    private ContextManager() {
        this.setCurrentState(InitState.getInstance());
        this.done = new Stack<>();
        this.undone = new Stack<>();
    }

    /**
     * @return the instance of ContextManager in the application, it is a
     * Singleton
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
     * @param cmd the command to execute
     */
    public void executeCommand(final ICommand cmd) {
        cmd.execute();
        // Add command to done commands history
        done.push(cmd);
        // Enable undo button
        UIManager.getInstance().getMainWindow().setEnableButton(ConstView.Button.UNDO, true);
    }

    /**
     * Clears commands history
     */
    public void clearCommandsHistory() {
        done.clear();
        undone.clear();
        // Disable undo/redo buttons
        UIManager.getInstance().getMainWindow().setEnableButton(ConstView.Button.UNDO, false);
        UIManager.getInstance().getMainWindow().setEnableButton(ConstView.Button.REDO, false);
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
        UIManager.getInstance().getMainWindow().setEnableButton(ConstView.Button.REDO, true);
        if (done.empty()) {
            // Disable undo button if done stack is empty
            UIManager.getInstance().getMainWindow().setEnableButton(ConstView.Button.UNDO, false);
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
        UIManager.getInstance().getMainWindow().setEnableButton(ConstView.Button.UNDO, true);
        if (undone.empty()) {
            // Disable redo button if undone stack is empty
            UIManager.getInstance().getMainWindow().setEnableButton(ConstView.Button.REDO, false);
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
     * Set a new state and call the method initView of this state
     *
     * @param currentState the currentState to set
     */
    public void setCurrentState(IState currentState) {
        this.currentState = currentState;
        this.currentState.initView();
        // removeMeLater DEBUG --------------
        Logger.getLogger(ContextManager.class.getName()).log(Level.INFO, currentState.toString());
        // ----------------------------------
    }
}
