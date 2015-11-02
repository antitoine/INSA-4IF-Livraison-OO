package com.hexanome.controller;

import javafx.application.Application;
/**
 * MainController
 */
public class Controller {
    private static Controller controller = null;
    
     private Controller() {
        // Nothing to do here for now
    }
     
    public static Controller getInstance() {
        if(controller == null)
        {   
            controller = new Controller();
        }
        return controller;
    }
    
    public void initManagers() {
        // <!>
        // WARNING : Calls' order matters ! 
        // <!>
        // Force ModelManager first instanciation
        ModelManager.getInstance();
        // Force IOManager first instanciation
        IOManager.getInstance();
        // Force UIManager first instanciation
        UIManager.getInstance();
        // Force ContextManager first instanciation
        ContextManager.getInstance();
    }   
    
    public void initUI(String[] args) {
        // Launch can only be done once
        Application.launch(AppMediator.class, args);
    }

}
