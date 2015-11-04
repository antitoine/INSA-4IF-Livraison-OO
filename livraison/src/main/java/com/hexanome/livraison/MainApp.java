package com.hexanome.livraison;

import com.hexanome.controller.Controller;
import com.hexanome.model.Arc;
import com.hexanome.model.Delivery;
import com.hexanome.model.Map;
import com.hexanome.model.ModelTest;
import com.hexanome.model.Node;
import com.hexanome.model.Path;
import com.hexanome.model.Planning;
import com.hexanome.model.Route;
import com.hexanome.model.TimeSlot;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
