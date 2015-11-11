package insa.h4401.controller.states;

import insa.h4401.controller.ContextManager;
import insa.h4401.controller.IOManager;
import insa.h4401.controller.ModelManager;
import insa.h4401.controller.UIManager;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;

/**
 * This class represents the logic state when the user is selecting a map to
 * load.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class MapSelectState extends DefaultState {

    /**
     * The unique instance of this class.
     */
    private static MapSelectState mapSelectState = null;

    private MapSelectState() {
        // Nothing to do here
    }

    /**
     * Returns the instance of the MapSelectState, which is a singleton.
     *
     * @return The instance of MapSelectState.
     */
    public static MapSelectState getInstance() {
        if (mapSelectState == null) {
            mapSelectState = new MapSelectState();
        }
        return mapSelectState;
    }

    /* (non-Javadoc)
     * @see IState#btnCancel()
     */
    @Override
    public void btnCancel() {
        ContextManager.getInstance().setCurrentState(InitState.getInstance());
    }

    /* (non-Javadoc)
     * @see IState#btnValidateFile(java.io.File)
     */
    @Override
    public void btnValidateFile(final File file) {

        UIManager.getInstance().beginLoadMap();

        /**
         * The task is created in a different thread. A listenner listen the
         * result of the thread and executes the right commands if the task
         * succeds.
         *
         * Otherwise, all the task listed here will be executed in the UI Thread
         */
        final Service<String> loadService = new Service<String>() {
            @Override
            protected Task<String> createTask() {
                return new Task<String>() {
                    @Override
                    protected String call() throws Exception {
                        return ModelManager.getInstance().initModelMap(
                                IOManager.getInstance().getMapDocument(file)
                        );
                    }
                };
            }
        };

        loadService.stateProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    switch (newValue) {
                        case SUCCEEDED:
                            if (loadService.getValue() != null) {
                                ModelManager.getInstance().clearModel();
                                ContextManager.getInstance().setCurrentState(
                                        InitState.getInstance()
                                );
                                UIManager.getInstance().showError(
                                        loadService.getValue()
                                );
                            } else {
                                ContextManager.getInstance().setCurrentState(
                                        MapLoadedState.getInstance()
                                );
                                UIManager.getInstance().endLoadMap();
                            }
                            break;
                    }
                }
        );
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
        return "MapSelectState";
    }
}
