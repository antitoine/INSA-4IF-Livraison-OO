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
import com.hexanome.view.MainWindow;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

/**
 * @author antitoine \todo TODO
 */
public class PlanningSelectState extends DefaultState {

    private static PlanningSelectState planningSelectState = null;

    private PlanningSelectState() {
        // Nothing to do here
    }

    /**
     * Returns the instance of the PlanningSelectState, it is a singleton
     *
     * @return The instance of PlanningSelectState
     */
    public static PlanningSelectState getInstance() {
        if (planningSelectState == null) {
            planningSelectState = new PlanningSelectState();
        }
        UIManager.getInstance().getMainWindow().disableButton(ConstView.Button.COMPUTE_ROUTE);
        UIManager.getInstance().getMainWindow().disableButton(ConstView.Button.ROAD_MAP);
        return planningSelectState;
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCancel()
     */
    @Override
    public void btnCancel() {
        // Jump to MapLoadedState
        ContextManager.getInstance().setCurrentState(MapLoadedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnValidateFile(java.io.File)
     */
    @Override
    public void btnValidateFile(final File file) {

        // update the view
        UIManager.getInstance().beginLoadPlanning();

        /**
         * The task is created in a different thread A listenner listen the
         * result of the thread and executes the right commands if the task
         * succeds
         *
         * Otherwise, all the task listed here will be executed in the UI Thread
         */
        final Service<Void> loadService = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        ModelManager.getInstance().initModelPlanning(IOManager.getInstance().getPlanningDocument(file));
                        return null;
                    }
                };
            }
        };
        loadService.stateProperty()
                .addListener(new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State oldValue,
                            Worker.State newValue) {
                        switch (newValue) {
                            case FAILED:
                                // Clear current model's planning
                                ModelManager.getInstance().clearPlanning();
                                // Jump to MapLoadedState
                                ContextManager.getInstance().setCurrentState(MapLoadedState.getInstance());
                                // Update mainWindow
                                UIManager.getInstance().loadError();
                                break;
                            case CANCELLED:
                                // File selection cancel is not managed here
                                break;
                            case SUCCEEDED:
                                // Jump to PlanningLoadedState
                                ContextManager.getInstance().setCurrentState(PlanningLoadedState.getInstance());
                                // update the view
                                UIManager.getInstance().endLoadPlanning();
                                break;
                        }
                    }
                });
        loadService.start();

    }

    @Override
    public String toString() {
        return "PlanningSelectState"; //To change body of generated methods, choose Tools | Templates.
    }
    
}
