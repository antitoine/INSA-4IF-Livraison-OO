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
    
    /** The unique instance of IOManager. */
    private static IOManager iomanager = null;

    /**
     * Builds a new instance of an IOmanager.
     */
    private IOManager() {
        // Nothing to do here but it's a private method.
    }

    /**
     * @return The instance of the IOManager, which is a singleton.
     */
    public static IOManager getInstance() {
        if (iomanager == null) {
            iomanager = new IOManager();
        }
        return iomanager;
    }

    /**
     * Returns a MapDocument built using the given file describing a map.
     *
     * @param file File to used to build MapDocument object.
     * @return The MapDocument newly created.
     */
    public MapDocument getMapDocument(File file) {
        return DocumentFactory.createMapDocument(file);
    }

    /**
     * Returns a PlanningDocument built using the given file describing a
     * planning.
     *
     * @param file File used to build PlanningDocument object.
     * @return The PlanningDocument newly created.
     */
    public PlanningDocument getPlanningDocument(File file) {
        return DocumentFactory.createPlanningDocument(file);
    }

    /**
     * Saves the current route of the model into a file.
     *
     * @param file File to write.
     * @param text The text to write in the file.
     * @return True if the file was successfully written, else it returns false.
     */
    boolean saveRouteDocument(File file, String text) {
        RouteDocument rdoc = DocumentFactory.createRouteDocument(file);
        rdoc.writeRoute(text);
        return rdoc.save();
    }
}
