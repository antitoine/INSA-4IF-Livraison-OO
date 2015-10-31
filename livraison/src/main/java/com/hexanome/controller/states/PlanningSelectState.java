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
        // Jump to MapLoadedState
        ContextManager.getInstance().setCurrentState(MapLoadedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnValidateFile(java.io.File)
     */
    @Override
    public void btnValidateFile(File file) {
        // Retreive mainWindow instance
        MainWindow mainWindow = UIManager.getInstance().getMainWindow();
        // Update mainwindow
        mainWindow.SetLoadingState("Loading Planning...");
        mainWindow.getDeliveryTree().clearTree();
        mainWindow.getMapView().clearDeliveries();

        // If an error occured while loading the planning,
        if( ! ModelManager.getInstance().initModelPlanning(IOManager.getInstance().getPlanningDocument(file)) ) {
            // Clear current model's planning
            ModelManager.getInstance().clearPlanning();
            // Jump to MapLoadedState
            ContextManager.getInstance().setCurrentState(MapLoadedState.getInstance());
            // Update mainwindow
            mainWindow.SetLoadingDone();
            mainWindow.displayError("The file can't be loaded !");
        } else {
            // Jump to PlanningLoadedState
            ContextManager.getInstance().setCurrentState(PlanningLoadedState.getInstance());
            // Add view subscribers to the model
            ModelManager.getInstance().getPlanning().addSubscriber(mainWindow.getDeliveryTree());
            ModelManager.getInstance().getPlanning().addSubscriber(mainWindow.getMapView());
            // Update mainwindow
            mainWindow.SetLoadingDone();
        }
    }

}
