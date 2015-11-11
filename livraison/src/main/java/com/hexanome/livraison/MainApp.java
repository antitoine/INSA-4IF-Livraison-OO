package com.hexanome.livraison;

import com.hexanome.controller.Controller;

class MainApp {

    /**
     * Main Method : start the application
     * launch the main controller and the view
     *
     * @param args the command line arguments
     * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
     */
    public static void main(String[] args) {
        // Init managers by instanciating singletons
        Controller.getInstance().initManagers();
        // launch the UI Manager to display the windows
        Controller.getInstance().initUI(args);
    }
}
