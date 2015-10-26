/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.controller;

/**
 *
 * @author paul
 */
public class ContextManager {
    private static ContextManager contextManager = null;
    /**
     * 
     */
    private ContextManager() {
        // Nothing to do here for now
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
}
