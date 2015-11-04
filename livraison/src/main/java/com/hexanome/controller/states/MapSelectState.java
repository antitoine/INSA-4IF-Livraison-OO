/**
 *
 */
package com.hexanome.controller.states;

import java.io.File;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.IOManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.UIManager;
import com.hexanome.view.ConstView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;

/**
 * @author antitoine \todo TODO
 */
public class MapSelectState extends DefaultState {

    private static MapSelectState mapSelectState = null;

    private MapSelectState() {
        // Nothing to do here
    }

    /**
     * Returns the instance of the MapSelectState, it is a singleton
     *
     * @return The instance of MapSelectState
     */
    public static MapSelectState getInstance() {
        if (mapSelectState == null) {
            mapSelectState = new MapSelectState();
        }
        UIManager.getInstance().getMainWindow().disableButton(ConstView.Button.LOAD_PLANNING);
        UIManager.getInstance().getMainWindow().disableButton(ConstView.Button.COMPUTE_ROUTE);
        UIManager.getInstance().getMainWindow().disableButton(ConstView.Button.ROAD_MAP);
        return mapSelectState;
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCancel()
     */
    @Override
    public void btnCancel() {
        // Jump back to InitState
        ContextManager.getInstance().setCurrentState(InitState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnValidateFile(java.io.File)
     */
    @Override
    public void btnValidateFile(final File file) {

        UIManager.getInstance().beginLoadMap();

        /**
         * The task is created in a different thread A listenner listen the
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
                        return ModelManager.getInstance().initModelMap(IOManager
                                .getInstance().getMapDocument(file));
                    }
                };
            }
        };
        loadService.stateProperty()
                .addListener(new ChangeListener<State>() {
                    @Override
                    public void changed(ObservableValue<? extends State> observableValue,
                            State oldValue, State newValue) {
                        switch (newValue) {
                            case SUCCEEDED:
                                if (loadService.getValue() != null) {
                                    // Full clear of the model
                                    ModelManager.getInstance().clearModel();
                                    // Jump to InitState
                                    ContextManager.getInstance()
                                            .setCurrentState(InitState.getInstance());
                                    // Update mainWindow
                                    UIManager.getInstance().showError(loadService.getValue());
                                } else {
                                    ContextManager.getInstance()
                                            .setCurrentState(MapLoadedState.getInstance());
                                    UIManager.getInstance().endLoadMap();
                                }
                                break;
                        }
                    }
                });
        loadService.start();

    }

    @Override
    public String toString() {
        return "MapSelectState"; //To change body of generated methods, choose Tools | Templates.
    }

}
