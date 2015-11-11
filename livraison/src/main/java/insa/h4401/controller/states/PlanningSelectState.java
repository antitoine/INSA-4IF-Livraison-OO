package insa.h4401.controller.states;

import insa.h4401.controller.ContextManager;
import insa.h4401.controller.IOManager;
import insa.h4401.controller.ModelManager;
import insa.h4401.controller.UIManager;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;

/**
 * This class represents the logic state when user is selecting a planning to
 * load.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class PlanningSelectState extends DefaultState {

    /**
     * The unique instance of this class.
     */
    private static PlanningSelectState planningSelectState = null;

    private PlanningSelectState() {
        // Nothing to do here
    }

    /**
     * Returns the instance of the PlanningSelectState, it is a singleton.
     *
     * @return The instance of PlanningSelectState
     */
    public static PlanningSelectState getInstance() {
        if (planningSelectState == null) {
            planningSelectState = new PlanningSelectState();
        }
        return planningSelectState;
    }

    /* (non-Javadoc)
     * @see IState#btnCancel()
     */
    @Override
    public void btnCancel() {
        ModelManager.getInstance().clearPlanning();
        UIManager.getInstance().getMainWindow().getDeliveryTreeView().clearTree();

        ContextManager.getInstance().setCurrentState(MapLoadedState.getInstance());
    }

    /* (non-Javadoc)
     * @see IState#btnValidateFile(java.io.File)
     */
    @Override
    public void btnValidateFile(final File file) {

        UIManager.getInstance().beginLoadPlanning();

        /**
         * The task is created in a different thread. A listenner listen the
         * result of the thread and executes the right commands if the task
         * succeds
         *
         * Otherwise, all the task listed here will be executed in the UI Thread
         */
        final Service<String> loadService = new Service<String>() {
            @Override
            protected Task<String> createTask() {
                return new Task<String>() {
                    @Override
                    protected String call() throws Exception {
                        return ModelManager.getInstance().initModelPlanning(
                                IOManager.getInstance().getPlanningDocument(
                                        file
                                )
                        );
                    }
                };
            }
        };

        loadService.stateProperty()
                .addListener((observableValue, oldValue, newValue) -> {
                    switch (newValue) {
                        case SUCCEEDED:
                            if (loadService.getValue() != null) {
                                ModelManager.getInstance().clearPlanning();
                                ContextManager.getInstance().setCurrentState(
                                        MapLoadedState.getInstance()
                                );
                                UIManager.getInstance().showError(
                                        loadService.getValue()
                                );
                            } else {
                                ContextManager.getInstance().setCurrentState(
                                        PlanningLoadedState.getInstance()
                                );
                                UIManager.getInstance().endLoadPlanning();
                            }
                            break;
                    }
                });
        loadService.start();

    }

    @Override
    public void initView() {
        // Nothing to do here
        // Override to not disable all buttons
    }

    /**
     * Returns the string describing the state, used for debug only
     *
     * @return a string describing the state
     */
    @Override
    public String toString() {
        return "PlanningSelectState";
    }
}
