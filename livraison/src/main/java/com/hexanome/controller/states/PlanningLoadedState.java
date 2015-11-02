/**
 * 
 */
package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.UIManager;
import com.hexanome.view.ConstView;

/**
 * @author antitoine
 *
 */
public class PlanningLoadedState extends DefaultState {

    private static PlanningLoadedState planningLoadedState = null;

    private PlanningLoadedState(){
        // Nothing to do here
    }

    /**
     * Returns the instance of the PlanningLoadedState,
     * it is a singleton
     * @return The instance of PlanningLoadedState
     */
    public static PlanningLoadedState getInstance() {
        if(planningLoadedState == null)
        {
            planningLoadedState = new PlanningLoadedState();
        }
        UIManager.getInstance().getMainWindow().enableButton(ConstView.Button.COMPUTE_ROUTE);
        UIManager.getInstance().getMainWindow().disableButton(ConstView.Button.ROAD_MAP);
        return planningLoadedState;
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadMap()
     */
    @Override
    public void btnLoadMap() {
        // WARNING : calls order matters
        // \todo Afficher par la vue un message comme quoi tout est perdu avant de changer d'état
        // Get ModelManager instance
        ModelManager modelManager = ModelManager.getInstance(); 
        // Full clear model
        modelManager.clearModel();
        // Jump to MapSelectState
        ContextManager.getInstance().setCurrentState(MapSelectState.getInstance());
        // Ask user for the map to load
        UIManager.getInstance().getMainWindow().askFile();
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadPlanning()
     */
    @Override
    public void btnLoadPlanning() {
        // WARNING : calls order matters
        // \todo Afficher par la vue un message comme quoi tout est perdu avant de changer d'état
        // Clear current model's planning
        ModelManager.getInstance().clearPlanning();
        // Jump to PlanningSelectState
        ContextManager.getInstance().setCurrentState(PlanningSelectState.getInstance());
        // Ask user for planning file to load
        UIManager.getInstance().getMainWindow().askFile();
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCloseMap()
     */
    @Override
    public void btnCloseMap() {
        // \todo Afficher par la vue un message comme quoi tout est perdu avant de changer d'état
        // Get model manager instance
        ModelManager modelManager = ModelManager.getInstance(); 
        // Full clear model
        modelManager.clearModel();
        // Jump to InitState
        ContextManager.getInstance().setCurrentState(InitState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnClearPlanning()
     */
    @Override
    public void btnClearPlanning() {
        // \todo Afficher par la vue un message comme quoi tout est perdu avant de changer d'état
        // Clear model's current planning
        ModelManager.getInstance().clearPlanning();
        // Jump to MapLoadedState
        ContextManager.getInstance().setCurrentState(MapLoadedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnGenerateRoute()
     */
    @Override
    public void btnGenerateRoute() {
        // Launch asynchronous Route computation algorithm
        ModelManager.getInstance().getPlanning().computeRoute();
        // \todo Catch potential errors, for instance, no Route found 
        // Jump to ComputingRouteState
        ContextManager.getInstance().setCurrentState(ComputingRouteState.getInstance());
    }

    @Override
    public String toString() {
        return "PlanningLoadedState"; //To change body of generated methods, choose Tools | Templates.
    }

}
