/**
 * 
 */
package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.UIManager;

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
        return planningLoadedState;
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadMap()
     */
    @Override
    public void btnLoadMap() {
        // \todo Afficher par la vue un message comme quoi tout est perdu avant de changer d'état
        ModelManager modelManager = ModelManager.getInstance(); 
        modelManager.clearModel();
        modelManager.clearPlanning(); //removeMeLater : redundancy : clearModel also clears the planning
        ContextManager.getInstance().setCurrentState(MapSelectState.getInstance());
        UIManager.getInstance().getMainWindow().askFile();
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadPlanning()
     */
    @Override
    public void btnLoadPlanning() {
        // \todo Afficher par la vue un message comme quoi tout est perdu avant de changer d'état
        ModelManager.getInstance().clearPlanning();
        ContextManager.getInstance().setCurrentState(PlanningSelectState.getInstance());
        UIManager.getInstance().getMainWindow().askFile();
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCloseMap()
     */
    @Override
    public void btnCloseMap() {
        // \todo Afficher par la vue un message comme quoi tout est perdu avant de changer d'état
        ModelManager modelManager = ModelManager.getInstance(); 
        modelManager.clearModel();
        modelManager.clearPlanning(); //removeMeLater : redundancy : clearModel also clears the planning
        ContextManager.getInstance().setCurrentState(InitState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnClearPlanning()
     */
    @Override
    public void btnClearPlanning() {
        // \todo Afficher par la vue un message comme quoi tout est perdu avant de changer d'état
        ModelManager.getInstance().clearPlanning();
        ContextManager.getInstance().setCurrentState(MapLoadedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnGenerateRoute()
     */
    @Override
    public void btnGenerateRoute() {
        // \todo Afficher une boite de dialogue d'attente pendant le calcul de la route 
        ModelManager.getInstance().getPlanning().computeRoute();
        // \todo Récupérer eventuellement une erreur de l'instruction précédente si aucune route n'a été trouvée.
        ContextManager.getInstance().setCurrentState(NothingSelectedState.getInstance());
    }

}
