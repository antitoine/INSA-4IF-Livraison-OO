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
public class MapSelectState extends DefaultState {

    private static MapSelectState mapSelectState = null;

    private MapSelectState(){
        // Nothing to do here
    }

    /**
     * Returns the instance of the MapSelectState,
     * it is a singleton
     * @return The instance of MapSelectState
     */
    public static MapSelectState getInstance() {
        if(mapSelectState == null)
        {
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
    public void btnValidateFile(File file) {
        
        // Get mainWindow instance
        MainWindow mainWindow = UIManager.getInstance().getMainWindow();
        // Update mainWindow
        mainWindow.SetLoadingState("Loading Map...");
        mainWindow.getMapView().clearMap();
        mainWindow.getDeliveryTree().clearTree();

        // Model's map initialization failed,  
        if( ! ModelManager.getInstance().initModelMap(IOManager.getInstance().getMapDocument(file)) ) {
            // Full clear of the model
            ModelManager.getInstance().clearModel();
            // Jump to InitState
            ContextManager.getInstance().setCurrentState(InitState.getInstance());
            // Update mainWindow
            mainWindow.SetLoadingDone();
            mainWindow.displayError("The file can't be loaded !");
        } else {
            // Jump to MapLoadedState
            ContextManager.getInstance().setCurrentState(MapLoadedState.getInstance());
            // Add view subscribers to model
            ModelManager.getInstance().getMap().addSubscriber(mainWindow.getMapView());
            ModelManager.getInstance().getMap().addSubscriber(mainWindow.getMapView());
            // Update mainWindow
            mainWindow.SetLoadingDone();
        }
    }

}
