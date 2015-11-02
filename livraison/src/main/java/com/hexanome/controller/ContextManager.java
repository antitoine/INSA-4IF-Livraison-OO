package com.hexanome.controller;

import com.hexanome.controller.command.ICommand;
import java.util.Stack;

import com.hexanome.controller.states.IState;
import com.hexanome.controller.states.InitState;
import com.hexanome.controller.states.MapLoadedState;
import com.hexanome.controller.states.PlanningLoadedState;
import com.hexanome.view.ConstView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

/**
 * This class manages both commands and state machines of the application
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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                UIManager.getInstance().getMainWindow().enableButton(ConstView.Button.UNDO);
            }
        });
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
    void undo() {
        // \todo (security) check if undo possible
        undone.push(done.pop().reverse());
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
    void redo() {
        // \todo (security) check if redo possible
        done.push(undone.pop().execute());
        // Enable undo button
        UIManager.getInstance().getMainWindow().enableButton(ConstView.Button.UNDO);
        if (undone.empty()) {
            // Disable redo button if undone stack is empty
            UIManager.getInstance().getMainWindow().disableButton(ConstView.Button.REDO);
        }
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
        // removeMeLater DEBUG --------------
        System.out.println(currentState.toString());
        // ----------------------------------
    }
}
