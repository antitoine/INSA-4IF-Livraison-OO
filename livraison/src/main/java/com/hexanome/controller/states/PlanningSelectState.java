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

/**
 * @author antitoine
 * \todo TODO
 */
public class PlanningSelectState extends DefaultState {

    private static PlanningSelectState planningSelectState = null;

    private PlanningSelectState(){
        // Nothing to do here
    }

    /**
     * Returns the instance of the PlanningSelectState,
     * it is a singleton
     * @return The instance of PlanningSelectState
     */
    public static PlanningSelectState getInstance() {
        if(planningSelectState == null)
        {
            planningSelectState = new PlanningSelectState();
        }
        return planningSelectState;
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCancel()
     */
    @Override
    public void btnCancel() {
        ContextManager.getInstance().setCurrentState(MapLoadedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnValidateFile(java.io.File)
     */
    @Override
    public void btnValidateFile(File file) {
        MainWindow mainWindow = UIManager.getInstance().getMainWindow();
        mainWindow.SetLoadingState("Loading Planning...");
        mainWindow.getDeliveryTree().clearTree();
        mainWindow.getMapView().clearDeliveries();
        /* \todo Revoir toute cette partie qui doit, normalement, être fait dans la vue !!
        final Service<Void> loadService = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        ModelManager.getInstance().clearPlanning();
                        ContextManager.getInstance().loadPlanning((File) arg); // Not undoable 
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
                                mainWindow.displayError("Unable to load this file");
                                break;
                            case CANCELLED:
                                mainWindow.SetLoadingDone();
                                break;
                            case SUCCEEDED:
                                ModelManager.getInstance().getPlanning().addSubscriber(mainWindow.getDeliveryTree());
                                ModelManager.getInstance().getPlanning().addSubscriber(mainWindow.getMapView());
                                mainWindow.SetLoadingDone();
                                break;
                        }
                    }
                });
        loadService.start();
        */
        // \todo (security) check if load planning is possible
        if( ! ModelManager.getInstance().initModelPlanning(IOManager.getInstance().getPlanningDocument(file)) ) {
            // \todo treat error case
        }
        // \todo update application state
    }

}
