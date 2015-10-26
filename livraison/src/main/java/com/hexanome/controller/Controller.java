package com.hexanome.controller;
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
