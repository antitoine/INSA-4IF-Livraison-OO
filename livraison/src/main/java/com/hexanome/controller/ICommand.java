package com.hexanome.controller;

/**
 * @author antoine
 *  This is the interface of a command which can be executed by the ContextManager
 */
public interface ICommand {
    /**
     * Execute the command
     * @return 
     */
    public ICommand execute();
    /**
     * Reverse execution of the command executing the exact opposite command
     * @return 
     */
    public ICommand reverse();
}
