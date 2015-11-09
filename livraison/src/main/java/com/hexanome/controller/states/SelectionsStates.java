/**
 * Meta-state for all interactions on map nodes
 */
package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.UIManager;
import com.hexanome.view.ConstView;

/**
 * This abstract class provides common code for logic states when 
 * a map and a planning are loaded
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public abstract class SelectionsStates extends DefaultState {

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadMap()
     */
    @Override
    public void btnLoadMap() {
        // WARNING : calls order matters
        if (UIManager.getInstance().askConfirmation("Current map and planning will be lost.")) {

            // Full clear of the model
            ModelManager.getInstance().clearModel();
            UIManager.getInstance().getMainWindow().getMapView().clearMap();

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
        if (UIManager.getInstance().askConfirmation("Current planning will be lost.")) {

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
        if (UIManager.getInstance().askConfirmation("Current map and planning will be lost.")) {

            UIManager.getInstance().getMainWindow().setLoadingState("Closing Map...");

            // Full clear model
            ModelManager.getInstance().clearModel();
            UIManager.getInstance().getMainWindow().getMapView().clearMap();
            UIManager.getInstance().getMainWindow().getDeliveryTreeView().clearTree();

            // Clear commands history
            ContextManager.getInstance().clearCommandsHistory();

            UIManager.getInstance().getMainWindow().endLoadingState();

            // Jump to InitState
            ContextManager.getInstance().setCurrentState(InitState.getInstance());
        }
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnClearPlanning()
     */
    @Override
    public void btnClearPlanning() {
        if (UIManager.getInstance().askConfirmation("Current planning will be lost.")) {

            UIManager.getInstance().getMainWindow().setLoadingState("Clearing Planning...");

            // Clear model's current planning
            ModelManager.getInstance().clearPlanning();
            UIManager.getInstance().getMainWindow().getDeliveryTreeView().clearTree();

            // Clear commands history
            ContextManager.getInstance().clearCommandsHistory();

            UIManager.getInstance().getMainWindow().endLoadingState();

            // Jump to MapLoadedState
            ContextManager.getInstance().setCurrentState(MapLoadedState.getInstance());
        }
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#leftClickPressedOnDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void leftClickPressedOnDelivery() {
        // Hide popover
        UIManager.getInstance().getMainWindow().getMapView().hidePopOver();
        // Jump to SwapDeliveryState
        ContextManager.getInstance().setCurrentState(SwapDeliveriesState.getInstance());
    }

    @Override
    public void initView() {
        super.initView();
        UIManager.getInstance().getMainWindow().enableButton(ConstView.Button.CLEAR_PLANNING);
        UIManager.getInstance().getMainWindow().enableButton(ConstView.Button.CLEAR_MAP);
        UIManager.getInstance().getMainWindow().enableButton(ConstView.Button.LOAD_PLANNING);
        UIManager.getInstance().getMainWindow().enableButton(ConstView.Button.ROAD_MAP);
        // Enable Drag-and-drop
        UIManager.getInstance().getMainWindow().getDeliveryTreeView().enableDragAndDrop(true);
    }
}
