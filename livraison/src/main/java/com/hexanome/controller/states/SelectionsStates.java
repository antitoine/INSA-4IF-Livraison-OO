package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.UIManager;
import com.hexanome.view.ConstView;

/**
 * This abstract class provides common code for logic states when
 * a map and a planning are loaded.
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

            ModelManager.getInstance().clearModel();
            UIManager.getInstance().getMainWindow().getMapView().clearMap();
            UIManager.getInstance().getMainWindow().getDeliveryTreeView().clearTree();
            UIManager.getInstance().getMainWindow().clearLegend();

            ContextManager.getInstance().clearCommandsHistory();

            ContextManager.getInstance().setCurrentState(MapSelectState.getInstance());

            UIManager.getInstance().getMainWindow().askFile();
        }
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadPlanning()
     */
    @Override
    public void btnLoadPlanning() {
        // WARNING : calls order matters
        if (UIManager.getInstance().askConfirmation("Current planning will be lost.")) {

            ModelManager.getInstance().clearPlanning();

            ContextManager.getInstance().clearCommandsHistory();

            ContextManager.getInstance().setCurrentState(PlanningSelectState.getInstance());

            UIManager.getInstance().getMainWindow().askFile();
        }
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#leftClickPressedOnDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void leftClickPressedOnDelivery() {
        UIManager.getInstance().getMainWindow().getMapView().hidePopOver();
        ContextManager.getInstance().setCurrentState(SwapDeliveriesState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCloseMap()
     */
    @Override
    public void btnCloseMap() {
        if (UIManager.getInstance().askConfirmation("Current map and planning will be lost.")) {

            UIManager.getInstance().getMainWindow().setLoadingState("Closing Map...");

            ModelManager.getInstance().clearModel();
            UIManager.getInstance().getMainWindow().getMapView().clearMap();
            UIManager.getInstance().getMainWindow().getDeliveryTreeView().clearTree();
            UIManager.getInstance().getMainWindow().clearLegend();

            ContextManager.getInstance().clearCommandsHistory();

            UIManager.getInstance().getMainWindow().endLoadingState();

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

            ModelManager.getInstance().clearPlanning();
            UIManager.getInstance().getMainWindow().getDeliveryTreeView().clearTree();
            UIManager.getInstance().getMainWindow().clearLegend();

            ContextManager.getInstance().clearCommandsHistory();

            UIManager.getInstance().getMainWindow().endLoadingState();

            ContextManager.getInstance().setCurrentState(MapLoadedState.getInstance());
        }
    }

    @Override
    public void initView() {
        super.initView();
        UIManager.getInstance().getMainWindow().setEnableButton(ConstView.Button.CLEAR_PLANNING, true);
        UIManager.getInstance().getMainWindow().setEnableButton(ConstView.Button.CLEAR_MAP, true);
        UIManager.getInstance().getMainWindow().setEnableButton(ConstView.Button.LOAD_PLANNING, true);
        UIManager.getInstance().getMainWindow().setEnableButton(ConstView.Button.ROAD_MAP, true);

        UIManager.getInstance().getMainWindow().getDeliveryTreeView().enableDragAndDrop(true);
    }
}
