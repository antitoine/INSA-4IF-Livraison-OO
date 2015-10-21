package com.hexanome.livraison;

import com.hexanome.controller.UIManager;
import static javafx.application.Application.launch;

public class MainApp {

    /** 
     * Main Method : start the application
     * launch the main controller and the view
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         
        // launch the UI Manager to display the windows
        // This is default JavaFX behaviour
        launch(UIManager.class, args);
    }

}
