package com.hexanome.controller;

/**
 * @author antoine
 *
 */
public interface ICommand {
    /**
     * 
     * @return 
     */
    public ICommand execute();
    /**
     * 
     * @return 
     */
    public ICommand reverse();
}
