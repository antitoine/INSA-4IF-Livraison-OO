package com.hexanome.controller.command;

/**
 * This is the interface of a command which can be executed by the ContextManager
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public interface ICommand {
    /**
     * Execute the command
     * @return 
     */
    ICommand execute();
    /**
     * Reverse execution of the command executing the exact opposite command
     * @return 
     */
    ICommand reverse();
}
