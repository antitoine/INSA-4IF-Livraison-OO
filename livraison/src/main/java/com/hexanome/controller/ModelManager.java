package com.hexanome.controller;

import com.hexanome.model.Map;
import com.hexanome.model.Planning;
import com.hexanome.model.Route;
import com.hexanome.utils.MapDocument;
import com.hexanome.utils.PlanningDocument;

/**
 * This class manages the model of the application, it is responsible of
 * instanciating objects of the model and guarantee model's consistency
 *
 * @author paul
 */
public class ModelManager {

    private static ModelManager modelManager = null;
    private Map map = null;
    private Planning planning = null;

    private ModelManager() {
        // Nothing to do here for now
    }

    /**
     * Return the instance of the ModelManager in the application, ModelManager
     * is a Singleton
     *
     * @return
     */
    public static ModelManager getInstance() {
        if (modelManager == null) {
            modelManager = new ModelManager();
        }
        return modelManager;
    }

    /**
     * Initialize model's Map with the given MapDocument
     *
     * @param mapDoc MapDocument used to fill Map object
     * @return false if a map already exists in the model or the integrity of
     * MapDocument is compromised, else it will return true.
     */
    public boolean initModelMap(MapDocument mapDoc) {
        if (map == null) {
            // Map creation
            map = new Map();
            if (mapDoc.checkIntegrity()) {
                mapDoc.fillMap(map);
            } else {
                // \todo treat error case
                return false;
            }
        } else {
            // \todo treat error case
            return false;
        }
        // removeMeLater : démarrer directement un premier calcul de route ?
        return true;
    }

    /**
     * Initialize model's Planning with the given PlanningDocument
     *
     * @param planDoc PlanningDocument used to fill Planning object
     * @return false if a map doesn't exists in the model or a planning already
     * exists in the model, it also returns false if the integrity of the
     * PlanningDocument is compromised, else it will return true.
     */
    public boolean initModelPlanning(PlanningDocument planDoc) {
        if (map != null && planning == null) {
            // Planning creation
            if (planDoc.checkIntegrity(map)) { // TODO : always true  
                planning = new Planning(map, planDoc.getWarehouse(map), planDoc.getTimeSlots(map));
            } else {
                // \todo treat error case
                return false;
            }
        } else {
            // \todo treat error case
            return false;
        }
        // removeMeLater : démarrer directement un premier calcul de route ?
        return true;
    }

    /**
     * Clears the model
     */
    public void clearModel() {
        map = null;
        planning = null;
    }

    /**
     * Returns model's map
     *
     * @return
     */
    public Map getMap() {
        return map;
    }

    /**
     *
     * @return
     */
    public Planning getPlanning() {
        return planning;
    }
}
