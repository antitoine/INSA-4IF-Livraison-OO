package com.hexanome.controller;

import javafx.application.Application;

/**
 * This class is the main controller, its role is to initialize
 * properly the application and its graphics components.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class Controller {
    private static Controller controller = null;

    /**
     * Builds a new instance of the Controller
     */
    private Controller() {
        // Nothing to do here for now
    }

    /**
     * @return the single existing instance of the controller
     */
    public static Controller getInstance() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    /**
     * Initialize internal sub-components of the controller
     * package called managers
     */
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

    /**
     * Initialize User Interface Event loop.
     * This instruction is blocking.
     *
     * @param args Arguments to give to the graphic app
     */
    public void initUI(String[] args) {
        // Launch can only be done once
        // This is JAVAFX default behaviour
        Application.launch(AppMediator.class, args);
    }

}
