package com.hexanome.controller;

import javafx.application.Application;

/**
 *
 * @author paul
 */
public class Controller {
    private static Controller controller = null;
    private static UIManager uimanager = null;
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
            uimanager = new UIManager();
        }
        return controller;
    }
    
    public void initManagers() {
        // Force ModelManager first instanciation
        ModelManager.getInstance();
        // Force ContextManager first instanciation
        ContextManager.getInstance();
        // Force IOManager first instanciation
        IOManager.getInstance();
    }
    
    public void initUI(String[] args) {
        // Launch can only be done once
        Application.launch(UIManager.class, args);
    }

}
