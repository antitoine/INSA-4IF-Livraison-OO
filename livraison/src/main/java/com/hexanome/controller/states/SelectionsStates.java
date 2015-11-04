/**
 * Meta-state for all interactions on map nodes
 */
package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.UIManager;
import com.hexanome.model.Delivery;

/**
 * @author antitoine \todo TODO
 */
public abstract class SelectionsStates extends DefaultState {

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadMap()
     */
    @Override
    public void btnLoadMap() {
        // WARNING : calls order matters
        if (UIManager.getInstance().askConfirmation("Planning and Map will be lost forever")) {
            // Full clear of the model
            ModelManager.getInstance().clearModel();
            // Jump to MapSelectState
            ContextManager.getInstance().setCurrentState(MapSelectState.getInstance());
            // Ask user for a file to load
            UIManager.getInstance().getMainWindow().askFile();
        }
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadPlanning()
     */
    @Override
    public void btnLoadPlanning() {
        // WARNING : calls order matters
        // Clear current model's planning
        if (UIManager.getInstance().askConfirmation("Planning will be lost forever")) {
            ModelManager.getInstance().clearPlanning();
            // Jump to PlanningSelectState
            ContextManager.getInstance().setCurrentState(PlanningSelectState.getInstance());
            // Ask user for planning file to load
            UIManager.getInstance().getMainWindow().askFile();
        }
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
     * @see com.hexanome.controller.states.IState#leftClickPressedOnDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void leftClickPressedOnDelivery(Delivery delivery) {
        // Jump to SwapDeliveryState
        ContextManager.getInstance().setCurrentState(SwapDeliveriesState.getInstance());
    }

}
