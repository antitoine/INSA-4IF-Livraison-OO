/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.controller;

import com.hexanome.model.Map;
import com.hexanome.model.Planning;
import com.hexanome.model.Route;
import com.hexanome.utils.MapDocument;
import com.hexanome.utils.PlanningDocument;

/**
 *
 * @author paul
 */
public class ModelManager {
    private static ModelManager modelManager = null;
    private Map map = null;
    private Planning planning = null;
    private Route route = null;
    /**
     * 
     */
    private ModelManager() {
        // Nothing to do here for now
    }
    /**
     * 
     * @return 
     */
    public static ModelManager getInstance() {
        if(modelManager == null)
        {
            modelManager = new ModelManager();
        }
        return modelManager;
    }
    /**
     * 
     * @param mapDoc
     * @param planDoc
     * @return 
     */
    boolean initModel(MapDocument mapDoc, PlanningDocument planDoc) {
        // Map creation
        map = new Map();
        if(mapDoc.checkIntegrity())
        {   
            mapDoc.fillMap(map);
        }
        else
        {
            // \todo treat error case
            return false;
        }
        // Planning creation
        if(planDoc.checkIntegrity(map))
        {
            planning = new Planning(map, planDoc.getWarehouse(map), planDoc.getTimeSlots(map));
        }
        else
        {
            // \todo treat error case
            return false;
        }
        // removeMeLater : démarrer directement un premier calcul de route ?
        return true;
    }
    /**
     * 
     */
    void clearModel() {
        map = null;
        planning = null;
        route = null;
    }
    /**
     * 
     * @return 
     */
    Map getMap() {
        return map;
    }
    /**
     * 
     * @return 
     */
    Planning getPlanning() {
       return planning; 
    }
    /**
     * 
     * @return 
     */
    Route getRoute() {
        // \todo
        return null;  
    }
}
