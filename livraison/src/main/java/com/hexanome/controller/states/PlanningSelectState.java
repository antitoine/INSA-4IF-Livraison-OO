/**
 *
 */
package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.IOManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.UIManager;
import com.hexanome.view.ConstView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

import java.io.File;

/**
 * This class represents the logic state when user is selecting a
 * planning to load
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
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
        final Service<String> loadService = new Service<String>() {
            @Override
            protected Task<String> createTask() {
                return new Task<String>() {
                    @Override
                    protected String call() throws Exception {
                        return ModelManager.getInstance()
                                .initModelPlanning(IOManager.getInstance()
                                        .getPlanningDocument(file));
                    }
                };
            }
        };
        loadService.stateProperty()
                .addListener(new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue<? extends Worker.State> observableValue,
                            Worker.State oldValue,
                            Worker.State newValue) {
                        switch (newValue) {
                            case SUCCEEDED:
                                if (loadService.getValue() != null) {
                                    // Clear current model's planning
                                    ModelManager.getInstance().clearPlanning();
                                    // Jump to MapLoadedState
                                    ContextManager.getInstance().setCurrentState(MapLoadedState.getInstance());
                                    // Update mainWindow
                                    UIManager.getInstance().showError(loadService.getValue());
                                    break;
                                } else {
                                    // Jump to PlanningLoadedState
                                    ContextManager.getInstance()
                                            .setCurrentState(PlanningLoadedState.getInstance());
                                    // update the view
                                    UIManager.getInstance().endLoadPlanning();
                                    ContextManager.getInstance().getCurrentState().btnGenerateRoute();
                                    break;
                                }
                        }
                    }
                });
        loadService.start();

    }

    /**
     * Returns the string describing the state, used for debug only
     * @return a string describing the state
     */
    @Override
    public String toString() {
        return "PlanningSelectState"; //To change body of generated methods, choose Tools | Templates.
    }

}
