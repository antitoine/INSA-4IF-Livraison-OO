package com.hexanome.controller;

import com.hexanome.utils.DocumentFactory;
import com.hexanome.utils.MapDocument;
import com.hexanome.utils.PlanningDocument;
import com.hexanome.utils.RouteDocument;
import java.io.File;

/**
 * This class manages Input/Output interactions with the File System
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class IOManager {
    private static IOManager iomanager = null;
  
    /**
     * Builds a new instance of an IOmanager
     */
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
     * @return the MapDocument created
     */
    public MapDocument getMapDocument(File file) {
        return DocumentFactory.createMapDocument(file);
    }
    /**
     * Returns a PlanningDocument built using the given file
     * @param file
     *      file used to build PlanningDocument object
     * @return the PlanningDocument created
     */
    public PlanningDocument getPlanningDocument(File file) {
        return DocumentFactory.createPlanningDocument(file);
    }
    /**
     * Save the current route of the model into a file.
     * @param file
     *      file to write
     * @return true if the file was successfully written, else it returns false
     */
    boolean saveRouteDocument(File file) {
        RouteDocument rdoc = DocumentFactory.createRouteDocument(file);
        rdoc.writeRoute(ModelManager.getInstance().getPlanning().getRoute());
        return rdoc.save();
    }
}
