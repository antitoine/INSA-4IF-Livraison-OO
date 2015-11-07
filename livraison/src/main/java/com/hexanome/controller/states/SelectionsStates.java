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
            // Clear commands history
            ContextManager.getInstance().clearCommandsHistory();
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
            // Clear planning in model
            ModelManager.getInstance().clearPlanning();
            // Clear commands history
            ContextManager.getInstance().clearCommandsHistory();
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
        // Full clear model
        ModelManager.getInstance().clearModel();
        // Clear commands history
        ContextManager.getInstance().clearCommandsHistory();
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
        // Clear commands history
        ContextManager.getInstance().clearCommandsHistory();
        // Jump to MapLoadedState
        ContextManager.getInstance().setCurrentState(MapLoadedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#leftClickPressedOnDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void leftClickPressedOnDelivery() {
        // Jump to SwapDeliveryState
        ContextManager.getInstance().setCurrentState(SwapDeliveriesState.getInstance());
    }

}
