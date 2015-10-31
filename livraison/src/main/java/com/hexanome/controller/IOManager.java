package com.hexanome.controller;

import com.hexanome.model.Route;
import com.hexanome.utils.DocumentFactory;
import com.hexanome.utils.MapDocument;
import com.hexanome.utils.PlanningDocument;
import com.hexanome.utils.RouteDocument;
import java.io.File;

/**
 * This class manages Input/Output interactions with the File System
 * @author paul
 */
public class IOManager {
    private static IOManager iomanager = null;
  
    private IOManager() {
        // Nothing to do here for now
    }
    /**
     * Returns the instance of the IOManager,
     * it is a singleton
     * @return 
     */
    public static IOManager getInstance() {
        if(iomanager == null)
        {
            iomanager = new IOManager();
        }
        return iomanager;
    }
    /**
     *  Returns a MapDocument built using the given file 
     * @param file
     *      file to used to build MapDocument object
     * @return 
     */
    public MapDocument getMapDocument(File file) {
        return DocumentFactory.createMapDocument(file);
    }
    /**
     * Returns a PlanningDocument built using the given file
     * @param file
     *      file used to build PlanningDocument object
     * @return 
     */
    public PlanningDocument getPlanningDocument(File file) {
        return DocumentFactory.createPlanningDocument(file);
    }
    /**
     * Save the given route into a file named using filename.
     * @param filename
     *      Name of the file to write
     * @param route
     *      Route to write in the file
     * @return true if the file was successfully written, else it returns false
     */
    boolean saveRouteDocument(String filename, Route route) {
        RouteDocument rdoc = DocumentFactory.createRouteDocument(filename);
        rdoc.writeRoute(route);
        return rdoc.save();
    }
}
