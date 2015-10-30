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
        System.out.println("btnCancel In MapSelectState");
        ContextManager.getInstance().setCurrentState(InitState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnValidateFile(java.io.File)
     */
    @Override
    public void btnValidateFile(File file) {

        MainWindow mainWindow = UIManager.getInstance().getMainWindow();
        mainWindow.SetLoadingState("Loading Map...");
        mainWindow.getMapView().clearMap();
        mainWindow.getDeliveryTree().clearTree();

        if( ! ModelManager.getInstance().initModelMap(IOManager.getInstance().getMapDocument(file)) ) {
            // \todo afficher dans la vue l'erreur
            ModelManager.getInstance().clearModel();
            ContextManager.getInstance().setCurrentState(InitState.getInstance());
            mainWindow.SetLoadingDone();
        } else {
            ContextManager.getInstance().setCurrentState(MapLoadedState.getInstance());
            ModelManager.getInstance().getMap().addSubscriber(mainWindow.getMapView());
            ModelManager.getInstance().getMap().addSubscriber(mainWindow.getMapView());
            mainWindow.SetLoadingDone();
        }
    }

}
