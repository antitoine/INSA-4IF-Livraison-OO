package com.hexanome.livraison;

import com.hexanome.controller.Controller;
import com.hexanome.model.ModelTest;

public class MainApp {

    /** 
     * Main Method : start the application
     * launch the main controller and the view
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TEST
       ModelTest mt = new ModelTest();
        // Init managers by instanciating singletons
       Controller.getInstance().initManagers();
        // launch the UI Manager to display the windows
        // This is default JavaFX behaviour
       Controller.getInstance().initUI(args);
    }

}
