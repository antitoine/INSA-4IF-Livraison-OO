/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    MapDocument getMapDocument(String filename) {
        return DocumentFactory.createMapDocument(filename);
    }
    
    PlanningDocument getPlanningDocument(String filename) {
        return DocumentFactory.createPlanningDocument(filename);
    }
    
    boolean saveRouteDocument(String filename, Route route) {
        RouteDocument rdoc = DocumentFactory.createRouteDocument(filename);
        rdoc.writeRoute(route);
        return rdoc.save();
    }
}
