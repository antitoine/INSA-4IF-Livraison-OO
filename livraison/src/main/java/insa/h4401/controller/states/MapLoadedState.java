package insa.h4401.controller.states;

import insa.h4401.controller.ContextManager;
import insa.h4401.controller.ModelManager;
import insa.h4401.controller.UIManager;
import insa.h4401.view.ConstView;

/**
 * This class represents the logic state when a map has been loaded.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class MapLoadedState extends DefaultState {

    /** The unique instance of this class. */
    private static MapLoadedState mapLoadedState = null;

    private MapLoadedState() {
        // Nothing to do here
    }

    /**
     * Returns the instance of the MapLoadedState, which is a singleton.
     *
     * @return The instance of MapLoadedState
     */
    public static MapLoadedState getInstance() {
        if (mapLoadedState == null) {
            mapLoadedState = new MapLoadedState();
        }
        return mapLoadedState;
    }

    /* (non-Javadoc)
     * @see IState#btnLoadMap()
     */
    @Override
    public void btnLoadMap() {
        // WARNING : calls order matters
        if (UIManager.getInstance().askConfirmation("Current map and planning will be lost.")) {

            ModelManager.getInstance().clearModel();
            UIManager.getInstance().getMainWindow().getMapView().clearMap();

            ContextManager.getInstance().setCurrentState(MapSelectState.getInstance());

            UIManager.getInstance().getMainWindow().askFile();
        }
    }

    /* (non-Javadoc)
     * @see IState#btnLoadPlanning()
     */
    @Override
    public void btnLoadPlanning() {
        // WARNING : calls order matters
        ContextManager.getInstance().setCurrentState(PlanningSelectState.getInstance());
        UIManager.getInstance().getMainWindow().askFile();
    }

    /* (non-Javadoc)
     * @see IState#btnCloseMap()
     */
    @Override
    public void btnCloseMap() {
        if (UIManager.getInstance().askConfirmation("Current map and planning will be lost.")) {

            UIManager.getInstance().getMainWindow().setLoadingState("Closing Map...");

            ModelManager.getInstance().clearModel();
            UIManager.getInstance().getMainWindow().getMapView().clearMap();

            UIManager.getInstance().getMainWindow().endLoadingState();

            ContextManager.getInstance().setCurrentState(InitState.getInstance());
        }
    }

    /* (non-Javadoc)
     * @see IState#initView()
     */
    @Override
    public void initView() {
        super.initView();
        UIManager.getInstance().getMainWindow().setEnableButton(ConstView.Button.CLEAR_MAP, true);
        UIManager.getInstance().getMainWindow().setEnableButton(ConstView.Button.LOAD_PLANNING, true);
    }

    /**
     * Returns the string describing the state, used for debug only
     *
     * @return a string describing the state
     */
    @Override
    public String toString() {
        return "MapLoadedState";
    }
}
