package insa.h4401.controller.command;

/**
 * This is the interface of a command which can be executed by the ContextManager.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public interface ICommand {
    
    /**
     * Execute the command.
     */
    void execute();

    /**
     * Reverse execution of the command executing the exact opposite command.
     */
    void reverse();
}
