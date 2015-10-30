package com.hexanome.controller;

import com.hexanome.view.ConstView;
import com.hexanome.view.DeliveryTreeView;
import com.hexanome.view.MainWindow;
import com.hexanome.view.NodeView;
import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.stage.Stage;

/**
 * This controller manage all the view and is notify when something happens on
 * the view
 *
 * It implements the sigleton design pattern and only one instance should exist
 * when the application is running
 *
 * Should always be use as following UIManager.getInstance(). ...
 */
public class UIManager {

    /**
     * Main Window of the application see MainWindow class for more information
     */
    MainWindow mainWindow;

    private static UIManager uimanager = null;

    private UIManager() {
    }

    /**
     * Return the single of the UIManager
     *
     * @return the UIManager Instance
     */
    public static UIManager getInstance() {
        if (uimanager == null) {
            uimanager = new UIManager();
        }
        return uimanager;
    }

    /**
     * Create the main Window and return it
     *
     * @param stage
     * @return the mainWindow which should be integrated into the scene
     */
    MainWindow createMainWindow(Stage stage) {
        mainWindow = new MainWindow(stage);
        return mainWindow;
    }

    /**
     * Method that allow to notify the UIController
     *
     * @param action Action to be executed
     * @param arg Optional argument
     *
     * Expected @param arg for the following action Load_MAP : File file (valid
     * xml file) Load_PLANNING : File file (valid xml file)
     */
    public void NotifyUI(ConstView.Action action, Object arg) {
        switch (action) {
            case QUIT:
                ContextManager.getInstance().exit(); // Special undoable
                break;
            case ADD_DELIVERY:
                // Create an AddDeliveryCommand and give it to context manager
                break;
            case DELETE_DELIVERY:
                // Create a RemoveDeliveryCommand and give it to context manager
                break;
            case SWAP_DELIVERIES:
                // Create a SwapDeliveryCommand and give it to context manager
                break;
            case LOAD_MAP:
                loadMap(arg);
                break;
            case LOAD_PLANNING:
                loadPlanning(arg);
                break;
            case CLICK_ON_DELIVERY_NODE:
                ((NodeView) (arg)).showPopOver();
                mainWindow.disablePanning();
                break;
            case CLICK_ON_EMPTY_NODE:
                ((NodeView) (arg)).showPopOver();
                mainWindow.disablePanning();
                break;
            case CLICK_ON_WAREHOUSE:
                ((NodeView) (arg)).showPopOver();
                mainWindow.disablePanning();
                break;
            case HIDE_POPOVER:
                mainWindow.ennablePanning();
                break;
        }
    }

    /**
     * Method that allow to notify the UIController
     *
     * @param action action to be executed
     */
    public void NotifyUI(ConstView.Action action) {
        NotifyUI(action, null);
    }

    private void loadMap(final Object arg) {
        mainWindow.SetLoadingState("Loading Map...");
        mainWindow.getMapView().clearMap();
        mainWindow.getDeliveryTree().clearTree();
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
                            case CANCELLED:
                            case SUCCEEDED:
                                ModelManager.getInstance().getMap().addSubscriber(mainWindow.getMapView());
                                mainWindow.SetLoadingDone();
                                break;
                        }
                    }
                });
        loadService.start();

    }

    private void loadPlanning(final Object arg) {
        mainWindow.SetLoadingState("Loading Planning...");

        final Service<Void> loadService = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        mainWindow.getDeliveryTree().clearTree();
                        ModelManager.getInstance().clearModel();
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
                            case CANCELLED:
                            case SUCCEEDED:
                                ModelManager.getInstance().getPlanning().addSubscriber(mainWindow.getDeliveryTree());
                                ModelManager.getInstance().getPlanning().addSubscriber(mainWindow.getMapView());
                                mainWindow.SetLoadingDone();
                                break;
                        }
                    }
                });
        loadService.start();

    }

}
