package insa.h4401.controller;

import insa.h4401.controller.command.ICommand;
import insa.h4401.controller.states.IState;
import insa.h4401.controller.states.InitState;
import insa.h4401.view.ConstView;

import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class manages both commands and the main state machine of the 
 * application
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class ContextManager {

    /** Instance of the class used by the Singleton. */
    private static ContextManager contextManager = null;
    
    /** Stack of commands already executed. */
    private final Stack<ICommand> done;
    
    /** Stack of commands canceled. */
    private final Stack<ICommand> undone;
    
    /** The current state of the application. */
    private IState currentState;

    /**
     * Builds a new ContextManager instance.
     */
    private ContextManager() {
        this.done = new Stack<>();
        this.undone = new Stack<>();
        this.setCurrentState(InitState.getInstance());
    }

    /**
     * @return The instance of ContextManager in the application, which is a
     * Singleton.
     */
    public static ContextManager getInstance() {
        if (contextManager == null) {
            contextManager = new ContextManager();
        }
        return contextManager;
    }

    /**
     * Executes the given command and add it to commands history. Enables the
     * undo button.
     *
     * @param cmd The command to execute
     */
    public void executeCommand(final ICommand cmd) {
        cmd.execute();
        done.push(cmd);
        
        UIManager.getInstance()
                 .getMainWindow()
                 .setEnableButton(ConstView.Button.UNDO, true);
    }

    /**
     * Clears commands history and disables undo/redo buttons.
     */
    public void clearCommandsHistory() {
        done.clear();
        undone.clear();
        
        UIManager.getInstance()
                 .getMainWindow()
                 .setEnableButton(ConstView.Button.UNDO, false);
        
        UIManager.getInstance()
                 .getMainWindow()
                 .setEnableButton(ConstView.Button.REDO, false);
    }

    /**
     * Undoes the last command added to done commands stack, and updates the 
     * undo/redo buttons.
     */
    public void undo() {
        ICommand cmd = done.pop();
        cmd.reverse();
        undone.push(cmd);

        UIManager.getInstance()
                 .getMainWindow()
                 .setEnableButton(ConstView.Button.REDO, true);
        
        if (done.empty()) {
            UIManager.getInstance()
                     .getMainWindow()
                     .setEnableButton(ConstView.Button.UNDO, false);
        }
    }

    /**
     * Redoes the last command added to undone commands stack and update the 
     * undo/redo buttons.
     */
    public void redo() {
        ICommand cmd = undone.pop();
        cmd.execute();
        done.push(cmd);

        UIManager.getInstance()
                 .getMainWindow()
                 .setEnableButton(ConstView.Button.UNDO, true);
        
        if (undone.empty()) {
            UIManager.getInstance()
                     .getMainWindow()
                     .setEnableButton(ConstView.Button.REDO, false);
        }
    }

    /**
     * Closes the application.
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * @return The current state of the application.
     */
    public IState getCurrentState() {
        return currentState;
    }

    /**
     * Sets a new state and calls the method initView of this state.
     *
     * @param currentState The currentState to set.
     */
    public final void setCurrentState(IState currentState) {
        this.currentState = currentState;
        this.currentState.initView();
        Logger.getLogger(ContextManager.class.getName()).log(Level.INFO, currentState.toString());
    }
}
