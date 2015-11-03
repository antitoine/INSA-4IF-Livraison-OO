package com.hexanome.controller;

import com.hexanome.model.Map;
import com.hexanome.model.Planning;
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
    public String initModelMap(MapDocument mapDoc) {
        String s = null;
        if (map == null) {
            // Map creation
            map = new Map();
            if (mapDoc.checkIntegrity()) {
                mapDoc.fillMap(map);
            } else {
                s = mapDoc.getErrorMsg();
            }
        } else {
            s = "ModelManager: a map already exists !";
        }
        return s;
    }

    /**
     * Initialize model's Planning with the given PlanningDocument
     *
     * @param planDoc PlanningDocument used to fill Planning object
     * @return false if a map doesn't exists in the model or a planning already
     * exists in the model, it also returns false if the integrity of the
     * PlanningDocument is compromised, else it will return true.
     */
    public String initModelPlanning(PlanningDocument planDoc) {
        String s = null;
        if (map != null && planning == null) {
            // Planning creation
            if (planDoc.checkIntegrity(map)) { // TODO : always true  
                planning = new Planning(map, planDoc.getWarehouse(map), planDoc.getTimeSlots(map));
            } else {
                s = planDoc.getErrorMsg();
            }
        } else {
            s = "ModelManager: map wasn't initialized or a planning already exists !";
        }
        // removeMeLater : démarrer directement un premier calcul de route ?
        return s;
    }

    /**
     * Clears the Model
     */
    public void clearModel() {
        map = null;
        planning = null;
    }

    /**
     * Clears the map
     */
    public void clearPlanning() {
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
