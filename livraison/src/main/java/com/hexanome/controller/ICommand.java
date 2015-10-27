package com.hexanome.controller;

/**
 * @author antoine
 *
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
