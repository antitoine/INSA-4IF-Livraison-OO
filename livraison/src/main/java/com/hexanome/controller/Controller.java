/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.controller;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.IOManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.UIManager;
/**
 *
 * @author paul
 */
public class Controller {
    private static Controller controller = null;
    /**
     * 
     */
    private Controller() {
        // Nothing to do here for now
    }
    /**
     * 
     * @return 
     */
    public static Controller getInstance() {
        if(controller == null)
        {
            controller = new Controller();
        }
        return controller;
    }
}
