package com.hexanome.livraison;

import com.hexanome.controller.Controller;

/**
 * Entry point class with the static main method.
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
class MainApp {

    /**
     * Main Method : start the application by launching the main controller and 
     * the view.
     *
     * @param args the command line arguments
     * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
     */
    public static void main(String[] args) {
        // Init managers by instanciating singletons
        Controller.getInstance().initManagers();
        
        // Launch the UI Manager to display the windows
        Controller.getInstance().initUI(args);
    }
}
