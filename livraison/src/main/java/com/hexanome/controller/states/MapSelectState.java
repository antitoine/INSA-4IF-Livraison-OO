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
        /* \todo Revoir toute cette partie qui doit, normalement, être fait dans la vue !!
        final Service<Void> loadService = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        ModelManager.getInstance().clearModel();
                        ContextManager.getInstance().loadMap((File) arg); // Not undoable
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
                            case SUCCEEDED:
                                ModelManager.getInstance().getMap().addSubscriber(mainWindow.getMapView());
                                mainWindow.SetLoadingDone();
                                break;
                        }
                    }
                });
        loadService.start();
        */
        // \todo (security) check if load map is possible
        if( ! ModelManager.getInstance().initModelMap(IOManager.getInstance().getMapDocument(file)) ) {
            // \todo treat error case
        }
        ContextManager.getInstance().setCurrentState(MapLoadedState.getInstance());
    }

}
