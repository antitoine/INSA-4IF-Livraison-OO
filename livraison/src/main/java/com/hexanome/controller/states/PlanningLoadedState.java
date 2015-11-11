package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.UIManager;
import com.hexanome.view.ConstView;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * This class represents the logic state when a planning has been loaded.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class PlanningLoadedState extends DefaultState implements EventHandler {

    /**
     * The unique instance of this class.
     */
    private static PlanningLoadedState planningLoadedState = null;

    private PlanningLoadedState() {
        // Nothing to do here
    }

    /**
     * Returns the instance of the PlanningLoadedState, which is a singleton.
     *
     * @return The instance of PlanningLoadedState
     */
    public static PlanningLoadedState getInstance() {
        if (planningLoadedState == null) {
            planningLoadedState = new PlanningLoadedState();
        }
        return planningLoadedState;
    }

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

            UIManager.getInstance().getMainWindow().getDeliveryTreeView().clearTree();
            UIManager.getInstance().getMainWindow().clearLegend();

            ContextManager.getInstance().setCurrentState(PlanningSelectState.getInstance());

            UIManager.getInstance().getMainWindow().askFile();
        }
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnGenerateRoute()
     */
    @Override
    public void btnGenerateRoute() {
        ContextManager.getInstance().setCurrentState(ComputingRouteState.getInstance());

        // Launch asynchronous Route computation algorithm
        UIManager.getInstance().beginComputingRoute();
        ModelManager.getInstance().getPlanning().computeRoute(this);
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

            UIManager.getInstance().getMainWindow().endLoadingState();

            ContextManager.getInstance().setCurrentState(MapLoadedState.getInstance());
        }
    }

    /**
     * Returns the string describing the state, used for debug only
     *
     * @return a string describing the state
     */
    @Override
    public String toString() {
        return "PlanningLoadedState";
    }

    /**
     * Handler for the end of the route computing.
     *
     * @param event Event handled at the end of route computing.
     */
    @Override
    public void handle(Event event) {
        ContextManager.getInstance().setCurrentState(NothingSelectedState.getInstance());

        UIManager.getInstance().getMainWindow().setEnableButton(ConstView.Button.ROAD_MAP, true);
        UIManager.getInstance().getMainWindow().setEnableButton(ConstView.Button.COMPUTE_ROUTE, false);

        UIManager.getInstance().endRouteComputation();
    }

    @Override
    public void initView() {
        super.initView();
        
        UIManager.getInstance().getMainWindow().setEnableButton(ConstView.Button.CLEAR_PLANNING, true);
        UIManager.getInstance().getMainWindow().setEnableButton(ConstView.Button.CLEAR_MAP, true);
        UIManager.getInstance().getMainWindow().setEnableButton(ConstView.Button.LOAD_PLANNING, true);
        UIManager.getInstance().getMainWindow().setEnableButton(ConstView.Button.COMPUTE_ROUTE, true);
    }
}
