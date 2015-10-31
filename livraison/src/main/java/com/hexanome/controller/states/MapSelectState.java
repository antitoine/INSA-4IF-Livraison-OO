/**
 *
 */
package com.hexanome.controller.states;

import java.io.File;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.IOManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.UIManager;
import com.hexanome.view.MainWindow;
import com.sun.javafx.property.adapter.PropertyDescriptor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
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
         * The task is created in a different thread
         * A listenner listen the result of the thread and executes
         * the right commands if the task succeds
         * 
         * Otherwise, all the task listed here will be executed in the UI
         * Thread
         */
        final Service<Void> loadService = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        ModelManager.getInstance().initModelMap(IOManager.getInstance().getMapDocument(file));
                        return null;
                    }
                };
            }
        };
        loadService.stateProperty()
                .addListener(new ChangeListener<State>() {
                    @Override
                    public void changed(ObservableValue<? extends State> observableValue, State oldValue,
                            State newValue) {
                        switch (newValue) {
                            case FAILED:
                                // Full clear of the model
                                ModelManager.getInstance().clearModel();
                                // Jump to InitState
                                ContextManager.getInstance().setCurrentState(InitState.getInstance());
                                // Update mainWindow
                                UIManager.getInstance().loadError();
                                break;
                            case CANCELLED:
                            case SUCCEEDED:
                                ContextManager.getInstance().setCurrentState(MapLoadedState.getInstance());
                                UIManager.getInstance().endLoadMap();
                                break;
                        }
                    }
                });
        loadService.start();

    }

}
