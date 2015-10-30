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

        if( ! ModelManager.getInstance().initModelPlanning(IOManager.getInstance().getPlanningDocument(file)) ) {
            // \todo treat error case
            ModelManager.getInstance().clearPlanning();
            ContextManager.getInstance().setCurrentState(MapLoadedState.getInstance());
            mainWindow.SetLoadingDone();
        } else {
            ContextManager.getInstance().setCurrentState(PlanningLoadedState.getInstance());
            ModelManager.getInstance().getPlanning().addSubscriber(mainWindow.getDeliveryTree());
            ModelManager.getInstance().getPlanning().addSubscriber(mainWindow.getMapView());
            mainWindow.SetLoadingDone();
        }
    }

}
