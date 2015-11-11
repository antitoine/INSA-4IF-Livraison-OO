package com.hexanome.controller;

import com.hexanome.model.Map;
import com.hexanome.model.Planning;
import com.hexanome.utils.DocumentFactory;
import com.hexanome.utils.MapDocument;
import com.hexanome.utils.PlanningDocument;

/**
 * This class manages the model of the application, it is responsible of
 * instanciating objects of the model and guarantee model's consistency
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class ModelManager {

    /** Unique instance of ModelManager. */
    private static ModelManager modelManager = null;
    
    /** The current map managed by the ModelManager. */
    private Map map = null;
    
    /** The current planning managed by the ModelManager. */
    private Planning planning = null;

    /**
     * Builds a new ModelManager instance.
     */
    private ModelManager() {
        // Nothing to do here, but it's a private method.
    }

    /**
     * @return The instance of the ModelManager in the application, which
     * is a Singleton.
     */
    public static ModelManager getInstance() {
        if (modelManager == null) {
            modelManager = new ModelManager();
        }
        return modelManager;
    }

    /**
     * Initializes model's Map with the given MapDocument.
     *
     * @param mapDoc MapDocument used to fill Map object.
     * @return An error message if a map already exists in the model or if the 
     * integrity of MapDocument is compromised, else it will return null.
     */
    public String initModelMap(MapDocument mapDoc) {
        String errorMsg = null;
        if (mapDoc != null) {
            if (map == null) {
                map = new Map();
                if (mapDoc.checkIntegrity()) {
                    mapDoc.fillMap(map);
                } else {
                    errorMsg = mapDoc.getErrorMsg();
                }
            } else {
                errorMsg = "ModelManager: a map already exists !";
            }
        } else {
            errorMsg = DocumentFactory.getLastError();
        }
        return errorMsg;
    }

    /**
     * Initializes model's Planning with the given PlanningDocument.
     *
     * @param planDoc PlanningDocument used to fill Planning object
     * @return An error message if a map doesn't exists in the model or if a 
     * planning already exists in the model. It also returns an error message if
     * the integrity of the PlanningDocument is compromised, else it will return
     * null.
     */
    public String initModelPlanning(PlanningDocument planDoc) {
        String errorMsg = null;
        if (planDoc != null) {
            if (map != null && planning == null) {
                if (planDoc.checkIntegrity(map)) {
                    planning = new Planning(map, planDoc.getWarehouse(map), 
                                    planDoc.getTimeSlots(map));
                } else {
                    errorMsg = planDoc.getErrorMsg();
                }
            } else {
                errorMsg = "ModelManager: map wasn't initialized or a planning already exists !";
            }
        } else {
            errorMsg = DocumentFactory.getLastError();
        }
        return errorMsg;
    }

    /**
     * Clears the Model.
     */
    public void clearModel() {
        map = null;
        planning = null;
    }

    /**
     * Clears the planning.
     */
    public void clearPlanning() {
        planning = null;
        map.resetArcs();
        map.resetNodes();
        map.notifySubscribers();
    }

    /**
     * @return The model's map, currently managed by the ModelManager.
     */
    public Map getMap() {
        return map;
    }

    /**
     * @return The model's planning, currently managed by the ModelManager.
     */
    public Planning getPlanning() {
        return planning;
    }
}
