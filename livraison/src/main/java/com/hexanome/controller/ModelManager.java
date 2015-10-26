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
public class ModelManager {
    private static ModelManager modelManager = null;
    /**
     * 
     */
    private ModelManager() {
        // Nothing to do here for now
    }
    /**
     * 
     * @return 
     */
    public static ModelManager getInstance() {
        if(modelManager == null)
        {
            modelManager = new ModelManager();
        }
        return modelManager;
    }
}
