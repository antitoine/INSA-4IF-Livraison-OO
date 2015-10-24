package com.hexanome.controller;

import com.hexanome.model.Route;
import com.hexanome.utils.DocumentFactory;
import com.hexanome.utils.MapDocument;
import com.hexanome.utils.PlanningDocument;
import com.hexanome.utils.RouteDocument;
import java.io.File;

/**
 *
 * @author paul
 */
public class IOManager {
    private static IOManager iomanager = null;
    /**
     * 
     */
    private IOManager() {
        // Nothing to do here for now
    }
    /**
     * 
     * @return 
     */
    public static IOManager getInstance() {
        if(iomanager == null)
        {
            iomanager = new IOManager();
        }
        return iomanager;
    }
    
    MapDocument getMapDocument(File file) {
        return DocumentFactory.createMapDocument(file);
    }
    
    PlanningDocument getPlanningDocument(File file) {
        return DocumentFactory.createPlanningDocument(file);
    }
    
    boolean saveRouteDocument(String filename, Route route) {
        RouteDocument rdoc = DocumentFactory.createRouteDocument(filename);
        rdoc.writeRoute(route);
        return rdoc.save();
    }
}
