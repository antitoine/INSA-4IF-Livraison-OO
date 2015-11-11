package insa.h4401.delivery;

import insa.h4401.controller.Controller;

import java.util.logging.Level;
import java.util.logging.Logger;

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
     */
    public static void main(String[] args) {
        // Define logging level
        Logger globalLogger = Logger.getLogger("");
        globalLogger.setLevel(Level.SEVERE);

        // Init managers by instanciating singletons
        Controller.getInstance().initManagers();
        
        // Launch the UI Manager to display the windows
        Controller.getInstance().initUI(args);
    }
}
